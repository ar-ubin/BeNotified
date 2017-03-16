package ar_ubin.benotified.data;


import android.support.annotation.NonNull;

import ar_ubin.benotified.data.source.messages.MessagesDataSource;

public class FakeMessagesRemoteDataSource implements MessagesDataSource
{
    @Override
    public void getMessages( @NonNull LoadMessagesCallback callback ) {

    }

    @Override
    public void getMessage( @NonNull String messageId, @NonNull GetMessageCallback callback ) {

    }

    @Override
    public void saveMessage( @NonNull Message message ) {

    }

    @Override
    public void readMessage( @NonNull Message message ) {

    }

    @Override
    public void readMessage( @NonNull String messageId ) {

    }

    @Override
    public void activateMessage( @NonNull Message message ) {

    }

    @Override
    public void activateMessage( @NonNull String messageId ) {

    }

    @Override
    public void clearReadedMessages() {

    }

    @Override
    public void refreshMessages() {

    }

    @Override
    public void deleteAllMessages() {

    }

    @Override
    public void deleteMessage( @NonNull String messageId ) {

    }
}
