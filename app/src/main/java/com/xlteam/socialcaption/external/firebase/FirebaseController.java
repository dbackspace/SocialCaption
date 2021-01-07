package com.xlteam.socialcaption.external.firebase;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xlteam.socialcaption.external.repository.CaptionRepository;
import com.xlteam.socialcaption.model.Caption;

public class FirebaseController {
    FirebaseFirestore db;
    private CaptionRepository mRepository;

    public FirebaseController(CaptionRepository repository) {
        db = FirebaseFirestore.getInstance();
        mRepository = repository;
    }


    public void updateCaption() {
        db.collection("captions").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Caption caption = documentSnapshot.toObject(Caption.class);
                    //insert caption to database
                    mRepository.insertSingleCaption(caption);
                }
            }
        });
    }

}
