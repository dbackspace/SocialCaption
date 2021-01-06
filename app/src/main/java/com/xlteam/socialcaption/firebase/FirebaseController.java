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


}
