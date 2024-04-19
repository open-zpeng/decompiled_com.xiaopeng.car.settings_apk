package com.xiaopeng.car.settingslibrary.ui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.xiaopeng.car.settingslibrary.R;
import com.xiaopeng.xui.view.XView;
import com.xiaopeng.xui.widget.XFrameLayout;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
/* loaded from: classes.dex */
public class AdaptivePickerView extends XFrameLayout implements GestureDetector.OnGestureListener {
    private Adapter mAdapter;
    private Stack<ItemHolder> mBufferedItems;
    private int mCenterItemIndex;
    private boolean mCircularly;
    private GestureDetector mGestureDetector;
    private boolean mInTouch;
    private boolean mInterceptEvent;
    private int mItemCount;
    private int mItemGap;
    private int mItemHeight;
    private int mLastTouchX;
    private int mLastTouchY;
    private ISelectedItemChangeListener mListener;
    private int mOffset;
    private int mReservedSize;
    private Scroller mScroller;
    private int mTouchDistance;
    private int mTouchDownX;
    private int mTouchDownY;
    private int mTouchSlop;
    private List<ItemHolder> mVisibleItems;

    /* loaded from: classes.dex */
    public interface ISelectedItemChangeListener {
        void onPickerViewItemChanged(AdaptivePickerView adaptivePickerView, int i, int i2, boolean z);
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onShowPress(MotionEvent motionEvent) {
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    /* loaded from: classes.dex */
    public static class LayoutParams {
        int mHeight;
        float mPivotX = 0.5f;
        float mPivotY = 0.5f;
        float mScaleX = 1.0f;
        float mScaleY = 1.0f;
        int mWidth;
        int mX;
        int mY;

        public void updatePosition(int i, int i2) {
            this.mX = i;
            this.mY = i2;
        }

        public void updateScale(float f, float f2, float f3, float f4) {
            this.mPivotX = f;
            this.mPivotY = f2;
            this.mScaleX = f3;
            this.mScaleY = f4;
        }

        public void updateSize(int i, int i2) {
            this.mWidth = i;
            this.mHeight = i2;
        }
    }

    /* loaded from: classes.dex */
    public static class ItemHolder {
        private boolean mCenterItem;
        private int mIndex;
        private LayoutParams mLayoutParams = new LayoutParams();
        private int mRealIndex;
        public final View mView;

        public ItemHolder(XView xView) {
            this.mView = xView;
        }

        public ItemHolder(ViewGroup viewGroup, int i) {
            this.mView = LayoutInflater.from(viewGroup.getContext()).inflate(i, viewGroup, false);
        }

        public int getIndex() {
            return this.mIndex;
        }

        public int getRealIndex() {
            return this.mRealIndex;
        }

        public boolean isCenterItem() {
            return this.mCenterItem;
        }

        public LayoutParams getLayoutParams() {
            return this.mLayoutParams;
        }

        public final <T extends View> T findViewById(int i) {
            return (T) this.mView.findViewById(i);
        }
    }

    /* loaded from: classes.dex */
    public static abstract class Adapter {
        private WeakReference<AdaptivePickerView> mViewRef;

        public abstract int getSize();

        public abstract void onItemUpdated(AdaptivePickerView adaptivePickerView, ItemHolder itemHolder);

        public abstract ItemHolder onNewItem(AdaptivePickerView adaptivePickerView);

        public void onUpdateTransform(AdaptivePickerView adaptivePickerView, ItemHolder itemHolder) {
        }

        public void notifyDataSetChange() {
            WeakReference<AdaptivePickerView> weakReference = this.mViewRef;
            AdaptivePickerView adaptivePickerView = weakReference != null ? weakReference.get() : null;
            if (adaptivePickerView != null) {
                adaptivePickerView.reload();
            }
        }
    }

    public AdaptivePickerView(Context context) {
        super(context);
        this.mReservedSize = 1;
        this.mCircularly = true;
        this.mVisibleItems = new ArrayList();
        this.mBufferedItems = new Stack<>();
        this.mCenterItemIndex = -1;
        init(context, null);
    }

    public AdaptivePickerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mReservedSize = 1;
        this.mCircularly = true;
        this.mVisibleItems = new ArrayList();
        this.mBufferedItems = new Stack<>();
        this.mCenterItemIndex = -1;
        init(context, attributeSet);
    }

