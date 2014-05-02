package com.advantej.glass.helloglass;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.google.android.glass.timeline.LiveCard;

import java.util.Random;

public class LiveCard1Service extends Service {

    private static final String LIVE_CARD_TAG = "LiveCard1";

    // https://developers.google.com/glass/develop/gdk/live-cards
    private LiveCard mLiveCard;
    private RemoteViews mLiveCardView;

    private final Handler mHandler = new Handler();
    private Random mRandomNumGenerator;

    private final UpdateLiveCardRunnable mUpdateLiveCardRunnable =
            new UpdateLiveCardRunnable();

    public LiveCard1Service() {
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mRandomNumGenerator = new Random();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (mLiveCard == null) {
            mLiveCard = new LiveCard(this, LIVE_CARD_TAG);

            mLiveCardView = new RemoteViews(getPackageName(), R.layout.live_card_1);
            mLiveCardView.setTextViewText(R.id.tv_live_card_1, "Hello Live Card");


            //IMP : set Action
            Intent menuIntent = new Intent(this, LiveCard1MenuActivity.class);
            menuIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mLiveCard.setAction(PendingIntent.getActivity(this, 0, menuIntent, 0));


            //Publish card
            mLiveCard.publish(LiveCard.PublishMode.REVEAL);

            mHandler.post(mUpdateLiveCardRunnable);

        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mLiveCard != null && mLiveCard.isPublished()) {
            mUpdateLiveCardRunnable.setStop(true);

            mLiveCard.unpublish();
            mLiveCard = null;
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    private class UpdateLiveCardRunnable implements Runnable
    {
        private boolean mIsStopped = false;

        @Override
        public void run()
        {
            if (!isStopped()) {
                mLiveCardView.setTextViewText(R.id.tv_live_card_1, " Value is  : " + String.valueOf(mRandomNumGenerator.nextInt(5)));
                mLiveCard.setViews(mLiveCardView);

                mHandler.postDelayed(mUpdateLiveCardRunnable, 5000); //every 5 seconds
            }

        }

        public boolean isStopped() {
            return mIsStopped;
        }

        public void setStop(boolean isStopped) {
            this.mIsStopped = isStopped;
        }
    }
}
