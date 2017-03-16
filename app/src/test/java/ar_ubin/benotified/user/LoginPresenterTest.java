package ar_ubin.benotified.user;


import android.app.Activity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ar_ubin.benotified.data.models.Credentials;
import ar_ubin.benotified.data.models.User;
import ar_ubin.benotified.data.source.user.UserDataSource;
import ar_ubin.benotified.data.source.user.UserRepository;
import ar_ubin.benotified.login.LoginContract;
import ar_ubin.benotified.login.LoginPresenter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class LoginPresenterTest
{
    private User mUser;
    private Credentials mCredentials = new Credentials( "dummy@test1", "Testkurt1" );

    @Mock
    private UserRepository mUserRepository;
    @Mock
    private LoginContract.View mLoginView;
    @Mock
    private Activity mActivity;

    private LoginPresenter mLoginPresenter;

    @Captor
    private ArgumentCaptor<UserDataSource.GetUserCallback> mGetUserCallbackCaptor;

    @Before
    public void setupTasksPresenter() {
        MockitoAnnotations.initMocks( this );
        mLoginPresenter = new LoginPresenter( mUserRepository, mLoginView );

        mUser = new User( "Uuid1", mCredentials.getEmail() );
    }

    @Test
    public void loginUser_showsLoginComplete() {
        mLoginPresenter.login( mActivity, mCredentials.getEmail(), mCredentials.getPassword() );
        verify( mLoginView ).showLoadingProgress();

        verify( mUserRepository )
                .getUser( any( Activity.class ), any( Credentials.class ), mGetUserCallbackCaptor.capture() );
        mGetUserCallbackCaptor.getValue().onUserLoaded( mUser );
        verify( mLoginView ).showLoginComplete( mUser );
        verify( mLoginView ).hideLoadingProgress();
    }

    @Test
    public void loginUser_showsLoginFailed() {
        Exception exception = new NullPointerException();

        mLoginPresenter.login( mActivity, "", "" );
        verify( mLoginView ).showLoadingProgress();

        verify( mUserRepository )
                .getUser( any( Activity.class ), any( Credentials.class ), mGetUserCallbackCaptor.capture() );
        mGetUserCallbackCaptor.getValue().onFailed( exception );
        verify( mLoginView ).showLoadingLoginError( exception.getMessage() );
        verify( mLoginView ).hideLoadingProgress();
    }

    @Test
    public void signOutUserFromRepository() {
        mLoginPresenter.signOut();
        verify( mUserRepository ).signOut();
    }
}
