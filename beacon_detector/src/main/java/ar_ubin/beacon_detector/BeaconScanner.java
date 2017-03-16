package ar_ubin.beacon_detector;


import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class BeaconScanner extends BeaconBluetoothService implements ServiceConnection
{
    private final String TAG = BeaconScanner.class.getSimpleName();

    private BeaconService mService;
    private boolean mIsBound;
    private BeaconNotifier mBeaconCallback;

    public BeaconScanner( Context context, BeaconNotifier beaconCallback ) {
        final Intent intent = new Intent( context, BeaconBluetoothService.class );
        mIsBound = context.getApplicationContext().bindService( intent, this, Service.BIND_AUTO_CREATE );
        this.mBeaconCallback = beaconCallback;
    }

    public void startScanning() {
        super.startOneScan();
    }

    public void destroyService() {
        if( mIsBound ) {
            this.mService.unregisterCallback();
            this.mService.stopService();
        }
    }

    @Override
    public void onServiceConnected( ComponentName name, IBinder iBinderService ) {
        this.mService = ( (BeaconBluetoothService.LocalBinder) iBinderService ).getService();
        this.mService.registerCallback( mBeaconCallback );
        this.mService.startService();
        if( BeaconConfig.DEBUG ) {
            Log.d( TAG, "Service connected" );
        }
    }

    @Override
    public void onServiceDisconnected( ComponentName name ) {
        if( this.mService != null ) {
            this.mService.unregisterCallback();
            this.mService.stopService();
        }
        if( BeaconConfig.DEBUG ) {
            Log.d( TAG, "Service disconnected" );
        }
    }

    public void setDebugLogging( boolean flag ) {
        BeaconConfig.DEBUG = flag;
    }
}
