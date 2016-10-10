package com.ponominalu.nvv.ponominalutest.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ponominalu.nvv.ponominalutest.R;
import com.ponominalu.nvv.ponominalutest.Utils.DaoManager;


public class LogoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((ImageView)findViewById(R.id.id_logo_image)).setImageDrawable(getDrawable(R.drawable.logo_tv_2015));
        }else{
            ((ImageView)findViewById(R.id.id_logo_image)).setBackgroundDrawable(getResources().getDrawable(R.drawable.logo_tv_2015));
        }

        DaoManager.checkDataBase(this);

        //heandler for start new actity at 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(LogoActivity.this, CategoriesActivity.class);
                LogoActivity.this.startActivity(mainIntent);
                LogoActivity.this.finish();
            }
        }, 3000);

        RelativeLayout mainLayout =  (RelativeLayout) findViewById(R.id.id_main_logo);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity();
            }
        });

    }

    private void startNewActivity(){
        startActivity(new Intent(this, CategoriesActivity.class));
    }
}
