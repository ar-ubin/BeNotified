package ar_ubin.benotified.tabs.messages.received;


import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;

import ar_ubin.benotified.data.source.messages.MessagesDataSource;
import ar_ubin.benotified.data.source.messages.MessagesRepository;

public final class ReceivedMessagesPresenter implements ReceivedMessagesContract.Presenter
{
    private final MessagesRepository mMessagesRepository;

    private final ReceivedMessagesContract.View mReceivedMessagesView;

    @Inject
    public ReceivedMessagesPresenter( MessagesRepository messagesRepository,
                                      ReceivedMessagesContract.View receivedMessagesView ) {
        mMessagesRepository = messagesRepository;
        mReceivedMessagesView = receivedMessagesView;
    }

    @Inject
    void setupListeners() {
        mReceivedMessagesView.setPresenter( this );
    }

    @Override
    public void start() {

    }

    @Override
    public void setMessageRefListener() {
        mMessagesRepository.setReceivedMessagesListener( new MessagesDataSource.MessageRefsListenerCallback()
        {
            @Override
            public void onMessageRefAdded( DatabaseReference messageRef ) {
                mReceivedMessagesView.onMessageRefAdded( messageRef );
            }

            @Override
            public void onMessageRefRemoved( DatabaseReference messageRef ) {
                mReceivedMessagesView.onMessageRefRemoved( messageRef );
            }

            @Override
            public void onCancelled() {

            }
        } );
    }

    @Override
    public void receiveMessages( int beaconMinor ) {
        mMessagesRepository.receiveMessages( beaconMinor, new MessagesDataSource.ReceiveMessagesCallback()
        {
            @Override
            public void onMessageReceived() {

            }

            @Override
            public void onFailed( Exception exception ) {

            }
        } );
    }
}
