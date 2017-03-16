package ar_ubin.benotified.tabs.messages.own;


import ar_ubin.benotified.ApplicationComponent;
import ar_ubin.benotified.utils.ActivityScope;
import dagger.Component;


@ActivityScope
@Component( dependencies = ApplicationComponent.class, modules = { OwnMessagesPresenterModule.class } )
public interface OwnMessagesComponent
{
    void inject( OwnMessagesFragment ownMessagesFragment );
}