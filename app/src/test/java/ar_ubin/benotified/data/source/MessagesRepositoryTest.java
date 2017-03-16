package ar_ubin.benotified.data.source;


import android.content.Context;

import com.google.common.collect.Lists;
import com.google.firebase.database.DatabaseReference;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import ar_ubin.benotified.data.models.Message;
import ar_ubin.benotified.data.source.messages.MessagesDataSource;
import ar_ubin.benotified.data.source.messages.MessagesRepository;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

public class MessagesRepositoryTest
{
    private static List<Message> MESSAGES = Lists
            .newArrayList( new Message( "Title1", 1 ), new Message( "Title2", 2 ) );

    private MessagesRepository mMessagesRepository;

    @Mock
    private MessagesDataSource mMessasgesRemoteDataSource;
    @Mock
    private MessagesDataSource mMessagesLocalDataSource;

    @Mock
    private Context mContext;
    @Mock
    private DatabaseReference mMessageRef;

    @Mock
    private MessagesDataSource.MessageRefsListenerCallback mMessageRefsCallback;
    @Mock
    private MessagesDataSource.ReceiveMessagesCallback mReceiveMessagesCallback;
    @Mock
    private MessagesDataSource.SaveMessageCallback mSaveMessageCallback;
    @Mock
    private MessagesDataSource.ReadMessageCallback mReadMessageCallback;
    @Mock
    private MessagesDataSource.DeleteMessageCallback mDeleteMessageCallback;

    @Captor
    private ArgumentCaptor<MessagesDataSource.MessageRefsListenerCallback> mMessageRefsCallbackCaptor;
    @Captor
    private ArgumentCaptor<MessagesDataSource.ReceiveMessagesCallback> mReceiveMessagesCallbackCaptor;
    @Captor
    private ArgumentCaptor<MessagesDataSource.SaveMessageCallback> mSaveMessageCallbackCaptor;
    @Captor
    private ArgumentCaptor<MessagesDataSource.ReadMessageCallback> mReadMessageCallbackCaptor;
    @Captor
    private ArgumentCaptor<MessagesDataSource.DeleteMessageCallback> mDeleteMessageCallbackCaptor;

    @Before
    public void setupTasksRepository() {
        MockitoAnnotations.initMocks( this );

        mMessagesRepository = new MessagesRepository( mMessasgesRemoteDataSource, mMessagesLocalDataSource );
    }

    @Test
    public void setReceivedMessagesFromRemoteDataSource_firesOnMessageRefAdded() {
        mMessagesRepository.setReceivedMessagesListener( mMessageRefsCallback );
        verify( mMessasgesRemoteDataSource ).setReceivedMessagesListener( mMessageRefsCallbackCaptor.capture() );

        mMessageRefsCallbackCaptor.getValue().onMessageRefAdded( mMessageRef );
        verify( mMessageRefsCallback ).onMessageRefAdded( mMessageRef );
    }

    @Test
    public void setReceivedMessagesFromRemoteDataSource_firesOnMessageRefRemoved() {
        mMessagesRepository.setReceivedMessagesListener( mMessageRefsCallback );
        verify( mMessasgesRemoteDataSource ).setReceivedMessagesListener( mMessageRefsCallbackCaptor.capture() );

        mMessageRefsCallbackCaptor.getValue().onMessageRefRemoved( mMessageRef );
        verify( mMessageRefsCallback ).onMessageRefRemoved( mMessageRef );
    }

    @Test
    public void setReceivedMessagesFromRemoteDataSource_firesOnCancelled() {
        mMessagesRepository.setReceivedMessagesListener( mMessageRefsCallback );
        verify( mMessasgesRemoteDataSource ).setReceivedMessagesListener( mMessageRefsCallbackCaptor.capture() );

        mMessageRefsCallbackCaptor.getValue().onCancelled();
        verify( mMessageRefsCallback ).onCancelled();
    }

    @Test
    public void setOwnMessagesFromRemoteDataSource_firesOnMessageRefAdded() {
        mMessagesRepository.setOwnMessageRefsListener( mMessageRefsCallback );
        verify( mMessasgesRemoteDataSource ).setOwnMessageRefsListener( mMessageRefsCallbackCaptor.capture() );

        mMessageRefsCallbackCaptor.getValue().onMessageRefAdded( mMessageRef );
        verify( mMessageRefsCallback ).onMessageRefAdded( mMessageRef );
    }

