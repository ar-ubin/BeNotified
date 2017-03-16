package ar_ubin.benotified.tabs.messages.received;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;

import ar_ubin.beacon_detector.Beacon;
import ar_ubin.beacon_detector.BeaconNotifier;
import ar_ubin.beacon_detector.BeaconScanner;
import ar_ubin.benotified.R;
import ar_ubin.benotified.base.BaseFragment;
import ar_ubin.benotified.data.models.Message;
import ar_ubin.benotified.tabs.TabActivity;
import ar_ubin.benotified.tabs.adapter.FirebaseRecyclerAdapter;
import ar_ubin.benotified.tabs.messages.MessagesViewHolder;

import static com.google.common.base.Preconditions.checkNotNull;

public class ReceivedMessagesFragment extends BaseFragment implements BeaconNotifier, ReceivedMessagesContract.View
{
    @Inject
    ReceivedMessagesPresenter mPresenter;
    FirebaseRecyclerAdapter<Message, MessagesViewHolder> mAdapter;
    private ReceivedMessagesListener mListener;
    private BeaconScanner mBeaconScanner;

    public interface ReceivedMessagesListener
    {
        void showReceivedMessage( Message message );
    }

    public static ReceivedMessagesFragment newInstance() {
        return new ReceivedMessagesFragment();
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        if( context instanceof ReceivedMessagesListener ) {
            mListener = (ReceivedMessagesListener) context;
        }
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        initializeInjector();
        mBeaconScanner = new BeaconScanner( getActivity(), this );
        mBeaconScanner.setDebugLogging( true );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        final View fragmentView = inflater.inflate( R.layout.fragment_received_messages, container, false );
        RecyclerView messagesList = (RecyclerView) fragmentView.findViewById( R.id.received_messages_list );
        LinearLayoutManager manager = new LinearLayoutManager( getActivity() );
        manager.setReverseLayout( true );
        manager.setStackFromEnd( true );
        messagesList.setLayoutManager( manager );

        initMessageRecyclerAdapter( messagesList );
        mPresenter.setMessageRefListener();

        return fragmentView;
    }

    private void initMessageRecyclerAdapter( RecyclerView list ) {
        mAdapter = new FirebaseRecyclerAdapter<Message, MessagesViewHolder>( Message.class, R.layout.card_message,
                MessagesViewHolder.class )
        {
            @Override
            protected void populateViewHolder( final MessagesViewHolder viewHolder, final Message model,
                                               final int position ) {
                viewHolder.itemView.setOnClickListener( new View.OnClickListener()
                {
                    @Override
                    public void onClick( View v ) {
                        mListener.showReceivedMessage( model );
                    }
                } );

                if( model.isCompleted() ) {
                    viewHolder.vMessageStatusIcon
                            .setImageDrawable( getResources().getDrawable( R.drawable.ic_action_done ) );
                } else {
                    viewHolder.vMessageStatusIcon
                            .setImageDrawable( getResources().getDrawable( R.mipmap.ic_action_device_access_time ) );
                }
                viewHolder.bindToMessage( model );
            }
        };
        list.setAdapter( mAdapter );
    }

    @Override
    public void onBeaconFound( Beacon beacon ) {
        //TODO filter Results: request only once
        mPresenter.receiveMessages( beacon.getMinorNumber() );
    }

    @Override
    public void onMessageRefAdded( DatabaseReference messageRef ) {
        mAdapter.addEventListener( messageRef );
    }

    @Override
    public void onMessageRefRemoved( DatabaseReference messageRef ) {
        mAdapter.removeEventListener( messageRef );
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
    }

    @Override
    public void setPresenter( ReceivedMessagesContract.Presenter presenter ) {
        mPresenter = (ReceivedMessagesPresenter) checkNotNull( presenter );
    }

    private void initializeInjector() {
        DaggerReceivedMessagesComponent.builder()
                .applicationComponent( ( (TabActivity) getActivity() ).getApplicationComponent() )
                .receivedMessagesPresenterModule( new ReceivedMessagesPresenterModule( this ) ).build().inject( this );
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mBeaconScanner.destroyService();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        this.mListener = null;
        super.onDetach();
    }
}
