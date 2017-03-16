package ar_ubin.benotified.tabs.messages.details;


import ar_ubin.benotified.ActivityComponent;
import ar_ubin.benotified.ActivityModule;
import ar_ubin.benotified.ApplicationComponent;
import ar_ubin.benotified.utils.ActivityScope;
import dagger.Component;

@ActivityScope
@Component( dependencies = ApplicationComponent.class, modules = { ActivityModule.class, MessageDetailsPresenterModule.class } )
public interface MessageDetailsComponent extends ActivityComponent
{
    void inject( MessageDetailsActivity messageDetailsActivity );
}