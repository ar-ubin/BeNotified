package ar_ubin.benotified.beacon;


import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import ar_ubin.benotified.data.models.Beacon;
import ar_ubin.benotified.data.source.beacons.BeaconDataSource;
import ar_ubin.benotified.data.source.beacons.BeaconRepository;
import ar_ubin.benotified.tabs.beacon.add.NewBeaconContract;
import ar_ubin.benotified.tabs.beacon.add.NewBeaconPresenter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class NewBeaconPresenterTest
{
    private static List<Beacon> BEACONS;

    @Mock
    private BeaconRepository mBeaconRepository;
    @Mock
    private NewBeaconContract.View mNewBeaconView;

    private NewBeaconPresenter mNewBeaconPresenter;

    @Captor
    private ArgumentCaptor<BeaconDataSource.SaveBeaconCallback> mSaveBeaconCallbackCaptor;

    @Before
    public void setupTasksPresenter() {
        MockitoAnnotations.initMocks( this );
        mNewBeaconPresenter = new NewBeaconPresenter( mBeaconRepository, mNewBeaconView );

        BEACONS = Lists.newArrayList( new Beacon( 1, 1, "Uuid1" ), new Beacon( 2, 1, "Uuid2" ),
                new Beacon( 3, 1, "Uuid3" ) );
    }

    @Test
    public void saveNewBeaconToRepository_showsSavingComplete() {
        mNewBeaconPresenter.saveBeacon( BEACONS.get( 0 ) );
        verify( mBeaconRepository ).saveBeacon( any( Beacon.class ), mSaveBeaconCallbackCaptor.capture() );

        mSaveBeaconCallbackCaptor.getValue().onBeaconSaved();
        verify( mNewBeaconView ).showSavingComplete( any( Beacon.class ) );
    }

    @Test
    public void saveNewBeaconToRepository_emptyBeaconShowsError() {
        Exception exception = new NullPointerException();

        mNewBeaconPresenter.saveBeacon( null );
        verify( mBeaconRepository ).saveBeacon( any( Beacon.class ), mSaveBeaconCallbackCaptor.capture() );

        mSaveBeaconCallbackCaptor.getValue().onFailed( exception );
        verify( mNewBeaconView ).showSavingError( exception.getMessage() );
    }
}
