package ar_ubin.benotified.data.models;


import android.support.annotation.Nullable;

public final class Credentials
{
    @Nullable
    private final String mEmail;

    @Nullable
    private final String mPassword;

    public Credentials( @Nullable String email, @Nullable String password ) {
        mEmail = email;
        mPassword = password;
    }

    @Nullable
    public String getEmail() {
        return mEmail;
    }

    @Nullable
    public String getPassword() {
        return mPassword;
    }
}
