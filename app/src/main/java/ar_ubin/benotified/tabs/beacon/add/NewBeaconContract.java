package ar_ubin.benotified.tabs.beacon.add;


import android.support.annotation.NonNull;

import ar_ubin.benotified.base.BasePresenter;
import ar_ubin.benotified.base.BaseView;
import ar_ubin.benotified.data.models.Beacon;

public interface NewBeaconContract
{
    interface View extends BaseView<NewBeaconContract.Presenter>
    {
        void showSavingComplete( Beacon beacon );

        void showSavingError( String message );
    }

    interface Presenter extends BasePresenter
    {
        void saveBeacon( @NonNull Beacon beacon );
    }
}
