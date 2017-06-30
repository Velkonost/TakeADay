package com.velkonost.takeaday;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.listeners.ItemRemovedListener;
import com.velkonost.takeaday.managers.DBHelper;
import com.velkonost.takeaday.swipe.TinderCard;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.velkonost.takeaday.Constants.COUNT_DONE;
import static com.velkonost.takeaday.Constants.MAX;
import static com.velkonost.takeaday.Constants.MIN;
import static com.velkonost.takeaday.Constants.MULT;
import static com.velkonost.takeaday.managers.PhoneDataStorageManager.loadText;
import static com.velkonost.takeaday.managers.PhoneDataStorageManager.saveText;

/**
 * @author Velkonost
 */
public class ActivityTinder extends AppCompatActivity {

    private static final String TAG = "ActivityTinder";

    public static int countDone = 0;
    public static int mult = 0;

    private int id1, id2, id3;

    @BindView(R.id.swipeView)
    SwipePlaceHolderView mSwipView;


    static TextView txtCountDone;

    private DBHelper dbHelper;
    private ArrayList<String> dones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinder_swipe);
        ButterKnife.bind(this);

        txtCountDone = (TextView) findViewById(R.id.count_done);

        countDone = loadText(ActivityTinder.this, COUNT_DONE).equals("")
                ? 0 : Integer.parseInt(loadText(ActivityTinder.this, COUNT_DONE));

        mult= loadText(ActivityTinder.this, MULT).equals("")
                ? 0 : Integer.parseInt(loadText(ActivityTinder.this, MULT));

        dbHelper = new DBHelper(this);


//        Cursor c = db.rawQuery(query);
//        if (c != null && c.moveToFirst()) {
//            lastId = c.getLong(0); //The 0 is the column index, we only have 1 column, so the index is 0
//        }

        Cursor c = dbHelper.queryDoneChallenges();
        if (c.moveToFirst()) {

            int challengeTaskIndex = c.getColumnIndex("list");

            String name = c.getString(challengeTaskIndex);

            dones = new ArrayList<String>(Arrays.asList(name.substring(1, name.length() - 1)));
        }

        Button button =(Button)findViewById(R.id.dbbtn);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent dbmanager = new Intent(ActivityTinder.this,AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });

        txtCountDone.setText(String.valueOf(countDone));
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        mSwipView.disableTouchSwipe();


        mSwipView.addItemRemoveListener(new ItemRemovedListener() {

            @Override
            public void onItemRemoved(int count) {
                Log.d(TAG, "onItemRemoved: " + count);

                if(count == 0){
                    generateThreeIds();
                    mSwipView
                            .addView(new TinderCard(id1, ActivityTinder.this))
                            .addView(new TinderCard(id2, ActivityTinder.this))
                            .addView(new TinderCard(id3, ActivityTinder.this));
                }
            }
        });
        mSwipView.getBuilder()
//                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_VERTICAL)
                .setDisplayViewCount(3)
                .setIsUndoEnabled(true)
                .setWidthSwipeDistFactor(15)
                .setHeightSwipeDistFactor(20)
                .setSwipeDecor(new SwipeDecor()
//                        .setMarginTop(300)
//                        .setMarginLeft(100)
//                        .setViewGravity(Gravity.TOP)
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));

        generateThreeIds();
        mSwipView
                .addView(new TinderCard(id1, ActivityTinder.this))
                .addView(new TinderCard(id2, ActivityTinder.this))
                .addView(new TinderCard(id3, ActivityTinder.this))
                ;

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Thread.sleep(8000);
                    mSwipView.enableTouchSwipe();
//                    mSwipView.lockViews();
//                    Thread.currentThread().sleep(4000);
//                    mSwipView.unlockViews();
//                    Thread.currentThread().sleep(4000);
//                    mSwipView.lockViews();
//                    Thread.currentThread().sleep(4000);
//                    mSwipView.unlockViews();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @OnClick(R.id.rejectBtn)
    void onRejectClick(){
        mSwipView.doSwipe(false);
    }

    @OnClick(R.id.acceptBtn)
    void onAcceptClick(){
        mSwipView.doSwipe(true);
    }

    @OnClick(R.id.undoBtn)
    void onUndoClick(){
        mSwipView.undoLastSwipe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveText(ActivityTinder.this, COUNT_DONE, String.valueOf(countDone));
        saveText(ActivityTinder.this, MULT, String.valueOf(mult));
    }

    public static void minusCountDone() {
        countDone--;
        txtCountDone.setText(String.valueOf(countDone));
    }

    public static void plusCountDone(Context context, int id) {
        countDone++;
        txtCountDone.setText(String.valueOf(countDone));
        DBHelper db = new DBHelper(context);
        db.updateDoneChallenges(id);
    }

    private void generateThreeIds() {
        if ((countDone - (MAX + 1) * mult) >= MAX + 1) {
            dbHelper.resetChallenges();
//            countDone = 0;
            mult ++;
            txtCountDone.setText(String.valueOf(countDone));

            Cursor c = dbHelper.queryDoneChallenges();
            if (c.moveToFirst()) {

                int challengeTaskIndex = c.getColumnIndex("list");

                String name = c.getString(challengeTaskIndex);

                dones = new ArrayList<String>(Arrays.asList(name.substring(1, name.length() - 1)));
            }
        }

        boolean flag1 = false, flag2 = false, flag3 = false;

        id1 = MIN + (int)(Math.random() * ((MAX - MIN) + 1));
        while(true) {
            flag1 = true;
            for(int i = 0; i < dones.size(); i++) {
                if (dones.contains(String.valueOf(id1))) {
                    id1 = MIN + (int)(Math.random() * ((MAX - MIN) + 1));

                    flag1 = false;
                    break;
                }
            }
            if (flag1) {
                dones.add(String.valueOf(id1));
                break;
            }
        }

        id2 = MIN + (int)(Math.random() * ((MAX - MIN) + 1));
        while(true) {
            flag2 = true;
            for(int i = 0; i < dones.size(); i++) {
                if (dones.contains(String.valueOf(id2))) {
                    id2 = MIN + (int)(Math.random() * ((MAX - MIN) + 1));
                    flag2 = false;
                    break;
                }
            }
            if (flag2) {
                dones.add(String.valueOf(id2));
                break;
            }
        }

        id3 = MIN + (int)(Math.random() * ((MAX - MIN) + 1));
        while(true) {
            flag3 = true;
            for(int i = 0; i < dones.size(); i++) {
                if (dones.contains(String.valueOf(id3))) {
                    id3 = MIN + (int)(Math.random() * ((MAX - MIN) + 1));
                    flag3 = false;
                    break;
                }
            }
            if (flag3) {
                dones.add(String.valueOf(id3));
                break;
            }
        }

        dones.remove(String.valueOf(id1));
        dones.remove(String.valueOf(id2));
        dones.remove(String.valueOf(id3));

    }
}
