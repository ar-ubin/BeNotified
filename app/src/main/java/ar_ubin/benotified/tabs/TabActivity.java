package ar_ubin.benotified.tabs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import ar_ubin.benotified.R;
import ar_ubin.benotified.base.BaseActivity;
import ar_ubin.benotified.data.models.Beacon;
import ar_ubin.benotified.data.models.Message;
import ar_ubin.benotified.navigation.Navigator;
import ar_ubin.benotified.tabs.adapter.SectionsPagerAdapter;
import ar_ubin.benotified.tabs.beacon.BeaconFragment;
import ar_ubin.benotified.tabs.messages.own.OwnMessagesFragment;
import ar_ubin.benotified.tabs.messages.received.ReceivedMessagesFragment;

public class TabActivity extends BaseActivity implements ViewPager.OnPageChangeListener,
        OwnMessagesFragment.OwnMessagesListener, ReceivedMessagesFragment.ReceivedMessagesListener,
        BeaconFragment.BeaconListener
{
    @Inject
    SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private FloatingActionButton mFab;

    public static Intent getCallingIntent( Context context ) {
        return new Intent( context, TabActivity.class );
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tab );
        initializeInjector();

        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        mViewPager = (ViewPager) findViewById( R.id.container );
        mViewPager.setAdapter( mSectionsPagerAdapter );
        mViewPager.addOnPageChangeListener( this );

        TabLayout tabLayout = (TabLayout) findViewById( R.id.tabs );
        tabLayout.setupWithViewPager( mViewPager );

        mFab = (FloatingActionButton) findViewById( R.id.fab );
        mFab.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view ) {

                Snackbar.make( view, "Position: " + mViewPager.getCurrentItem(), Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();

                onFabClicked( mViewPager.getCurrentItem() );
            }
        } );
    }

    private void onFabClicked( int position ) {
        if( position == SectionsPagerAdapter.OWN_MESSAGES_FRAGMENT ) {
            mNavigator.navigateToNewMessage( this );
        } else
            if( position == SectionsPagerAdapter.BEACON_FRAGMENT ) {
                mNavigator.navigateToNewBeacon( this );
            }
    }

    @Override
    public void onPageSelected( int position ) {
        if( position == SectionsPagerAdapter.RECEIVED_MESSAGES_FRAGMENT ) {
            mFab.setVisibility( View.GONE );
        } else {
            mFab.setVisibility( View.VISIBLE );
        }
    }

    @Override
    public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels ) {}

    @Override
    public void onPageScrollStateChanged( int state ) {}

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
        if( resultCode == RESULT_OK ) {
            switch( requestCode ) {
                case Navigator.CREATE_MESSAGE:
                    Log.d( "RESULT OK", "MESSAGE CREATED" );
                    break;
                case Navigator.CREATE_BEACON:
                    Log.d( "RESULT OK", "BEACON CREATED" );
                    break;
                case Navigator.MESSAGE_DETAILS:
                    Log.d( "RESULT OK", "MESSAGE DETAILS" );
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.menu_tab, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        int id = item.getItemId();

        if( id == R.id.action_signout ) {
            mNavigator.finishActivityWithResult( this );
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initializeInjector() {
        DaggerTabActivityComponent.builder().applicationComponent( getApplicationComponent() )
                .activityModule( getActivityModule() ).tabActivityModule( new TabActivityModule( this ) ).build()
                .inject( this );
    }

    @Override
    public void showOwnMessage( Message message ) {
        mNavigator.navigateToMessageDetails( this, message );
    }

    @Override
    public void editOwnMessage( Message message ) {
        mNavigator.navigateToNewMessage( this, message );
    }

    @Override
    public void showReceivedMessage( Message message ) {
        mNavigator.navigateToMessageDetails( this, message );
    }

    @Override
    public void showBeacon( Beacon beacon ) {

    }

    @Override
    public void editBeacon( Beacon beacon ) {
        mNavigator.navigateToNewBeacon( this, beacon );
    }
}