    @Test
    public void setOwnMessagesFromRemoteDataSource_firesOnMessageRefRemoved() {
        mMessagesRepository.setOwnMessageRefsListener( mMessageRefsCallback );
        verify( mMessasgesRemoteDataSource ).setOwnMessageRefsListener( mMessageRefsCallbackCaptor.capture() );

        mMessageRefsCallbackCaptor.getValue().onMessageRefRemoved( mMessageRef );
        verify( mMessageRefsCallback ).onMessageRefRemoved( mMessageRef );
    }

    @Test
    public void setOwnMessagesFromRemoteDataSource_firesOnCancelled() {
        mMessagesRepository.setOwnMessageRefsListener( mMessageRefsCallback );
        verify( mMessasgesRemoteDataSource ).setOwnMessageRefsListener( mMessageRefsCallbackCaptor.capture() );

        mMessageRefsCallbackCaptor.getValue().onCancelled();
        verify( mMessageRefsCallback ).onCancelled();
    }

    @Test
    public void receiveMessagesFromRemoteDataSource_firesOnMessageReadedThanOnMessageReceived() {
        mMessagesRepository.receiveMessages( 1, mReceiveMessagesCallback );
        mMessagesRepository.readMessage( MESSAGES.get( 0 ), mReadMessageCallback );

        verify( mMessasgesRemoteDataSource ).receiveMessages( anyInt(), mReceiveMessagesCallbackCaptor.capture() );
        verify( mMessasgesRemoteDataSource ).readMessage( any( Message.class ), mReadMessageCallbackCaptor.capture() );

        mReadMessageCallbackCaptor.getValue().onMessageReaded();
        mReceiveMessagesCallbackCaptor.getValue().onMessageReceived();

        verify( mReceiveMessagesCallback ).onMessageReceived();
    }

    @Test
    public void receiveMessagesFromRemoteDataSource_firesOnReadMessageFailedThanOnReceiveMessageFailed() {
        NullPointerException exception = new NullPointerException();

        mMessagesRepository.receiveMessages( 1, mReceiveMessagesCallback );
        mMessagesRepository.readMessage( MESSAGES.get( 0 ), mReadMessageCallback );

        verify( mMessasgesRemoteDataSource ).receiveMessages( anyInt(), mReceiveMessagesCallbackCaptor.capture() );
        verify( mMessasgesRemoteDataSource ).readMessage( any( Message.class ), mReadMessageCallbackCaptor.capture() );

        mReadMessageCallbackCaptor.getValue().onFailed( exception );
        mReceiveMessagesCallbackCaptor.getValue().onFailed( exception );

        verify( mReceiveMessagesCallback ).onFailed( exception );
    }

    @Test
    public void saveMessageToRemoteDataSource_firesOnMessageSaved() {
        mMessagesRepository.saveMessage( MESSAGES.get( 0 ), mSaveMessageCallback );
        verify( mMessasgesRemoteDataSource ).saveMessage( any( Message.class ), mSaveMessageCallbackCaptor.capture() );

        mSaveMessageCallbackCaptor.getValue().onMessageSaved();
        verify( mSaveMessageCallback ).onMessageSaved();
    }

    @Test
    public void saveMessageToRemoteDataSource_firesOnFailed() {
        NullPointerException exception = new NullPointerException();

        mMessagesRepository.saveMessage( MESSAGES.get( 0 ), mSaveMessageCallback );
        verify( mMessasgesRemoteDataSource ).saveMessage( any( Message.class ), mSaveMessageCallbackCaptor.capture() );

        mSaveMessageCallbackCaptor.getValue().onFailed( exception );
        verify( mSaveMessageCallback ).onFailed( exception );
    }

    @Test
    public void deleteMessageToRemoteDataSource_firesOnMessageDeleted() {
        mMessagesRepository.deleteMessage( MESSAGES.get( 0 ), mDeleteMessageCallback );
        verify( mMessasgesRemoteDataSource )
                .deleteMessage( any( Message.class ), mDeleteMessageCallbackCaptor.capture() );

        mDeleteMessageCallbackCaptor.getValue().onMessageDeleted();
        verify( mDeleteMessageCallback ).onMessageDeleted();
    }

    @Test
    public void deleteMessageToRemoteDataSource_firesOnFailed() {
        NullPointerException exception = new NullPointerException();

        mMessagesRepository.deleteMessage( MESSAGES.get( 0 ), mDeleteMessageCallback );
        verify( mMessasgesRemoteDataSource )
                .deleteMessage( any( Message.class ), mDeleteMessageCallbackCaptor.capture() );

        mDeleteMessageCallbackCaptor.getValue().onFailed( exception );
        verify( mDeleteMessageCallback ).onFailed( exception );
    }
}
