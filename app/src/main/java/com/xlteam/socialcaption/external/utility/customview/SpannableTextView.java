package com.xlteam.socialcaption.external.utility.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;

import com.xlteam.socialcaption.R;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class SpannableTextView extends AppCompatTextView {
    public SpannableTextView(@NonNull Context context) {
        super(context);
    }

    public SpannableTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SpannableTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setText(String sourceText, String spanText) {
        if (!TextUtils.isEmpty(sourceText)) {
            if (TextUtils.isEmpty(spanText)) {
                super.setText(sourceText);
            } else {
                SpannableString span = getSpannableString(sourceText, spanText);
                super.setText(span);
            }
        }
    }

    private SpannableString getSpannableString(String sourceText, String spanText) {
        SpannableString span = new SpannableString(sourceText);
        int matchedColor = getResources().getColor(R.color.search_result_text_color_activated, null);
        String[] queryArray = spanText.split("\\s+");

        for (String query : queryArray) {
            int index;
            String resultLowerStr = sourceText.toLowerCase(Locale.getDefault());
            if (!TextUtils.isEmpty(spanText)) {
                index = resultLowerStr.indexOf(query.toLowerCase());
            } else {
                query = resultLowerStr.substring(resultLowerStr.lastIndexOf('.') + 1);
                index = resultLowerStr.lastIndexOf(query.toLowerCase());
            }
            while (index >= 0) {
                setSpan(span, matchedColor, index, index + query.length());
                index = resultLowerStr.indexOf(query, index + 1);
            }
        }
        return span;
    }

    private void setSpan(SpannableString span, int matchedColor, int start, int end) {
        span.setSpan(new ForegroundColorSpan(matchedColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new StyleSpan(Typeface.NORMAL), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
