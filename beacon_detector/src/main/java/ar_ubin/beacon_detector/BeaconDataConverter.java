package ar_ubin.beacon_detector;

import android.util.Log;

public class BeaconDataConverter
{
    private final String TAG = BeaconDataConverter.class.getSimpleName();

    public BeaconDataConverter() {}

    /**
     * Copied from Stackoverflow answer.
     * Answered Jan 24 at 18:48 by davidgyoung
     *
     * http://stackoverflow.com/questions/21338031/radius-networks-ibeacon-ranging-fluctuation
     */
    private static double calculateAccuracy( int txPower, double rssi ) {
        if( rssi == 0 ) {
            return -1.0; // if we cannot determine accuracy, return -1.
        }

        double ratio = rssi * 1.0 / txPower;
        if( ratio < 1.0 ) {
            return Math.pow( ratio, 10 );
        } else {
            double accuracy = ( 0.89976 ) * Math.pow( ratio, 7.7095 ) + 0.111;
            return accuracy;
        }
    }

    public Beacon decodeData( final byte[] data, final Beacon beacon ) {
        try {
            if( data != null && data.length > 0 ) {
                decode( data, beacon );
            }
        } catch( Exception e ) {
            Log.e( TAG, "Decoding not possible something went wrong!", e );
        }

        return beacon;
    }

    public Beacon decodeData( final byte[] data ) {
        final Beacon beacon = new Beacon();
        return decodeData( data, beacon );
    }

    public Beacon calculateAccuracy( final double rssi, final Beacon beacon ) {
        beacon.setDistance( calculateAccuracy( beacon.getTxPower(), rssi ) );
        return beacon;
    }

    /*
     * Extracted from Radius Networks and their GitHub project. IBeacon class
     *
     * Radius Networks, Inc. http://www.radiusnetworks.com
     *
     * @author David G. Young
     *
     * AirLocate: 02 01 1a 1a ff 4c 00 02 15
     * # Apple's fixed iBeacon advertising prefix e2 c5 6d b5 df fb 48 d2 b0 60 d0 f5 a7 10 96 e0
     * # iBeacon profile uuid 00 00
     * # major 00 00
     * # minor c5
     * # The 2's complement of the calibrated Tx Power
     */
    private void decode( final byte[] data, final Beacon beacon ) {
        int startByte = 2;
        boolean patternFound = false;
        while( startByte <= 5 ) {
            if( ( (int) data[startByte + 2] & 0xff ) == 0x02 && ( (int) data[startByte + 3] & 0xff ) == 0x15 ) {
                patternFound = true; // yes! This is an iBeacon
                break;
            }
            startByte++;
        }

        if( !patternFound ) {
            if( BeaconConfig.DEBUG ) {
                Log.d( TAG, "Could not match the byte data to the IBeacon specification" );
            }
            return;
        }

        int major = ( data[startByte + 20] & 0xff ) * 0x100 + ( data[startByte + 21] & 0xff );
        int minor = ( data[startByte + 22] & 0xff ) * 0x100 + ( data[startByte + 23] & 0xff );
        int txPower = (int) data[startByte + 24]; // this one is signed

        beacon.setMinorNumber( minor );
        beacon.setMajorNumber( major );
        beacon.setTxPower( txPower );
    }
}
