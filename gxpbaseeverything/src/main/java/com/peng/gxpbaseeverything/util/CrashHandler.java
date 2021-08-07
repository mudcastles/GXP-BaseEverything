package com.peng.gxpbaseeverything.util;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 应用异常处理类
 * 使用方法：在Application中进行初始化：CrashHandler.getINSTANCE().init(this);
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    public static final boolean DEBUG = true;
    /**
     * 文件名
     */
    public static final String FILE_NAME = "crash_";
    /**
     * 异常日志 存储位置为根目录下的 Crash文件夹
     */
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() +
            "/Crash/log/";
    /**
     * 文件名后缀
     */
    private static final String FILE_NAME_SUFFIX = ".log";

    private static CrashHandler sInstance = new CrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;


    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        return sInstance;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        //得到系统的应用异常处理器
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        //将当前应用异常处理器改为默认的
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }


    /**
     * 这个是最关键的函数，当系统中有未被捕获的异常，系统将会自动调用 uncaughtException 方法
     *
     * @param thread 为出现未捕获异常的线程
     * @param ex     为未捕获的异常 ，可以通过e 拿到异常信息
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //导入异常信息到SD卡中
        try {
            dumpExceptionToSDCard(ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //这里可以上传异常信息到服务器，便于开发人员分析日志从而解决Bug
        uploadExceptionToServer();
        ex.printStackTrace();
        //如果系统提供了默认的异常处理器，则交给系统去结束程序，否则就由自己结束自己
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(thread, ex);
        } else {
            new AlertDialog.Builder(mContext)
                    .setMessage("程序出现异常，即将退出！")
                    .setCancelable(false)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Process.killProcess(Process.myPid());
                        }
                    }).create().show();
        }
    }

    /**
     * 将异常信息写入SD卡
     *
     * @param e
     */
    public void dumpExceptionToSDCard(Throwable e) throws IOException {
        //如果SD卡不存在或无法使用，则无法将异常信息写入SD卡
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (DEBUG) {
                Log.w(TAG, "sdcard unmounted,skip dump exception");
                return;
            }
        }
        writeToFile(e);
    }

    /**
     * 真正开始写入
     *
     * @param e
     */
    private void writeToFile(Throwable e) throws IOException {
        //得到当前年月日时分秒
        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(current));
        String fileName = FILE_NAME + time + FILE_NAME_SUFFIX;

        OutputStreamWriter outputStreamWriter = null;
        if (AndroidVersionAdaptUtil.isLegacyExternalStorage()) {
            File dir = new File(PATH);
            //如果目录下没有文件夹，就创建文件夹
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //在定义的Crash文件夹下创建文件
            File file = new File(PATH + fileName);
            outputStreamWriter = new FileWriter(file);
        } else {
            Uri external = MediaStore.Files.getContentUri("external");
            ContentValues values = new ContentValues();
            // 需要指定文件信息时，非必须
            values.put(MediaStore.Images.Media.DESCRIPTION, "This is an bug crash log");
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
            //如果添加下面一行，会自动生成txt文件
//            values.put(MediaStore.Images.Media.MIME_TYPE, "text/plain");
            values.put(MediaStore.Images.Media.TITLE, fileName);
            values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/Crash/log");

            Uri insert = mContext.getContentResolver().insert(external, values);
            if (insert != null)
                outputStreamWriter = new OutputStreamWriter(mContext.getContentResolver().openOutputStream(insert));
        }

        if (outputStreamWriter != null) {
            try {
                PrintWriter pw = new PrintWriter(new BufferedWriter(outputStreamWriter));
                //写入时间
                pw.println(time);
                //写入手机信息
                dumpPhoneInfo(pw);
                pw.println();//换行
                e.printStackTrace(pw);
                pw.close();//关闭输入流
            } catch (Exception e1) {
                Log.e(TAG, "dump crash info failed");
            }
        } else {
            Log.e(TAG, "outputStreamWriter == null");
        }
    }

    /**
     * 获取手机各项信息
     *
     * @param pw
     */
    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        //得到包管理器
        PackageManager pm = mContext.getPackageManager();
        //得到包对象
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        //写入APP版本号
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print("_");
        pw.println(pi.versionCode);
        //写入 Android 版本号
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);
        //手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);
        //手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);
        //CPU架构
        pw.print("CPU ABI: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pw.println(Build.SUPPORTED_ABIS);
        } else {
            pw.println(Build.CPU_ABI);
        }
    }

    /**
     * 将错误信息上传至服务器
     */
    private void uploadExceptionToServer() {

    }


}
