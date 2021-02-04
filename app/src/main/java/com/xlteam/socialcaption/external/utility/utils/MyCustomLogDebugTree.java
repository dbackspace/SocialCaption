package com.xlteam.socialcaption.external.utility.utils;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

public class MyCustomLogDebugTree extends Timber.DebugTree {
    @Override
    protected @Nullable
    String createStackElementTag(@NotNull StackTraceElement element) {
        return makeClickableLineNumber(element);
    }

    private String makeClickableLineNumber(@NotNull StackTraceElement element) {

        StringBuilder stringBuilder = new StringBuilder(element.getMethodName())
                .append("SocialCaption ")
                .append("(")
                .append(element.getFileName())
                .append(":")
                .append(element.getLineNumber())
                .append(")  ");
        return stringBuilder.toString();
    }
}
