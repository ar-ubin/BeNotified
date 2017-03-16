package ar_ubin.benotified.data.source;


import android.content.Context;

import javax.inject.Singleton;

import ar_ubin.benotified.data.FakeBeaconsRemoteDataSource;
import ar_ubin.benotified.data.FakeMessagesRemoteDataSource;
import ar_ubin.benotified.data.FakeUserRemoteDataSource;
import ar_ubin.benotified.data.source.beacons.BeaconDataSource;
import ar_ubin.benotified.data.source.local.BeaconLocalDataSource;
import ar_ubin.benotified.data.source.local.MessagesLocalDataSource;
import ar_ubin.benotified.data.source.local.UserLocalDataSource;
import ar_ubin.benotified.data.source.messages.MessagesDataSource;
import ar_ubin.benotified.data.source.user.UserDataSource;
import dagger.Provides;

public class BeNotifiedRepositoryModule
{
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
        return new FakeBeaconsRemoteDataSource();
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
    MessagesDataSource provideMessagesRemoteDataSource() {
        return new FakeMessagesRemoteDataSource();
    }

    @ApplicationScope
    @Provides
    @Local
    UserDataSource provideUserLocalDataSource( Context context ) {
        return new UserLocalDataSource( context );
    }

    @ApplicationScope
    @Provides
    @Remote
    UserDataSource provideUserRemoteDataSource() {
        return new FakeUserRemoteDataSource();
    }
}
