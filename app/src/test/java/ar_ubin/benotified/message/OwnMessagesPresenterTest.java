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
import ar_ubin.benotified.tabs.messages.own.OwnMessagesContract;
import ar_ubin.benotified.tabs.messages.own.OwnMessagesPresenter;

import static org.mockito.Mockito.verify;

public class OwnMessagesPresenterTest
{
    @Mock
    private MessagesRepository mMessagesRepository;
    @Mock
    private OwnMessagesContract.View mOwnMessageView;
    @Mock
    private DatabaseReference mMessageRef;

    private OwnMessagesPresenter mOwnMessagePresenter;

    @Captor
    private ArgumentCaptor<MessagesDataSource.MessageRefsListenerCallback> mMessagesRefsListenerCallbackCaptor;

    @Before
    public void setupTasksPresenter() {
        MockitoAnnotations.initMocks( this );
        mOwnMessagePresenter = new OwnMessagesPresenter( mMessagesRepository, mOwnMessageView );
    }

    @Test
    public void setMessageRefListener_showsMessageRefAdded() {
        mOwnMessagePresenter.setMessageRefListener();
        verify( mMessagesRepository ).setOwnMessageRefsListener( mMessagesRefsListenerCallbackCaptor.capture() );

        mMessagesRefsListenerCallbackCaptor.getValue().onMessageRefAdded( mMessageRef );
        verify( mOwnMessageView ).onMessageRefAdded( mMessageRef );
    }

    @Test
    public void setMessageRefListener_showsMessageRefRemoved() {
        mOwnMessagePresenter.setMessageRefListener();
        verify( mMessagesRepository ).setOwnMessageRefsListener( mMessagesRefsListenerCallbackCaptor.capture() );

        mMessagesRefsListenerCallbackCaptor.getValue().onMessageRefRemoved( mMessageRef );
        verify( mOwnMessageView ).onMessageRefRemoved( mMessageRef );
    }

    @Test
    public void setMessageRefListener_showsMessageRefsCancelled() {
        mOwnMessagePresenter.setMessageRefListener();
        verify( mMessagesRepository ).setOwnMessageRefsListener( mMessagesRefsListenerCallbackCaptor.capture() );

        mMessagesRefsListenerCallbackCaptor.getValue().onCancelled();
    }
}
