package com.xlteam.textonpicture.ui.home;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.xlteam.textonpicture.BuildConfig;
import com.xlteam.textonpicture.R;
import com.xlteam.textonpicture.external.utility.utils.Constant;
import com.xlteam.textonpicture.external.utility.utils.Utility;

import java.util.Objects;

import de.cketti.mailto.EmailIntentBuilder;

public class SettingsDialogFragment extends DialogFragment {
    private Context mContext;
    private PackageInfo pInfo;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_TextOnPicture);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_settings, container, false);
        TextView tvVersion = view.findViewById(R.id.tvVersion);
        String version = "v1.0.1";
        try {
            pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        tvVersion.setText(getString(R.string.phien_ban, version));

        view.findViewById(R.id.tvFeedback).setOnClickListener(v -> {
            if (Utility.isValidClick(v.getId())) {
                sendEmail();
            }
        });

        view.findViewById(R.id.tvGiayPhep).setOnClickListener(v -> {
            if (Utility.isValidClick(v.getId())) {
                showDialogFullScreen("license.html", R.string.license);
            }
        });

        view.findViewById(R.id.tvTermOfUse).setOnClickListener(v -> {
            if (Utility.isValidClick(v.getId())) {
                showDialogFullScreen("term_of_use.html", R.string.term_of_use);
            }
        });

        view.findViewById(R.id.tvRateApp).setOnClickListener(v -> {
            if (Utility.isValidClick(v.getId())) {
                Uri uri = Uri.parse(Constant.LINK_MARKET_APP + BuildConfig.APPLICATION_ID);
                Intent intentMarket = new Intent(Intent.ACTION_VIEW, uri);
                intentMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(intentMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.LINK_GOOGLE_PLAY_APP + BuildConfig.APPLICATION_ID)));
                }
            }
        });

        view.findViewById(R.id.tvShareApp).setOnClickListener(v -> {
            if (Utility.isValidClick(v.getId())) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                    String shareMessage = Constant.LINK_GOOGLE_PLAY_APP + BuildConfig.APPLICATION_ID;
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.choose_one)));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });

        view.findViewById(R.id.tvOtherApps).setOnClickListener(v -> {
            if (Utility.isValidClick(v.getId())) {
                recommendApp();
            }
        });
        view.findViewById(R.id.image_back).setOnClickListener(v -> dismiss());
        return view;
    }

    private void sendEmail() {
        String[] emailList = {Constant.EMAIL_TEAM};
        EmailIntentBuilder.from(mContext)
                .to(emailList[0])
                .subject(getString(R.string.feedback_title))
                .body(preGetAppInfo().toString())
                .start();
    }

    private void showDialogFullScreen(String assetName, int title) {
        Dialog dialog = new Dialog(mContext, R.style.Theme_TextOnPicture);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_web_view_fullscreen);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        tvTitle.setText(title);
        WebView webView = dialog.findViewById(R.id.web);
        webView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_FC));
        String urlWebView = "file:///android_asset/" + assetName;
        webView.loadUrl(urlWebView);
        ImageView imgBack = dialog.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private StringBuilder preGetAppInfo() {
        StringBuilder info = new StringBuilder();
        info.append(getString(R.string.app_info))
                .append(":\n- PACKAGE NAME: ").append(pInfo.packageName)
                .append("\n- VERSION NAME: ").append(pInfo.versionName)
                .append("\n- VERSION CODE: ").append(pInfo.versionCode)
                .append("\n- VERSION OS: ").append(Build.VERSION.SDK_INT)
                .append("\n- MODEL: ").append(Build.MODEL)
                .append("\n- MANUFACTURER: ").append(Build.MANUFACTURER.toUpperCase());
        DisplayMetrics dm = getResources().getDisplayMetrics();
        info.append("\n- DISPLAY SIZE: ").append(dm.heightPixels).append('x').append(dm.widthPixels);
        info.append("\n\n").append(getString(R.string.feedback_msg)).append("\n");
        return info;
    }

    private void recommendApp() {
        Uri uri = Uri.parse(Constant.LINK_MARKET_DEVELOPER);
        Intent intentMarket = new Intent(Intent.ACTION_VIEW, uri);
        intentMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(intentMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.LINK_GOOGLE_PLAY_DEVELOPER)));
        }
        // TODO: phát triển thêm tính năng hiện dialog show các app gợi ý trực tiếp
    }
}
