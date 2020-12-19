package ai.fedml.iot;

import android.content.Context;
import android.content.Intent;

import ai.fedml.iot.service.DaemonService;

public class IOTCore {
    private IOTCore() {
    }

    private final static class LazyHolder {
        private final static IOTCore sInstance = new IOTCore();
    }

    private volatile boolean mInitialized = false;

    public IOTCore getInstance() {
        return LazyHolder.sInstance;
    }

    public void init(final Context context) {
        if (mInitialized) return;
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler(context));
        //init process keeping alive service
        context.startService(new Intent(context, DaemonService.class));
        //init MQ service. Inside the MQ service, it triggers location SDK and start to collect trajectory.
//        IOTServiceServiceManager.getInstance().init(context);
        mInitialized = true;
    }
}
