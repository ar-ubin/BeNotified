package ar_ubin.benotified.tabs.messages.details;


import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ar_ubin.benotified.R;
import ar_ubin.benotified.base.BaseFragment;
import ar_ubin.benotified.data.models.Beacon;
import ar_ubin.benotified.data.models.Message;
import ar_ubin.benotified.utils.GsonSharedPrefs;

import static com.google.common.base.Preconditions.checkNotNull;


public class MessageDetailsFragment extends BaseFragment implements MessageDetailsContract.View
{
    protected static final String ARGUMENT_MESSAGE_DETAILS = "message_details";
    private MessageDetailsListener mListener;
    public MessageDetailsContract.Presenter mPresenter;

    private GsonSharedPrefs mGsonSharedPrefs;
    private Message mMessage;
    private Beacon mBeacon;

    private TextView vTitel;
    private TextView vDecription;
    private TextView vBeaconName;
    private ImageView vBeaconColor;

    public interface MessageDetailsListener
    {
        void onMessageEdit( Message message );

        void onMessageDelete( Message message );
    }

    public static MessageDetailsFragment newInstance( @NonNull final Message message ) {
        Bundle arguments = new Bundle();
        arguments.putSerializable( ARGUMENT_MESSAGE_DETAILS, message );
        MessageDetailsFragment fragment = new MessageDetailsFragment();
        fragment.setArguments( arguments );
        return fragment;
    }

    @Override
    public void setPresenter( MessageDetailsContract.Presenter presenter ) {
        mPresenter = checkNotNull( presenter );
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        if( context instanceof MessageDetailsListener ) {
            mListener = (MessageDetailsListener) context;
        }
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setHasOptionsMenu( true );
        mGsonSharedPrefs = ( (MessageDetailsActivity) getActivity() ).mGsonSharedPrefs;
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        final View fragmentView = inflater.inflate( R.layout.fragment_message_details, container, false );

        vTitel = (TextView) fragmentView.findViewById( R.id.message_details_title );
        vDecription = (TextView) fragmentView.findViewById( R.id.message_details_description );
        vBeaconName = (TextView) fragmentView.findViewById( R.id.details_beacon_name );
        vBeaconColor = (ImageView) fragmentView.findViewById( R.id.details_beacon_color );

        FloatingActionButton fab = (FloatingActionButton) fragmentView.findViewById( R.id.edit_message );
        fab.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view ) {
                mListener.onMessageEdit( mMessage );
            }
        } );

        initMessage();

        return fragmentView;
    }

    private void initMessage() {
        mMessage = new Message();
        if( getArguments().getSerializable( ARGUMENT_MESSAGE_DETAILS ) != null ) {
            mMessage = (Message) getArguments().getSerializable( ARGUMENT_MESSAGE_DETAILS );
            mBeacon = mGsonSharedPrefs.loadBeacon( mMessage.getBeacon() );

            vTitel.setText( mMessage.getTitle() );
            vDecription.setText( mMessage.getDescription() );

            if( mBeacon != null ) {
                vBeaconName.setText( mBeacon.getName() );
                vBeaconColor.setBackgroundTintList( ColorStateList.valueOf( mBeacon.getColor() ) );
            } else {
                vBeaconName.setText( mMessage.getBeacon() );
            }
        }
    }

    @Override
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {
        super.onCreateOptionsMenu( menu, inflater );
    }


    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        int id = item.getItemId();

        if( id == R.id.action_delete ) {
            showProgressDialog();
            mListener.onMessageDelete( mMessage );
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public void showDeletingComplete() {
        hideProgressDialog();
        getActivity().finish();
    }

    @Override
    public void showDeletingError( String message ) {
        hideProgressDialog();
        Snackbar.make( getView(), "Delete Message failed: " + message, Snackbar.LENGTH_SHORT ).show();
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
