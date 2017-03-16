package ar_ubin.beacon_detector;


import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class BeaconBluetoothService extends Service implements BeaconService
{
    private final String TAG = BeaconBluetoothService.class.getSimpleName();

    protected final BeaconNotifier mEmptyNotifier = new EmptyCallBackNotifier();
    protected final Runnable mStatusChecker = new BeaconScanRunnable();
    protected final IBinder mAppBinder = new LocalBinder();

    private final Handler mHandler;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;
    private ScanCallback mScanCallback;
    private BeaconDataConverter mConverter;
    private BeaconNotifier mNotifier;

    private boolean mStartNextScan = false;

    public BeaconBluetoothService() {
        this.mHandler = new Handler();
        this.mConverter = new BeaconDataConverter();
        this.mNotifier = mEmptyNotifier;
        initScanCallback();
    }

    private void initScanCallback() {
        mScanCallback = new ScanCallback()
        {
            @Override
            public void onScanResult( int callbackType, ScanResult result ) {
                super.onScanResult( callbackType, result );
                final String deviceName = result.getDevice().getName();

                if( BeaconConfig.getInstance().isBeaconSupported( deviceName ) ) {
                    final Beacon beacon = new Beacon();

                    mConverter.decodeData( result.getScanRecord().getBytes(), beacon );
                    mConverter.calculateAccuracy( result.getRssi(), beacon );
                    beacon.setName( deviceName + "-" + beacon.getMinorNumber() );
                    beacon.setRssi( result.getRssi() );

                    Log.d( TAG, beacon.toString() );

                    mNotifier.onBeaconFound( beacon );
                }
            }

            @Override
            public void onBatchScanResults( List<ScanResult> results ) {
                super.onBatchScanResults( results );
            }

            @Override
            public void onScanFailed( int errorCode ) {
                super.onScanFailed( errorCode );
                Log.e( "BEACON", "Start BT LE Scan failed. ErrorCode: " + errorCode );
            }
        };
    }

    protected void startOneScan() {
        this.mHandler.postDelayed( new Runnable()
        {
            @Override
            public void run() {
                Log.d( "BEACON", "Stop scanning" );
                stopScan();
            }
        }, BeaconConfig.getInstance().getScanPeriod() );

        if( BeaconConfig.DEBUG ) Log.d( "BEACON", "Start scanning" );

        if( isBluetoothOk() ) {
            mBluetoothLeScanner.startScan( mScanCallback );
        }
    }

    @Override
    public IBinder onBind( final Intent intent ) {
        return this.mAppBinder;
    }

    @Override
    public boolean startService() {
        if( this.mNotifier == null ) {
            throw new IllegalArgumentException( "Callback is not set. Do you even want to be notified about beacons?" );
        }

        startOneScan();

        return true;
    }

    @Override
    public boolean stopService() {
        this.mStartNextScan = false;
        return stopScan();
    }

    private boolean stopScan() {
        if( mBluetoothLeScanner != null ) {
            this.mBluetoothLeScanner.stopScan( mScanCallback );
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void registerCallback( final BeaconNotifier notifier ) {
        this.mNotifier = notifier;
    }

    @Override
    public void unregisterCallback() {
        this.mNotifier = mEmptyNotifier;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        this.mStartNextScan = false;
        this.stopService();
        super.onDestroy();
    }

    @Override
    public boolean onUnbind( final Intent intent ) {
        return this.stopService() && super.onUnbind( intent );
    }

    private boolean isBluetoothOk() {
        if( this.mBluetoothAdapter == null ) {
            this.mBluetoothAdapter = getBtAdapter();
            this.mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        }

        if( !isBtEnabled() ) {
            final Intent enableBtIntent = new Intent( BluetoothAdapter.ACTION_REQUEST_ENABLE );
            enableBtIntent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            this.getBaseContext().startActivity( enableBtIntent );
        }

        return isBtEnabled();
    }

    private BluetoothAdapter getBtAdapter() {
        if( !getPackageManager().hasSystemFeature( PackageManager.FEATURE_BLUETOOTH_LE ) ) {
            Toast.makeText( this.getBaseContext(), "I need Bluetoth LE", Toast.LENGTH_LONG ).show();
            this.stopSelf();
        }

        return BluetoothAdapter.getDefaultAdapter();
    }

    private boolean isBtEnabled() {
        return this.mBluetoothAdapter != null && this.mBluetoothAdapter.isEnabled();
    }

    private class BeaconScanRunnable implements Runnable
    {
        @Override
        public void run() {
            startOneScan();
            if( mStartNextScan ) {
                mHandler.postDelayed( mStatusChecker, BeaconConfig.getInstance().getWaitBetweenScans() );
            }
        }
    }

    private final class EmptyCallBackNotifier implements BeaconNotifier
    {
        @Override
        public void onBeaconFound( Beacon beacon ) {
        }
    }

    public class LocalBinder extends Binder
    {
        public BeaconService getService() {
            return BeaconBluetoothService.this;
        }
    }
}
