package ar_ubin.benotified.tabs.beacon.add;


import dagger.Module;
import dagger.Provides;

@Module
public class NewBeaconPresenterModule
{
    private final NewBeaconContract.View mView;

    public NewBeaconPresenterModule( NewBeaconContract.View view ) {
        mView = view;
    }

    @Provides
    NewBeaconContract.View provideNewBeaconContractView() {
        return mView;
    }
}
