package ar_ubin.benotified.tabs.beacon;


import ar_ubin.benotified.ApplicationComponent;
import ar_ubin.benotified.utils.ActivityScope;
import dagger.Component;

@ActivityScope
@Component( dependencies = ApplicationComponent.class, modules = { BeaconPresenterModule.class } )
public interface BeaconComponent
{
    void inject( BeaconFragment beaconFragment );
}

