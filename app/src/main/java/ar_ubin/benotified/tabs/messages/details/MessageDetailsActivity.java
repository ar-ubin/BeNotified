package ar_ubin.benotified.tabs.messages.details;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import ar_ubin.benotified.R;
import ar_ubin.benotified.base.BaseActivity;
import ar_ubin.benotified.data.models.Message;


public class MessageDetailsActivity extends BaseActivity implements MessageDetailsFragment.MessageDetailsListener
{
    @Inject
    MessageDetailsPresenter mMessageDetailsPresenter;
    MessageDetailsFragment mMessageDetailsFragment;
    private Message mMessage;

    public static Intent getCallingIntent( Context context, Message message ) {
        Intent callingIntent = new Intent( context, MessageDetailsActivity.class );
        callingIntent.putExtra( MessageDetailsFragment.ARGUMENT_MESSAGE_DETAILS, message );
        return callingIntent;
    }

    public static Intent getCallingIntent( Context context ) {
        return new Intent( context, MessageDetailsActivity.class );
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_message_details );

        mMessageDetailsFragment = (MessageDetailsFragment) getSupportFragmentManager()
                .findFragmentById( R.id.message_details_container );

        addMessageDetailsFragment();
        initializeInjector();
    }

    private void addMessageDetailsFragment() {
        mMessage = new Message();
        if( getIntent() != null ) {
            mMessage = (Message) getIntent().getSerializableExtra( MessageDetailsFragment.ARGUMENT_MESSAGE_DETAILS );
        }

        if( mMessageDetailsFragment == null ) {
            mMessageDetailsFragment = MessageDetailsFragment.newInstance( mMessage );
            addFragment( R.id.message_details_container, mMessageDetailsFragment );
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate( R.menu.menu_message_details, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        int id = item.getItemId();

        if( id == R.id.action_delete ) {
            return false;
        }

        return super.onOptionsItemSelected( item );
    }

    private void initializeInjector() {
        DaggerMessageDetailsComponent.builder().applicationComponent( getApplicationComponent() )
                .activityModule( getActivityModule() )
                .messageDetailsPresenterModule( new MessageDetailsPresenterModule( mMessageDetailsFragment ) ).build()
                .inject( this );
    }

    @Override
    public void onMessageEdit( Message message ) {
        mNavigator.navigateToEditMessage( this, message );
    }

    @Override
    public void onMessageDelete( Message message ) {
        mMessageDetailsPresenter.deleteMessage( message );
    }
}