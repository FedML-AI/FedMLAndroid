// IFedMLAidlInterface.aidl
package ai.fedml.iot;

// Declare any non-default types here with import statements

interface IFedMLAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int processMessage(int command);
    void onServiceConnectedOk();
    void onServiceDisconnected();
}