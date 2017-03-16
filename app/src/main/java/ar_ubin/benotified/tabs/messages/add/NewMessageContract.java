package ar_ubin.benotified.tabs.messages.add;


import android.support.annotation.NonNull;

import ar_ubin.benotified.base.BasePresenter;
import ar_ubin.benotified.base.BaseView;
import ar_ubin.benotified.data.models.Message;

public interface NewMessageContract
{
    interface View extends BaseView<NewMessageContract.Presenter>
    {
        void showSavingComplete();

        void showSavingError( String message );
    }

    interface Presenter extends BasePresenter
    {
        void saveMessage( @NonNull Message message );
    }
}
