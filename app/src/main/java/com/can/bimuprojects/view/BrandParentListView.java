package com.can.bimuprojects.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by can on 2017/4/14.
 */

public class BrandParentListView extends ListView {

    private static final float PREFERRED_SELECTION_OFFSET_FROM_TOP = 0f;

    private int mRequestedScrollPosition = -1;
    private boolean mSmoothScrollRequested;
    public BrandParentListView(Context context) {
        super(context);
    }

    public BrandParentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BrandParentListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void requestPositionToScreen(int position, boolean smoothScroll) {
        mRequestedScrollPosition = position;
        mSmoothScrollRequested = smoothScroll;
        requestLayout();
    }


    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        if (mRequestedScrollPosition == -1) {
            return;
        }

        final int position = mRequestedScrollPosition;
        mRequestedScrollPosition = -1;

        int firstPosition = getFirstVisiblePosition() +1 ;
        int lastPosition = getLastVisiblePosition();


        final int offset = (int) (getHeight() * PREFERRED_SELECTION_OFFSET_FROM_TOP);
        if (!mSmoothScrollRequested) {
            setSelectionFromTop(position, offset);

            // Since we have changed the scrolling position, we need to redo child layout
            // Calling "requestLayout" in the middle of a layout pass has no effect,
            // so we call layoutChildren explicitly
            super.layoutChildren();

        } else {
            // We will first position the list a couple of screens before or after
            // the new selection and then scroll smoothly to it.
            int twoScreens = (lastPosition - firstPosition) * 2;
            int preliminaryPosition;
            if (position < firstPosition) {
                preliminaryPosition = position + twoScreens;
                if (preliminaryPosition >= getCount()) {
                preliminaryPosition = getCount() - 1;
            }
                if (preliminaryPosition < firstPosition) {
                    setSelection(preliminaryPosition);
                    super.layoutChildren();
                }
            } else {
                preliminaryPosition = position - twoScreens;
                if (preliminaryPosition < 0) {
                    preliminaryPosition = 0;                 }
                if (preliminaryPosition > lastPosition) {
                    setSelection(preliminaryPosition);
                    super.layoutChildren();
                }
            }
            smoothScrollToPositionFromTop(position, offset,500);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}
