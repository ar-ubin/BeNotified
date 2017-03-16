package ar_ubin.benotified;


import android.app.Activity;

import ar_ubin.benotified.base.BaseActivity;
import ar_ubin.benotified.utils.ActivityScope;
import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent
{
    void inject( BaseActivity baseActivity );

    Activity activity();
}