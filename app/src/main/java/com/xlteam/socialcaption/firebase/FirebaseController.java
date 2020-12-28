package com.xlteam.socialcaption.firebase;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.xlteam.socialcaption.model.Caption;

import java.util.ArrayList;
import java.util.Objects;

public class FirebaseController {
    FirebaseFirestore db;

    public FirebaseController() {
        db = FirebaseFirestore.getInstance();
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
