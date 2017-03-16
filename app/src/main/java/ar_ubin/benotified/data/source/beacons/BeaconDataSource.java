package ar_ubin.benotified.data.source.beacons;


import android.support.annotation.NonNull;

import java.util.List;

import ar_ubin.benotified.data.models.Beacon;

public interface BeaconDataSource
{
    interface LoadBeaconCallback
    {
        void onBeaconLoaded( List<Beacon> beacons );

        void onDataNotAvailable();
    }

    interface GetBeaconCallback
    {
        void onBeaconLoaded( Beacon beacon );

        void onDataNotAvailable();
    }

    interface SaveBeaconCallback
    {
        void onBeaconSaved();

        void onFailed( Exception exception );
    }

    void getBeacons( @NonNull LoadBeaconCallback callback );

    void getBeacon( @NonNull int minor, @NonNull GetBeaconCallback callback );

    void saveBeacon( @NonNull Beacon beacon, @NonNull SaveBeaconCallback callback );

    void activateBeacon( @NonNull Beacon beacon );

    void activateBeacon( @NonNull int minor );

    void deleteAllBeacons();

    void deleteBeacon( @NonNull int minor );
}
