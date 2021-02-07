package com.xlteam.socialcaption.external.repository;
import java.util.List;

public interface ILoader<CaptionType> {
    default void loadResult(int loaderTaskType, List<CaptionType> result) {
        return;
    }
}
