package ar_ubin.benotified.login;


import ar_ubin.benotified.ActivityComponent;
import ar_ubin.benotified.ActivityModule;
import ar_ubin.benotified.ApplicationComponent;
import ar_ubin.benotified.utils.ActivityScope;
import dagger.Component;

@ActivityScope
@Component( dependencies = ApplicationComponent.class, modules = { ActivityModule.class, LoginPresenterModule.class } )
public interface LoginComponent extends ActivityComponent
{
    void inject( LoginActivity activity );
}

