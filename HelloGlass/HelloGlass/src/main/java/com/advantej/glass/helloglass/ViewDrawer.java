package com.advantej.glass.helloglass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

import com.google.android.glass.timeline.DirectRenderingCallback;

/**
 * Created by tejas on 5/5/14.
 */
class ViewDrawer implements DirectRenderingCallback
{

    private static final String TAG = "ViewDrawer";
    private LiveCard2View mLiveCard2View;
    private SurfaceHolder mHolder;

    private LiveCard2View.Listener mLiveCard2ViewListener = new LiveCard2View.Listener()
    {
        @Override
        public void onChange()
        {
            if (mHolder != null)
            {
                draw(mLiveCard2View);
            }
        }
    };
    private boolean mRenderingPaused;


    public ViewDrawer(Context context)
    {
        mLiveCard2View = new LiveCard2View(context);
        mLiveCard2View.setChangeListener(mLiveCard2ViewListener);
    }

    @Override
    public void renderingPaused(SurfaceHolder surfaceHolder, boolean paused)
    {
        mRenderingPaused = paused;
        updateRenderingState();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        mRenderingPaused = false;
        mHolder = holder;
        updateRenderingState();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        // Measure and layout the view with the canvas dimensions.
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);

        mLiveCard2View.measure(measuredWidth, measuredHeight);
        mLiveCard2View.layout(0, 0, mLiveCard2View.getMeasuredWidth(), mLiveCard2View.getMeasuredHeight());

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        mHolder = null;
        updateRenderingState();
    }

    private void updateRenderingState()
    {
        if (mHolder != null && !mRenderingPaused)
        {
            mLiveCard2View.start();
        } else
        {
            mLiveCard2View.stop();
        }
    }

    private void draw(View view)
    {
        Canvas canvas;
        try
        {
            canvas = mHolder.lockCanvas();
        } catch (Exception e)
        {
            Log.e(TAG, "Unable to lock canvas: " + e);
            return;
        }

        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            view.draw(canvas);

            try {
                mHolder.unlockCanvasAndPost(canvas);
            } catch (RuntimeException e) {
                Log.d(TAG, "unlockCanvasAndPost failed", e);
            }
        }
    }
}
