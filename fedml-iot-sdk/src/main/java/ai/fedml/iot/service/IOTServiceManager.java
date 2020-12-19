package ai.fedml.iot.service;

import android.content.ComponentName;
import android.os.IBinder;

public class IOTServiceManager extends BaseServiceManager{
    protected IOTServiceManager(String serviceAction) {
        super(serviceAction);
    }

    @Override
    protected void onServiceConnectedOk(IBinder service) {

    }

    @Override
    protected void onServiceDisconnected(ComponentName name) {

    }

    @Override
    protected void dispose() {

    }
}
