package ar_ubin.benotified.message;


import com.google.firebase.database.DatabaseReference;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ar_ubin.benotified.data.source.messages.MessagesDataSource;
import ar_ubin.benotified.data.source.messages.MessagesRepository;
import ar_ubin.benotified.tabs.messages.received.ReceivedMessagesContract;
import ar_ubin.benotified.tabs.messages.received.ReceivedMessagesPresenter;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

public class ReceivedMessagesPresenterTest
{
    @Mock
    private MessagesRepository mMessagesRepository;
    @Mock
    private ReceivedMessagesContract.View mReceivedMessageView;
    @Mock
    private DatabaseReference mMessageRef;

    private ReceivedMessagesPresenter mReceivedMessagePresenter;

    @Captor
    private ArgumentCaptor<MessagesDataSource.MessageRefsListenerCallback> mMessagesRefsListenerCallbackCaptor;
    @Captor
    private ArgumentCaptor<MessagesDataSource.ReceiveMessagesCallback> mReceiveMessagesCallbackCaptor;

    @Before
    public void setupTasksPresenter() {
        MockitoAnnotations.initMocks( this );
        mReceivedMessagePresenter = new ReceivedMessagesPresenter( mMessagesRepository, mReceivedMessageView );
    }

    @Test
    public void setMessageRefListener_showsMessageRefAdded() {
        mReceivedMessagePresenter.setMessageRefListener();
        verify( mMessagesRepository ).setReceivedMessagesListener( mMessagesRefsListenerCallbackCaptor.capture() );

        mMessagesRefsListenerCallbackCaptor.getValue().onMessageRefAdded( mMessageRef );
        verify( mReceivedMessageView ).onMessageRefAdded( mMessageRef );
    }

    @Test
    public void setMessageRefListener_showsMessageRefRemoved() {
        mReceivedMessagePresenter.setMessageRefListener();
        verify( mMessagesRepository ).setReceivedMessagesListener( mMessagesRefsListenerCallbackCaptor.capture() );

        mMessagesRefsListenerCallbackCaptor.getValue().onMessageRefRemoved( mMessageRef );
        verify( mReceivedMessageView ).onMessageRefRemoved( mMessageRef );
    }

    @Test
    public void setMessageRefListener_showsMessageRefsCancelled() {
        mReceivedMessagePresenter.setMessageRefListener();
        verify( mMessagesRepository ).setReceivedMessagesListener( mMessagesRefsListenerCallbackCaptor.capture() );

        mMessagesRefsListenerCallbackCaptor.getValue().onCancelled();
    }

    @Test
    public void receiveMessages_showsMessageReceived() {
        final int beaconMinor = 1;

        mReceivedMessagePresenter.receiveMessages( beaconMinor );
        verify( mMessagesRepository ).receiveMessages( anyInt(), mReceiveMessagesCallbackCaptor.capture() );

        mReceiveMessagesCallbackCaptor.getValue().onMessageReceived();
    }

    @Test
    public void receiveMessages_showsReceiveMessageFailed() {
        final int beaconMinor = -1;
        Exception exception = new NullPointerException();

        mReceivedMessagePresenter.receiveMessages( beaconMinor );
        verify( mMessagesRepository ).receiveMessages( anyInt(), mReceiveMessagesCallbackCaptor.capture() );

        mReceiveMessagesCallbackCaptor.getValue().onFailed( exception );
    }
}
