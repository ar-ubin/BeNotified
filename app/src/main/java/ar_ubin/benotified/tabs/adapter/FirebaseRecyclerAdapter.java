package ar_ubin.benotified.tabs.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * This is a modified Version of the Firebase UI Library
 * https://github.com/firebase/FirebaseUI-Android
 */
public abstract class FirebaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends
        RecyclerView.Adapter<VH> implements ValueEventListener
{
    private Class<T> mModelClass;
    protected int mModelLayout;
    private Class<VH> mViewHolderClass;
    private List<DatabaseReference> mFirebaseRefs;
    private List<DataSnapshot> mSnapshots;


    public FirebaseRecyclerAdapter( Class<T> modelClass, int modelLayout, Class<VH> viewHolderClass ) {
        mModelClass = modelClass;
        mModelLayout = modelLayout;
        mViewHolderClass = viewHolderClass;
        mFirebaseRefs = new ArrayList<>();
        mSnapshots = new ArrayList<>();
    }

    @Override
    public void onDataChange( DataSnapshot dataSnapshot ) {
        removeSnapshot( dataSnapshot.getKey() );

        mSnapshots.add( dataSnapshot );
        notifyDataSetChanged();
    }

    @Override
    public void onCancelled( DatabaseError databaseError ) {

    }

    public void addEventListener( DatabaseReference firebaseRef ) {
        mFirebaseRefs.add( firebaseRef );
        firebaseRef.addValueEventListener( this );
    }

    public void removeEventListener( DatabaseReference firebaseRef ) {
        removeSnapshot( firebaseRef.getKey() );
        mFirebaseRefs.remove( firebaseRef );
        firebaseRef.removeEventListener( this );
    }

    private void removeSnapshot( String dataKey ) {
        Iterator<DataSnapshot> dataSnapshotIterator = mSnapshots.iterator();

        while( dataSnapshotIterator.hasNext() ) {
            DataSnapshot snapshot = dataSnapshotIterator.next();

            if( snapshot.getKey().equals( dataKey ) ) {
                dataSnapshotIterator.remove();
            }
        }
    }

    public void cleanup() {
        for( DatabaseReference ref : mFirebaseRefs ) {
            removeEventListener( ref );
        }
        mSnapshots.clear();
    }

    @Override
    public int getItemCount() {
        return mSnapshots.size();
    }

    public T getItem( int position ) {
        return parseSnapshot( mSnapshots.get( position ) );
    }

    protected T parseSnapshot( DataSnapshot snapshot ) {
        return snapshot.getValue( mModelClass );
    }

    public DatabaseReference getRef( int position ) {
        return mFirebaseRefs.get( position );
    }

    @Override
    public long getItemId( int position ) {
        return mSnapshots.get( position ).getKey().hashCode();
    }

    @Override
    public VH onCreateViewHolder( ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( viewType, parent, false );
        try {
            Constructor<VH> constructor = mViewHolderClass.getConstructor( View.class );
            return constructor.newInstance( view );
        } catch( NoSuchMethodException e ) {
            throw new RuntimeException( e );
        } catch( InvocationTargetException e ) {
            throw new RuntimeException( e );
        } catch( InstantiationException e ) {
            throw new RuntimeException( e );
        } catch( IllegalAccessException e ) {
            throw new RuntimeException( e );
        }
    }

    @Override
    public void onBindViewHolder( VH viewHolder, int position ) {
        T model = getItem( position );
        populateViewHolder( viewHolder, model, position );
    }

    @Override
    public int getItemViewType( int position ) {
        return mModelLayout;
    }

    abstract protected void populateViewHolder( VH viewHolder, T model, int position );
}
