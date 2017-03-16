package ar_ubin.benotified.tabs.messages.add;


import ar_ubin.benotified.ActivityComponent;
import ar_ubin.benotified.ActivityModule;
import ar_ubin.benotified.ApplicationComponent;
import ar_ubin.benotified.utils.ActivityScope;
import dagger.Component;


@ActivityScope
@Component( dependencies = ApplicationComponent.class, modules = { ActivityModule.class, NewMessagePresenterModule.class} )
public interface NewMessageComponent extends ActivityComponent
{
    void inject( NewMessageActivity newMessageActivity );
}
