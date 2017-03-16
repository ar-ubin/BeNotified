package ar_ubin.benotified.data.models;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.UUID;

public class Message implements Serializable
{
    @NonNull
    private String mUuid = "";
    @Nullable
    private String mTitle = "";
    @Nullable
    private String mDescription = "";
    @Nullable
    private String mAuthorId;
    private int mBeaconMinor;
    private long mTimestamp;
    private boolean mCompleted = false;

    public Message() {}

    public Message( @Nullable String title, @Nullable int beaconMinor ) {
        this( UUID.randomUUID().toString(), title, beaconMinor, "" );
    }

    public Message( @NonNull String uuid, @Nullable String title, @Nullable int beaconMinor ) {
        this( uuid, title, beaconMinor, "" );
    }

    public Message( @NonNull String uuid, @Nullable String title, @Nullable int beaconMinor,
                    @Nullable String description ) {
        this.mUuid = uuid;
        this.mTitle = title;
        this.mBeaconMinor = beaconMinor;
        this.mDescription = description;
    }

    @NonNull
    public String getUuid() {
        return mUuid;
    }

    public void setUuid( @NonNull String mUuid ) {
        this.mUuid = mUuid;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    public void setTitle( @Nullable String mTitle ) {
        this.mTitle = mTitle;
    }

    public void setDescription( @Nullable String mDescription ) {
        this.mDescription = mDescription;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }

    @Nullable
    public String getAuthor() {
        return mAuthorId;
    }

    public void setAuthor( @Nullable String authorId ) {
        this.mAuthorId = authorId;
    }

    @Nullable
    public int getBeacon() {
        return mBeaconMinor;
    }

    public void setBeacon( @Nullable int beaconMinor ) {
        this.mBeaconMinor = beaconMinor;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp( long mTimestamp ) {
        this.mTimestamp = mTimestamp;
    }

    @Nullable
    public boolean isCompleted() {
        return mCompleted;
    }

    @Nullable
    public boolean isActive() {
        return !mCompleted;
    }

    public void setCompleted( boolean mCompleted ) {
        this.mCompleted = mCompleted;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) return true;
        if( o == null || getClass() != o.getClass() ) return false;
        Message message = (Message) o;
        return Objects.equal( mUuid, message.mUuid ) && Objects.equal( mTitle, message.mTitle );
    }

    @Override
    public int hashCode() {
        return Objects.hashCode( mUuid, mTitle, mDescription );
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append( "***** Message *****\n" );
        stringBuilder.append( "uuid = " + this.getUuid() + "\n" );
        stringBuilder.append( "title = " + this.getTitle() + "\n" );
        stringBuilder.append( "description = " + this.getDescription() + "\n" );
        stringBuilder.append( "author = " + this.getAuthor() + "\n" );
        stringBuilder.append( "beacon = " + this.getBeacon() + "\n" );
        stringBuilder.append( "complete = " + this.isCompleted() + "\n" );
        stringBuilder.append( "*******************************" );

        return stringBuilder.toString();
    }
}
