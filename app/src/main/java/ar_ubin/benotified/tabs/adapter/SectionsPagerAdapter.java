package ar_ubin.benotified.tabs.adapter;


import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

import ar_ubin.benotified.R;
import ar_ubin.benotified.tabs.TabActivity;
import ar_ubin.benotified.tabs.beacon.BeaconFragment;
import ar_ubin.benotified.tabs.messages.own.OwnMessagesFragment;
import ar_ubin.benotified.tabs.messages.received.ReceivedMessagesFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter
{
    private Context mContext;

    public static final int OWN_MESSAGES_FRAGMENT = 0;
    public static final int RECEIVED_MESSAGES_FRAGMENT = 1;
    public static final int BEACON_FRAGMENT = 2;

    @Retention( RetentionPolicy.CLASS )
    @IntDef( { BEACON_FRAGMENT, OWN_MESSAGES_FRAGMENT, RECEIVED_MESSAGES_FRAGMENT } )
    public @interface PagerFragment
    {}

    @Inject
    public SectionsPagerAdapter( TabActivity context, FragmentManager fm ) {
        super( fm );
        this.mContext = context;
    }

    @Override
    public Fragment getItem( @PagerFragment int position ) {
        switch( position ) {
            case OWN_MESSAGES_FRAGMENT:
                return OwnMessagesFragment.newInstance();
            case RECEIVED_MESSAGES_FRAGMENT:
                return ReceivedMessagesFragment.newInstance();
            case BEACON_FRAGMENT:
                return BeaconFragment.newInstance();
            default:
                return new Fragment();
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle( @PagerFragment int position ) {
        switch( position ) {
            case OWN_MESSAGES_FRAGMENT:
                return mContext.getString( R.string.notification_list_title );
            case RECEIVED_MESSAGES_FRAGMENT:
                return mContext.getString( R.string.received_notifications_title );
            case BEACON_FRAGMENT:
                return mContext.getString( R.string.beacons_title );
        }
        return null;
    }
}
