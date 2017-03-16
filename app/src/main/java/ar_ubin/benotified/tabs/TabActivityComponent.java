package ar_ubin.benotified.tabs;


import ar_ubin.benotified.ActivityComponent;
import ar_ubin.benotified.ActivityModule;
import ar_ubin.benotified.ApplicationComponent;
import ar_ubin.benotified.utils.ActivityScope;
import dagger.Component;

@ActivityScope
@Component( dependencies = ApplicationComponent.class, modules = { ActivityModule.class, TabActivityModule.class } )
public interface TabActivityComponent extends ActivityComponent
{
    void inject( TabActivity tabActivity );
}