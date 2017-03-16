package ar_ubin.benotified.data.source.messages;


import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;

import ar_ubin.benotified.data.models.Message;

public interface MessagesDataSource
{
    interface MessageRefsListenerCallback
    {
        void onMessageRefAdded( DatabaseReference messageRef );

        void onMessageRefRemoved( DatabaseReference messageRef );

        void onCancelled();
    }

    interface ReceiveMessagesCallback
    {
        void onMessageReceived();

        void onFailed( Exception exception );
    }

    interface SaveMessageCallback
    {
        void onMessageSaved();

        void onFailed( Exception exception );
    }

    interface ReadMessageCallback
    {
        void onMessageReaded();

        void onFailed( Exception exception );
    }

    interface DeleteMessageCallback
    {
        void onMessageDeleted();

        void onFailed( Exception exception );
    }

    void setReceivedMessagesListener( @NonNull MessageRefsListenerCallback callback );

    void setOwnMessageRefsListener( @NonNull MessageRefsListenerCallback callback );

    void receiveMessages( @NonNull int beaconMinor, @NonNull ReceiveMessagesCallback callback );

    void saveMessage( @NonNull Message message, @NonNull SaveMessageCallback callback );

    void readMessage( @NonNull Message message, @NonNull ReadMessageCallback callback );

    void readMessage( @NonNull String messageId, @NonNull ReadMessageCallback callback );

    void activateMessage( @NonNull Message message );

    void activateMessage( @NonNull String messageId );

    void deleteAllMessages();

    void deleteMessage( @NonNull Message message, @NonNull DeleteMessageCallback callback );
}
