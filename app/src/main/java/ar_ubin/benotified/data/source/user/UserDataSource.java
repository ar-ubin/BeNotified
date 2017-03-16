package ar_ubin.benotified.data.source.user;


import android.app.Activity;
import android.support.annotation.NonNull;

import ar_ubin.benotified.data.models.Credentials;
import ar_ubin.benotified.data.models.User;

public interface UserDataSource
{
    interface GetUserCallback
    {
        void onUserLoaded( User user );

        void onFailed( Exception exception );
    }

    void getUser( @NonNull Activity activity, @NonNull Credentials credentials, @NonNull GetUserCallback callback );

    void updateUser( @NonNull User user );

    void storeUser( @NonNull User user );

    void reloadUser();

    void signOut();

    void deleteUser();
}
