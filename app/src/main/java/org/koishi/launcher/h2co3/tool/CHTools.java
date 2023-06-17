package org.koishi.launcher.h2co3.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class CHTools {

    public static String LAUNCHER_FILE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/games/org.koishi.launcher/h2co3/";
    @SuppressLint("SdCardPath")
    public static String LAUNCHER_DATA_DIR = "/data/data/org.koishi.launcher.h2co3/";
    public static String boatCfg = LAUNCHER_FILE_DIR + "config.txt";
    public static String h2co3Cfg = LAUNCHER_FILE_DIR + "h2co3cfg.json";
    ZipListener zipListener;
    @SuppressLint("HandlerLeak")
    private final Handler zipHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                zipListener.onStart();
            } else if (msg.what == 1) {
                zipListener.onProgress((int) msg.obj);
            } else if (msg.what == 2) {
                zipListener.onFinish();
            } else if (msg.what == 3) {
                zipListener.onError((String) msg.obj);
            }
        }
    };
    int lastProgress = 0;

    //---------------------获取json的值---------------------

    public CHTools(ZipListener zipListener) {
        this.zipListener = zipListener;
    }

    public static void setBoatJson(String name, String value) {
        try {
            FileInputStream in = new FileInputStream(boatCfg);
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();
            String str = new String(b);
            JSONObject json = new JSONObject(str);
            json.remove(name);
            json.put(name, value);
            FileWriter fr = new FileWriter(boatCfg);
            fr.write(json.toString());
            fr.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void setAppJson(String name, String value) {
        try {
            FileInputStream in = new FileInputStream(h2co3Cfg);
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();
            String str = new String(b);
            JSONObject json = new JSONObject(str);
            json.remove(name);
            json.put(name, value);
            FileWriter fr = new FileWriter(h2co3Cfg);
            fr.write(json.toString());
            fr.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void setExtraJson(String name, String value, String dir) {
        try {
            FileInputStream in = new FileInputStream(dir);
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();
            String str = new String(b);
            JSONObject json = new JSONObject(str);
            json.remove(name);
            json.put(name, value);
            FileWriter fr = new FileWriter(dir);
            fr.write(json.toString());
            fr.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //h2co3cfg
    public static String getBoatCfg(String name, String defaultValue) {
        try {
            FileInputStream in = new FileInputStream(boatCfg);
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();
            String str = new String(b);
            JSONObject json = new JSONObject(str);
            return json.getString(name);
        } catch (Exception e) {
            System.out.println(e);
        }
        return defaultValue;
    }

    public static String getAppCfg(String name, String defaultValue) {
        try {
            FileInputStream in = new FileInputStream(h2co3Cfg);
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();
            String str = new String(b);
            JSONObject json = new JSONObject(str);
            return json.getString(name);
        } catch (Exception e) {
            System.out.println(e);
        }
        return defaultValue;
    }

    public static String getExtraCfg(String value, String defaultValue, String dir) {
        try {
            FileInputStream in = new FileInputStream(dir);
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();
            String str = new String(b);
            JSONObject json = new JSONObject(str);
            return json.getString(value);
        } catch (Exception e) {
            System.out.println(e);
        }
        return defaultValue;
    }

    public static String getInsideString(String str, String strStart, String strEnd) {
        if (!str.contains(strStart)) {
            return "";
        }
        if (!str.contains(strEnd)) {
            return "";
        }
        return str.substring(str.indexOf(strStart) + strStart.length(), str.indexOf(strEnd));
    }

    public static String getPictureData(URL path) throws Exception {
        // 类 URL 代表一个统一资源定位符，它是指向互联网“资源”的指针。
        // 每个 HttpURLConnection 实例都可用于生成单个请求，
        //但是其他实例可以透明地共享连接到 HTTP 服务器的基础网络
        HttpURLConnection conn = (HttpURLConnection) path.openConnection();
        //设置 URL 请求的方法
        conn.setRequestMethod("GET");
        //设置一个指定的超时值（以毫秒为单位），
        //该值将在打开到此 URLConnectio  DO引用的资源的通信链接时使用。
        conn.setConnectTimeout(2 * 1000);
        // conn.getInputStream()返回从此打开的连接读取的输入流
        InputStream inStream = conn.getInputStream();// 通过输入流获取html数据
        byte[] data = readInputStream(inStream);// 得到html的二进制数据
        return new String(data);

    }

    //读取输入流中的数据，返回字节数组byte[]
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        //此类实现了一个输出流，其中的数据被写入一个 byte 数组
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 字节数组
        byte[] buffer = new byte[1024];
        int len;
        //从输入流中读取一定数量的字节，并将其存储在缓冲区数组buffer 中
        while ((len = inStream.read(buffer)) != -1) {
            // 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此输出流
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        //toByteArray()创建一个新分配的 byte 数组。
        return outStream.toByteArray();
    }

    public static String getAppVersionName(Context context) {

        String versionName = "";
        try {

            PackageManager pm = context.getPackageManager();

            PackageInfo p1 = pm.getPackageInfo(context.getPackageName(), 0);

            versionName = p1.versionName;

            if (TextUtils.isEmpty(versionName) || versionName.length() == 0) {

                return "";

            }

        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();

        }

        return versionName;

    }

    public static String getAppLicatiom(Context context) {

        String versionName = "";
        try {

            PackageManager pm = context.getPackageManager();

            PackageInfo p1 = pm.getPackageInfo(context.getPackageName(), 0);

            versionName = p1.packageName;

            if (TextUtils.isEmpty(versionName) || versionName.length() == 0) {

                return "";

            }

        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();

        }

        return versionName;

    }

    public static boolean moveFile(String src, String dest) {
        File fs = new File(src);
        if (!fs.exists()) {
            return false;
        }
        if (fs.isDirectory()) {
            File fss = new File(dest, fs.getName());
            fss.mkdir();
            for (File f : fs.listFiles()) {
                moveFile(f.getAbsolutePath(), dest + fss.getName());
            }
        } else {
            fs.renameTo(new File(dest, fs.getName()));
            return true;
        }
        fs.renameTo(new File(dest, fs.getName()));
        return true;
    }

    public static boolean unzip(String srcPath, String destPath) throws Exception {
        if (!new File(srcPath).exists()) {
            return false;
        }
        FileInputStream in = new FileInputStream(srcPath);
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry ze;
        long zipCurrentLen = 0;
        long zipLen = getZipFileSize(srcPath);
        while ((ze = zin.getNextEntry()) != null) {
            if (ze.isDirectory()) {
                new File(destPath, ze.getName()).mkdirs();
            } else {
                File f = new File(destPath, ze.getName());
                f.createNewFile();
                FileOutputStream out = new FileOutputStream(f);
                int len;
                byte[] buf = new byte[2048];
                while ((len = zin.read(buf)) != -1) {
                    zipCurrentLen += len;
                    System.out.println((zipCurrentLen * 100 / zipLen) + "%");
                    out.write(buf, 0, len);
                    out.flush();
                }
                out.close();
            }
        }
        zin.closeEntry();
        zin.close();
        in.close();
        return true;
    }

    private static long getZipFileSize(String filePath) {
        long size = 0;
        ZipFile f;
        try {
            f = new ZipFile(filePath);
            Enumeration<? extends ZipEntry> en = f.entries();
            while (en.hasMoreElements()) {
                size += en.nextElement().getSize();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    public static boolean writeTxt(String str, String filePath) {
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter(filePath));
            bfw.write(str);
            bfw.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static String readTxt(String filePath) {
        String str = "";
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(filePath));
            String temp;
            while ((temp = bfr.readLine()) != null) {
                str += temp + "\n";
            }
        } catch (IOException e) {
            str += "错误：" + e;
        }
        return str;
    }

    public static String getStoragePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static String getExternalFilesDir(Context context) {
        return context.getExternalFilesDir(null).getParentFile().getAbsolutePath();
    }

    public static void copyFromAssets(Context context, String source,
                                      String destination) throws IOException {
        /**
         * 获取assets目录下文件的输入流
         *
         * 1、获取AssetsManager : 调用 Context 上下文对象的 context.getAssets() 即可获取 AssetsManager对象;
         * 2、获取输入流 : 调用 AssetsManager 的 open(String fileName) 即可获取对应文件名的输入流;
         */
        InputStream is = context.getAssets().open(source);
        // 获取文件大小
        int size = is.available();
        // 创建文件的缓冲区
        byte[] buffer = new byte[size];
        // 将文件读取到缓冲区中
        is.read(buffer);
        // 关闭输入流
        is.close();
        /*
         *  打开app安装目录文件的输出流
         *  Context.MODE_PRIVATE  设置该文件只能被本应用使用，为私有数据
         */
        FileOutputStream output = context.openFileOutput(destination,
                Context.MODE_PRIVATE);
        // 将文件从缓冲区中写出到内存中
        output.write(buffer);
        //关闭输出流
        output.close();
    }

    public static boolean deleteFile(String src) {
        File fs = new File(src);
        if (!fs.exists()) {
            return false;
        }
        if (fs.isDirectory()) {
            for (File f : fs.listFiles()) {
                deleteFile(f.getAbsolutePath());
            }
        } else {
            fs.delete();
            return true;
        }
        fs.delete();
        return true;
    }

    public static boolean dirCopy(String srcPath, String destPath) {
        File src = new File(srcPath);
        if (!new File(destPath).exists()) {
            new File(destPath).mkdirs();
        }
        for (File s : src.listFiles()) {
            if (s.isFile()) {
                fileCopy(s.getPath(), destPath + File.separator + s.getName());
            } else {
                dirCopy(s.getPath(), destPath + File.separator + s.getName());
            }
        }
        return true;
    }

    public static boolean fileCopy(String srcPath, String destPath) {
        File src = new File(srcPath);
        File dest = new File(destPath);
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(src));
            OutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
            byte[] flush = new byte[2 * 1024];
            int len = -1;
            while ((len = is.read(flush)) != -1) {
                out.write(flush, 0, len);
            }
            out.flush();
            out.close();
            is.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void unzipWithProgress(final String srcPath, final String destPath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    zipHandler.sendEmptyMessage(0);
                    if (!new File(srcPath).exists()) {
                        zipListener.onError("文件不存在");
                        return;
                    }
                    FileInputStream in = new FileInputStream(srcPath);
                    ZipInputStream zin = new ZipInputStream(in);
                    ZipEntry ze;
                    long zipCurrentLen = 0;
                    long zipLen = getZipFileSize(srcPath);
                    while ((ze = zin.getNextEntry()) != null) {
                        if (ze.isDirectory()) {
                            new File(destPath, ze.getName()).mkdirs();
                        } else {
                            File f = new File(destPath, ze.getName());
                            f.createNewFile();
                            FileOutputStream out = new FileOutputStream(f);
                            int len;
                            byte[] buf = new byte[2048];
                            while ((len = zin.read(buf)) != -1) {
                                zipCurrentLen += len;
                                updateProgress((int) (zipCurrentLen * 100 / zipLen));
                                out.write(buf, 0, len);
                                out.flush();
                            }
                            out.close();
                        }
                    }
                    zin.closeEntry();
                    zin.close();
                    in.close();
                    zipHandler.sendEmptyMessage(2);
                } catch (IOException e) {
                    Message msg = new Message();
                    msg.what = 3;
                    msg.obj = e.toString();
                    zipHandler.sendMessage(msg);
                }
            }
        }).start();

    }

    private void updateProgress(int progress) {
        if (progress > lastProgress) {
            lastProgress = progress;
            Message msg = new Message();
            msg.what = 1;
            msg.obj = progress;
            zipHandler.sendMessage(msg);
        }
    }

    public interface ZipListener {
        void onStart();

        void onProgress(int progress);

        void onFinish();

        void onError(String err);
    }

}
