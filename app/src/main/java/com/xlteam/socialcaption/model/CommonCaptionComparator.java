package com.xlteam.socialcaption.model;

import java.util.Comparator;

public class CommonCaptionComparator implements Comparator<CommonCaption>{
    @Override
    public int compare(CommonCaption o1, CommonCaption o2) {
        return o1.getContent().toLowerCase().compareTo(o2.getContent().toLowerCase());
    }
}
