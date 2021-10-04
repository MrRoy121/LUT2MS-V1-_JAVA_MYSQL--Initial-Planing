package com.example.tms;

import com.google.android.gms.maps.model.Marker;

public class locationModel {
    Integer id;
    Double latitude, longitude;

    public locationModel(Integer id, Double latitude, Double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
