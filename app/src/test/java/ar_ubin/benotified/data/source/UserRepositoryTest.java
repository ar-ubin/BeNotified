package ar_ubin.benotified.data.source;


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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class UserRepositoryTest
{
    private User mUser;
    private Credentials mCredentials = new Credentials( "dummy@test1", "Testkurt1" );

    private UserRepository mUserRepository;

    @Mock
    private UserDataSource mUserRemoteDataSource;
    @Mock
    private UserDataSource mUserLocalDataSource;

    @Mock
    private Activity mActivity;

    @Mock
    private UserDataSource.GetUserCallback mGetUserCallback;
    @Captor
    private ArgumentCaptor<UserDataSource.GetUserCallback> mGetUserCallbackCaptor;

    @Before
    public void setupTasksRepository() {
        MockitoAnnotations.initMocks( this );

        mUserRepository = new UserRepository( mUserRemoteDataSource, mUserLocalDataSource );
        mUser = new User( "Uuid1", mCredentials.getEmail() );
    }

    @Test
    public void getUserFromRemoteDataSource_storeUserfiresOnUserLoaded() {
        mUserRepository.getUser( mActivity, mCredentials, mGetUserCallback );

        mUserRepository.storeUser( mUser );
        verify( mUserRemoteDataSource ).storeUser( any( User.class ) );

        verify( mUserRemoteDataSource )
                .getUser( any( Activity.class ), any( Credentials.class ), mGetUserCallbackCaptor.capture() );
        mGetUserCallbackCaptor.getValue().onUserLoaded( mUser );

        verify( mGetUserCallback ).onUserLoaded( mUser );
    }

    @Test
    public void getUserFromRemoteDataSource_firesOnFailed() {
        Exception exception = new NullPointerException();

        mUserRepository.getUser( mActivity, mCredentials, mGetUserCallback );
        verify( mUserRemoteDataSource )
                .getUser( any( Activity.class ), any( Credentials.class ), mGetUserCallbackCaptor.capture() );

        mGetUserCallbackCaptor.getValue().onFailed( exception );
        verify( mGetUserCallback ).onFailed( exception );
    }

    @Test
    public void signOutUserFromRemoteDataSource() {
        mUserRepository.signOut();
        verify( mUserRemoteDataSource ).signOut();
    }
}
