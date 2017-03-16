package ar_ubin.benotified.tabs.messages.own;


import dagger.Module;
import dagger.Provides;


@Module
public class OwnMessagesPresenterModule
{
    private final OwnMessagesContract.View mView;

    public OwnMessagesPresenterModule( OwnMessagesContract.View view ) {
        mView = view;
    }

    @Provides
    OwnMessagesContract.View provideMessagesContractView() {
        return mView;
    }
}

