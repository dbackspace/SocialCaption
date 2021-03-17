package com.xlteam.socialcaption.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.animation.ViManager;
import com.xlteam.socialcaption.external.utility.utils.Constant;
import com.xlteam.socialcaption.external.utility.utils.FileUtils;
import com.xlteam.socialcaption.external.utility.utils.Utility;
import com.xlteam.socialcaption.ui.edit.EditCaptionActivity;
import com.xlteam.socialcaption.ui.home.created.PictureCreatedDialogFragment;
import com.xlteam.socialcaption.ui.home.firebase.PictureFirebaseDialogFragment;

import java.util.List;

public class HomePageActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {
    RecyclerView rvFirebase, rvCreated;
    TextView tvViewMoreFirebase, tvViewMoreCreated;
    TextView tvEmptyCreated;
    LinearLayout layoutTakePhoto, layoutGallery;
    ImageView imgSettings;
    PictureFirebaseDialogFragment pictureFirebaseDialogFragment;
    PictureCreatedDialogFragment pictureCreatedDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        rvFirebase = findViewById(R.id.rv_picture_firebase);
        rvCreated = findViewById(R.id.rv_picture_created);
        tvViewMoreFirebase = findViewById(R.id.view_more_picture_firebase);
        tvViewMoreCreated = findViewById(R.id.view_more_picture_created);
        tvEmptyCreated = findViewById(R.id.tv_empty_picture_created);
        layoutTakePhoto = findViewById(R.id.layout_take_photo);
        layoutGallery = findViewById(R.id.layout_gallery);
        imgSettings = findViewById(R.id.image_settings);

        hasPermission();
        rvFirebase.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvFirebase.setAdapter(new PictureHomeAdapter(this, Constant.TYPE_PICTURE_FIREBASE, Utility.getUrlPictureHome()));

//        rvCreated.setDrawingCacheEnabled(true);
//        rvCreated.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rvCreated.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvCreated.setAdapter(new PictureHomeAdapter(HomePageActivity.this, Constant.TYPE_PICTURE_CREATED, FileUtils.getListPathsIfFolderExist()));
        if (FileUtils.getListPathsIfFolderExist().isEmpty()) {
            tvEmptyCreated.setVisibility(View.VISIBLE);
            rvCreated.setVisibility(View.GONE);
        }

        tvViewMoreFirebase.setOnClickListener(view -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ViManager.getInstance().setFragmentDefaultAnimation(HomePageActivity.this, fragmentTransaction);
            pictureFirebaseDialogFragment = new PictureFirebaseDialogFragment();
            pictureFirebaseDialogFragment.show(fragmentTransaction, "dialog_firebase");
        });

        tvViewMoreCreated.setOnClickListener(view -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ViManager.getInstance().setFragmentDefaultAnimation(HomePageActivity.this, fragmentTransaction);
            pictureCreatedDialogFragment = new PictureCreatedDialogFragment();
            pictureCreatedDialogFragment.show(fragmentTransaction, "dialog_created");
        });
        layoutTakePhoto.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, Constant.REQUEST_CODE_TAKE_PHOTO);
        });

        layoutGallery.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), Constant.REQUEST_CODE_PICK_PHOTO_GALLERY);
        });

        imgSettings.setOnClickListener(v -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ViManager.getInstance().setFragmentDefaultAnimation(HomePageActivity.this, fragmentTransaction);
            SettingsDialogFragment settingsDialogFragment = new SettingsDialogFragment();
            settingsDialogFragment.show(fragmentTransaction, "dialog_settings");
        });
    }

    private void hasPermission() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            List<String> listImagePaths = FileUtils.getListPathsIfFolderExist();
                            if (listImagePaths.isEmpty()) {
                                tvEmptyCreated.setText("Không có ảnh nào");
                            } else {
                                rvCreated.setAdapter(new PictureHomeAdapter(HomePageActivity.this, Constant.TYPE_PICTURE_CREATED, listImagePaths));
                            }
                        } else {
                            Utility.showDialogRequestPermission(HomePageActivity.this);
                            if (!isHasAllPermission()) {
                                String noPermission = "Không có quyền. Cấp quyền";
                                tvEmptyCreated.setMovementMethod(LinkMovementMethod.getInstance());
                                tvEmptyCreated.setText(noPermission, TextView.BufferType.SPANNABLE);
                                Spannable mySpannable = (Spannable) tvEmptyCreated.getText();
                                ClickableSpan myClickableSpan = new ClickableSpan() {
                                    @Override
                                    public void onClick(View view) {
                                        hasPermission();
                                    }
                                };
                                mySpannable.setSpan(myClickableSpan, 0, noPermission.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private boolean isHasAllPermission() {
        return checkReadExternalPermission()
                && checkWriteExternalPermission();
    }

    private boolean checkReadExternalPermission() {
        String permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private boolean checkWriteExternalPermission() {
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE_PICK_PHOTO_GALLERY) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    Toast.makeText(this, R.string.not_selected_picture, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(this, EditCaptionActivity.class);
                    intent.putExtra(Constant.EXTRA_PICK_PHOTO_URL, data.getData());
                    intent.putExtra(Constant.EXTRA_TYPE_PICTURE, Constant.TYPE_PICK_PHOTO);
                    startActivityForResult(intent, Constant.REQUEST_CODE_PHOTO_FROM_HOME);
                }
            }
        } else if (requestCode == Constant.REQUEST_CODE_TAKE_PHOTO) {
            Bitmap photo = null;
            if (data != null) {
                if (data.getExtras() != null)
                    photo = (Bitmap) data.getExtras().get("data");
            }
            if (photo != null) {
                Intent intent = new Intent(this, EditCaptionActivity.class);
                intent.putExtra(Constant.EXTRA_PICK_PHOTO_URL, photo);
                intent.putExtra(Constant.EXTRA_TYPE_PICTURE, Constant.TYPE_TAKE_PHOTO);
                startActivityForResult(intent, Constant.REQUEST_CODE_PHOTO_FROM_HOME);
            }
        } else if (requestCode == Constant.REQUEST_CODE_PHOTO_FROM_HOME) {
            if (resultCode == Activity.RESULT_OK) {
                if (pictureCreatedDialogFragment != null) {
                    pictureCreatedDialogFragment.dismiss();
                }
                if (pictureFirebaseDialogFragment != null) {
                    pictureFirebaseDialogFragment.dismiss();
                }
                rvCreated.setAdapter(new PictureHomeAdapter(HomePageActivity.this, Constant.TYPE_PICTURE_CREATED, FileUtils.getListPathsIfFolderExist()));
            }
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        rvCreated.setAdapter(new PictureHomeAdapter(HomePageActivity.this, Constant.TYPE_PICTURE_CREATED, FileUtils.getListPathsIfFolderExist()));
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mAdView != null) {
//            mAdView.resume();
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (mAdView != null) {
//            mAdView.pause();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        AsyncLayoutInflateManager.getInstance(this).onDestroy();
//        if (mAdView != null) {
//            mAdView.destroy();
//        }
//    }
}