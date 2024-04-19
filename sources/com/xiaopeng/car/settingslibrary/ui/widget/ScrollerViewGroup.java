package com.xiaopeng.car.settingslibrary.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.xiaopeng.car.settingslibrary.interfaceui.constant.InterfaceConstant;
import com.xiaopeng.xui.widget.XRelativeLayout;
import java.math.BigDecimal;
/* loaded from: classes.dex */
public class ScrollerViewGroup extends XRelativeLayout {
    private static final String TAG = "ScrollerViewGroup";
    private boolean isOpen;
    private boolean isScrollUp;
    private GestureDetector mGestureDetector;
    private boolean mIsPrintLog;
    private OnDragListener mOnDragListener;
    private float mPrevY;
    private Scroller mScroller;
    public State mState;

    /* loaded from: classes.dex */
    public interface OnDragListener {
        void onScrollerClose();

        void onScrollerOpen();

        void onScrollerPercent(float f);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public enum State {
        Null,
        SingleTapUp,
        Scroll,
        Fling
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.i(TAG, "onFinishInflate");
    }

    public ScrollerViewGroup(Context context) {
        this(context, null);
    }

    public ScrollerViewGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mState = State.Null;
        this.isScrollUp = false;
        this.isOpen = false;
        this.mIsPrintLog = false;
        init(context);
    }

    private void init(Context context) {
        Log.i(TAG, "init");
        this.mScroller = new Scroller(context, new LinearInterpolator());
        this.mGestureDetector = new GestureDetector(context, new CustomGestureListener());
    }

