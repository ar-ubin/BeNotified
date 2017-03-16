package ar_ubin.benotified.tabs.beacon.add;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import ar_ubin.benotified.R;
import ar_ubin.benotified.base.BaseActivity;
import ar_ubin.benotified.data.models.Beacon;

public class NewBeaconActivity extends BaseActivity implements NewBeaconFragment.CreateBeaconListener
{
    @Inject
    NewBeaconPresenter mNewBeaconPresenter;
    NewBeaconFragment mNewBeaconFragment;

    public static Intent getCallingIntent( Context context ) {
        return new Intent( context, NewBeaconActivity.class );
    }

    public static Intent getCallingIntent( Context context, Beacon beacon ) {
        Intent callingIntent = new Intent( context, NewBeaconActivity.class );
        callingIntent.putExtra( NewBeaconFragment.ARGUMENT_EDIT_BEACON, beacon );
        return callingIntent;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_new_beacon );

        mNewBeaconFragment = (NewBeaconFragment) getSupportFragmentManager()
                .findFragmentById( R.id.new_beacon_container );

        addNewBeaconFragment();
        initializeInjector();
    }

    private void addNewBeaconFragment() {
        Beacon beacon = new Beacon();
        if( getIntent() != null ) {
            beacon = (Beacon) getIntent().getSerializableExtra( NewBeaconFragment.ARGUMENT_EDIT_BEACON );
        }

        if( mNewBeaconFragment == null ) {
            mNewBeaconFragment = NewBeaconFragment.newInstance( beacon );
            addFragment( R.id.new_beacon_container, mNewBeaconFragment );
        }
    }

    private void initializeInjector() {
        DaggerNewBeaconComponent.builder().applicationComponent( getApplicationComponent() )
                .activityModule( getActivityModule() )
                .newBeaconPresenterModule( new NewBeaconPresenterModule( mNewBeaconFragment ) ).build().inject( this );
    }

    @Override
    public void onBeaconCreated( Beacon beacon ) {
        mGsonSharedPrefs.storeBeacon( beacon );
        mNavigator.finishActivityWithResult( this );
    }
}
