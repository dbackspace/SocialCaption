package com.xlteam.socialcaption.firebase;

public interface FirebaseListener<T> {

    void onResponse(T t);

    void onError();
}
