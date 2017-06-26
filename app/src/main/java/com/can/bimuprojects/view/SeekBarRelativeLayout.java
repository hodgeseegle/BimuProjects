package com.can.bimuprojects.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.can.bimuprojects.R;

/**
 * Created by can on 2017/4/9.
 * 单点滑动
 */

public class SeekBarRelativeLayout extends RelativeLayout {
    public SeekBarRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SeekBarRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private TextView textView;
    private SeekBar seekBar;
    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener;
    private int textViewPaddingLeft = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void setText(String str) {
        textView.setText(str);
    }

    private void setMarginLeftForTextView(int progress) {
        if (seekBar != null && textView != null) {
            LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
            int width = seekBar.getWidth() - seekBar.getPaddingLeft() - seekBar.getPaddingRight();
            layoutParams.leftMargin = (int) (((float) progress / seekBar.getMax()) * width);

            layoutParams.leftMargin += seekBar.getPaddingRight() - textView.getWidth() / 2 + textViewPaddingLeft;

            setText(Integer.toString((progress+10)/10*10));
            textView.setLayoutParams(layoutParams);
        }
    }

    public void setProgress(int process) {
        if (seekBar != null) {
            seekBar.setProgress(process);
        }
    }

    public void setEnabled(boolean enabled) {
        if (seekBar != null) {
            seekBar.setEnabled(enabled);
        }
    }

    public void initSeekBar() {
        seekBar = (SeekBar) findViewById(R.id.seek_bar_relative_layout_seek_bar);
        textView = (TextView) findViewById(R.id.seek_bar_relative_layout_text_view);

        textViewPaddingLeft = ((LayoutParams) textView.getLayoutParams()).leftMargin;
        if (seekBar != null && textView != null) {
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    setMarginLeftForTextView(progress);
                    if (onSeekBarChangeListener != null) {
                        onSeekBarChangeListener.onProgressChanged(seekBar, progress, fromUser);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    if (onSeekBarChangeListener != null) {
                        onSeekBarChangeListener.onStartTrackingTouch(seekBar);
                    }

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    if (onSeekBarChangeListener != null) {
                        onSeekBarChangeListener.onStopTrackingTouch(seekBar);
                    }
                }
            });
        }
    }

    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        this.onSeekBarChangeListener = onSeekBarChangeListener;
    }
}