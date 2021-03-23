package com.xlteam.textonpicture.external.utility.utils;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

public class MyCustomLogDebugTree extends Timber.DebugTree {
    private static final String TAG = "TextOnPicture";

    @Override
    protected @Nullable
    String createStackElementTag(@NotNull StackTraceElement element) {
        return makeClickableLineNumber(element);
    }

    private String makeClickableLineNumber(@NotNull StackTraceElement element) {
        StringBuilder stringBuilder = new StringBuilder(TAG)
                .append(": (")
                .append(element.getFileName())
                .append(":")
                .append(element.getLineNumber())
                .append(") [")
                .append(element.getMethodName())
                .append("]");
        return stringBuilder.toString();
    }
}
