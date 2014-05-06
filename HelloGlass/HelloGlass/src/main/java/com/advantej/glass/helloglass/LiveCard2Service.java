package com.advantej.glass.helloglass;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.android.glass.timeline.LiveCard;

public class LiveCard2Service extends Service {

    private static final String LIVE_CARD_TAG = "LiveCard2";

    private ViewDrawer mViewDrawer;
    private LiveCard mLiveCard;

    public LiveCard2Service() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (mLiveCard == null)
        {
            mLiveCard = new LiveCard(this, LIVE_CARD_TAG);
            mViewDrawer = new ViewDrawer(this);

            mLiveCard.setDirectRenderingEnabled(true).getSurfaceHolder().addCallback(mViewDrawer);

            //IMP : set Action
            Intent menuIntent = new Intent(this, LiveCard2MenuActivity.class);
            menuIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mLiveCard.attach(this);
            mLiveCard.setAction(PendingIntent.getActivity(this, 0, menuIntent, 0));


            //Publish card
            mLiveCard.publish(LiveCard.PublishMode.REVEAL);
        }
        else
        {
            mLiveCard.navigate();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        if (mLiveCard != null && mLiveCard.isPublished())
        {
            mLiveCard.getSurfaceHolder().removeCallback(mViewDrawer);
            mLiveCard.unpublish();
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
