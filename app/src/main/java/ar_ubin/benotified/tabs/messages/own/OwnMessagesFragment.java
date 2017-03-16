package ar_ubin.benotified.tabs.messages.own;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;

import ar_ubin.benotified.R;
import ar_ubin.benotified.base.BaseFragment;
import ar_ubin.benotified.data.models.Message;
import ar_ubin.benotified.tabs.TabActivity;
import ar_ubin.benotified.tabs.adapter.FirebaseRecyclerAdapter;
import ar_ubin.benotified.tabs.messages.MessagesViewHolder;

import static com.google.common.base.Preconditions.checkNotNull;

public class OwnMessagesFragment extends BaseFragment implements OwnMessagesContract.View
{
    @Inject
    OwnMessagesPresenter mPresenter;
    private OwnMessagesListener mListener;
    private FirebaseRecyclerAdapter<Message, MessagesViewHolder> mAdapter;

    public interface OwnMessagesListener
    {
        void showOwnMessage( Message message );

        void editOwnMessage( Message message );
    }

    public static OwnMessagesFragment newInstance() {
        return new OwnMessagesFragment();
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        if( context instanceof OwnMessagesListener ) {
            mListener = (OwnMessagesListener) context;
        }
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        initializeInjector();
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        final View fragmentView = inflater.inflate( R.layout.fragment_own_messages, container, false );

        RecyclerView messagesList = (RecyclerView) fragmentView.findViewById( R.id.own_messages_list );
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
                        mListener.showOwnMessage( model );
                    }
                } );

                if( model.isCompleted() ) {
                    viewHolder.vMessageStatusIcon
                            .setImageDrawable( getResources().getDrawable( R.drawable.ic_action_done ) );
                } else {
                    viewHolder.vMessageStatusIcon
                            .setImageDrawable( getResources().getDrawable( R.mipmap.ic_action_device_access_time ) );
                }
                viewHolder.bindToMessage( model, new View.OnClickListener()
                {
                    @Override
                    public void onClick( View menuView ) {
                        mListener.editOwnMessage( model );
                    }
                } );
            }
        };

        list.setAdapter( mAdapter );
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
    public void setPresenter( OwnMessagesContract.Presenter presenter ) {
        mPresenter = (OwnMessagesPresenter) checkNotNull( presenter );
    }

    private void initializeInjector() {
        DaggerOwnMessagesComponent.builder()
                .applicationComponent( ( (TabActivity) getActivity() ).getApplicationComponent() )
                .ownMessagesPresenterModule( new OwnMessagesPresenterModule( this ) ).build().inject( this );
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
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        this.mListener = null;
        super.onDetach();
    }
}
