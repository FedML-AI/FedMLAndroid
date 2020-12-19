package ai.fedml.iot.service;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import ai.fedml.iot.IFedMLAidlInterface;
import ai.fedml.iot.Plugin.FedMLPlugin;

import static android.app.Service.START_REDELIVER_INTENT;

public class FedMLServiceImpl extends IFedMLAidlInterface.Stub {
    public static final String TAG = "FedMLServiceImpl";

    public static final String INTENT_KEY_BUNDLE = "bundle";

    private static final FedMLEventHandler sFedMLEventHandler = new FedMLEventHandler("FedMLEventHandler");

    public void init() {
        Log.d(TAG, "init");
    }

    public void unInit() {
        Log.d(TAG, "unInit");
    }


    public int onStartCommand(Intent intent) {
        Log.e(TAG, "onStartCommand");
        Bundle bundle = intent.getBundleExtra(INTENT_KEY_BUNDLE);
        sFedMLEventHandler.obtainMessage(SERVICE_PROCESS_MSG, bundle).sendToTarget();
        return START_REDELIVER_INTENT;
    }

    @Override
    public int processMessage(int command) throws RemoteException {
        return 0;
    }

    @Override
    public void onServiceConnectedOk() throws RemoteException {
        Log.d(TAG, "onServiceConnectedOk");
    }

    @Override
    public void onServiceDisconnected() throws RemoteException {
        Log.d(TAG, "onServiceDisconnected");
    }

    @Override
    public IBinder asBinder() {
        return null;
    }

    private static final int SERVICE_PROCESS_MSG = 0x1001;

    private static class FedMLEventHandler extends BackgroundHandler {
        private final FedMLPlugin mFedMLPlugin = new FedMLPlugin();

        public FedMLEventHandler(String name) {
            super(name);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == SERVICE_PROCESS_MSG) {
                try {
                    Log.d(TAG, "handleMessage: SERVICE_PROCESS_MSG");
                    processInternal((Bundle) msg.obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void processInternal(Bundle bundle) {
            Log.d(TAG, "processInternal");
            mFedMLPlugin.processMessage(bundle);
        }
    }
}
