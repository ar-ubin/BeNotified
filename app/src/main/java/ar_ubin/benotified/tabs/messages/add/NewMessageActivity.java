package ar_ubin.benotified.tabs.messages.add;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import ar_ubin.benotified.R;
import ar_ubin.benotified.base.BaseActivity;
import ar_ubin.benotified.data.models.Message;
import ar_ubin.benotified.tabs.adapter.BeaconSpinnerAdapter;

public class NewMessageActivity extends BaseActivity implements NewMessageFragment.CreateMessageListener
{
    @Inject
    NewMessagePresenter mNewMessagePresenter;
    @Inject
    BeaconSpinnerAdapter mBeaconAdapter;
    NewMessageFragment mNewMessageFragment;

    public static Intent getCallingIntent( Context context, Message message ) {
        Intent callingIntent = new Intent( context, NewMessageActivity.class );
        callingIntent.putExtra( NewMessageFragment.ARGUMENT_EDIT_MESSAGE, message );
        return callingIntent;
    }

    public static Intent getCallingIntent( Context context ) {
        return new Intent( context, NewMessageActivity.class );
    }

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_new_message );

        mNewMessageFragment = (NewMessageFragment) getSupportFragmentManager()
                .findFragmentById( R.id.new_message_container );

        addNewMessageFragment();
        initializeInjector();
    }

    private void addNewMessageFragment() {
        Message message = new Message();
        if( getIntent() != null ) {
            message = (Message) getIntent().getSerializableExtra( NewMessageFragment.ARGUMENT_EDIT_MESSAGE );
        }

        if( mNewMessageFragment == null ) {
            mNewMessageFragment = NewMessageFragment.newInstance( message );
            addFragment( R.id.new_message_container, mNewMessageFragment );
        }
    }

    private void initializeInjector() {
        DaggerNewMessageComponent.builder().applicationComponent( getApplicationComponent() )
                .activityModule( getActivityModule() )
                .newMessagePresenterModule( new NewMessagePresenterModule( mNewMessageFragment ) ).build()
                .inject( this );
    }

    @Override
    public void onMessageCreated() {
        mNavigator.finishActivityWithResult( this );
    }
}
