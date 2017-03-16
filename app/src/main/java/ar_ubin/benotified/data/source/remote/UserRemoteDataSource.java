package ar_ubin.benotified.data.source.remote;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ar_ubin.benotified.data.models.Credentials;
import ar_ubin.benotified.data.models.User;
import ar_ubin.benotified.data.source.user.UserDataSource;
import ar_ubin.benotified.utils.ApplicationScope;

import static ar_ubin.benotified.data.Constants.DB_USERS;
import static com.google.common.base.Preconditions.checkNotNull;

@ApplicationScope
public class UserRemoteDataSource implements UserDataSource
{
    private final Context mContext;
    private final FirebaseAuth mAuth;
    private final DatabaseReference mDatabase;

    public UserRemoteDataSource( @NonNull Context context ) {
        checkNotNull( context );
        mContext = context;
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void getUser( @NonNull Activity activity, @NonNull final Credentials credentials,
                         @NonNull final GetUserCallback callback ) {
        mAuth.signInWithEmailAndPassword( credentials.getEmail(), credentials.getPassword() )
                .addOnCompleteListener( activity, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete( @NonNull Task<AuthResult> task ) {
                        if( task.isSuccessful() ) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            User currentUser = new User( firebaseUser.getUid(), firebaseUser.getEmail() );
                            storeUser( currentUser );
                            callback.onUserLoaded( currentUser );
                        } else {
                            callback.onFailed( task.getException() );
                        }
                    }
                } );
    }

    @Override
    public void updateUser( @NonNull User user ) {

    }

    @Override
    public void storeUser( @NonNull User user ) {
        mDatabase.child( DB_USERS ).child( mAuth.getCurrentUser().getUid() ).setValue( user );
    }

    @Override
    public void reloadUser() {

    }

    @Override
    public void signOut() {
        mAuth.signOut();
    }

    @Override
    public void deleteUser() {

    }
}
