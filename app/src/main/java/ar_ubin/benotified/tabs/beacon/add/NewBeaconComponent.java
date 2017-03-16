package ar_ubin.benotified.tabs.beacon.add;


import ar_ubin.benotified.ActivityComponent;
import ar_ubin.benotified.ActivityModule;
import ar_ubin.benotified.ApplicationComponent;
import ar_ubin.benotified.utils.ActivityScope;
import dagger.Component;

@ActivityScope
@Component( dependencies = ApplicationComponent.class, modules = { ActivityModule.class, NewBeaconPresenterModule.class } )
public interface NewBeaconComponent extends ActivityComponent
{
    void inject( NewBeaconActivity newBeaconActivity );
}