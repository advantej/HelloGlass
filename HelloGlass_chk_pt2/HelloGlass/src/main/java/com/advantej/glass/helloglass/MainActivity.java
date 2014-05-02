package com.advantej.glass.helloglass;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mCardScrollerView = new CardScrollView(this);
        mCardScrollerView.setAdapter(new CardsAdapter());
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
}
