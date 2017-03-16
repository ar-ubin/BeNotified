package ar_ubin.benotified.tabs.messages.own;


import com.google.firebase.database.DatabaseReference;

import ar_ubin.benotified.base.BasePresenter;
import ar_ubin.benotified.base.BaseView;

public interface OwnMessagesContract
{
    interface View extends BaseView<Presenter>
    {
        void onMessageRefAdded( DatabaseReference messageRef );

        void onMessageRefRemoved( DatabaseReference messageRef );
    }

    interface Presenter extends BasePresenter
    {
        void getMessages();

        void setMessageRefListener();
    }
}
