package com.ponominalu.nvv.ponominalutest.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ponominalu.nvv.ponominalutest.Adapters.EasyArrayAdapter;
import com.ponominalu.nvv.ponominalutest.Adapters.ViewBinder;
import com.ponominalu.nvv.ponominalutest.Adapters.ViewHolder;
import com.ponominalu.nvv.ponominalutest.Entity.CategoryResponse;
import com.ponominalu.nvv.ponominalutest.Interfaces.IPonominalu;
import com.ponominalu.nvv.ponominalutest.R;
import com.ponominalu.nvv.ponominalutest.Utils.DaoManager;
import com.ponominalu.nvv.ponominalutest.Utils.Dialogs;
import com.ponominalu.nvv.ponominalutest.Utils.Helper;
import com.ponominalu.nvv.ponominalutest.dao.Category;
import com.ponominalu.nvv.ponominalutest.dao.DaoSession;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CategoriesActivity extends Activity{

    private Context mContext;
    private CategoryResponse myresponse = null;
    private EasyArrayAdapter<Category> easyArrayAdapter;
    private boolean isNeedSave = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        mContext = this;
        ListView list = (ListView)findViewById(R.id.id_categories_list);
        if(Helper.hasNetworkConnection(this)){
            isNeedSave = true;
            final CategoryResponse categories = getCategories();
        }else{
            isNeedSave = false;
            getCategoriesFromDB();
        }

        easyArrayAdapter = new EasyArrayAdapter<Category>(this,R.layout.list_item_categories, myresponse.getMessage()){
          protected void bindView(ViewHolder holder, ViewBinder binder, Category item, int position) {
              binder.setText(R.id.id_category_name, item.getTitle());
              binder.setText(R.id.id_category_count, item.getEvents_count().toString());
              if(isNeedSave)
                saveCategories(item);
          }
        };
        list.setAdapter(easyArrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(mContext, EventsActivity.class);
                intent.putExtra(getString(R.string.str_extra_categoryId),myresponse.getMessage().get(position).getId());
                startActivity(intent);
            }
        });
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        Dialogs.exitDialog(this);
    }

    private CategoryResponse getCategories(){
        final ProgressDialog progressDialog = Dialogs.progressDialog(this);
        progressDialog.show();
        myresponse = new CategoryResponse();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.str_url_main))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IPonominalu service = retrofit.create(IPonominalu.class);

        Call<CategoryResponse> repos = service.listCategories();

        repos.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                myresponse = response.body();
                updateAdapter();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                getCategoriesFromDB();
                updateAdapter();
                progressDialog.dismiss();
                Toast.makeText(mContext, getString(R.string.str_network_error), Toast.LENGTH_LONG).show();
            }
        });
        return myresponse;
    }

    private void updateAdapter(){
        easyArrayAdapter.clear();
        easyArrayAdapter.addAll(myresponse.getMessage());
        easyArrayAdapter.notifyDataSetChanged();
    }


    private void saveCategories(final Category category){
        DaoManager.dbRoundTrip(this, new DaoManager.SessionCallable<String>() {

            @Override
            public String callDaoSession(DaoSession session) {
                session.getCategoryDao().insertOrReplace(category);
                return null;
            }
        });
    }

    private void getCategoriesFromDB(){
        myresponse = new CategoryResponse();
        DaoManager.dbRoundTrip(this, new DaoManager.SessionCallable<Object>() {
            @Override
            public Object callDaoSession(DaoSession session) {
                myresponse.setMessage(session.getCategoryDao().loadAll());
                return null;
            }
        });
    }
}
