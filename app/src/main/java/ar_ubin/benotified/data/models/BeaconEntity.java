package ar_ubin.benotified.data.models;


import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class BeaconEntity
{
    @Expose
    public List<Beacon> beacons = new ArrayList<>();
}
