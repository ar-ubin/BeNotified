package ar_ubin.benotified.tabs.messages.details;


import android.support.annotation.NonNull;

import javax.inject.Inject;

import ar_ubin.benotified.data.models.Message;
import ar_ubin.benotified.data.source.messages.MessagesDataSource;
import ar_ubin.benotified.data.source.messages.MessagesRepository;

public final class MessageDetailsPresenter implements MessageDetailsContract.Presenter
{
    private final MessagesRepository mMessagesRepository;
    private final MessageDetailsContract.View mMessageDetailsView;

    @Inject
    public MessageDetailsPresenter( MessagesRepository messagesRepository,
                                    MessageDetailsContract.View messageDetailsView ) {
        mMessagesRepository = messagesRepository;
        mMessageDetailsView = messageDetailsView;
    }

    @Inject
    void setupListeners() {
        mMessageDetailsView.setPresenter( this );
    }

    @Override
    public void start() {

    }

    @Override
    public void deleteMessage( @NonNull Message message ) {
        mMessagesRepository.deleteMessage( message, new MessagesDataSource.DeleteMessageCallback()
        {
            @Override
            public void onMessageDeleted() {
                mMessageDetailsView.showDeletingComplete();
            }

            @Override
            public void onFailed( Exception exception ) {
                mMessageDetailsView.showDeletingError( exception.getMessage() );
            }
        } );
    }
}
