package ar_ubin.benotified.tabs.messages.received;


import com.google.firebase.database.DatabaseReference;

import ar_ubin.benotified.base.BasePresenter;
import ar_ubin.benotified.base.BaseView;

public interface ReceivedMessagesContract
{
    interface View extends BaseView<ReceivedMessagesContract.Presenter>
    {
        void onMessageRefAdded( DatabaseReference messageRef );

        void onMessageRefRemoved( DatabaseReference messageRef );
    }

    interface Presenter extends BasePresenter
    {
        void setMessageRefListener();

        void receiveMessages( int beaconMinor );
    }
}
