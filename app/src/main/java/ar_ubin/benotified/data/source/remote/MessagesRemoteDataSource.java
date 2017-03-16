package ar_ubin.benotified.data.source.remote;


import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ar_ubin.benotified.data.models.Message;
import ar_ubin.benotified.data.source.messages.MessagesDataSource;
import ar_ubin.benotified.utils.ApplicationScope;

import static ar_ubin.benotified.data.Constants.DB_BEACONS;
import static ar_ubin.benotified.data.Constants.DB_MESSAGES;
import static ar_ubin.benotified.data.Constants.DB_RECEIVED_MESSAGES;
import static ar_ubin.benotified.data.Constants.DB_USERS;
import static com.google.common.base.Preconditions.checkNotNull;

@ApplicationScope
public class MessagesRemoteDataSource implements MessagesDataSource
{
    private final Context mContext;
    private final DatabaseReference mDatabase;
    private final FirebaseUser mCurrentUser;

    public MessagesRemoteDataSource( @NonNull Context context ) {
        checkNotNull( context );
        mContext = context;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void setReceivedMessagesListener( @NonNull final MessageRefsListenerCallback callback ) {
        final DatabaseReference ref = mDatabase.child( DB_USERS ).child( mCurrentUser.getUid() )
                .child( DB_RECEIVED_MESSAGES );
        setMessageRefsListener( ref, callback );
    }

    @Override
    public void setOwnMessageRefsListener( @NonNull final MessageRefsListenerCallback callback ) {
        final DatabaseReference ref = mDatabase.child( DB_USERS ).child( mCurrentUser.getUid() ).child( DB_MESSAGES );
        setMessageRefsListener( ref, callback );
    }

    public void setMessageRefsListener( @NonNull final DatabaseReference ref,
                                        @NonNull final MessageRefsListenerCallback callback ) {
        ref.addChildEventListener( new ChildEventListener()
        {
            @Override
            public void onChildAdded( DataSnapshot dataSnapshot, String s ) {
                callback.onMessageRefAdded( mDatabase.child( DB_MESSAGES ).child( dataSnapshot.getKey() ) );
            }

            @Override
            public void onChildChanged( DataSnapshot dataSnapshot, String s ) {
                // NOTHING TO DO HERE
            }

            @Override
            public void onChildRemoved( DataSnapshot dataSnapshot ) {
                callback.onMessageRefRemoved( mDatabase.child( DB_MESSAGES ).child( dataSnapshot.getKey() ) );
            }

            @Override
            public void onChildMoved( DataSnapshot dataSnapshot, String s ) {
                // NOTHING TO DO HERE
            }

            @Override
            public void onCancelled( DatabaseError databaseError ) {
                callback.onCancelled();
            }
        } );
    }

    @Override
    public void receiveMessages( int beaconMinor, @NonNull final ReceiveMessagesCallback callback ) {
        mDatabase.child( DB_BEACONS ).child( String.valueOf( beaconMinor ) ).child( DB_MESSAGES )
                .addListenerForSingleValueEvent( new ValueEventListener()
                {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot ) {
                        for( DataSnapshot snapshot : dataSnapshot.getChildren() ) {
                            mDatabase.child( DB_USERS ).child( mCurrentUser.getUid() ).child( DB_RECEIVED_MESSAGES )
                                    .child( snapshot.getKey() ).setValue( true );
                            readMessage( snapshot.getKey(), new ReadMessageCallback()
                            {
                                @Override
                                public void onMessageReaded() {
                                    callback.onMessageReceived();
                                }

                                @Override
                                public void onFailed( Exception exception ) {
                                    callback.onFailed( exception );
                                }
                            } );
                        }
                    }

                    @Override
                    public void onCancelled( DatabaseError databaseError ) {
                        callback.onFailed( databaseError.toException() );
                    }
                } );
    }

    @Override
    public void saveMessage( @NonNull final Message message, @NonNull final SaveMessageCallback callback ) {
        mDatabase.child( DB_MESSAGES ).child( message.getUuid() ).setValue( message )
                .addOnCompleteListener( new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete( @NonNull Task<Void> task ) {
                        if( task.isSuccessful() ) {
                            mDatabase.child( DB_BEACONS ).child( String.valueOf( message.getBeacon() ) )
                                    .child( DB_MESSAGES ).child( message.getUuid() ).setValue( true );
                            mDatabase.child( DB_USERS ).child( mCurrentUser.getUid() ).child( DB_MESSAGES )
                                    .child( message.getUuid() ).setValue( true );

                            callback.onMessageSaved();
                        } else {
                            callback.onFailed( task.getException() );
                        }
                    }
                } );
    }

    @Override
    public void readMessage( @NonNull final Message message, @NonNull ReadMessageCallback callback ) {
        readMessage( message.getUuid(), callback );
    }

    @Override
    public void readMessage( @NonNull final String messageId, @NonNull final ReadMessageCallback callback ) {
        mDatabase.child( DB_MESSAGES ).child( messageId ).child( "timestamp" ).setValue( System.currentTimeMillis() );
        mDatabase.child( DB_MESSAGES ).child( messageId ).child( "completed" ).setValue( true )
                .addOnCompleteListener( new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete( @NonNull Task<Void> task ) {
                        if( task.isSuccessful() ) {
                            callback.onMessageReaded();
                        } else {
                            callback.onFailed( task.getException() );
                        }
                    }
                } );
    }

    @Override
    public void activateMessage( @NonNull final Message message ) {

    }

    @Override
    public void activateMessage( @NonNull final String messageId ) {

    }

    @Override
    public void deleteAllMessages() {

    }

    @Override
    public void deleteMessage( @NonNull final Message message, @NonNull final DeleteMessageCallback callback ) {
        mDatabase.child( DB_MESSAGES ).child( message.getUuid() ).removeValue()
                .addOnCompleteListener( new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete( @NonNull Task<Void> task ) {
                        if( task.isSuccessful() ) {
                            mDatabase.child( DB_BEACONS ).child( String.valueOf( message.getBeacon() ) )
                                    .child( DB_MESSAGES ).child( message.getUuid() ).removeValue();
                            mDatabase.child( DB_USERS ).child( mCurrentUser.getUid() ).child( DB_MESSAGES )
                                    .child( message.getUuid() ).removeValue();

                            callback.onMessageDeleted();
                        } else {
                            callback.onFailed( task.getException() );
                        }
                    }
                } );
    }
}
