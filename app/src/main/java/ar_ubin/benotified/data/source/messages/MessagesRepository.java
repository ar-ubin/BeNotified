package ar_ubin.benotified.data.source.messages;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;

import ar_ubin.benotified.data.models.Message;
import ar_ubin.benotified.data.source.Local;
import ar_ubin.benotified.data.source.Remote;
import ar_ubin.benotified.utils.ApplicationScope;

import static android.R.id.message;
import static com.google.common.base.Preconditions.checkNotNull;

@ApplicationScope
public class MessagesRepository implements MessagesDataSource
{
    private final MessagesDataSource mMessagesRemoteDataSource;
    private final MessagesDataSource mMessagesLocalDataSource;

    @Inject
    public MessagesRepository( @Remote MessagesDataSource messagesRemoteDataSource,
                               @Local MessagesDataSource messagesLocalDataSource ) {
        mMessagesRemoteDataSource = messagesRemoteDataSource;
        mMessagesLocalDataSource = messagesLocalDataSource;
    }

    @Override
    public void setReceivedMessagesListener( @NonNull final MessageRefsListenerCallback callback ) {
        checkNotNull( callback );
        mMessagesRemoteDataSource.setReceivedMessagesListener( new MessageRefsListenerCallback()
        {
            @Override
            public void onMessageRefAdded( DatabaseReference messageRef ) {
                callback.onMessageRefAdded( messageRef );
            }

            @Override
            public void onMessageRefRemoved( DatabaseReference messageRef ) {
                callback.onMessageRefRemoved( messageRef );
            }

            @Override
            public void onCancelled() {
                callback.onCancelled();
            }
        } );
    }

    @Override
    public void setOwnMessageRefsListener( @NonNull final MessageRefsListenerCallback callback ) {
        checkNotNull( callback );
        mMessagesRemoteDataSource.setOwnMessageRefsListener( new MessageRefsListenerCallback()
        {
            @Override
            public void onMessageRefAdded( DatabaseReference messageRef ) {
                callback.onMessageRefAdded( messageRef );
            }

            @Override
            public void onMessageRefRemoved( DatabaseReference messageRef ) {
                callback.onMessageRefRemoved( messageRef );
            }

            @Override
            public void onCancelled() {
                callback.onCancelled();
            }
        } );
    }

    @Override
    public void receiveMessages( @NonNull int beaconMinor, @NonNull final ReceiveMessagesCallback callback ) {
        checkNotNull( callback );
        mMessagesRemoteDataSource.receiveMessages( beaconMinor, new ReceiveMessagesCallback()
        {
            @Override
            public void onMessageReceived() {
                callback.onMessageReceived();
            }

            @Override
            public void onFailed( Exception exception ) {
                callback.onFailed( exception );
            }
        } );
    }

    @Override
    public void saveMessage( @NonNull Message message, @NonNull SaveMessageCallback callback ) {
        checkNotNull( message );
        checkNotNull( callback );
        mMessagesRemoteDataSource.saveMessage( message, callback );
    }

    @Override
    public void readMessage( @NonNull Message message, @NonNull ReadMessageCallback callback ) {
        checkNotNull( message );
        checkNotNull( callback );
        mMessagesRemoteDataSource.readMessage( message, callback );
    }

    @Override
    public void readMessage( @NonNull String messageId, @NonNull ReadMessageCallback callback ) {
        checkNotNull( message );
        checkNotNull( callback );
        mMessagesRemoteDataSource.readMessage( messageId, callback );
    }

    @Override
    public void activateMessage( @NonNull Message message ) {
        checkNotNull( message );
        mMessagesRemoteDataSource.activateMessage( message );
    }

    @Override
    public void activateMessage( @NonNull String messageId ) {
        checkNotNull( message );
        mMessagesRemoteDataSource.activateMessage( messageId );
    }

    @Override
    public void deleteAllMessages() {
        mMessagesRemoteDataSource.deleteAllMessages();
    }

    @Override
    public void deleteMessage( @NonNull Message message, @NonNull DeleteMessageCallback callback ) {
        checkNotNull( message );
        checkNotNull( callback );
        mMessagesRemoteDataSource.deleteMessage( message, callback );
    }
}
