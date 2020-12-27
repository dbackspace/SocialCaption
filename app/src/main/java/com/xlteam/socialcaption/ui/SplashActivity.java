package com.xlteam.socialcaption.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.common.utility.SharedPreferencesController;
import com.xlteam.socialcaption.firebase.FirebaseController;
import com.xlteam.socialcaption.firebase.FirebaseListener;
import com.xlteam.socialcaption.model.Category;
import com.xlteam.socialcaption.ui.main.MainActivity;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    ProgressBar progressBarLoading;
    LinearLayout layoutRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!SharedPreferencesController.getCategoryList(this).isEmpty()) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        } else {
            Dialog dialog = new Dialog(this, R.style.CustomDialog);
            dialog.setContentView(R.layout.dialog_update_category);
            dialog.setCancelable(false);
            progressBarLoading = dialog.findViewById(R.id.progress_loading);
            layoutRetry = dialog.findViewById(R.id.layout_retry);
            layoutRetry.setOnClickListener(view -> updateCategories());
            updateCategories();
            dialog.show();
        }
    }

    private void updateCategories() {
        progressBarLoading.setVisibility(View.VISIBLE);
        layoutRetry.setVisibility(View.GONE);
        new FirebaseController().updateTopicList(new FirebaseListener<ArrayList<Category>>() {
            @Override
            public void onResponse(ArrayList<Category> categories) {
                SharedPreferencesController.putCategoryList(SplashActivity.this, categories);
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onError() {
                progressBarLoading.setVisibility(View.GONE);
                layoutRetry.setVisibility(View.VISIBLE);
            }
        });
    }
}