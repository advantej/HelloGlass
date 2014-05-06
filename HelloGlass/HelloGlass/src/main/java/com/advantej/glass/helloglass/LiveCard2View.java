package com.advantej.glass.helloglass;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by tejas on 5/4/14.
 */
public class LiveCard2View extends FrameLayout
{

    public TextView mTextView;

    private boolean mRunning;

    private Listener mChangeListener;

    static final long DELAY_MILLIS = 500;

    public LiveCard2View(Context context)
    {
        this(context, null, 0);
    }

    public LiveCard2View(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public LiveCard2View(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.live_card_2, this);

        mTextView = (TextView) findViewById(R.id.tv_live_card_2);

        updateText();
    }

    /**
     * Interface to listen for changes on the view layout.
     */
    public interface Listener
    {
        /**
         * Notified of a change in the view.
         */
        public void onChange();
    }

    private final Handler mHandler = new Handler();

    private final Runnable mUpdateTextRunnable = new Runnable()
    {

        @Override
        public void run()
        {
            if (mRunning)
            {
                updateText();
                mHandler.postDelayed(mUpdateTextRunnable, DELAY_MILLIS);
            }
        }
    };

    public void start()
    {
        if (!mRunning)
        {
            mHandler.postDelayed(mUpdateTextRunnable, DELAY_MILLIS);
        }
        mRunning = true;
    }

    public void stop()
    {
        if (mRunning)
        {
            mHandler.removeCallbacks(mUpdateTextRunnable);
        }
        mRunning = false;
    }

    private void updateText()
    {
        mTextView.setText(String.valueOf(SystemClock.elapsedRealtime() % 1000));

        if (mChangeListener != null) {
            mChangeListener.onChange();
        }
    }

    public void setChangeListener(Listener changeListener)
    {
        mChangeListener = changeListener;
    }
}
