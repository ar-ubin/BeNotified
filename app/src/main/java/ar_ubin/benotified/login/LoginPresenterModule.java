package ar_ubin.benotified.login;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginPresenterModule
{
    private final LoginContract.View mView;

    public LoginPresenterModule( LoginContract.View view ) {
        mView = view;
    }

    @Provides
    LoginContract.View provideLoginContractView() {
        return mView;
    }
}
