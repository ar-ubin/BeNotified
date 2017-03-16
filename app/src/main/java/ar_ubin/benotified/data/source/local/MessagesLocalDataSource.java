package ar_ubin.benotified.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import ar_ubin.benotified.data.models.Message;
import ar_ubin.benotified.data.source.messages.MessagesDataSource;
import ar_ubin.benotified.utils.ApplicationScope;

import static com.google.common.base.Preconditions.checkNotNull;

@ApplicationScope
public class MessagesLocalDataSource implements MessagesDataSource
{
    public MessagesLocalDataSource( @NonNull Context context ) {
        checkNotNull( context );
        //TODO init DB
    }

    @Override
    public void setReceivedMessagesListener( @NonNull MessagesDataSource.MessageRefsListenerCallback callback ) {

    }

    @Override
    public void setOwnMessageRefsListener(
            @NonNull ar_ubin.benotified.data.source.messages.MessagesDataSource.MessageRefsListenerCallback callback ) {

    }

    @Override
    public void receiveMessages( int beaconMinor, @NonNull ReceiveMessagesCallback callback ) {

    }

    @Override
    public void saveMessage( @NonNull Message message, @NonNull SaveMessageCallback callback ) {

    }

    @Override
    public void readMessage( @NonNull Message message, @NonNull ReadMessageCallback callback ) {

    }

    @Override
    public void readMessage( @NonNull String messageId, @NonNull ReadMessageCallback callback ) {

    }

    @Override
    public void activateMessage( @NonNull Message message ) {

    }

    @Override
    public void activateMessage( @NonNull String messageId ) {

    }

    @Override
    public void deleteAllMessages() {

    }

    @Override
    public void deleteMessage( @NonNull Message message, @NonNull DeleteMessageCallback callback ) {

    }
}
