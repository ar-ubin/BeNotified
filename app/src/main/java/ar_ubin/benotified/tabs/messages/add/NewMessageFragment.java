package ar_ubin.benotified.tabs.messages.add;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import java.util.UUID;

import ar_ubin.benotified.R;
import ar_ubin.benotified.base.BaseFragment;
import ar_ubin.benotified.data.models.Beacon;
import ar_ubin.benotified.data.models.Message;
import ar_ubin.benotified.tabs.adapter.BeaconSpinnerAdapter;
import ar_ubin.benotified.utils.GsonSharedPrefs;

import static com.google.common.base.Preconditions.checkNotNull;

public class NewMessageFragment extends BaseFragment implements NewMessageContract.View
{
    protected static final String ARGUMENT_EDIT_MESSAGE = "edit_message";
    private CreateMessageListener mListener;
    public NewMessageContract.Presenter mPresenter;

    private BeaconSpinnerAdapter mBeaconAdapter;
    private GsonSharedPrefs mGsonSharedPrefs;
    private Beacon mSelectedBeacon;
    private Message mMessage;

    private EditText vTitel;
    private EditText vDecription;
    private AppCompatSpinner vBeaconSpinner;

    public interface CreateMessageListener
    {
        void onMessageCreated();
    }

    public static NewMessageFragment newInstance( @NonNull final Message message ) {
        Bundle arguments = new Bundle();
        arguments.putSerializable( ARGUMENT_EDIT_MESSAGE, message );
        NewMessageFragment fragment = new NewMessageFragment();
        fragment.setArguments( arguments );
        return fragment;
    }

    @Override
    public void setPresenter( NewMessageContract.Presenter presenter ) {
        mPresenter = checkNotNull( presenter );
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        if( context instanceof CreateMessageListener ) {
            mListener = (CreateMessageListener) context;
        }
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        mGsonSharedPrefs = ( (NewMessageActivity) getActivity() ).mGsonSharedPrefs;
        mBeaconAdapter = ( (NewMessageActivity) getActivity() ).mBeaconAdapter;
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        final View fragmentView = inflater.inflate( R.layout.fragment_new_message, container, false );

        vTitel = (EditText) fragmentView.findViewById( R.id.edit_message_title );
        vDecription = (EditText) fragmentView.findViewById( R.id.edit_message_description );
        vBeaconSpinner = (AppCompatSpinner) fragmentView.findViewById( R.id.message_beacon );
        mBeaconAdapter.setData( mGsonSharedPrefs.loadAllBeacon() );
        vBeaconSpinner.setAdapter( mBeaconAdapter );

        vBeaconSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected( AdapterView<?> parentView, View v, int position, long id ) {
                mSelectedBeacon = mBeaconAdapter.getItem( position );
            }

            @Override
            public void onNothingSelected( AdapterView<?> parent ) {
                mSelectedBeacon = mBeaconAdapter.getItem( 0 );
            }
        } );

        FloatingActionButton fab = (FloatingActionButton) fragmentView.findViewById( R.id.save_message );
        fab.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view ) {
                createMessage();
            }
        } );

        initMessage();

        return fragmentView;
    }

    private void initMessage() {
        mMessage = new Message();
        if( getArguments().getSerializable( ARGUMENT_EDIT_MESSAGE ) != null ) {
            mMessage = (Message) getArguments().getSerializable( ARGUMENT_EDIT_MESSAGE );
            if( !isNewMessage() ) {
                vTitel.setText( mMessage.getTitle() );
                vDecription.setText( mMessage.getDescription() );
                vBeaconSpinner.setSelection( mBeaconAdapter.getPosition( mMessage.getBeacon() ) );
            }
        }
    }

    private void createMessage() {
        if( validateForm() ) {
            if( isNewMessage() ) {
                mMessage.setUuid( UUID.randomUUID().toString() );
            }
            mMessage.setTitle( vTitel.getText().toString() );
            mMessage.setDescription( vDecription.getText().toString() );
            mMessage.setAuthor( mGsonSharedPrefs.loadUser().getUuid() );
            mMessage.setBeacon( mSelectedBeacon.getMinor() );
            mMessage.setTimestamp( System.currentTimeMillis() );

            mPresenter.saveMessage( mMessage );
            showProgressDialog();
        }
    }

    private boolean isNewMessage() {
        return mMessage == null || mMessage.getUuid().isEmpty();
    }

    @Override
    public void showSavingComplete() {
        hideProgressDialog();
        mListener.onMessageCreated();
    }

    @Override
    public void showSavingError( String message ) {
        hideProgressDialog();
        Snackbar.make( getView(), "Saving Message failed: " + message, Snackbar.LENGTH_SHORT ).show();
    }

    private boolean validateForm() {
        return validateRequiredText( vTitel ) && validateRequiredText( vDecription );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mPresenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        this.mListener = null;
        super.onDetach();
    }
}
