package ar_ubin.beacon_detector;

import java.util.List;

public class Beacon
{
    private int mMajorNumber;
    private int mMinorNumber;
    private int mTxPower;
    private int mRssi;
    private RssiFifoQueue mRssiSignals = new RssiFifoQueue( 5 );
    private String mName;
    private double mDistance;

    public Beacon( final String deviceName ) {
        this.mName = deviceName;
    }

    public Beacon() {
        this.mName = "Unkown Beacon";
    }

    public int getMajorNumber() {
        return mMajorNumber;
    }

    public void setMajorNumber( int majorNumber ) {
        this.mMajorNumber = majorNumber;
    }

    public int getMinorNumber() {
        return mMinorNumber;
    }

    public void setMinorNumber( int minorNumber ) {
        this.mMinorNumber = minorNumber;
    }

    public int getTxPower() {
        return mTxPower;
    }

    public void setTxPower( int txPower ) {
        this.mTxPower = txPower;
    }

    public int getRssi() {
        return mRssi;
    }

    public void setRssi( int rssi ) {
        this.mRssi = rssi;
    }

    public List<Integer> getRssiSignals() {
        return mRssiSignals.mQueue;
    }

    public void setRssiSignals( List<Integer> rssiSignals ) {
        this.mRssiSignals.mQueue = rssiSignals;
    }

    public String getName() {
        return mName;
    }

    public void setName( String name ) {
        this.mName = name;
    }

    public void addMeasurement( final int rssi ) {
        this.mRssiSignals.add( rssi );
        this.mRssi = rssi;
    }

    public float getLastRssiValue() {
        if( this.mRssiSignals.size() > 0 ) return this.mRssiSignals.get( this.mRssiSignals.size() - 1 );
        else return Float.NaN;
    }

    public void clear() {
        this.mRssiSignals.clear();
    }

    public double getDistance() {
        return this.mDistance;
    }

    public void setDistance( double distance ) {
        this.mDistance = distance;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "***** Beacon *****\n" );
        stringBuilder.append( "minor = " + this.mMinorNumber + "\n" );
        stringBuilder.append( "major = " + this.mMajorNumber + "\n" );
        stringBuilder.append( "name = " + this.mName + "\n" );
        stringBuilder.append( "distance = " + this.mDistance + "\n" );
        stringBuilder.append( "time = " + System.currentTimeMillis() + "\n" );
        stringBuilder.append( "*******************************" );

        return stringBuilder.toString();
    }
}
