package ar_ubin.benotified.data.models;


import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Beacon implements Serializable
{
    private int mMinor;
    private int mMajor;
    @Nullable
    private String mUuid;
    @Nullable
    private String mName;
    @Nullable
    private String mLocation;
    private int mColor;
    private int mRadius;

    public Beacon() {}

    public Beacon( int minor, int major, @Nullable String uuid ) {
        mMinor = minor;
        mMajor = major;
        mUuid = uuid;
    }

    public int getMinor() {
        return mMinor;
    }

    public void setMinor( int minor ) {
        this.mMinor = minor;
    }

    public int getMajor() {
        return mMajor;
    }

    public void setMajor( int major ) {
        this.mMajor = major;
    }

    @Nullable
    public String getUuid() {
        return mUuid;
    }

    public void setUuid( @Nullable String uuid ) {
        this.mUuid = uuid;
    }

    @Nullable
    public String getName() {
        return mName;
    }

    public void setName( @Nullable String name ) {
        this.mName = name;
    }

    @Nullable
    public String getLocation() {
        return mLocation;
    }

    public void setLocation( @Nullable String location ) {
        this.mLocation = location;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor( int color ) {
        this.mColor = color;
    }

    @Retention( RetentionPolicy.CLASS )
    @IntDef( { RADIUS_IMMEDIATE, RADIUS_NEAR, RADIUS_FAR } )
    private @interface BeaconRadius
    {}

    public static final int RADIUS_IMMEDIATE = 0; // Radius < 20cm
    public static final int RADIUS_NEAR = 1; // Radius between 20cm-2m
    public static final int RADIUS_FAR = 2; // Radius between 2m-50m

    @BeaconRadius
    public int getRadius() {
        return mRadius;
    }

    public void setRadius( @BeaconRadius int radius ) {
        this.mRadius = radius;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) return true;
        if( o == null || getClass() != o.getClass() ) return false;
        Beacon beacon = (Beacon) o;
        return Objects.equal( mMinor, beacon.mMinor ) &&
                Objects.equal( mMajor, beacon.mMajor ) &&
                Objects.equal( mUuid, beacon.mUuid );
    }

    @Override
    public int hashCode() {
        return Objects.hashCode( mMinor, mMajor, mName );
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append( "***** Beacon *****\n" );
        stringBuilder.append( "minor = " + this.getMinor() + "\n" );
        stringBuilder.append( "major = " + this.getMajor() + "\n" );
        stringBuilder.append( "uuid = " + this.getUuid() + "\n" );
        stringBuilder.append( "name = " + this.getName() + "\n" );
        stringBuilder.append( "location = " + this.getLocation() + "\n" );
        stringBuilder.append( "color = " + this.getColor() + "\n" );
        stringBuilder.append( "radius = " + this.getRadius() + "\n" );
        stringBuilder.append( "*******************************" );

        return stringBuilder.toString();
    }
}
