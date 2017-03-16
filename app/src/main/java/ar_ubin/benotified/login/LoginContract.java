package ar_ubin.benotified.login;


import android.app.Activity;
import android.support.annotation.NonNull;

import ar_ubin.benotified.base.BasePresenter;
import ar_ubin.benotified.base.BaseView;
import ar_ubin.benotified.data.models.User;

public interface LoginContract
{
    interface View extends BaseView<LoginContract.Presenter>
    {
        void isAuthorized( User user );

        void showLoginComplete( User user );

        void showLoadingProgress();

        void hideLoadingProgress();

        void showLoadingLoginError( String message );
    }

    interface Presenter extends BasePresenter
    {
        void login( @NonNull Activity activity, @NonNull String email, @NonNull String password );

        void signOut();
    }
}