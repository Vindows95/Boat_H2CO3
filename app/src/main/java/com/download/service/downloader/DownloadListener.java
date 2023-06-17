package com.download.service.downloader;

/**
 * 下载监听
 *
 * @author Cheny
 */
public interface DownloadListener {

    void onFinished();
    void onProgress(float progress, long currentSize, long totalSize);
    void onPause();
    void onCancel();

}