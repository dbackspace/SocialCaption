package com.xlteam.socialcaption.firebase;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.xlteam.socialcaption.model.Caption;
import com.xlteam.socialcaption.model.Category;

import java.util.ArrayList;
import java.util.Objects;

public class FirebaseController {
    FirebaseFirestore db;

    public FirebaseController() {
        db = FirebaseFirestore.getInstance();
    }

    public void updateTopicList(FirebaseListener<ArrayList<Category>> listener) {
        ArrayList<Category> result = new ArrayList<>();
        db.collection("categories")
                .whereNotEqualTo("categoryNumber", 0)
                .orderBy("categoryNumber", Query.Direction.ASCENDING)
                .limit(10).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Category category = document.toObject(Category.class);
                            result.add(category);
                            Log.d("binh.ngk ", category.toString());
                        }
                        listener.onResponse(result);
                    } else {
                        listener.onError();
                    }
                });
    }

    public void getCaptionByCategoryNumber(int category, FirebaseListener<ArrayList<Caption>> listener) {
        ArrayList<Caption> result = new ArrayList<>();
        db.collection("captions")
                .whereArrayContains("category", category).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Caption caption = document.toObject(Caption.class);
                            caption.setId(document.getId());
                            result.add(caption);
                        }
                        listener.onResponse(result);
                    } else {
                        listener.onError();
                    }
                });
    }
}
