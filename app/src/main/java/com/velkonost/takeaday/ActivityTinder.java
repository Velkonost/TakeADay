package com.velkonost.takeaday;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.listeners.ItemRemovedListener;
import com.velkonost.takeaday.managers.DBHelper;
import com.velkonost.takeaday.swipe.TinderCard;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.velkonost.takeaday.Constants.COUNT_DONE;
import static com.velkonost.takeaday.Constants.MAX;
import static com.velkonost.takeaday.Constants.MIN;
import static com.velkonost.takeaday.R.id.count_done;
import static com.velkonost.takeaday.managers.PhoneDataStorageManager.loadText;
import static com.velkonost.takeaday.managers.PhoneDataStorageManager.saveText;

/**
 * @author Velkonost
 */

public class ActivityTinder extends AppCompatActivity {

    private static final String TAG = "ActivityTinder";

    public static int countDone = 0;

    private int id1, id2, id3;

    @BindView(R.id.swipeView)
    SwipePlaceHolderView mSwipView;

    @BindView(count_done)
    TextView txtCountDone;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinder_swipe);
        ButterKnife.bind(this);

        countDone = loadText(ActivityTinder.this, COUNT_DONE).equals("")
                ? 0 : Integer.parseInt(loadText(ActivityTinder.this, COUNT_DONE));

        dbHelper = new DBHelper(this);


//        Cursor c = db.rawQuery(query);
//        if (c != null && c.moveToFirst()) {
//            lastId = c.getLong(0); //The 0 is the column index, we only have 1 column, so the index is 0
//        }

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
                    mSwipView
                            .addView(new TinderCard(MIN + (int)(Math.random() * ((MAX - MIN) + 1))))
                            .addView(new TinderCard(MIN + (int)(Math.random() * ((MAX - MIN) + 1))))
                            .addView(new TinderCard(MIN + (int)(Math.random() * ((MAX - MIN) + 1))))
                            ;
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


        mSwipView.addView(new TinderCard())
                .addView(new TinderCard())
                .addView(new TinderCard())
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
        countDone ++;
        txtCountDone.setText(String.valueOf(countDone));
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
    }

    public static void minusCountDone() {
        countDone--;
    }
    public static void plusCountDone() {
        countDone++;
    }

    private void generateThreeIds() {

    }
}
