package ar_ubin.benotified.tabs.beacon;


import javax.inject.Inject;

import ar_ubin.benotified.data.source.beacons.BeaconRepository;

public final class BeaconPresenter implements BeaconContract.Presenter
{
    private final BeaconRepository mBeaconRepository;

    private final BeaconContract.View mBeaconView;

    @Inject
    public BeaconPresenter( BeaconRepository beaconRepository, BeaconContract.View beaconView ) {
        mBeaconRepository = beaconRepository;
        mBeaconView = beaconView;
    }

    @Inject
    void setupListeners() {
        mBeaconView.setPresenter(this);
    }

    @Override
    public void start() {
    }
}
