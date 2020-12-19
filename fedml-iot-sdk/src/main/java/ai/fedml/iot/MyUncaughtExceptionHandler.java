package ai.fedml.iot;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = MyUncaughtExceptionHandler.class.getSimpleName();
    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;
    private Context mApplicationContext;
    private boolean bShowLog = false;

    private static final boolean bDebugMode = false;

    public MyUncaughtExceptionHandler(Context context) {
        mApplicationContext = context;
        mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(final Thread thread, final Throwable ex) {
        if (mApplicationContext.getPackageName().equals(getProcessName(mApplicationContext))) {
            if (bShowLog) {
                Log.e(TAG, "uncaughtException, show log");
                mUncaughtExceptionHandler.uncaughtException(thread, ex);
            } else {
                Log.e(TAG, "toast Sorry");
                //toastSorry();
                restart();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        } else {
            Log.e(TAG, "kill process directly!");//here
            ex.printStackTrace();
            if (bDebugMode) {
                toastSorry();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    private void toastSorry() {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(
                        mApplicationContext.getApplicationContext(),
                        "Program is abnormal, is about to restart the application.",
                        Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
    }

    public static String getProcessName(Context appContext) {
        String currentProcessName = "";
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                currentProcessName = processInfo.processName;
                break;
            }
        }
        return currentProcessName;
    }

    private void restart() {
        Log.e(TAG, "restart");
        // TODO: pull the DaemonService
    }
}