package ar_ubin.benotified.data.models;


import android.support.annotation.Nullable;

public class User
{
    @Nullable
    private String mUuid;
    @Nullable
    private String mEmail;
    @Nullable
    private String mUsername;

    public User() {}

    public User( @Nullable String uuid, @Nullable String email ) {
        this( uuid, email, usernameFromEmail( email ) );
    }

    public User( @Nullable String uuid, @Nullable String email, @Nullable String username ) {
        this.mUuid = uuid;
        this.mEmail = email;
        this.mUsername = username;
    }

    @Nullable
    public String getUuid() {
        return mUuid;
    }

    public void setUuid( @Nullable String uuid ) {
        this.mUuid = uuid;
    }

    @Nullable
    public String getEmail() {
        return mEmail;
    }

    public void setEmail( @Nullable String email ) {
        this.mEmail = email;
    }

    @Nullable
    public String getUsername() {
        return mUsername;
    }

    public void setUsername( @Nullable String username ) {
        this.mUsername = username;
    }

    private static String usernameFromEmail( String email ) {
        if( email.contains( "@" ) ) {
            email = email.split( "@" )[0];
        }
        return email;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append( "***** User *****\n" );
        stringBuilder.append( "uuid = " + this.getUuid() + "\n" );
        stringBuilder.append( "email = " + this.getEmail() + "\n" );
        stringBuilder.append( "name = " + this.getUsername() + "\n" );
        stringBuilder.append( "*******************************" );

        return stringBuilder.toString();
    }
}
