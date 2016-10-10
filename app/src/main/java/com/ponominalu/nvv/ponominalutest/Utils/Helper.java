package com.ponominalu.nvv.ponominalutest.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Environment;

import com.ponominalu.nvv.ponominalutest.R;

import java.io.File;


public class Helper {

    public static boolean hasNetworkConnection(Context context){
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static Bitmap loadImageFromStorage(Context context, String fileName){
        return BitmapFactory.decodeFile(new File(Environment.getExternalStorageDirectory().getPath() + context.getString(R.string.str_name_folder) + fileName).getAbsolutePath());
    }
}
