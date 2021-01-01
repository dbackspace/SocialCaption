package com.xlteam.socialcaption.ui.upload;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.common.datasource.FontDataSource;
import com.xlteam.socialcaption.model.Font;
import com.xlteam.socialcaption.model.User;
import com.xlteam.socialcaption.ui.common.views.BaseObservableViewMvc;
import com.xlteam.socialcaption.ui.upload.adapter.DetailForFontAdapter;
import com.xlteam.socialcaption.ui.upload.adapter.DetailForToolsAdapter;
import com.xlteam.socialcaption.ui.upload.adapter.ToolColorAlignFontAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.xlteam.socialcaption.common.utility.Constant.CATEGORY_ARRAY;
import static com.xlteam.socialcaption.common.utility.Constant.GRADIENT_BACKGROUND_ARRAY;
import static com.xlteam.socialcaption.common.utility.Constant.TOOL_FOR_COLOR_FONT_ALIGN_POST;

public class DialogUploadCaptionsViewMvcImpl extends BaseObservableViewMvc<DialogUploadCaptionsViewMvc.Listener>
        implements DialogUploadCaptionsViewMvc, DetailForFontAdapter.DetailForFontCallback {

    private final ImageButton mBtnBack;
    private final TextView mBtnPostCaption;
    private final ImageView mImgUser;
    private final TextView mTvUserName;
    private final Spinner mSpinnerCategory;
    private final ImageView imgBackgroundForCaption;
    private final EditText mEdtCaption;
    private final RecyclerView mRvDetailForTools;
    private final RecyclerView mRvTool;
    private List<Font> mFonts;

    public DialogUploadCaptionsViewMvcImpl(LayoutInflater inflater, @Nullable ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.dialog_upload, parent, false));

        // init view
        mBtnBack = findViewById(R.id.btn_back);
        mBtnPostCaption = findViewById(R.id.btn_search);
        mImgUser = findViewById(R.id.img_avatar_user);
        mTvUserName = findViewById(R.id.tv_user_name);
        mSpinnerCategory = findViewById(R.id.spinner_category_post);
        mEdtCaption = findViewById(R.id.edtInput);
        mRvDetailForTools = findViewById(R.id.rv_background_post_caption);
        imgBackgroundForCaption = findViewById(R.id.img_background_post_caption);
        mRvTool = findViewById(R.id.rv_tool_color_font_align);

        // set spinner category
        List<String> listCategory = new ArrayList<>();
        listCategory.add("Chọn chủ đề");
        listCategory.addAll(Arrays.asList(CATEGORY_ARRAY));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listCategory);
        mSpinnerCategory.setAdapter(adapter);

        // focus editCaption and show keyboard
        mEdtCaption.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEdtCaption, InputMethodManager.SHOW_IMPLICIT);

        // init adapter
        // color background
        DetailForToolsAdapter adapterBgr = new DetailForToolsAdapter(GRADIENT_BACKGROUND_ARRAY, position -> imgBackgroundForCaption.setImageResource(GRADIENT_BACKGROUND_ARRAY[position]));
        // font
        mFonts = FontDataSource.getInstance().getAllFonts();
        DetailForFontAdapter adapterFont = new DetailForFontAdapter(getContext(), mFonts, this);

        // set recycler view for tool (align, font, color, ...)
        ToolColorAlignFontAdapter toolAdapter = new ToolColorAlignFontAdapter(TOOL_FOR_COLOR_FONT_ALIGN_POST, position -> {
            switch (position) {
                case 1:
                    // Font caption
                    mRvDetailForTools.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    mRvDetailForTools.setAdapter(adapterFont);
                    mRvDetailForTools.scrollToPosition(position);
                    break;
                case 2:
                    // Color for font caption
                    break;
                case 3:
                    // Align for caption
                    break;
                // Set recycler view for detail of tools
                case 0:
                    // color background
                default:
                    // color background
                    mRvDetailForTools.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    mRvDetailForTools.setAdapter(adapterBgr);
            }
        });

        mRvTool.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvTool.setAdapter(toolAdapter);

        mBtnBack.setOnClickListener((v) -> {
            for (Listener listener : getListeners()) {
                listener.onBackButtonClicked();
            }
        });
    }

    @Override
    public void bindUser(User user) {
        mImgUser.setImageResource(R.drawable.ic_user_default);
        mTvUserName.setText(user.getUserId());
    }

    @Override
    public void onFontClicked(int position) {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "font/" + mFonts.get(position).getFont());
        mEdtCaption.setTypeface(typeface);
    }
}
