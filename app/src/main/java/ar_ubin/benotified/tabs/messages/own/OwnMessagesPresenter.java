package ar_ubin.benotified.tabs.messages.own;


import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;

import ar_ubin.benotified.data.source.messages.MessagesDataSource;
import ar_ubin.benotified.data.source.messages.MessagesRepository;

public final class OwnMessagesPresenter implements OwnMessagesContract.Presenter
{
    private final MessagesRepository mMessagesRepository;

    private final OwnMessagesContract.View mOwnMessagesView;

    @Inject
    public OwnMessagesPresenter( MessagesRepository messagesRepository, OwnMessagesContract.View ownMessagesView ) {
        mMessagesRepository = messagesRepository;
        mOwnMessagesView = ownMessagesView;
    }

    @Inject
    void setupListeners() {
        mOwnMessagesView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getMessages() {
    }

    @Override
    public void setMessageRefListener() {
        mMessagesRepository.setOwnMessageRefsListener( new MessagesDataSource.MessageRefsListenerCallback() {
            @Override
            public void onMessageRefAdded( DatabaseReference messageRef ) {
                mOwnMessagesView.onMessageRefAdded( messageRef );
            }

            @Override
            public void onMessageRefRemoved( DatabaseReference messageRef ) {
                mOwnMessagesView.onMessageRefRemoved( messageRef );
            }

            @Override
            public void onCancelled() {

            }
        } );
    }
}
