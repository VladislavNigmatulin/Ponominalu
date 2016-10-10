package com.ponominalu.nvv.ponominalutest.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ponominalu.nvv.ponominalutest.Adapters.EasyArrayAdapter;
import com.ponominalu.nvv.ponominalutest.Adapters.ViewBinder;
import com.ponominalu.nvv.ponominalutest.Adapters.ViewHolder;
import com.ponominalu.nvv.ponominalutest.Entity.EventResponse;
import com.ponominalu.nvv.ponominalutest.Interfaces.IPonominalu;
import com.ponominalu.nvv.ponominalutest.R;
import com.ponominalu.nvv.ponominalutest.Utils.DaoManager;
import com.ponominalu.nvv.ponominalutest.Utils.Dialogs;
import com.ponominalu.nvv.ponominalutest.Utils.Helper;
import com.ponominalu.nvv.ponominalutest.dao.DaoSession;
import com.ponominalu.nvv.ponominalutest.dao.Event;
import com.ponominalu.nvv.ponominalutest.dao.EventDao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EventsActivity extends Activity {

    private Context mContext;
    private EasyArrayAdapter<Event> eventEasyArrayAdapter;
    private EventResponse myresponse = null;
    private boolean isNeedSave = false;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        ListView list = (ListView) findViewById(R.id.id_event_list);
        mContext = this;
        final Long categoryId = getIntent().getLongExtra(getString(R.string.str_extra_categoryId), 0);
        if(Helper.hasNetworkConnection(this)){
            isNeedSave = true;
            getEvents(categoryId);
        }else{
            isNeedSave = false;
            getEventsFromDB(categoryId);
        }
        eventEasyArrayAdapter = new EasyArrayAdapter<Event>(this,R.layout.list_item_event, myresponse.getMessage()){
            protected void bindView(ViewHolder holder, ViewBinder binder, Event item, int position) {
                if(item.getSubevents().get(0).getImage()!=null) {
                    binder.setImageBitmap(R.id.id_event_image, Helper.loadImageFromStorage(mContext, item.getSubevents().get(0).getImage_name()));
                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        binder.setImageDrawable(R.id.id_event_image, getDrawable(R.drawable.logo_tv_2015));
                    }else{
                        binder.setImageDrawable(R.id.id_event_image, getResources().getDrawable(R.drawable.logo_tv_2015));
                    }

                }
                binder.setText(R.id.id_event_title, item.getTitle());
                binder.setText(R.id.id_event_subtitle, item.getSubevents().get(0).getDate().toString().replace("T", " "));
                if(isNeedSave) {
                    item.setCategory_id(categoryId);
                    saveEvents(item);
                }
            }
        };

        list.setAdapter(eventEasyArrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(mContext, DescriptionActivity.class);
                intent.putExtra(getString(R.string.str_extra_eventId), myresponse.getMessage().get(position).getId());
                startActivity(intent);
            }
        });
    }


    private void getEvents(final Long categoryId){
        myresponse = new EventResponse();
        progressDialog = Dialogs.progressDialog(this);
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.str_url_main))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IPonominalu service = retrofit.create(IPonominalu.class);

        Call<EventResponse> repos = service.listEvents(categoryId);

        repos.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                myresponse = response.body();
                getEventImages(categoryId);
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                getEventsFromDB(categoryId);
                updateAdapter();
                progressDialog.dismiss();
                Toast.makeText(mContext, getString(R.string.str_network_error) + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void getEventImages(final Long categoryId){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mContext.getString(R.string.str_url_image))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        IPonominalu service = retrofit.create(IPonominalu.class);

        for(final Event item: myresponse.getMessage()){
            Call<ResponseBody> imagesSyncCall = service.eventImage(item.getSubevents().get(0).getImage_name());
            imagesSyncCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    DownloadImage(response.body(), item.getSubevents().get(0).getImage_name());
                    item.getSubevents().get(0).setImage(new byte[0]);
                    updateAdapter();
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    getEventsFromDB(categoryId);
                    updateAdapter();
                    progressDialog.dismiss();
                    Toast.makeText(mContext, getString(R.string.str_network_error) + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void updateAdapter(){
        eventEasyArrayAdapter.clear();
        eventEasyArrayAdapter.addAll(myresponse.getMessage());
        eventEasyArrayAdapter.notifyDataSetChanged();
    }


    private boolean DownloadImage(ResponseBody body, String name) {
        try {
            InputStream in = null;
            FileOutputStream out = null;
            try {
                in = body.byteStream();
                out = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + getString(R.string.str_name_folder) + name);
                int c;
                while ((c = in.read()) != -1) {
                    out.write(c);
                }
            }
            catch (IOException e) {
                return false;
            }
            finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void saveEvents(final Event event){
        DaoManager.dbRoundTrip(this, new DaoManager.SessionCallable<String>(){
            @Override
            public String callDaoSession(DaoSession session) {
                session.getEventDao().insertOrReplace(event);
                return null;
            }
        });
    }

    private void getEventsFromDB(final Long category_id){
        myresponse = new EventResponse();
        DaoManager.dbRoundTrip(this, new DaoManager.SessionCallable<Object>() {
            @Override
            public Object callDaoSession(DaoSession session) {
                myresponse.setMessage(session.getEventDao().queryBuilder().where(EventDao.Properties.Category_id.eq(category_id)).list());
                return null;
            }
        });
    }
}
