package com.velkonost.takeaday.swipe;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.velkonost.takeaday.R;
import com.velkonost.takeaday.managers.DBHelper;

import static com.velkonost.takeaday.ActivityTinder.plusCountDone;

/**
 * @author Velkonost
 */

@NonReusable
@Layout(R.layout.tinder_card_view)
public class TinderCard {

    private int challengeId;
    private DBHelper dbHelper;

    private String description;
    private String titleTxt;

    private Context context;

    public TinderCard(int challengeId, Context context) {
        this.challengeId = challengeId;
        this.context = context;

        dbHelper = new DBHelper(context);

        Cursor c = dbHelper.queryByIdChallenge(challengeId);

        if (c.moveToFirst()) {


            int titleIndex = c.getColumnIndex("title");
            int descIndex = c.getColumnIndex("desc");

            titleTxt = c.getString(titleIndex);
            description = c.getString(descIndex);


        }

    }


    @View(R.id.title)
    private TextView title;

    @View(R.id.desc)
    private TextView desc;



    @Resolve
    private void onResolve(){
        title.setText(titleTxt);
        desc.setText(description);
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("DEBUG", "onSwipedOut");
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("DEBUG", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        plusCountDone(context, challengeId);
        Log.d("DEBUG", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState(){
        Log.d("DEBUG", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("DEBUG", "onSwipeOutState");
    }
}
