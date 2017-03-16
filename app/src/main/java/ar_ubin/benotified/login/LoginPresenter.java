package ar_ubin.benotified.login;


import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import ar_ubin.benotified.data.models.Credentials;
import ar_ubin.benotified.data.models.User;
import ar_ubin.benotified.data.source.user.UserDataSource;
import ar_ubin.benotified.data.source.user.UserRepository;

public final class LoginPresenter implements LoginContract.Presenter
{
    private final UserRepository mUserRepository;

    private final LoginContract.View mLoginView;

    @Inject
    public LoginPresenter( UserRepository userRepository, LoginContract.View loginView ) {
        mUserRepository = userRepository;
        mLoginView = loginView;
    }

    @Inject
    void setupListeners() {
        mLoginView.setPresenter(this);
    }

    @Override
    public void start() {
        if( FirebaseAuth.getInstance().getCurrentUser() != null){
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final User authorizedUser = new User();
            authorizedUser.setUuid( user.getUid() );
            authorizedUser.setEmail( user.getEmail() );
            mLoginView.showLoginComplete( authorizedUser );
        }
    }

    @Override
    public void login( @NonNull Activity activity, @NonNull String email, @NonNull String password ) {
        Credentials credentials = new Credentials( email, password );

        mLoginView.showLoadingProgress();
        mUserRepository.getUser( activity, credentials, new UserDataSource.GetUserCallback()
        {
            @Override
            public void onUserLoaded( User user ) {
                mLoginView.showLoginComplete( user );
                mLoginView.hideLoadingProgress();
            }

            @Override
            public void onFailed( Exception exception ) {
                mLoginView.showLoadingLoginError( exception.getMessage() );
                mLoginView.hideLoadingProgress();
            }
        } );
    }

    @Override
    public void signOut() {
        mUserRepository.signOut();
    }
}
