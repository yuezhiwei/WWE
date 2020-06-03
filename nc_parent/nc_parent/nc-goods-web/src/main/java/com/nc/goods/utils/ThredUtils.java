package com.nc.goods.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThredUtils {
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(10);

    public static void synCreateHtml(Runnable runnable){
        EXECUTOR_SERVICE.submit(runnable);
    }
}