    public AdaptivePickerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mReservedSize = 1;
        this.mCircularly = true;
        this.mVisibleItems = new ArrayList();
        this.mBufferedItems = new Stack<>();
        this.mCenterItemIndex = -1;
        init(context, attributeSet);
    }

    protected void init(Context context, AttributeSet attributeSet) {
        this.mScroller = new Scroller(context, new DecelerateInterpolator(2.0f));
        this.mGestureDetector = new GestureDetector(getContext(), this);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SoundEffectSetting);
            this.mReservedSize = obtainStyledAttributes.getInt(R.styleable.SoundEffectSetting_se_item_reserved_size, 1);
            this.mItemHeight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SoundEffectSetting_se_item_height, (int) (getResources().getDisplayMetrics().density * 30.0f));
            this.mItemGap = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SoundEffectSetting_se_item_gap, 0);
            this.mCircularly = obtainStyledAttributes.getBoolean(R.styleable.SoundEffectSetting_se_circularly, false);
            obtainStyledAttributes.recycle();
        }
        this.mTouchSlop = (int) (ViewConfiguration.get(context).getScaledTouchSlop() * 1.2f);
    }

    public void setAdapter(Adapter adapter) {
        this.mCenterItemIndex = -1;
        this.mAdapter = adapter;
        if (adapter != null) {
            adapter.mViewRef = new WeakReference(this);
            adapter.notifyDataSetChange();
        }
    }

    public void setListener(ISelectedItemChangeListener iSelectedItemChangeListener) {
        this.mListener = iSelectedItemChangeListener;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        updateItems();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        for (int size = this.mVisibleItems.size() - 1; size >= 0; size--) {
            ItemHolder remove = this.mVisibleItems.remove(size);
            this.mBufferedItems.push(remove);
            remove.mCenterItem = false;
            remove.mIndex = -1;
            remove.mRealIndex = -1;
            remove.mView.setVisibility(8);
        }
        reload();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reload() {
        if (this.mReservedSize < 0) {
            this.mReservedSize = ((int) Math.ceil((getMeasuredHeight() / (this.mItemHeight + this.mItemGap)) / 2)) + 1;
        }
        Adapter adapter = this.mAdapter;
        this.mItemCount = adapter != null ? adapter.getSize() : 0;
        requestLayout();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void updateItems() {
        int i;
        if (this.mAdapter == null || this.mItemCount == 0) {
            this.mVisibleItems.clear();
            this.mBufferedItems.clear();
            while (getChildCount() > 0) {
                removeViewAt(0);
            }
            return;
        }
        int width = getWidth();
        int i2 = 1;
        int height = getHeight() >> 1;
        int i3 = this.mOffset;
        int i4 = this.mItemHeight;
        int itemIndex = getItemIndex(i3);
        int i5 = this.mReservedSize;
        int i6 = itemIndex - i5;
        int i7 = i5 + itemIndex;
        for (int size = this.mVisibleItems.size() - 1; size >= 0; size--) {
            ItemHolder itemHolder = this.mVisibleItems.get(size);
            if (itemHolder.mRealIndex > i7 || itemHolder.mRealIndex < i6) {
                this.mVisibleItems.remove(size);
                this.mBufferedItems.push(itemHolder);
                itemHolder.mCenterItem = false;
                itemHolder.mIndex = -1;
                itemHolder.mRealIndex = -1;
                itemHolder.mView.setVisibility(8);
            }
        }
        while (i6 <= i7) {
            final ItemHolder findItemInScreen = findItemInScreen(i6);
            if (findItemInScreen != null || this.mBufferedItems.size() <= 0) {
                i = 0;
            } else {
                findItemInScreen = this.mBufferedItems.pop();
                this.mVisibleItems.add(findItemInScreen);
                findItemInScreen.mView.setVisibility(0);
                i = i2;
            }
            if (findItemInScreen == null) {
                findItemInScreen = this.mAdapter.onNewItem(this);
                this.mVisibleItems.add(findItemInScreen);
                addView(findItemInScreen.mView, new ViewGroup.LayoutParams(width, i4));
                findItemInScreen.mView.setVisibility(0);
                i = i2;
            }
            findItemInScreen.mRealIndex = i6;
            int i8 = this.mItemCount;
            findItemInScreen.mIndex = ((i6 % i8) + i8) % i8;
            findItemInScreen.mCenterItem = i6 == itemIndex ? i2 : 0;
            int i9 = (this.mCircularly || (i6 >= 0 && i6 <= this.mItemCount - i2)) ? i2 : 0;
            findItemInScreen.mView.setVisibility(i9 != 0 ? 0 : 4);
            if (i != 0 && i9 != 0) {
                findItemInScreen.mView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.car.settingslibrary.ui.widget.-$$Lambda$AdaptivePickerView$GUb-NKqLmsgG4cGrnj6K_sn60hY
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AdaptivePickerView.this.lambda$updateItems$0$AdaptivePickerView(findItemInScreen, view);
                    }
                });
                this.mAdapter.onItemUpdated(this, findItemInScreen);
            }
            if (i9 != 0) {
                findItemInScreen.mLayoutParams.updatePosition(0, i3 - getItemLayoutY(findItemInScreen.mRealIndex));
                findItemInScreen.mLayoutParams.updateSize(width, i4);
                findItemInScreen.mLayoutParams.updateScale(0.0f, 0.0f, 1.0f, 1.0f);
                this.mAdapter.onUpdateTransform(this, findItemInScreen);
                findItemInScreen.mView.setPivotX(findItemInScreen.mLayoutParams.mPivotX * width);
                findItemInScreen.mView.setPivotY(findItemInScreen.mLayoutParams.mPivotY * i4);
                findItemInScreen.mView.setScaleX(findItemInScreen.mLayoutParams.mScaleX);
                findItemInScreen.mView.setScaleY(findItemInScreen.mLayoutParams.mScaleY);
                findItemInScreen.mView.getLayoutParams().width = findItemInScreen.mLayoutParams.mWidth;
                findItemInScreen.mView.getLayoutParams().height = findItemInScreen.mLayoutParams.mHeight;
                if (i != 0 || width != findItemInScreen.mView.getWidth() || i4 != findItemInScreen.mView.getHeight()) {
                    findItemInScreen.mView.measure(View.MeasureSpec.makeMeasureSpec(width, BasicMeasure.EXACTLY), View.MeasureSpec.makeMeasureSpec(i4, BasicMeasure.EXACTLY));
                }
                int i10 = findItemInScreen.mLayoutParams.mX;
                int i11 = (height - (i4 >> 1)) - findItemInScreen.mLayoutParams.mY;
                findItemInScreen.mView.layout(i10, i11, i10 + width, i11 + i4);
            }
            if (i9 != 0 && findItemInScreen.mCenterItem && findItemInScreen.mIndex != this.mCenterItemIndex) {
                this.mCenterItemIndex = findItemInScreen.mIndex;
                ISelectedItemChangeListener iSelectedItemChangeListener = this.mListener;
                if (iSelectedItemChangeListener != null) {
                    iSelectedItemChangeListener.onPickerViewItemChanged(this, findItemInScreen.mIndex, findItemInScreen.mRealIndex, this.mInTouch);
                }
            }
            i6++;
            i2 = 1;
        }
    }

    public /* synthetic */ void lambda$updateItems$0$AdaptivePickerView(ItemHolder itemHolder, View view) {
        scrollToIndex(itemHolder.mRealIndex, 200);
    }

    private ItemHolder findItemInScreen(int i) {
        for (ItemHolder itemHolder : this.mVisibleItems) {
            if (itemHolder.mRealIndex == i) {
                return itemHolder;
            }
        }
        return null;
    }

    public int getSelectIndex() {
        if (this.mAdapter == null || this.mItemCount <= 0) {
            return -1;
        }
        int findCenterItem = findCenterItem();
        int i = this.mItemCount;
        return ((findCenterItem % i) + i) % i;
    }

    public void setSelectIndex(int i) {
        this.mScroller.forceFinished(true);
        this.mOffset = (this.mItemHeight + this.mItemGap) * i;
        Adapter adapter = this.mAdapter;
        if (adapter != null) {
            adapter.notifyDataSetChange();
        }
    }

    public void scrollToIndex(int i, int i2) {
        Scroller scroller = this.mScroller;
        int i3 = this.mOffset;
        scroller.startScroll(0, i3, 0, (int) (getItemLayoutY(i) - i3), i2);
        postInvalidate();
    }

    private int findCenterItem() {
        return getItemIndex(this.mOffset);
    }

    private int getItemIndex(float f) {
        return Math.round(f / (this.mItemHeight + this.mItemGap));
    }

    private int getItemLayoutY(int i) {
        return i * (this.mItemGap + this.mItemHeight);
    }

    private int getItemRealY(int i) {
        return this.mOffset + getItemLayoutY(i);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mInterceptEvent = false;
            this.mTouchDownX = (int) motionEvent.getX();
            this.mTouchDownY = (int) motionEvent.getY();
            this.mLastTouchX = (int) motionEvent.getX();
            this.mLastTouchY = (int) motionEvent.getY();
            this.mTouchDistance = 0;
        } else if (action == 2) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            int i = x - this.mLastTouchX;
            int i2 = y - this.mLastTouchY;
            this.mTouchDistance = (int) (this.mTouchDistance + Math.sqrt((i * i) + (i2 * i2)));
            this.mLastTouchX = x;
            this.mLastTouchY = y;
            if (!this.mInterceptEvent && this.mTouchDistance > this.mTouchSlop) {
                this.mInterceptEvent = true;
                motionEvent.setAction(0);
                onTouchEvent(motionEvent);
            }
        }
        return this.mInterceptEvent;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action;
        int action2 = motionEvent.getAction();
        if (action2 == 0) {
            this.mInTouch = true;
        } else if (action2 == 1 || action2 == 3) {
            this.mInTouch = false;
        }
        boolean onTouchEvent = this.mGestureDetector.onTouchEvent(motionEvent);
        if (!onTouchEvent && ((action = motionEvent.getAction()) == 1 || action == 3)) {
            onUp(motionEvent);
            Log.i("AdaptivePicker", "onCancel");
            onTouchEvent = true;
        }
        return onTouchEvent || super.onTouchEvent(motionEvent);
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onDown(MotionEvent motionEvent) {
        this.mScroller.forceFinished(true);
        return true;
    }

    public void onUp(MotionEvent motionEvent) {
        int findCenterItem = findCenterItem();
        if (!this.mCircularly) {
            if (findCenterItem < 0) {
                findCenterItem = 0;
            }
            int i = this.mItemCount;
            if (findCenterItem > i - 1) {
                findCenterItem = i - 1;
            }
        }
        scrollToIndex(findCenterItem, 150);
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (!this.mCircularly) {
            int itemLayoutY = getItemLayoutY(0);
            int itemLayoutY2 = getItemLayoutY(this.mItemCount - 1);
            if (this.mOffset < itemLayoutY && f2 < 0.0f) {
                f2 /= 2.0f;
            }
            if (this.mOffset > itemLayoutY2 && f2 > 0.0f) {
                f2 /= 2.0f;
            }
        }
        this.mOffset = (int) (this.mOffset + f2);
        updateItems();
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        Log.i("AdaptivePicker", "fling: " + f2 + ", " + motionEvent.getY() + " : " + motionEvent2.getY());
        if (Math.signum(motionEvent2.getY() - motionEvent.getY()) != Math.signum(f2)) {
            return false;
        }
        int i = Integer.MIN_VALUE;
        int i2 = Integer.MAX_VALUE;
        if (!this.mCircularly) {
            i = getItemLayoutY(0);
            i2 = getItemLayoutY(this.mItemCount - 1);
        }
        this.mScroller.fling(0, this.mOffset, 0, (int) (-f2), 0, 0, i, i2);
        int finalY = this.mScroller.getFinalY();
        if (finalY >= i) {
            i = finalY;
        }
        if (i > i2) {
            i = i2;
        }
        int itemLayoutY = getItemLayoutY(getItemIndex(i));
        this.mScroller.setFinalY(itemLayoutY);
        int duration = this.mScroller.getDuration();
        this.mScroller.forceFinished(true);
        Scroller scroller = this.mScroller;
        int i3 = this.mOffset;
        scroller.startScroll(0, i3, 0, itemLayoutY - i3, Math.max((int) ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, duration));
        postInvalidate();
        return true;
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            this.mOffset = this.mScroller.getCurrY();
            updateItems();
            postInvalidate();
        }
    }
}
