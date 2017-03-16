package ar_ubin.benotified.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import com.google.gson.Gson;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import javax.inject.Inject;

import ar_ubin.benotified.data.models.Beacon;
import ar_ubin.benotified.data.models.BeaconEntity;
import ar_ubin.benotified.data.models.User;

@ActivityScope
public class GsonSharedPrefs
{
    private static final String GSON_PREFS_NAME = "ar_ubin.benotified.utils.gson_prefs";

    @Retention( RetentionPolicy.CLASS )
    @StringDef( { BEACON, USER } )
    private @interface PrefsKey
    {}

    public static final String BEACON = "beacon";
    public static final String USER = "user";

    private Context mContext;
    private Gson mGson;

    @Inject
    public GsonSharedPrefs( Context context, Gson gson ) {
        this.mContext = context;
        this.mGson = gson;
    }

    private <T> T getSharedPref( @NonNull Class<T> classOfT, @PrefsKey String prefsKey ) {
        final String resultAsString = getPrefs().getString( prefsKey, "" );

        return mGson.fromJson( resultAsString, classOfT );
    }

    private SharedPreferences getPrefs() {
        return mContext.getSharedPreferences( GSON_PREFS_NAME, Context.MODE_PRIVATE );
    }

    private void setSharedPrefs( String valueToStore, @PrefsKey String prefsKey ) {
        final SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString( prefsKey, valueToStore );
        editor.apply();
    }

    public void storeUser( User user ) {
        this.setSharedPrefs( mGson.toJson( user ), USER );
    }

    public User loadUser() {
        return this.getSharedPref( User.class, USER );
    }

    public void storeBeacon( Beacon beacon ) {
        List<Beacon> beacons = loadAllBeacon();
        Iterator<Beacon> beaconIterator = beacons.iterator();

        while( beaconIterator.hasNext() ) {
            Beacon storedBeacon = beaconIterator.next();

            if( Objects.equals( beacon.getUuid(), storedBeacon.getUuid() ) ) {
                beaconIterator.remove();
            }
        }
        beacons.add( beacon );
        this.storeAllBeacon( beacons );
    }

    public Beacon loadBeacon( int minor ) throws NoSuchElementException {
        Beacon beaconToReturn = null;

        List<Beacon> beacons = loadAllBeacon();
        for( Beacon beacon : beacons ) {
            if( Objects.equals( beacon.getMinor(), minor ) ) {
                beaconToReturn = beacon;
            }
        }

        if( beaconToReturn != null ) {
            return beaconToReturn;
        } else {
            throw new NoSuchElementException();
        }
    }

    public void storeAllBeacon( List<Beacon> beacons ) {
        final BeaconEntity beaconEntity = new BeaconEntity();
        beaconEntity.beacons = beacons;
        this.setSharedPrefs( mGson.toJson( beaconEntity ), BEACON );
    }

    @NonNull
    public List<Beacon> loadAllBeacon() {
        BeaconEntity entity = getSharedPref( BeaconEntity.class, BEACON );
        return ( entity != null )? entity.beacons : new ArrayList<Beacon>();
    }
}
