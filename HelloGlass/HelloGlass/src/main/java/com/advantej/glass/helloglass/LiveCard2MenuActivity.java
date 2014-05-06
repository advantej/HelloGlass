package com.advantej.glass.helloglass;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by tejas on 5/2/14.
 */
public class LiveCard2MenuActivity extends Activity
{
    @Override
    public void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        openOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_live_card1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_lc1_stop:
                stopService(new Intent(this, LiveCard2Service.class));
                return true;

            case R.id.menu_lc1_detail:
                startActivity(new Intent(this, MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOptionsMenuClosed(Menu menu)
    {
        super.onOptionsMenuClosed(menu);
        finish();
    }
}
