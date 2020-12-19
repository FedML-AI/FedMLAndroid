package ai.fedml.fedmlsdk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.util.UUID;

import ai.fedml.fedmlsdk.ContextHolder;

public class DeviceUtils {

    private static final String DEVICE_ID = "DeviceId";
    private static String deviceId;
    private static String sPackageName;
    private static String sAppVersionName;
    private static int sAppVersionCode;

    private DeviceUtils() {
    }

    public static String getDeviceId() {
        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }
        deviceId = getUuidAsDeviceId();
        return deviceId;
    }

    private static String getUuidAsDeviceId() {
        final Context context = ContextHolder.getAppContext();
        SharedPreferences sp = context.getSharedPreferences(DEVICE_ID, Context.MODE_PRIVATE);
        String id = sp.getString(DEVICE_ID, "");
        if (TextUtils.isEmpty(id)) {
            final String uuid = UUID.randomUUID().toString();
            id = uuid;
            sp.edit().putString(DEVICE_ID, uuid).apply();
        }
        return id;
    }

    public static String getAppPackageName() {
        initAppVersion();
        return sPackageName;
    }

    public static int getAppVersionCode() {
        initAppVersion();
        return sAppVersionCode;
    }

    public static String getAppVersionName() {
        initAppVersion();
        return sAppVersionName;
    }

    private static void initAppVersion() {
        if (!TextUtils.isEmpty(sPackageName)) {
            return;
        }
        final Context context = ContextHolder.getAppContext();
        PackageManager manager = context.getPackageManager();
        try {
            sPackageName = context.getPackageName();
            PackageInfo info = manager.getPackageInfo(sPackageName, 0);
            sAppVersionName = info.versionName;
            sAppVersionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            sAppVersionName = "NULL";
            sAppVersionCode = -1;
        }
    }

}
