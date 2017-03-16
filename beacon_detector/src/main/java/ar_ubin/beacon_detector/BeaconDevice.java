package ar_ubin.beacon_detector;

import java.util.Locale;
import java.util.regex.Pattern;


public class BeaconDevice
{
    private final String mDeviceName;
    private final Pattern mDeviceNamePattern;

    public BeaconDevice( final String deviceName ) {
        this.mDeviceName = deviceName;
        this.mDeviceNamePattern = Pattern.compile( deviceName.toLowerCase( Locale.GERMANY ) + ".*" );
    }

    @Override
    public boolean equals( Object o ) {
        if( o instanceof String ) {
            return this.mDeviceNamePattern.matcher( ( (String) o ).toLowerCase( Locale.GERMANY ) ).matches();
        }
        return super.equals( o );
    }

    @Override
    public String toString() {
        return this.mDeviceName;
    }
}