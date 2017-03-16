package ar_ubin.benotified.data.source;


import android.content.Context;

import ar_ubin.benotified.data.source.beacons.BeaconDataSource;
import ar_ubin.benotified.data.source.local.BeaconLocalDataSource;
import ar_ubin.benotified.data.source.local.MessagesLocalDataSource;
import ar_ubin.benotified.data.source.local.UserLocalDataSource;
import ar_ubin.benotified.data.source.messages.MessagesDataSource;
import ar_ubin.benotified.data.source.remote.BeaconRemoteDataSource;
import ar_ubin.benotified.data.source.remote.MessagesRemoteDataSource;
import ar_ubin.benotified.data.source.remote.UserRemoteDataSource;
import ar_ubin.benotified.data.source.user.UserDataSource;
import ar_ubin.benotified.utils.ApplicationScope;
import dagger.Module;
import dagger.Provides;

@Module
public class BeNotifiedRepositoryModule
{
    @ApplicationScope
    @Provides
    @Local
    UserDataSource provideUserLocalDataSource( Context context ) {
        return new UserLocalDataSource( context );
    }

    @ApplicationScope
    @Provides
    @Remote
    UserDataSource provideUserRemoteDataSource( Context context ) {
        return new UserRemoteDataSource( context );
    }

    @ApplicationScope
    @Provides
    @Local
    MessagesDataSource provideMessagesLocalDataSource( Context context ) {
        return new MessagesLocalDataSource( context );
    }

    @ApplicationScope
    @Provides
    @Remote
    MessagesDataSource provideMessagesRemoteDataSource( Context context ) {
        return new MessagesRemoteDataSource( context );
    }

    @ApplicationScope
    @Provides
    @Local
    BeaconDataSource provideBeaconLocalDataSource( Context context ) {
        return new BeaconLocalDataSource( context );
    }

    @ApplicationScope
    @Provides
    @Remote
    BeaconDataSource provideBeaconRemoteDataSource() {
        return new BeaconRemoteDataSource();
    }
}