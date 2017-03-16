package ar_ubin.benotified.navigation;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import ar_ubin.benotified.data.models.Beacon;
import ar_ubin.benotified.data.models.Message;
import ar_ubin.benotified.login.LoginActivity;
import ar_ubin.benotified.tabs.TabActivity;
import ar_ubin.benotified.tabs.beacon.add.NewBeaconActivity;
import ar_ubin.benotified.tabs.messages.add.NewMessageActivity;
import ar_ubin.benotified.tabs.messages.details.MessageDetailsActivity;
import ar_ubin.benotified.utils.ActivityScope;

import static com.google.common.base.Preconditions.checkNotNull;

@ActivityScope
public class Navigator
{
    public static final int LOGIN_SESSION = 0;
    public static final int CREATE_MESSAGE = 1;
    public static final int CREATE_BEACON = 2;
    public static final int MESSAGE_DETAILS = 3;

    @Inject
    public Navigator() {
        //empty
    }

    public void startLoginSession( @NonNull Activity context ) {
        checkNotNull( context );
        Intent intent = TabActivity.getCallingIntent( context );
        context.startActivityForResult( intent, LOGIN_SESSION );
    }

    public void finishActivityWithResult( @NonNull Activity context ) {
        checkNotNull( context );
        context.setResult( Activity.RESULT_OK );
        context.finish();
    }

    public void navigateToLogin( @Nullable Context context ) {
        checkNotNull( context );
        Intent intent = LoginActivity.getCallingIntent( context );
        context.startActivity( intent );
    }

    public void navigateToTabActivity( @NonNull Activity context ) {
        checkNotNull( context );
        Intent intent = TabActivity.getCallingIntent( context );
        context.startActivity( intent );
    }

    public void navigateToNewMessage( @NonNull Activity context, @NonNull Message message ) {
        checkNotNull( context );
        checkNotNull( message );
        Intent intent = NewMessageActivity.getCallingIntent( context, message );
        context.startActivityForResult( intent, CREATE_MESSAGE );
    }

    public void navigateToEditMessage( @NonNull Activity context, @NonNull Message message ) {
        checkNotNull( context );
        checkNotNull( message );
        Intent intent = NewMessageActivity.getCallingIntent( context, message );
        context.startActivity( intent );
        context.finish();
    }

    public void navigateToNewMessage( @NonNull Activity context ) {
        checkNotNull( context );
        Intent intent = NewMessageActivity.getCallingIntent( context );
        context.startActivityForResult( intent, CREATE_MESSAGE );
    }

    public void navigateToNewBeacon( @NonNull Activity context ) {
        checkNotNull( context );
        Intent intent = NewBeaconActivity.getCallingIntent( context );
        context.startActivityForResult( intent, CREATE_BEACON );
    }

    public void navigateToNewBeacon( @NonNull Activity context, @NonNull Beacon beacon ) {
        checkNotNull( context );
        Intent intent = NewBeaconActivity.getCallingIntent( context, beacon );
        context.startActivityForResult( intent, CREATE_BEACON );
    }

    public void navigateToMessageDetails( @NonNull Activity context, @NonNull Message message ) {
        checkNotNull( context );
        checkNotNull( message );
        Intent intent = MessageDetailsActivity.getCallingIntent( context, message );
        context.startActivityForResult( intent, MESSAGE_DETAILS );
    }
}
