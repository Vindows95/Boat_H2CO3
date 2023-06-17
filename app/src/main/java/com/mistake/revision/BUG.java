package com.mistake.revision;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class BUG extends Application implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity p1, Bundle p2) {
        // TODO: Implement this method

    }

    @Override
    public void onActivityStarted(Activity p1) {
        // TODO: Implement this method
    }

    @Override
    public void onActivityResumed(Activity p1) {
        // TODO: Implement this method

    }

    @Override
    public void onActivityPaused(Activity p1) {
        // TODO: Implement this method

    }

    @Override
    public void onActivityStopped(Activity p1) {
        // TODO: Implement this method
    }

    @Override
    public void onActivitySaveInstanceState(Activity p1, Bundle p2) {
        // TODO: Implement this method
    }

    @Override
    public void onActivityDestroyed(Activity p1) {
        // TODO: Implement this method
    }


    @Override
    public void onCreate() {

        super.onCreate();


        this.registerActivityLifecycleCallbacks(this);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            //设置默认的未捕获的异常处理程序
            @Override
            public void uncaughtException(@NonNull Thread p1, @NonNull Throwable p2) {


                Writer i = new StringWriter();
                p2.printStackTrace(new PrintWriter(i));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                p2.printStackTrace(new PrintStream(baos));
                byte[] bug = baos.toByteArray();

                try {
                    FileOutputStream f = new FileOutputStream("/sdcard/boat/error.log");

                    //f.write(i.toString().getBytes());


                    f.write(bug);


                    f.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Utils.writeFile("/sdcard/boat/err.log",i.toString());
                // TODO: Implement this method
            }
        });

        //instance = this;
        // 在设置文件名参数时，不要带 “.xml” 后缀，android会自动添加
        //SpUtils.getInstance(this,"setting");
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);


    }
}
