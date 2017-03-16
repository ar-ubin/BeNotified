package ar_ubin.benotified.tabs.messages.add;


import dagger.Module;
import dagger.Provides;

@Module
public class NewMessagePresenterModule
{
    private final NewMessageContract.View mView;

    public NewMessagePresenterModule( NewMessageContract.View view ) {
        mView = view;
    }

    @Provides
    NewMessageContract.View provideNewMessageContractView() {
        return mView;
    }
}
