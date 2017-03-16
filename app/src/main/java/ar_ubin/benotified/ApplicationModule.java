package ar_ubin.benotified;


import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public final class ApplicationModule
{
    private final BeNotifiedApplication mContext;

    ApplicationModule( BeNotifiedApplication context ) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
