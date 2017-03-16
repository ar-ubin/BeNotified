package ar_ubin.benotified.data.source.user;


import android.app.Activity;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import ar_ubin.benotified.data.models.Credentials;
import ar_ubin.benotified.data.models.User;
import ar_ubin.benotified.data.source.Local;
import ar_ubin.benotified.data.source.Remote;
import ar_ubin.benotified.utils.ApplicationScope;

import static com.google.common.base.Preconditions.checkNotNull;

@ApplicationScope
public class UserRepository implements UserDataSource
{
    private final UserDataSource mUserRemoteDataSource;
    private final UserDataSource mUserLocalDataSource;

    @Inject
    public UserRepository( @Remote UserDataSource userRemoteDataSource, @Local UserDataSource userLocalDataSource ) {
        mUserRemoteDataSource = userRemoteDataSource;
        mUserLocalDataSource = userLocalDataSource;
    }

    @Override
    public void getUser( @NonNull Activity activity, @NonNull Credentials credentials, @NonNull final GetUserCallback callback ) {
        checkNotNull( activity );
        checkNotNull( credentials.getEmail() );
        checkNotNull( credentials.getPassword() );
        checkNotNull( callback );

        mUserRemoteDataSource.getUser( activity, credentials, new GetUserCallback()
        {
            @Override
            public void onUserLoaded( User user ) {
                callback.onUserLoaded( user );
            }

            @Override
            public void onFailed( Exception exception ) {
                callback.onFailed( exception );
            }
        } );
    }

    @Override
    public void updateUser( @NonNull User user ) {
        checkNotNull( user );
        mUserRemoteDataSource.updateUser( user );
    }

    @Override
    public void storeUser( @NonNull User user ) {
        checkNotNull( user );
        mUserRemoteDataSource.storeUser( user );
    }

    @Override
    public void reloadUser() {
        mUserRemoteDataSource.reloadUser();
    }

    @Override
    public void signOut() {
        mUserRemoteDataSource.signOut();
    }

    @Override
    public void deleteUser() {
        mUserRemoteDataSource.deleteUser();
    }
}
