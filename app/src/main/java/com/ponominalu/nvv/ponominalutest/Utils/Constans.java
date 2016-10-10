package com.ponominalu.nvv.ponominalutest.Utils;

import android.os.Environment;

import java.io.File;
import java.io.IOException;


public class Constans {

    private static String DB_PATH = "";

    public static String getDbPath() {
        return DB_PATH;
    }

    public static void setDbPath(String dbPath) {
        DB_PATH = dbPath;
    }
}
