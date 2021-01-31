package ai.fedml.fedmlsdk.listeners;

public interface OnDataSetPretreatedListener {
    void onDataSetDownloaded(final String dataSetPath);

    void onDataSetUnzip(final String dataSetDir);
}
