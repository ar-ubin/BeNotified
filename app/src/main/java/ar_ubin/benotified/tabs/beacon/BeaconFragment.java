package ar_ubin.benotified.tabs.beacon;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import javax.inject.Inject;

import ar_ubin.benotified.R;
import ar_ubin.benotified.base.BaseFragment;
import ar_ubin.benotified.data.models.Beacon;
import ar_ubin.benotified.tabs.TabActivity;

import static ar_ubin.benotified.data.Constants.DB_BEACONS;
import static com.google.common.base.Preconditions.checkNotNull;

public class BeaconFragment extends BaseFragment implements BeaconContract.View
{
    @Inject
    BeaconPresenter mPresenter;
    private BeaconListener mBeaconListener;

    public interface BeaconListener
    {
        void showBeacon( Beacon beacon );

        void editBeacon( Beacon beacon );
    }

    public static BeaconFragment newInstance() {
        return new BeaconFragment();
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        if( context instanceof BeaconListener ) {
            mBeaconListener = (BeaconListener) context;
        }
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        initializeInjector();
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        final View fragmentView = inflater.inflate( R.layout.fragment_beacon, container, false );

        RecyclerView beaconList = (RecyclerView) fragmentView.findViewById( R.id.beacon_list );
        LinearLayoutManager manager = new LinearLayoutManager( getActivity() );
        manager.setReverseLayout( true );
        manager.setStackFromEnd( true );
        beaconList.setLayoutManager( manager );

        Query postsQuery = FirebaseDatabase.getInstance().getReference().child( DB_BEACONS );
        FirebaseRecyclerAdapter<Beacon, BeaconViewHolder> mAdapter = new FirebaseRecyclerAdapter<Beacon, BeaconViewHolder>(
                Beacon.class, R.layout.card_beacon, BeaconViewHolder.class, postsQuery )
        {
            @Override
            protected void populateViewHolder( final BeaconViewHolder viewHolder, final Beacon model,
                                               final int position ) {
                viewHolder.bindToBeacon( model, new View.OnClickListener()
                {
                    @Override
                    public void onClick( View starView ) {
                        mBeaconListener.editBeacon( model );
                    }
                } );
            }
        };

        beaconList.setAdapter( mAdapter );

        return fragmentView;
    }

    @Override
    public void setPresenter( BeaconContract.Presenter presenter ) {
        mPresenter = (BeaconPresenter) checkNotNull( presenter );
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        super.onViewCreated( view, savedInstanceState );
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    private void initializeInjector() {
        DaggerBeaconComponent.builder()
                .applicationComponent( ( (TabActivity) getActivity() ).getApplicationComponent() )
                .beaconPresenterModule( new BeaconPresenterModule( this ) ).build().inject( this );
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        mBeaconListener = null;
        super.onDetach();
    }
}
