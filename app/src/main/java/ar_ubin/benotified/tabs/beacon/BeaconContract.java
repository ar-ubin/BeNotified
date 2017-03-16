package ar_ubin.benotified.tabs.beacon;


import ar_ubin.benotified.base.BasePresenter;
import ar_ubin.benotified.base.BaseView;

public interface BeaconContract
{
    interface View extends BaseView<BeaconContract.Presenter>
    {
    }

    interface Presenter extends BasePresenter
    {
    }
}
