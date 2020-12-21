package com.xlteam.socialcaption.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.ui.account.AccountFragment;
import com.xlteam.socialcaption.ui.category.CategoryFragment;
import com.xlteam.socialcaption.ui.home.HomePageFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.mainBottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        replaceFragment(R.id.frame, new HomePageFragment());
                        break;
                    case R.id.nav_upload:
                        showDialogUpload();
                        break;
                    case R.id.nav_category:
                        replaceFragment(R.id.frame, new CategoryFragment());
                        break;
                    case R.id.nav_account:
                        replaceFragment(R.id.frame, new AccountFragment());
                        break;
                }
                return false;
            }
        });
    }

    private void replaceFragment(int id, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        transaction.commit();
    }

    private void showDialogUpload() {

    }
}