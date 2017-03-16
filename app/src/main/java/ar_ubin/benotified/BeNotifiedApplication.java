package ar_ubin.benotified;


import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class BeNotifiedApplication extends Application
{
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjectors();
        initializeLeakDetection();
    }

    private void initializeInjectors() {
        mApplicationComponent = DaggerApplicationComponent.builder().applicationModule( new ApplicationModule( this ) )
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    private void initializeLeakDetection() {
        if( BuildConfig.DEBUG ) {
            LeakCanary.install( this );
        }
    }
}
