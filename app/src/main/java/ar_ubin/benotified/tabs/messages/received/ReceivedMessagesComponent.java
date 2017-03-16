package ar_ubin.benotified.tabs.messages.received;


import ar_ubin.benotified.ApplicationComponent;
import ar_ubin.benotified.utils.ActivityScope;
import dagger.Component;


@ActivityScope
@Component( dependencies = ApplicationComponent.class, modules = { ReceivedMessagesPresenterModule.class } )
public interface ReceivedMessagesComponent
{
    void inject( ReceivedMessagesFragment receivedMessagesFragment );
}

