package ar_ubin.benotified.data.source;


import android.app.Activity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ar_ubin.benotified.data.models.Beacon;
import ar_ubin.benotified.data.source.beacons.BeaconDataSource;
import ar_ubin.benotified.data.source.beacons.BeaconRepository;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class BeaconRepositoryTest
{
    private Beacon BEACON = new Beacon( 1, 1, "Uuid1" );
    private BeaconRepository mBeaconRepository;

    @Mock
    private BeaconDataSource mBeaconRemoteDataSource;
    @Mock
    private BeaconDataSource mBeaconLocalDataSource;

    @Mock
    private Activity mActivity;

    @Mock
    private BeaconDataSource.SaveBeaconCallback mSaveBeaconCallback;
    @Captor
    private ArgumentCaptor<BeaconDataSource.SaveBeaconCallback> mSaveBeaconCallbackCaptor;

    @Before
    public void setupTasksRepository() {
        MockitoAnnotations.initMocks( this );

        mBeaconRepository = new BeaconRepository( mBeaconRemoteDataSource, mBeaconLocalDataSource );
    }

    @Test
    public void saveBeaconToRemoteDataSource_firesOnBeaconSaved() {
        mBeaconRepository.saveBeacon( BEACON, mSaveBeaconCallback );
        verify( mBeaconRemoteDataSource ).saveBeacon( any( Beacon.class ), mSaveBeaconCallbackCaptor.capture() );

        mSaveBeaconCallbackCaptor.getValue().onBeaconSaved();
        verify( mSaveBeaconCallback ).onBeaconSaved();
    }

    @Test
    public void saveBeaconToRemoteDataSource_firesOnFailed() {
        Exception exception = new NullPointerException();

        mBeaconRepository.saveBeacon( BEACON, mSaveBeaconCallback );
        verify( mBeaconRemoteDataSource ).saveBeacon( any( Beacon.class ), mSaveBeaconCallbackCaptor.capture() );

        mSaveBeaconCallbackCaptor.getValue().onFailed( exception );
        verify( mSaveBeaconCallback ).onFailed( exception );
    }
}
