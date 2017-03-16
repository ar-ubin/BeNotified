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
import ar_ubin.benotified.tabs.messages.add.NewMessageContract;
import ar_ubin.benotified.tabs.messages.add.NewMessagePresenter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class NewMessagePresenterTest
{
    private static List<Message> MESSAGES = Lists
            .newArrayList( new Message( "Title1", 1 ), new Message( "Title2", 2 ) );

    @Mock
    private MessagesRepository mMessagesRepository;
    @Mock
    private NewMessageContract.View mNewMessageView;

    private NewMessagePresenter mNewMessagePresenter;

    @Captor
    private ArgumentCaptor<MessagesDataSource.SaveMessageCallback> mSaveMessageCallbackCaptor;

    @Before
    public void setupTasksPresenter() {
        MockitoAnnotations.initMocks( this );
        mNewMessagePresenter = new NewMessagePresenter( mMessagesRepository, mNewMessageView );
    }

    @Test
    public void saveNewMessageToRepository_showsSavingComplete() {
        mNewMessagePresenter.saveMessage( MESSAGES.get( 0 ) );
        verify( mMessagesRepository ).saveMessage( any( Message.class ), mSaveMessageCallbackCaptor.capture() );

        mSaveMessageCallbackCaptor.getValue().onMessageSaved();
        verify( mNewMessageView ).showSavingComplete();
    }

    @Test
    public void saveNewMessageToRepository_emptyMessageShowsError() {
        Exception exception = new NullPointerException();

        mNewMessagePresenter.saveMessage( null );
        verify( mMessagesRepository ).saveMessage( any( Message.class ), mSaveMessageCallbackCaptor.capture() );

        mSaveMessageCallbackCaptor.getValue().onFailed( exception );
        verify( mNewMessageView ).showSavingError( exception.getMessage() );
    }
}
