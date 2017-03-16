package ar_ubin.benotified.data.source.local;


import android.content.Context;
import android.support.annotation.NonNull;

import ar_ubin.benotified.data.models.Beacon;
import ar_ubin.benotified.data.source.beacons.BeaconDataSource;
import ar_ubin.benotified.utils.ApplicationScope;

import static com.google.common.base.Preconditions.checkNotNull;

@ApplicationScope
public class BeaconLocalDataSource implements BeaconDataSource
{
    public BeaconLocalDataSource( @NonNull Context context ) {
        checkNotNull( context );
        //TODO init DB
    }

    @Override
    public void getBeacons( @NonNull LoadBeaconCallback callback ) {

    }

    @Override
    public void getBeacon( @NonNull int minor, @NonNull GetBeaconCallback callback ) {

    }

    @Override
    public void saveBeacon( @NonNull Beacon beacon, @NonNull SaveBeaconCallback callback ) {

    }

    @Override
    public void activateBeacon( @NonNull Beacon beacon ) {

    }

    @Override
    public void activateBeacon( @NonNull int minor ) {

    }

    @Override
    public void deleteAllBeacons() {

    }

    @Override
    public void deleteBeacon( @NonNull int minor ) {

    }
}
