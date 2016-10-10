package com.ponominalu.nvv.ponominalutest.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.provider.SyncStateContract;

import com.ponominalu.nvv.ponominalutest.R;
import com.ponominalu.nvv.ponominalutest.dao.DaoMaster;
import com.ponominalu.nvv.ponominalutest.dao.DaoSession;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;


public class DaoManager {

    private static Connection mDefaultConnection;


    public static boolean hasDefaultConnection() {
        return mDefaultConnection != null;
    }

    public static void initializeDefaultConnection() {
        if (mDefaultConnection != null) {
            throw new IllegalStateException("The default connection already initialized");
        }
        mDefaultConnection = Connection.open();
    }

    public static void checkDataBase(Context context){
        Constans.setDbPath(Environment.getExternalStorageDirectory().getPath() + context.getString(R.string.str_name_folder));
        File folder = new File(Constans.getDbPath());
        File file = null;
        folder.mkdirs();
        Constans.setDbPath(Constans.getDbPath()+context.getString(R.string.str_name_database));
        file = new File(Constans.getDbPath());
        if(folder.listFiles()!=null && folder.listFiles().length==0){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, file.getAbsolutePath(), null);

    }



    public static class Connection implements Closeable {
        private DaoMaster mDaoMaster;
        private DaoSession mDaoSession;

        private Connection() {
            File file = new File(Constans.getDbPath());
            SQLiteDatabase db = SQLiteDatabase.openDatabase(Constans.getDbPath(), null, SQLiteDatabase.OPEN_READWRITE);
            mDaoMaster = new DaoMaster(db);
        }

        private Connection(Context context, String name) {
            OpenHelper openHelper = new OpenHelper(context, name, null);
            mDaoMaster = new DaoMaster(openHelper.getWritableDatabase());
        }

        public static Connection open() {
            return new Connection();
        }

        public static Connection open(Context context, String name) {
            return new Connection(context, name);
        }

        public void close() {
            mDaoMaster.getDatabase().close();
            mDaoSession = null;
            mDaoMaster = null;
        }

        public DaoSession getSession() {
            if (mDaoSession == null)
                mDaoSession = mDaoMaster.newSession();
            return mDaoSession;
        }

        public DaoSession newSession() {
            return mDaoMaster.newSession();
        }
    }

    public interface SessionRunnable {
        void onDaoSession(DaoSession session);
    }
    public interface SessionCallable<T> {
        T callDaoSession(DaoSession session);
    }

    /* Opens db connection, invokes consumer's action and then closes the connection */
    public static void dbRoundTrip(Context context, SessionRunnable runnable) {
        dbRoundTrip(context, Constans.getDbPath(), runnable);
    }

    /* Opens db connection, invokes consumer's action and then closes the connection */
    public static void dbRoundTrip(Context context, String name, SessionRunnable runnable) {
        Connection connection = Connection.open(context, name);
        try {
            runnable.onDaoSession(connection.getSession());
        } finally {
            connection.close();
        }
    }

    /* Opens db connection, invokes consumer's action and then closes the connection */
    public static <T> T dbRoundTrip(Context context, SessionCallable<T> callable) {
        return dbRoundTrip(context, Constans.getDbPath(), callable);
    }
    /* Opens db connection, invokes consumer's action and then closes the connection */
    public static <T> T dbRoundTrip(Context context, String name, SessionCallable<T> callable) {
        Connection connection = Connection.open(context, name);
        try {
            return callable.callDaoSession(connection.getSession());
        } finally {
            connection.close();
        }
    }


    private static class OpenHelper extends DaoMaster.OpenHelper {
        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }


}
