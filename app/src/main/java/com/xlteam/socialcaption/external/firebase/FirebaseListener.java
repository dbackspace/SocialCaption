package com.xlteam.socialcaption.external.firebase;

public interface FirebaseListener<T> {

    void onResponse(T t);

    void onError();
}