    public void onResume() {
        Log.i(TAG, InterfaceConstant.DOWNLOAD_RESUME);
        this.mIsPrintLog = true;
        this.mState = State.Null;
        if (!this.isOpen) {
            float finalY = this.mScroller.getFinalY();
            Log.i(TAG, "onResume scrollerFinalY = " + finalY);
            if (BigDecimal.valueOf(finalY).compareTo(BigDecimal.ZERO) == 0) {
                Scroller scroller = this.mScroller;
                scroller.startScroll(scroller.getFinalX(), 1200, 0, -1200);
                invalidate();
            } else {
                openMenu(InterfaceConstant.DOWNLOAD_RESUME);
            }
            postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$ScrollerViewGroup$5y52fuwWc-zKn8FtDPTBr_K9P3E
                @Override // java.lang.Runnable
                public final void run() {
                    ScrollerViewGroup.this.lambda$onResume$0$ScrollerViewGroup();
                }
            }, this.mScroller.getDuration());
        }
        this.isOpen = true;
    }

    public /* synthetic */ void lambda$onResume$0$ScrollerViewGroup() {
        Log.i(TAG, "onResume postDelayed " + getVisibility());
        scrollTo(0, 0);
        OnDragListener onDragListener = this.mOnDragListener;
        if (onDragListener != null) {
            onDragListener.onScrollerPercent(0.0f);
        }
        postInvalidate();
    }

    public boolean isScrollFinished() {
        return this.mScroller.isFinished();
    }

    public void onStop() {
        Log.i(TAG, "onStop");
        this.isScrollUp = false;
        this.isOpen = false;
        this.mPrevY = 0.0f;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mGestureDetector.onTouchEvent(motionEvent);
        if (motionEvent.getAction() == 1) {
            onUp();
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.mIsPrintLog) {
            Log.i(TAG, "dispatchDraw");
            this.mIsPrintLog = false;
        }
    }

    public void onUp() {
        if (this.mState == State.SingleTapUp || this.mState == State.Fling) {
            return;
        }
        int scrollY = getScrollY();
        Log.i(TAG, "ACTION_UP scrollY = " + scrollY);
        if (scrollY == 0) {
            return;
        }
        if (scrollY < getMeasuredHeight() / 2) {
            openMenu("onUp");
        } else {
            closeMenu("onUp");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class CustomGestureListener implements GestureDetector.OnGestureListener {
        CustomGestureListener() {
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            Log.i(ScrollerViewGroup.TAG, "onDown");
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public void onShowPress(MotionEvent motionEvent) {
            Log.i(ScrollerViewGroup.TAG, "onShowPress");
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            Log.i(ScrollerViewGroup.TAG, "onSingleTapUp");
            ScrollerViewGroup.this.mState = State.SingleTapUp;
            ScrollerViewGroup.this.closeMenu("onSingleTapUp");
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            ScrollerViewGroup.this.mState = State.Scroll;
            Log.i(ScrollerViewGroup.TAG, "onScroll distanceY = " + f2);
            if (ScrollerViewGroup.this.mScroller.getFinalY() + f2 < 0.0d) {
                ScrollerViewGroup.this.mPrevY = 0.0f;
                f2 = -ScrollerViewGroup.this.mScroller.getFinalY();
            }
            ScrollerViewGroup.this.smoothSlideViewBy(0, (int) f2, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
            float y = motionEvent.getY();
            float y2 = motionEvent2.getY();
            Log.i(ScrollerViewGroup.TAG, "onScroll firstTouch = " + y + " & mPrevY = " + ScrollerViewGroup.this.mPrevY + " & currentY = " + y2);
            if (BigDecimal.valueOf(ScrollerViewGroup.this.mPrevY).compareTo(BigDecimal.ZERO) == 0) {
                if (y2 < y) {
                    ScrollerViewGroup.this.isScrollUp = true;
                } else {
                    ScrollerViewGroup.this.isScrollUp = false;
                }
            } else if (y2 < ScrollerViewGroup.this.mPrevY) {
                ScrollerViewGroup.this.isScrollUp = true;
            } else {
                ScrollerViewGroup.this.isScrollUp = false;
            }
            Log.i(ScrollerViewGroup.TAG, "onScroll isScrollUp = " + ScrollerViewGroup.this.isScrollUp);
            ScrollerViewGroup.this.mPrevY = y2;
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            Log.i(ScrollerViewGroup.TAG, "onLongPress");
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            float y = motionEvent.getY();
            float y2 = motionEvent2.getY();
            Log.i(ScrollerViewGroup.TAG, "onFling isScrollUp = " + ScrollerViewGroup.this.isScrollUp + " & down = " + y + " & up = " + y2);
            if (y <= y2 || !ScrollerViewGroup.this.isScrollUp) {
                return true;
            }
            ScrollerViewGroup.this.mState = State.Fling;
            Log.i(ScrollerViewGroup.TAG, "onFling close");
            ScrollerViewGroup.this.closeMenu("onFling");
            return true;
        }
    }

    public void smoothSlideViewTo(int i, int i2, int i3) {
        Log.i(TAG, "smoothSlideViewTo");
        smoothSlideViewBy(i - this.mScroller.getFinalX(), i2 - this.mScroller.getFinalY(), i3);
    }

    public void smoothSlideViewBy(int i, int i2, int i3) {
        Log.i(TAG, "smoothSlideViewBy startScroll");
        Scroller scroller = this.mScroller;
        scroller.startScroll(scroller.getFinalX(), this.mScroller.getFinalY(), i, i2, i3);
        invalidate();
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            Log.i(TAG, "computeScroll mScroller.getCurrY() = " + this.mScroller.getCurrY());
            scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
            float currY = (float) (((double) this.mScroller.getCurrY()) / (((double) getMeasuredHeight()) / 3.0d));
            OnDragListener onDragListener = this.mOnDragListener;
            if (onDragListener != null) {
                onDragListener.onScrollerPercent(currY);
            }
            postInvalidate();
        }
    }

    private void openMenu(String str) {
        Log.i(TAG, "openMenu " + str);
        smoothSlideViewTo(0, 0, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        this.mPrevY = 0.0f;
        open();
    }

    public void closeMenu(String str) {
        if (this.isOpen) {
            this.isOpen = false;
            Log.i(TAG, "closeMenu " + str);
            smoothSlideViewTo(0, getMeasuredHeight(), 500);
            new Handler().postDelayed(new Runnable() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.ScrollerViewGroup.1
                @Override // java.lang.Runnable
                public void run() {
                    ScrollerViewGroup.this.close();
                }
            }, 300L);
        }
    }

    private void open() {
        OnDragListener onDragListener = this.mOnDragListener;
        if (onDragListener != null) {
            onDragListener.onScrollerOpen();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void close() {
        if (this.mOnDragListener != null) {
            this.mScroller.abortAnimation();
            this.mOnDragListener.onScrollerClose();
        }
    }

    public void setOnDragListener(OnDragListener onDragListener) {
        this.mOnDragListener = onDragListener;
    }
}
