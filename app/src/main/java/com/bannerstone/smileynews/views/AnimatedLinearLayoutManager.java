package com.bannerstone.smileynews.views;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AnimatedLinearLayoutManager extends LinearLayoutManager {
    private static final int SMOOTH_VALUE = 100;

    private final float mShrinkAmount = 0.15f;
    private final float mShrinkDistance = 1.0f;
    private int featuredItem = 0;

    public AnimatedLinearLayoutManager(Context context) {
        super(context, RecyclerView.VERTICAL, false);
        setSmoothScrollbarEnabled(false);
    }

    @Override
    public int scrollVerticallyBy(int dy, @NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state) {
        int orientation = getOrientation();
        if (orientation == VERTICAL) {
            int scrolled = super.scrollVerticallyBy(dy, recycler, state);

            boolean forwardDirection = dy >= 0;
            float midpoint = getHeight() / 2.0f;
            float d0 = 0.0f;
            float d1 = mShrinkDistance * midpoint;
            float s0 = 1.0f;
            float s1 = 1.0f - mShrinkAmount;

            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                int childPosition = getPosition(child);

                if( childPosition == 0 ) {
                    child.setScaleX(1.00f);
                    if( getDecoratedTop(child) == 0 ){
                        featuredItem = 0;
                    }
                    continue;
                }

                if( forwardDirection) {
                    //Only scale child who is after featuredItem
                    if( getDecoratedTop(child) == 0 ){
                        child.setScaleX(1.00f);
                    }
                    if( childPosition > featuredItem && getDecoratedTop(child) >=0 ) {
                        float childMidpoint = (getDecoratedBottom(child) + getDecoratedTop(child)) / 2.0f;
                        float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
                        float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
                        if (scale > 0.99) {
                            scale = 1.00f;
                            featuredItem = childPosition;
                        }
                        child.setScaleX(scale);
                    }
                } else {
                    if(childPosition < featuredItem) {
                        child.setScaleX(1.00f);
                    }

                    if( childPosition >= featuredItem && getDecoratedTop(child) > 0 ) {
                        float childMidpoint = (getDecoratedBottom(child) + getDecoratedTop(child)) / 2.0f;
                        float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
                        float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
                        child.setScaleX(scale);
                    }
                    if( getDecoratedTop(child) == 0 ){
                        featuredItem = getPosition(child);
                        child.setScaleX(1.00f);
                    }

                }
            }
            return scrolled;
        } else {
            return 0;
        }
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        scrollVerticallyBy(0, recycler, state);
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public boolean canScrollHorizontally() {
        return false;
    }

    @Override
    public int computeVerticalScrollExtent(RecyclerView.State state) {
        final int count = getChildCount();
        if (count > 0) {
            return SMOOTH_VALUE * 3;
        }
        return 0;
    }

    @Override
    public int computeVerticalScrollRange(RecyclerView.State state) {
        return Math.max((getItemCount() - 1) * SMOOTH_VALUE, 0);
    }

    @Override
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        final int count = getChildCount();

        if (count <= 0) {
            return 0;
        }
        if (findLastCompletelyVisibleItemPosition() == getItemCount() - 1) {
            return Math.max((getItemCount() - 1) * SMOOTH_VALUE, 0);
        }

        int heightOfScreen;
        int firstPos = findFirstVisibleItemPosition();
        if (firstPos == RecyclerView.NO_POSITION) {
            return 0;
        }
        View view = findViewByPosition(firstPos);
        if (view == null) {
            return 0;
        }
        // Top of the view in pixels
        final int top = getDecoratedTop(view);
        int height = getDecoratedMeasuredHeight(view);
        if (height <= 0) {
            heightOfScreen = 0;
        } else {
            heightOfScreen = Math.abs(SMOOTH_VALUE * top / height);
        }
        if (heightOfScreen == 0 && firstPos > 0) {
            return SMOOTH_VALUE * firstPos - 1;
        }
        return (SMOOTH_VALUE * firstPos) + heightOfScreen;
    }
}