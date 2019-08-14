package mandroid.tutorial.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

public class MyService_RedeliverIntent extends Service {

    private int mRandomNumber;
    private boolean mIsRandomGeneratorOn;

    private final int COUNTER=0;
    private IBinder mBinder = new MyserviceBinder();

    private class MyserviceBinder extends Binder{
        public MyService_RedeliverIntent getServiceInstance() {
            return MyService_RedeliverIntent.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(MainActivity.TAG,"SERVICE STARTED: THREAD ID  "+ Thread.currentThread().getId());
        //Log.v(MainActivity.TAG,"SERVICE  INtent  "+ intent.getStringExtra());
        Log.v(MainActivity.TAG,"SERVICE  ID  "+ intent.getAction());
        //stopSelf();
        //return super.onStartCommand(intent, flags, startId);
        mIsRandomGeneratorOn =true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                startRandomNumberGenerator();
            }
        }).start();
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(MainActivity.TAG," SERVICE DESTROYED "+ Thread.currentThread().getId());
        stopRandomNumberGenerator();
    }

    private void startRandomNumberGenerator(){
        while (mIsRandomGeneratorOn){
            try{
                Thread.sleep(1000);
                if(mIsRandomGeneratorOn){
                    mRandomNumber =mRandomNumber+1;
                    Log.i(MainActivity.TAG,"Thread id: "+Thread.currentThread().getId()+", Random Number: "+ mRandomNumber);
                }
            }catch (InterruptedException e){
                Log.i(MainActivity.TAG,"Thread Interrupted");
            }

        }
    }

    private void stopRandomNumberGenerator(){
        mIsRandomGeneratorOn =false;
    }

}
