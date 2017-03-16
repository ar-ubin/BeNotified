
package ar_ubin.benotified.data;


import android.support.annotation.NonNull;

import ar_ubin.benotified.data.source.beacons.BeaconDataSource;

public class FakeBeaconsRemoteDataSource implements BeaconDataSource
{
    @Override
    public void getBeacons( @NonNull LoadBeaconCallback callback ) {

    }

    @Override
    public void getBeacon( @NonNull int minor, @NonNull GetBeaconCallback callback ) {

    }

    @Override
    public void saveBeacon( @NonNull Beacon beacon ) {

    }

    @Override
    public void activateBeacon( @NonNull Beacon beacon ) {

    }

    @Override
    public void activateBeacon( @NonNull int minor ) {

    }

    @Override
    public void refreshMessages() {

    }

    @Override
    public void deleteAllBeacons() {

    }

    @Override
    public void deleteBeacon( @NonNull int minor ) {

    }
}
