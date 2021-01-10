package com.xlteam.socialcaption.external.firebase;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xlteam.socialcaption.external.repository.CommonCaptionRepository;
import com.xlteam.socialcaption.model.CommonCaption;

public class FirebaseController {
    FirebaseFirestore db;
    private CommonCaptionRepository mRepository;

    public FirebaseController(CommonCaptionRepository repository) {
        db = FirebaseFirestore.getInstance();
        mRepository = repository;
    }


    public void updateCaption() {
        db.collection("captions").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    CommonCaption caption = documentSnapshot.toObject(CommonCaption.class);
                    //insert caption to database
                    mRepository.insertSingleCaption(caption);
                }
            }
        });
    }

}
