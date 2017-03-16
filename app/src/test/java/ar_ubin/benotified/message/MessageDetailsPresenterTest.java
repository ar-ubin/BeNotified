package ar_ubin.benotified.message;


import com.google.common.collect.Lists;

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
import ar_ubin.benotified.tabs.messages.details.MessageDetailsContract;
import ar_ubin.benotified.tabs.messages.details.MessageDetailsPresenter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class MessageDetailsPresenterTest
{
    private static List<Message> MESSAGES = Lists
            .newArrayList( new Message( "Title1", 1 ), new Message( "Title2", 2 ) );

    @Mock
    private MessagesRepository mMessagesRepository;
    @Mock
    private MessageDetailsContract.View mMessageDetailsView;

    private MessageDetailsPresenter mMessageDetailsPresenter;

    @Captor
    private ArgumentCaptor<MessagesDataSource.DeleteMessageCallback> mDeleteMessageCallbackCaptor;

    @Before
    public void setupTasksPresenter() {
        MockitoAnnotations.initMocks( this );
        mMessageDetailsPresenter = new MessageDetailsPresenter( mMessagesRepository, mMessageDetailsView );
    }

    @Test
    public void deleteMessageFromRepository_showsDeletingComplete() {
        mMessageDetailsPresenter.deleteMessage( MESSAGES.get( 0 ) );
        verify( mMessagesRepository ).deleteMessage( any( Message.class ), mDeleteMessageCallbackCaptor.capture() );

        mDeleteMessageCallbackCaptor.getValue().onMessageDeleted();
        verify( mMessageDetailsView ).showDeletingComplete();
    }

    @Test
    public void deleteMessageFromRepository_emptyMessageShowsError() {
        Exception exception = new NullPointerException();

        mMessageDetailsPresenter.deleteMessage( null );
        verify( mMessagesRepository ).deleteMessage( any( Message.class ), mDeleteMessageCallbackCaptor.capture() );

        mDeleteMessageCallbackCaptor.getValue().onFailed( exception );
        verify( mMessageDetailsView ).showDeletingError( exception.getMessage() );
    }
}
