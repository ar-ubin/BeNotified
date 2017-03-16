package ar_ubin.benotified.tabs.messages.details;


import android.support.annotation.NonNull;

import ar_ubin.benotified.base.BasePresenter;
import ar_ubin.benotified.base.BaseView;
import ar_ubin.benotified.data.models.Message;

public interface MessageDetailsContract
{
    interface View extends BaseView<MessageDetailsContract.Presenter>
    {
        void showDeletingComplete();

        void showDeletingError( String message );
    }

    interface Presenter extends BasePresenter
    {
        void deleteMessage( @NonNull Message message );
    }
}
