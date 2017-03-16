package ar_ubin.benotified.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import ar_ubin.benotified.ActivityModule;
import ar_ubin.benotified.ApplicationComponent;
import ar_ubin.benotified.BeNotifiedApplication;
import ar_ubin.benotified.navigation.Navigator;
import ar_ubin.benotified.utils.GsonSharedPrefs;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class BaseActivity extends AppCompatActivity
{
    @Inject
    public Navigator mNavigator;

    @Inject
    public GsonSharedPrefs mGsonSharedPrefs;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }

    protected void addFragment( int containerViewId, Fragment fragment ) {
        checkNotNull( containerViewId );
        checkNotNull( fragment );
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add( containerViewId, fragment );
        fragmentTransaction.commit();
    }

    public ApplicationComponent getApplicationComponent() {
        return ( (BeNotifiedApplication) getApplication() ).getApplicationComponent();
    }

    public ActivityModule getActivityModule() {
        return new ActivityModule( this );
    }
}
