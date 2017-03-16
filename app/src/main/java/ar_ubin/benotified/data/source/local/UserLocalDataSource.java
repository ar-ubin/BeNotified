package ar_ubin.benotified.data.source.local;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import ar_ubin.benotified.data.models.Credentials;
import ar_ubin.benotified.data.models.User;
import ar_ubin.benotified.data.source.user.UserDataSource;
import ar_ubin.benotified.utils.ApplicationScope;

import static com.google.common.base.Preconditions.checkNotNull;

@ApplicationScope
public class UserLocalDataSource implements UserDataSource
{
    public UserLocalDataSource( @NonNull Context context ) {
        checkNotNull( context );
        //TODO init DB
    }

    @Override
    public void getUser( @NonNull Activity activity, @NonNull Credentials credentials,
                         @NonNull GetUserCallback callback ) {

    }

    @Override
    public void updateUser( @NonNull User user ) {

    }

    @Override
    public void storeUser( @NonNull User user ) {

    }

    @Override
    public void reloadUser() {

    }

    @Override
    public void signOut() {

    }

    @Override
    public void deleteUser() {

    }
}
