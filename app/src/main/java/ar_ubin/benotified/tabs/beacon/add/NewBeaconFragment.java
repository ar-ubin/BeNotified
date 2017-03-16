package ar_ubin.benotified.tabs.beacon.add;


import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.thebluealliance.spectrum.SpectrumDialog;

import java.util.ArrayList;

import ar_ubin.benotified.R;
import ar_ubin.benotified.base.BaseFragment;
import ar_ubin.benotified.data.models.Beacon;

import static com.google.common.base.Preconditions.checkNotNull;

public class NewBeaconFragment extends BaseFragment implements NewBeaconContract.View
{
    protected static final String ARGUMENT_EDIT_BEACON = "edit_beacon";
    private CreateBeaconListener mListener;
    public NewBeaconContract.Presenter mPresenter;

    private Beacon mBeacon;
    private SpectrumDialog.Builder mColorDialogBuilder;

    private EditText vBeaconName;
    private EditText vBeaconMinor;
    private EditText vBeaconMajor;
    private EditText vBeaconUuid;
    private EditText vBeaconLocation;
    private AppCompatSpinner vBeaconRadius;
    private FloatingActionButton vBeaconColor;

    private int mSelectedRadius;
    private int mSelectedColor;

    public interface CreateBeaconListener
    {
        void onBeaconCreated( Beacon beacon );
    }

    public static NewBeaconFragment newInstance( @NonNull final Beacon beacon ) {
        Bundle arguments = new Bundle();
        arguments.putSerializable( ARGUMENT_EDIT_BEACON, beacon );
        NewBeaconFragment fragment = new NewBeaconFragment();
        fragment.setArguments( arguments );
        return fragment;
    }

    @Override
    public void setPresenter( NewBeaconContract.Presenter presenter ) {
        mPresenter = checkNotNull( presenter );
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach( context );
        if( context instanceof CreateBeaconListener ) {
            mListener = (CreateBeaconListener) context;
        }
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        final View fragmentView = inflater.inflate( R.layout.fragment_new_beacon, container, false );

        vBeaconName = (EditText) fragmentView.findViewById( R.id.new_beacon_name );
        vBeaconMinor = (EditText) fragmentView.findViewById( R.id.new_beacon_minor );
        vBeaconMajor = (EditText) fragmentView.findViewById( R.id.new_beacon_major );
        vBeaconUuid = (EditText) fragmentView.findViewById( R.id.new_beacon_uuid );
        vBeaconLocation = (EditText) fragmentView.findViewById( R.id.new_beacon_location );
        vBeaconRadius = (AppCompatSpinner) fragmentView.findViewById( R.id.new_beacon_radius );
        vBeaconColor = (FloatingActionButton) fragmentView.findViewById( R.id.new_beacon_color );
        FloatingActionButton fab = (FloatingActionButton) fragmentView.findViewById( R.id.save_beacon );

        initBeacon();
        initColorDialog();
        initRadius();

        vBeaconColor.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v ) {
                showColorDialog();
            }
        } );
        fab.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view ) {
                createBeacon();
            }
        } );

        return fragmentView;
    }

    private void initBeacon() {
        mBeacon = new Beacon();
        if( getArguments().getSerializable( ARGUMENT_EDIT_BEACON ) != null ) {
            mBeacon = (Beacon) getArguments().getSerializable( ARGUMENT_EDIT_BEACON );
            if( !isNewBeacon() ) {
                vBeaconName.setText( mBeacon.getName() );
                vBeaconMinor.setText( String.valueOf( mBeacon.getMinor() ) );
                vBeaconMajor.setText( String.valueOf( mBeacon.getMajor() ) );
                vBeaconUuid.setText( mBeacon.getUuid() );
                vBeaconLocation.setText( mBeacon.getLocation() );
                vBeaconColor.setBackgroundTintList( ColorStateList.valueOf( mBeacon.getColor() ) );

                mSelectedRadius = mBeacon.getRadius();
                mSelectedColor = mBeacon.getColor();
            } else {
                initColor();
            }
        } else {
            initColor();
        }
    }

    private void initColor() {
        int[] colors = getResources().getIntArray( R.array.beacon_colors );
        mSelectedColor = colors[0];
        vBeaconColor.setBackgroundTintList( ColorStateList.valueOf( mSelectedColor ) );
    }

    private boolean isNewBeacon() {
        return mBeacon == null || mBeacon.getUuid().isEmpty();
    }


    private void initRadius() {
        final ArrayList<String> items = new ArrayList<>();
        items.add( Beacon.RADIUS_IMMEDIATE, "Immediate" );
        items.add( Beacon.RADIUS_NEAR, "Near" );
        items.add( Beacon.RADIUS_FAR, "Far" );

        ArrayAdapter<String> adapter = new ArrayAdapter<>( getActivity(), android.R.layout.simple_list_item_1, items );
        vBeaconRadius.setAdapter( adapter );
        vBeaconRadius.setSelection( adapter.getPosition( items.get( mSelectedRadius ) ) );

        vBeaconRadius.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected( AdapterView<?> parentView, View v, int position, long id ) {
                mSelectedRadius = position;
            }

            @Override
            public void onNothingSelected( AdapterView<?> parent ) {
                //NOTHING TO DO
            }
        } );
    }

    private void showColorDialog() {
        mColorDialogBuilder.setSelectedColor( mSelectedColor ).build().show( getFragmentManager(), "dialog_demo_1" );
    }

    private void initColorDialog() {
        mColorDialogBuilder = new SpectrumDialog.Builder( getContext() ).setColors( R.array.beacon_colors )
                .setDismissOnColorSelected( true ).setOutlineWidth( 2 )
                .setOnColorSelectedListener( new SpectrumDialog.OnColorSelectedListener()
                {
                    @Override
                    public void onColorSelected( boolean positiveResult, @ColorInt int color ) {
                        if( positiveResult ) {
                            mSelectedColor = color;
                            vBeaconColor.setBackgroundTintList( ColorStateList.valueOf( mSelectedColor ) );
                        }
                    }
                } );
    }

    private void createBeacon() {
        if( validateForm() ) {
            mBeacon.setMajor( Integer.parseInt( vBeaconMajor.getText().toString() ) );
            mBeacon.setMinor( Integer.parseInt( vBeaconMinor.getText().toString() ) );
            mBeacon.setUuid( vBeaconUuid.getText().toString() );
            mBeacon.setName( vBeaconName.getText().toString() );
            mBeacon.setLocation( vBeaconLocation.getText().toString() );
            mBeacon.setRadius( mSelectedRadius );
            mBeacon.setColor( mSelectedColor );

            mPresenter.saveBeacon( mBeacon );
            showProgressDialog();
        }
    }

    private boolean validateForm() {
        return validateRequiredText( vBeaconName ) &&
                validateRequiredNumber( vBeaconMinor ) &&
                validateRequiredNumber( vBeaconMajor );
    }

    @Override
    public void showSavingComplete( Beacon beacon ) {
        hideProgressDialog();
        mListener.onBeaconCreated( beacon );
    }

    @Override
    public void showSavingError( String message ) {
        hideProgressDialog();
        Snackbar.make( getView(), "Saving Beacon failed: " + message, Snackbar.LENGTH_SHORT ).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }
}
