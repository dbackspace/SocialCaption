package com.xlteam.socialcaption.external.repository;

import com.xlteam.socialcaption.model.Caption;

import java.util.List;

public interface ICaptionRepository {
    void loadResult(List<Caption> result);
}
