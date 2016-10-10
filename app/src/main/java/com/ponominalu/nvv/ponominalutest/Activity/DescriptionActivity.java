package com.ponominalu.nvv.ponominalutest.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ponominalu.nvv.ponominalutest.Entity.Description;
import com.ponominalu.nvv.ponominalutest.Entity.DescriptionResponse;
import com.ponominalu.nvv.ponominalutest.Interfaces.IPonominalu;
import com.ponominalu.nvv.ponominalutest.R;
import com.ponominalu.nvv.ponominalutest.Utils.DaoManager;
import com.ponominalu.nvv.ponominalutest.Utils.Dialogs;
import com.ponominalu.nvv.ponominalutest.Utils.Helper;
import com.ponominalu.nvv.ponominalutest.dao.Category;
import com.ponominalu.nvv.ponominalutest.dao.DaoSession;
import com.ponominalu.nvv.ponominalutest.dao.Event;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DescriptionActivity extends Activity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        mContext = this;

        Long eventId = getIntent().getLongExtra(getString(R.string.str_extra_eventId), 0);
        getDescritpion(eventId);
    }

    private void getDescritpion(final Long eventId){
        final ProgressDialog progressDialog = Dialogs.progressDialog(this);
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.str_url_main))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IPonominalu service = retrofit.create(IPonominalu.class);

        Call<Description> repos = service.eventDescription(eventId);
        repos.enqueue(new Callback<Description>() {
            @Override
            public void onResponse(Call<Description> call, Response<Description> response) {
                Event event = saveDescritpion(response.body(), eventId);
                fillFields(event);
                showLabels();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Description> call, Throwable t) {
                Event event = getDescritpionFromDB(eventId);
                fillFields(event);
                showLabels();
                progressDialog.dismiss();
                Toast.makeText(mContext, getString(R.string.str_network_error) + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fillFields(Event event){
        ((ImageView) findViewById(R.id.id_event_iamge_description)).setImageBitmap(Helper.loadImageFromStorage(mContext, event.getSubevents().get(0).getImage_name()));
        ((TextView) findViewById(R.id.id_date_info)).setText(event.getSubevents().get(0).getDate().split("T")[0]);
        ((TextView) findViewById(R.id.id_time_info)).setText(event.getSubevents().get(0).getDate().split("T")[1]);
        ((TextView) findViewById(R.id.id_description_info)).setText(event.getDescription());
    }

    private void showLabels(){
        ((TextView)findViewById(R.id.id_date_label)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.id_time_label)).setVisibility(View.VISIBLE);
        ((TextView)findViewById(R.id.id_description_label)).setVisibility(View.VISIBLE);
    }

    private Event saveDescritpion(final Description description, final Long eventId){
        Event event = DaoManager.dbRoundTrip(mContext, new DaoManager.SessionCallable<Event>() {
            @Override
            public Event callDaoSession(DaoSession session) {
                Event event = session.getEventDao().load(eventId);
                event.setDescription(description.getMessage());
                session.getEventDao().update(event);
                synchronized(session){
                    session.notify();
                }
                return event;
            }
        });
        return event;
    }

    private Event getDescritpionFromDB(final Long eventId){
        return DaoManager.dbRoundTrip(mContext, new DaoManager.SessionCallable<Event>() {
            @Override
            public Event callDaoSession(DaoSession session) {
                return session.getEventDao().load(eventId);
            }
        });
    }

}
