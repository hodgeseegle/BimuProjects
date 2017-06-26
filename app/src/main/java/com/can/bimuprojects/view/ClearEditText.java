package com.can.bimuprojects.view;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.can.bimuprojects.R;
import com.can.bimuprojects.utils.UiUtils;

/**
 * Created by can on 2017/4/12.
 * 可以删除内容的编辑框
 */
public class ClearEditText extends AppCompatEditText {

    private Drawable clearDrawable;

    public ClearEditText(Context context) {
        super(context);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if(focused){
            setIconVisible(this.getText().toString().trim().length()>0);
        }else {
            setIconVisible(false);
        }
    }

    private void init() {
        clearDrawable = getCompoundDrawables()[2];
        if (clearDrawable == null) {
            //未设置DrawableRight的情况,就使用默认的图标
            clearDrawable = ContextCompat.getDrawable(getContext(),R.drawable.clear);
        }
//        clearDrawable.setBounds(0, 0, clearDrawable.getIntrinsicWidth(),
//                clearDrawable.geV√tIntrinsicHeight());
        clearDrawable.setBounds(0,0, UiUtils.dip2px(15),UiUtils.dip2px(15));

        setIconVisible(false);

    }

    private void setIconVisible(boolean visible) {
        Drawable right = visible ? clearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1],right,getCompoundDrawables()[3]);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setIconVisible(this.getText().toString().trim().length()>0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(getCompoundDrawables()[2]!=null){
                int x = (int)event.getX();
                int y = (int)event.getY();
                Rect rect = getCompoundDrawables()[2].getBounds();
                int height = rect.height();
                int distance = (getHeight() - height)/2;
                boolean isInnerWidth = x > (getWidth() - getTotalPaddingRight()) && x < (getWidth() - getPaddingRight());
                boolean isInnerHeight = y > distance && y <(distance + height);
                if (isInnerWidth && isInnerHeight) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

}
