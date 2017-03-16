package ar_ubin.beacon_detector;


public interface BeaconService
{
    boolean startService();

    boolean stopService();

    void registerCallback( BeaconNotifier callback );

    void unregisterCallback();
}
