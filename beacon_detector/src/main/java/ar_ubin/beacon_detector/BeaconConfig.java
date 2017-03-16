package ar_ubin.beacon_detector;


import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public class BeaconConfig
{
    public static boolean DEBUG = true;
    private static BeaconConfig mInstance;
    protected long mScanPeriod = 150000;
    protected long mWaitBetweenScans = 1700;
    private ArrayList<BeaconDevice> mSupportedDevices = new ArrayList<>();

    @Retention( RetentionPolicy.SOURCE )
    @IntDef( { RADIUS_IMMEDIATE, RADIUS_NEAR, RADIUS_FAR } )
    private @interface BeaconRadius
    {}

    public static final int RADIUS_IMMEDIATE = 0; // Radius < 20cm
    public static final int RADIUS_NEAR = 1; // Radius between 20cm-2m
    public static final int RADIUS_FAR = 2; // Radius between 2m-50m
    private int mScanRadius;

    private BeaconConfig() {
        this.mSupportedDevices.add( new BeaconDevice( "BlueBar" ) );
        this.mSupportedDevices.add( new BeaconDevice( "Kontakt" ) );
        this.mSupportedDevices.add( new BeaconDevice( "BluLocButton" ) );
    }

    public static BeaconConfig getInstance() {
        return mInstance == null? mInstance = new BeaconConfig() : mInstance;
    }

    @BeaconRadius
    public int getScanRadius() {
        return mScanRadius;
    }

    public void setScanRadius( @BeaconRadius int radius ) {
        this.mScanRadius = radius;
    }

    public long getScanPeriod() {
        return mScanPeriod;
    }

    public void setScanPeriod( long scanPeriod ) {
        this.mScanPeriod = scanPeriod;
    }

    public long getWaitBetweenScans() {
        return mWaitBetweenScans;
    }

    public void setWaitBetweenScans( long waitBetweenScans ) {
        this.mWaitBetweenScans = waitBetweenScans;
    }

    public void addSupportedDevice( final String deviceName ) {
        this.mSupportedDevices.add( new BeaconDevice( deviceName ) );
    }

    public boolean isBeaconSupported( String devicename ) {
        for( BeaconDevice dev : this.mSupportedDevices ) {
            if( dev.equals( devicename ) ) {
                return true;
            }
        }
        return false;
    }
}
