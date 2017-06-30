package com.velkonost.takeaday.swipe;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.velkonost.takeaday.R;

/**
 * @author Velkonost
 */

@NonReusable
@Layout(R.layout.tinder_card_view)
public class TinderCard2 {

    private static int count;
    private CardCallback callback;



    public TinderCard2(CardCallback callback) {
        this.callback = callback;
    }

    @Resolve
    private void onResolve(){

    }

    @SwipeOut
    private void onSwipedOut(){
        callback.onSwipingEnd();
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        callback.onSwipingEnd();
    }

    @SwipeIn
    private void onSwipeIn(){
        callback.onSwipingEnd();
    }

    @SwipeInState
    private void onSwipeInState(){
        callback.onSwiping();
    }

    @SwipeOutState
    private void onSwipeOutState(){
        callback.onSwiping();
    }

    public interface CardCallback{
        void onSwiping();
        void onSwipingEnd();
    }
}
