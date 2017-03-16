package ar_ubin.benotified.tabs.beacon.add;


import android.support.annotation.NonNull;

import javax.inject.Inject;

import ar_ubin.benotified.data.models.Beacon;
import ar_ubin.benotified.data.source.beacons.BeaconDataSource;
import ar_ubin.benotified.data.source.beacons.BeaconRepository;

public final class NewBeaconPresenter implements NewBeaconContract.Presenter
{
    private final BeaconRepository mBeaconRepository;

    private final NewBeaconContract.View mNewBeaconView;

    @Inject
    public NewBeaconPresenter( BeaconRepository beaconRepository, NewBeaconContract.View newBeaconView ) {
        mBeaconRepository = beaconRepository;
        mNewBeaconView = newBeaconView;
    }

    @Inject
    void setupListeners() {
        mNewBeaconView.setPresenter( this );
    }

    @Override
    public void start() {
    }

    @Override
    public void saveBeacon( @NonNull final Beacon beacon ) {
        mBeaconRepository.saveBeacon( beacon, new BeaconDataSource.SaveBeaconCallback() {
            @Override
            public void onBeaconSaved() {
                mNewBeaconView.showSavingComplete( beacon );
            }

            @Override
            public void onFailed( Exception exception ) {
                mNewBeaconView.showSavingError( exception.getMessage() );
            }
        } );
    }
}
