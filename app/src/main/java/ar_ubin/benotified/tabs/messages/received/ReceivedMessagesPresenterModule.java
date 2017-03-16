package ar_ubin.benotified.tabs.messages.received;


import dagger.Module;
import dagger.Provides;


@Module
public class ReceivedMessagesPresenterModule
{
    private final ReceivedMessagesContract.View mView;

    public ReceivedMessagesPresenterModule( ReceivedMessagesContract.View view ) {
        mView = view;
    }

    @Provides
    ReceivedMessagesContract.View provideMessagesContractView() {
        return mView;
    }
}

