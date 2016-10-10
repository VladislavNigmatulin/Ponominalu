package com.ponominalu.nvv.ponominalutest.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.ponominalu.nvv.ponominalutest.R;


public class Dialogs {

    public static void exitDialog(final Activity activity){
        new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(activity.getString(R.string.str_alertdialog_title))
                .setMessage(activity.getString(R.string.str_alerdialog_message))
                .setPositiveButton(activity.getString(R.string.str_btn_yes), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }

                })
                .setNegativeButton(activity.getString(R.string.str_btn_no), null)
                .show();
    }

    public static ProgressDialog progressDialog(Activity activity){
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(activity.getString(R.string.str_progressdialog_message));

        return progressDialog;
    }
}
