package ar_ubin.benotified.beacon;


import com.google.common.collect.Lists;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import ar_ubin.benotified.data.models.Beacon;
import ar_ubin.benotified.data.source.beacons.BeaconRepository;
import ar_ubin.benotified.tabs.beacon.BeaconContract;
import ar_ubin.benotified.tabs.beacon.BeaconPresenter;

public class BeaconPresenterTest
{
    private static List<Beacon> BEACONS;

    @Mock
    private BeaconRepository mBeaconRepository;

    @Mock
    private BeaconContract.View mBeaconView;

    private BeaconPresenter mBeaconPresenter;

    @Before
    public void setupTasksPresenter() {
        MockitoAnnotations.initMocks( this );

        mBeaconPresenter = new BeaconPresenter( mBeaconRepository, mBeaconView );

        BEACONS = Lists.newArrayList( new Beacon( 1, 1, "Uuid1" ), new Beacon( 2, 1, "Uuid2" ),
                new Beacon( 3, 1, "Uuid3" ) );
    }
}
