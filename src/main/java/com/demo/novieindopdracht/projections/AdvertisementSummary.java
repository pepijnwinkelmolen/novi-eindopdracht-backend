package com.demo.novieindopdracht.projections;

import java.io.File;

public interface AdvertisementSummary {
    Long getAdvertisementId();
    String getTitle();
    Double getPrice();
    File getImage();
}
