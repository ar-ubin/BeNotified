package ar_ubin.benotified.data.source.beacons;


import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import ar_ubin.benotified.data.models.Beacon;
import ar_ubin.benotified.data.source.Local;
import ar_ubin.benotified.data.source.Remote;
import ar_ubin.benotified.utils.ApplicationScope;

import static com.google.common.base.Preconditions.checkNotNull;

@ApplicationScope
public class BeaconRepository implements BeaconDataSource
{
    private final BeaconDataSource mBeaconsRemoteDataSource;
    private final BeaconDataSource mBeaconsLocalDataSource;

    @Inject
    public BeaconRepository( @Remote BeaconDataSource beaconsRemoteDataSource,
                             @Local BeaconDataSource beaconsLocalDataSource ) {
        mBeaconsRemoteDataSource = beaconsRemoteDataSource;
        mBeaconsLocalDataSource = beaconsLocalDataSource;
    }

    @Override
    public void getBeacons( @NonNull final LoadBeaconCallback callback ) {
        checkNotNull( callback );
        mBeaconsRemoteDataSource.getBeacons( new LoadBeaconCallback()
        {
            @Override
            public void onBeaconLoaded( List<Beacon> beacons ) {
                callback.onBeaconLoaded( beacons );
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        } );
    }

    @Override
    public void getBeacon( int minor, @NonNull final GetBeaconCallback callback ) {
        checkNotNull( callback );
        mBeaconsRemoteDataSource.getBeacon( minor, new GetBeaconCallback()
        {
            @Override
            public void onBeaconLoaded( Beacon beacon ) {
                callback.onBeaconLoaded( beacon );
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        } );
    }

    @Override
    public void saveBeacon( @NonNull Beacon beacon, @NonNull SaveBeaconCallback callback ) {
        checkNotNull( beacon );
        checkNotNull( callback );
        mBeaconsRemoteDataSource.saveBeacon( beacon, callback );
    }

    @Override
    public void activateBeacon( @NonNull Beacon beacon ) {
        checkNotNull( beacon );
        mBeaconsRemoteDataSource.activateBeacon( beacon );
    }

    @Override
    public void activateBeacon( int minor ) {
        mBeaconsRemoteDataSource.activateBeacon( minor );
    }

    @Override
    public void deleteAllBeacons() {
        mBeaconsRemoteDataSource.deleteAllBeacons();
    }

    @Override
    public void deleteBeacon( int minor ) {
        mBeaconsRemoteDataSource.deleteBeacon( minor );
    }
}
