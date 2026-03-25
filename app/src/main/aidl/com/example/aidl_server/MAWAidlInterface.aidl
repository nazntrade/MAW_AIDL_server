// MAWAidlInterface.aidl
package com.example.aidl_server;

// Declare any non-default types here with import statements

interface MAWAidlInterface {

    int getPID();

    int getConnectionCount();

    void setDisplayedValue(String packegeName, int pid, String data);
}