package com.example.tms;

public class dataset {
    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getBusid() {
        return busid;
    }

    public void setBusid(String busid) {
        this.busid = busid;
    }

    public dataset(String route, String busid) {
        this.route = route;
        this.busid = busid;
    }

    String route, busid;
}
