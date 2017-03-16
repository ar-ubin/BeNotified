package ar_ubin.benotified.login;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import ar_ubin.benotified.R;
import ar_ubin.benotified.base.BaseActivity;
import ar_ubin.benotified.data.models.User;
import ar_ubin.benotified.navigation.Navigator;


public class LoginActivity extends BaseActivity implements EmailPasswordFragment.AuthListener
{
    private final String TAG = LoginActivity.class.getSimpleName();
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    @Inject
    LoginPresenter mLoginPresenter;
    EmailPasswordFragment mEmailPasswordFragment;

    public static Intent getCallingIntent( Context context ) {
        Intent callingIntent = new Intent( context, LoginActivity.class );
        return callingIntent;
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        getSupportActionBar().hide();

        ImageView loginImg = (ImageView) findViewById( R.id.login_img );
        Picasso.with( this ).load( R.drawable.beacon ).fit().centerCrop().into( loginImg );

        mEmailPasswordFragment = (EmailPasswordFragment) getSupportFragmentManager()
                .findFragmentById( R.id.fragmentContainer );

        if( mEmailPasswordFragment == null ) {
            mEmailPasswordFragment = EmailPasswordFragment.newInstance();
            addFragment( R.id.fragmentContainer, mEmailPasswordFragment );
        }
        initializeInjector();
        requestLocationPermision();
    }

    private void requestLocationPermision() {
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            // Android M Permission check 
            if( this.checkSelfPermission(
                    Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                final AlertDialog.Builder builder = new AlertDialog.Builder( this );
                builder.setTitle( R.string.title_request_location_access );
                builder.setMessage( R.string.description_request_location_access );
                builder.setPositiveButton( android.R.string.ok, null );
                builder.setOnDismissListener( new DialogInterface.OnDismissListener()
                {
                    @Override
                    public void onDismiss( DialogInterface dialogInterface ) {
                        requestPermissions( new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION },
                                PERMISSION_REQUEST_COARSE_LOCATION );
                    }
                } );
                builder.show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult( int requestCode, String permissions[], int[] grantResults ) {
        switch( requestCode ) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if( grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                    Log.d( TAG, "Location permission granted!" );
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder( this );
                    builder.setTitle( R.string.title_location_access_denied );
                    builder.setMessage( R.string.description_location_access_denied );
                    builder.setPositiveButton( R.string.grant_access, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick( DialogInterface dialogInterface, int i ) {
                            requestPermissions( new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION },
                                    PERMISSION_REQUEST_COARSE_LOCATION );
                        }
                    } );
                    builder.setNegativeButton( R.string.close_app, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick( DialogInterface dialogInterface, int i ) {
                            finish();
                        }
                    } );
                    builder.show();
                }
                return;
            }
        }
    }

    private void initializeInjector() {
        DaggerLoginComponent.builder().applicationComponent( getApplicationComponent() )
                .activityModule( getActivityModule() )
                .loginPresenterModule( new LoginPresenterModule( mEmailPasswordFragment ) ).build().inject( this );
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
        if( resultCode == RESULT_OK ) {
            if( requestCode == Navigator.LOGIN_SESSION ) {
                mLoginPresenter.signOut();
            }
        } else {
            finish();
        }
    }

    @Override
    public void onLogedIn( User user ) {
        mGsonSharedPrefs.storeUser( user );
        mNavigator.startLoginSession( this );
    }
}