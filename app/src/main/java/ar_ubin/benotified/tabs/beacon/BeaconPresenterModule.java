package ar_ubin.benotified.tabs.beacon;


import dagger.Module;
import dagger.Provides;

@Module
public class BeaconPresenterModule
{
    private final BeaconContract.View mView;

    public BeaconPresenterModule( BeaconContract.View view ) {
        mView = view;
    }

    @Provides
    BeaconContract.View provideBeaconContractView() {
        return mView;
    }
}

