package com.tachnologies.myligapro.common.callbacks;

public interface BasicErrorEventCallback {
    void onSuccess();
    void onError(int typeEvent, int resMsg);
}
