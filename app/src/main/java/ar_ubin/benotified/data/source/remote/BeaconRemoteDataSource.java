package ar_ubin.benotified.data.source.remote;


import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ar_ubin.benotified.data.models.Beacon;
import ar_ubin.benotified.data.source.beacons.BeaconDataSource;
import ar_ubin.benotified.utils.ApplicationScope;

import static ar_ubin.benotified.data.Constants.DB_BEACONS;

@ApplicationScope
public class BeaconRemoteDataSource implements BeaconDataSource
{
    private final DatabaseReference mDatabase;
    private final FirebaseUser mCurrentUser;

    public BeaconRemoteDataSource() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void getBeacons( @NonNull LoadBeaconCallback callback ) {

    }

    @Override
    public void getBeacon( @NonNull int minor, @NonNull GetBeaconCallback callback ) {

    }

    @Override
    public void saveBeacon( @NonNull Beacon beacon, @NonNull final SaveBeaconCallback callback ) {
        mDatabase.child( DB_BEACONS ).child( String.valueOf( beacon.getMinor() ) ).setValue( beacon )
                .addOnCompleteListener( new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete( @NonNull Task<Void> task ) {
                        if( task.isSuccessful() ) {
                            callback.onBeaconSaved();
                        } else {
                            callback.onFailed( task.getException() );
                        }
                    }
                } );
    }

    @Override
    public void activateBeacon( @NonNull Beacon beacon ) {

    }

    @Override
    public void activateBeacon( @NonNull int minor ) {

    }

    @Override
    public void deleteAllBeacons() {

    }

    @Override
    public void deleteBeacon( @NonNull int minor ) {

    }
}
