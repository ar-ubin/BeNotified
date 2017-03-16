package ar_ubin.beacon_detector;

import java.util.ArrayList;
import java.util.List;


public class RssiFifoQueue
{
    private final int mMaxSize;
    public List<Integer> mQueue;

    public RssiFifoQueue( final int maxSize ) {
        this.mMaxSize = maxSize;
        mQueue = new ArrayList<>();
    }

    public int size() {
        return mQueue.size();
    }

    public int getMaxSize() {
        return mMaxSize;
    }

    public void add( int rssi ) {
        if( mQueue.size() < mMaxSize ) {
            mQueue.add( rssi );
        } else {
            mQueue.remove( 0 );
            mQueue.add( rssi );
        }
    }

    public void remove( int index ) {
        if( index >= 0 && index < mQueue.size() ) {
            mQueue.remove( index );
        }
    }

    public void clear() {
        mQueue.clear();
    }

    public int getFirst() {
        return mQueue.get( 0 );
    }

    public int get( int index ) {
        return mQueue.get( index );
    }
}
