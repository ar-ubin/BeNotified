package ar_ubin.benotified.tabs.messages.details;


import dagger.Module;
import dagger.Provides;

@Module
public class MessageDetailsPresenterModule
{
    private final MessageDetailsContract.View mView;

    public MessageDetailsPresenterModule( MessageDetailsContract.View view ) {
        mView = view;
    }

    @Provides
    MessageDetailsContract.View provideMessageDetailsView() {
        return mView;
    }
}
