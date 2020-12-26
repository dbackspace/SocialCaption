package com.xlteam.socialcaption.firebase;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xlteam.socialcaption.common.utility.Constant;
import com.xlteam.socialcaption.common.utility.PrefUtils;

import java.util.ArrayList;

public class FirebaseController {
    FirebaseFirestore db;

    public FirebaseController() {
        db = FirebaseFirestore.getInstance();
    }

    public static void updateTopicList(Context context) {
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("topics").document("templateTopics");
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    ArrayList<String> result = (ArrayList<String>) document.get("topicList");
                    PrefUtils.putStringArrayList(context, Constant.FIREBASE, Constant.FIREBASE_CATEGORY_LIST, result);
                } else {
                    Log.d("binh.ngk", "No such document");
                }
            } else {
                Log.d("binh.ngk", "get failed with ", task.getException());
            }
        });
    }

    public void getCaptionByCategoryNumber(int category) {

    }
}
