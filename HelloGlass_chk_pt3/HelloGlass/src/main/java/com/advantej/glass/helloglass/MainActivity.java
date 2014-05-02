package com.advantej.glass.helloglass;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tejas on 5/1/14.
 */
public class MainActivity extends Activity
{
    private CardScrollView mCardScrollerView;
    private CardScrollAdapter mCardScrollAdapter;
    private int mSelectedCardIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mCardScrollerView = new CardScrollView(this);
        mCardScrollAdapter = new CardsAdapter();
        mCardScrollerView.setAdapter(mCardScrollAdapter);
        mCardScrollerView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                mSelectedCardIndex = position;
                openOptionsMenu();
            }
        });

        mCardScrollerView.activate();
        setContentView(mCardScrollerView);

//        setContentView(R.layout.activity_main);
    }

    private class CardsAdapter extends CardScrollAdapter
    {
        List<Card> mCardList = new ArrayList<Card>();

        private CardsAdapter()
        {
            initData();
        }

        private void initData()
        {
            Card card;
            for (int i = 0; i < 5; i++)
            {
                card = new Card(MainActivity.this);
                card.setText(String.format(" Card %s ", i));
                card.setFootnote(String.format(" Footnote %s ", i));
                card.addImage(R.drawable.ic_launcher);

                mCardList.add(card);
            }
        }

        @Override
        public int getCount()
        {
            return mCardList.size();
        }

        @Override
        public Card getItem(int i)
        {
            return mCardList.get(i);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            return getItem(i).getView();
        }

        @Override
        public int getPosition(Object o)
        {
            return 0;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_stop:
                finish();
                return true;
            case R.id.menu_read:
                Card card = (Card) mCardScrollAdapter.getItem(mSelectedCardIndex);
                if (card != null) {
                    Toast.makeText(MainActivity.this, card.getText(), Toast.LENGTH_SHORT).show();
                }
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
