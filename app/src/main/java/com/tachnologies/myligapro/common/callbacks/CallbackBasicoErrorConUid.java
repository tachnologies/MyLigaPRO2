package com.tachnologies.myligapro.common.callbacks;

public interface CallbackBasicoErrorConUid {
    void onSuccess(String uid);
    void onError(int typeEvent, int resMsg);
}
