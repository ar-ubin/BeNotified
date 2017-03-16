package ar_ubin.benotified.tabs.messages.add;


import android.support.annotation.NonNull;

import javax.inject.Inject;

import ar_ubin.benotified.data.models.Message;
import ar_ubin.benotified.data.source.messages.MessagesDataSource;
import ar_ubin.benotified.data.source.messages.MessagesRepository;

public final class NewMessagePresenter implements NewMessageContract.Presenter
{
    private final MessagesRepository mMessagesRepository;

    private final NewMessageContract.View mNewMessageView;

    @Inject
    public NewMessagePresenter( MessagesRepository messagesRepository, NewMessageContract.View newMessageView ) {
        mMessagesRepository = messagesRepository;
        mNewMessageView = newMessageView;
    }

    @Inject
    void setupListeners() {
        mNewMessageView.setPresenter( this );
    }

    @Override
    public void start() {
    }

    @Override
    public void saveMessage( @NonNull Message message ) {
        mMessagesRepository.saveMessage( message, new MessagesDataSource.SaveMessageCallback() {
            @Override
            public void onMessageSaved() {
                mNewMessageView.showSavingComplete();
            }

            @Override
            public void onFailed( Exception exception ) {
                mNewMessageView.showSavingError( exception.getMessage() );
            }
        } );
    }
}
