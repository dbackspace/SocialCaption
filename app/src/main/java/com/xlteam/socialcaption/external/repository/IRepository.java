package com.xlteam.socialcaption.external.repository;

import com.xlteam.socialcaption.model.CommonCaption;

import java.util.List;

public interface IRepository {
    void loadResult(int loaderTaskType, List<CommonCaption> result);
}
