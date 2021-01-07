package com.xlteam.socialcaption.external.repository;

import com.xlteam.socialcaption.model.Caption;

import java.util.List;

public interface ICaptionRepository {
    void loadResult(int loaderTaskType, List<Caption> result);
}
