package ar_ubin.benotified.tabs;

import android.support.v4.app.FragmentManager;

import ar_ubin.benotified.utils.ActivityScope;
import dagger.Module;
import dagger.Provides;

@Module
public class TabActivityModule
{
    private final TabActivity mTabActivity;

    public TabActivityModule( TabActivity mTabActivity ) {
        this.mTabActivity = mTabActivity;
    }

    @Provides
    @ActivityScope
    public TabActivity provideTabActivity(){
        return mTabActivity;
    }

    @Provides
    @ActivityScope
    public FragmentManager provideFragmentManager(){
        return mTabActivity.getSupportFragmentManager();
    }
}
