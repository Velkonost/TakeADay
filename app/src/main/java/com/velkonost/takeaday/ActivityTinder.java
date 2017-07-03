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
import static com.velkonost.takeaday.Constants.ID1;
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
    public static boolean isLastAccept;

    private int id1, id2, id3;
    private static boolean isId1;
    private static int currentIdOnTop;

    @BindView(R.id.swipeView)
    SwipePlaceHolderView mSwipView;


    static TextView txtCountDone;

    private DBHelper dbHelper;
    private ArrayList<Integer> dones;

    public static String titleUndo, descUndo;

    public static boolean isUndoAllow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinder_swipe);
        ButterKnife.bind(this);

        txtCountDone = (TextView) findViewById(R.id.count_done);

        countDone = loadText(ActivityTinder.this, COUNT_DONE).equals("")
                ? 0 : Integer.parseInt(loadText(ActivityTinder.this, COUNT_DONE));

        mult = loadText(ActivityTinder.this, MULT).equals("")
                ? 0 : Integer.parseInt(loadText(ActivityTinder.this, MULT));

        isId1 = true;

        currentIdOnTop = 1;

        dbHelper = new DBHelper(this);

        Cursor c = dbHelper.queryDoneChallenges();
        if (c.moveToFirst()) {

            int challengeTaskIndex = c.getColumnIndex("list");

            String name = c.getString(challengeTaskIndex);
            name = name.substring(1, name.length() - 1);
            String[] str = name.split(", ");

            Integer[] numbers = new Integer[str.length];

            for(int i = 0;i < str.length;i++) {
                numbers[i] = Integer.parseInt(str[i]);
            }

            dones = new ArrayList<Integer>(Arrays.<Integer>asList(numbers));
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
                    isUndoAllow = false;
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
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @OnClick(R.id.rejectBtn)
    void onRejectClick(){
        isUndoAllow = true;
        mSwipView.doSwipe(false);
    }

    @OnClick(R.id.acceptBtn)
    void onAcceptClick(){
        isUndoAllow = true;
        mSwipView.doSwipe(true);
    }

    @OnClick(R.id.undoBtn)
    void onUndoClick(){
        if (isUndoAllow) {
            mSwipView.undoLastSwipe();
            if (isLastAccept) {
                countDone--;
                txtCountDone.setText(String.valueOf(countDone));
            }
            isUndoAllow = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveText(ActivityTinder.this, COUNT_DONE, String.valueOf(countDone));
        saveText(ActivityTinder.this, MULT, String.valueOf(mult));

        if (currentIdOnTop == 1)
            saveText(ActivityTinder.this, ID1, String.valueOf(id1));
        else if (currentIdOnTop == 2)
            saveText(ActivityTinder.this, ID1, String.valueOf(id2));
        else if (currentIdOnTop == 3)
            saveText(ActivityTinder.this, ID1, String.valueOf(id3));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        saveText(ActivityTinder.this, COUNT_DONE, String.valueOf(countDone));
        saveText(ActivityTinder.this, MULT, String.valueOf(mult));

        if (currentIdOnTop == 1)
            saveText(ActivityTinder.this, ID1, String.valueOf(id1));
        else if (currentIdOnTop == 2)
            saveText(ActivityTinder.this, ID1, String.valueOf(id2));
        else if (currentIdOnTop == 3)
            saveText(ActivityTinder.this, ID1, String.valueOf(id3));
    }

    public static void minusCountDone() {
        currentIdOnTop ++;
        if (currentIdOnTop > 3) currentIdOnTop = 1;

        txtCountDone.setText(String.valueOf(countDone));
        isLastAccept = false;
    }

    public static void plusCountDone(Context context, int id) {
        countDone++;
        txtCountDone.setText(String.valueOf(countDone));
        DBHelper db = new DBHelper(context);
        db.updateDoneChallenges(String.valueOf(id));

        currentIdOnTop ++;
        if (currentIdOnTop > 3) currentIdOnTop = 1;

        isLastAccept = true;

    }

    private void generateThreeIds() {
        if ((countDone - (MAX + 1) * mult) >= MAX + 1) {
            dbHelper.resetChallenges();
//            countDone = 0;
            mult ++;
            txtCountDone.setText(String.valueOf(countDone));


        }
        Cursor c = dbHelper.queryDoneChallenges();
        if (c.moveToFirst()) {

            int challengeTaskIndex = c.getColumnIndex("list");

            String name = c.getString(challengeTaskIndex);
            name = name.substring(1, name.length() - 1);
            String[] str = name.split(", ");

            Integer[] numbers = new Integer[str.length];

            for(int i = 0;i < str.length;i++)
            {
                numbers[i] = Integer.parseInt(str[i]);
            }
            dones = new ArrayList<Integer>(Arrays.<Integer>asList(numbers));

        }

        boolean flag1 = false, flag2 = false, flag3 = false;

        if (isId1) {
            id1 = loadText(ActivityTinder.this, ID1).equals("")
                    ? MIN + (int) (Math.random() * ((MAX - MIN) + 1)) : Integer.parseInt(loadText(ActivityTinder.this, ID1));
            isId1 = false;
        } else {
            id1 = MIN + (int) (Math.random() * ((MAX - MIN) + 1));
        }

        while (true) {
            flag1 = true;
            for (int i = 0; i < dones.size(); i++) {
                if (dones.get(i) == id1) {
                    id1 = MIN + (int) (Math.random() * ((MAX - MIN) + 1));

                    flag1 = false;
                    break;
                }
            }

            if (flag1) {
                dones.add(id1);
                break;
            }

        }

        id2 = MIN + (int)(Math.random() * ((MAX - MIN) + 1));
        while(true) {
            flag2 = true;
            for(int i = 0; i < dones.size(); i++) {
                if (dones.get(i).equals(id2)) {
                    id2 = MIN + (int)(Math.random() * ((MAX - MIN) + 1));


                    flag2 = false;
                    break;
                }
            }
            if (flag2) {
                dones.add(id2);
                break;
            }
        }

        id3 = MIN + (int)(Math.random() * ((MAX - MIN) + 1));
        while(true) {
            flag3 = true;
            for(int i = 0; i < dones.size(); i++) {
                if (dones.get(i).equals(id3)) {
                    id3 = MIN + (int)(Math.random() * ((MAX - MIN) + 1));

                    flag3 = false;
                    break;
                }
            }
            if (flag3) {
                dones.add(id3);
                break;
            }
        }

        dones.remove(Integer.valueOf(id1));
        dones.remove(Integer.valueOf(id2));
        dones.remove(Integer.valueOf(id3));

    }
}
