package ar_ubin.benotified.data;


import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;

import ar_ubin.benotified.data.source.user.UserDataSource;

public class FakeUserRemoteDataSource implements UserDataSource
{
    @Override
    public void getUser( @NonNull GetUserCallback callback ) {

    }

    @Override
    public void updateUser( @NonNull FirebaseUser user ) {

    }

    @Override
    public void reloadUser() {

    }

    @Override
    public void deleteUser() {

    }
}
