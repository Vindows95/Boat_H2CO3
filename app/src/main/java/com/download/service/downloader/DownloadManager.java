package com.download.service.downloader;

import android.text.TextUtils;

/**
 * 下载管理器，断点续传
 *
 * @author Cheny
 */
public class DownloadManager {

    private DownloadTask mDownloadTask1;
    private DownloadTask mDownloadTask2;
    private DownloadTask mDownloadTask3;
    private DownloadTask mDownloadTask4;
    private FilePoint mfilePoint1;
    private FilePoint mfilePoint2;
    private FilePoint mfilePoint3;
    private FilePoint mfilePoint4;

    private static final String TAG = "DownloadManager";

    private DownloadManager() { }

    private static final class InstanceHolder {
        private static final DownloadManager INSTANCE = new DownloadManager();
    }

    public static DownloadManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public boolean get1() {
        return mDownloadTask1 != null && mDownloadTask1.isDownloading();
    }

    public boolean get2() {
        return mDownloadTask2 != null && mDownloadTask2.isDownloading();
    }

    public boolean get3() {
        return mDownloadTask3 != null && mDownloadTask3.isDownloading();
    }

    public boolean get4() {
        return mDownloadTask4 != null && mDownloadTask4.isDownloading();
    }

    public boolean download1_stop_start(){
        if (mDownloadTask1 != null) {
            if (!mDownloadTask1.isDownloading()) {
                mDownloadTask1.start();
                return true;
            } else {
                mDownloadTask1.pause();
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean download2_stop_start(){
        if (mDownloadTask2 != null) {
            if (!mDownloadTask2.isDownloading()) {
                mDownloadTask2.start();
                return true;
            } else {
                mDownloadTask2.pause();
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean download3_stop_start(){
        if (mDownloadTask3 != null) {
            if (!mDownloadTask3.isDownloading()) {
                mDownloadTask3.start();
                return true;
            } else {
                mDownloadTask3.pause();
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean download4_stop_start(){
        if (mDownloadTask4 != null) {
            if (!mDownloadTask4.isDownloading()) {
                mDownloadTask4.start();
                return true;
            } else {
                mDownloadTask4.pause();
                return false;
            }
        } else {
            return false;
        }
    }

    public String getFileName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public void add1(String url, String filePath, String fileName, int a, Object object, int size, DownloadListener listener) {
        if (TextUtils.isEmpty(fileName)) {
            fileName = getFileName(url);
        }
        mfilePoint1 = new FilePoint(url, filePath, fileName, a, object, size);
        mDownloadTask1 = new DownloadTask(mfilePoint1, listener);
    }

    public void add2(String url, String filePath, String fileName, int a, Object object, int size, DownloadListener listener) {
        if (TextUtils.isEmpty(fileName)) {
            fileName = getFileName(url);
        }
        mfilePoint2 = new FilePoint(url, filePath, fileName, a, object, size);
        mDownloadTask2 = new DownloadTask(mfilePoint2, listener);
    }

    public void add3(String url, String filePath, String fileName, int a, Object object, int size, DownloadListener listener) {
        if (TextUtils.isEmpty(fileName)) {
            fileName = getFileName(url);
        }
        mfilePoint3 = new FilePoint(url, filePath, fileName, a, object, size);
        mDownloadTask3 = new DownloadTask(mfilePoint3, listener);
    }

    public void add4(String url, String filePath, String fileName, int a, Object object, int size, DownloadListener listener) {
        if (TextUtils.isEmpty(fileName)) {
            fileName = getFileName(url);
        }
        mfilePoint4 = new FilePoint(url, filePath, fileName, a, object, size);
        mDownloadTask4 = new DownloadTask(mfilePoint4, listener);
    }

    public FilePoint selfDestruction1() {
        if (mDownloadTask1 != null) {
            mDownloadTask1.cancel();
            return mfilePoint1;
        } else {
            return null;
        }
    }

    public FilePoint selfDestruction2() {
        if (mDownloadTask2 != null) {
            mDownloadTask2.cancel();
            return mfilePoint2;
        } else {
            return null;
        }
    }

    public FilePoint selfDestruction3() {
        if (mDownloadTask3 != null) {
            mDownloadTask3.cancel();
            return mfilePoint3;
        } else {
            return null;
        }
    }

    public FilePoint selfDestruction4() {
        if (mDownloadTask4 != null) {
            mDownloadTask4.cancel();
            return mfilePoint4;
        } else {
            return null;
        }
    }

    public void selfDestructionAll() {
        if (mDownloadTask1 != null) {
            mDownloadTask1.cancel();
        }
        if (mDownloadTask2 != null) {
            mDownloadTask2.cancel();
        }
        if (mDownloadTask3 != null) {
            mDownloadTask3.cancel();
        }
        if (mDownloadTask4 != null) {
            mDownloadTask4.cancel();
        }
    }

}