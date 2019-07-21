package com.bannerstone.smileynews.views;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

public class StartSnapHelper extends LinearSnapHelper {
    private int previousClosestPosition = 0;
    private int snapCount = 1;

    public StartSnapHelper() {

    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView)
            throws IllegalStateException {
        super.attachToRecyclerView(recyclerView);
    }

    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager,
                                              @NonNull View targetView) {
        OrientationHelper orientationHelper = OrientationHelper.createVerticalHelper(layoutManager);
        int[] out = new int[2];
        out[0] = 0;
        out[1] = orientationHelper.getDecoratedStart(targetView) - orientationHelper.getStartAfterPadding();

        return out;
    }

    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        OrientationHelper helper = OrientationHelper.createVerticalHelper(layoutManager);
        int childCount = layoutManager.getChildCount();

        View closestChild = null;
        int closestPosition = RecyclerView.NO_POSITION;
        final int containerPosition = helper.getStartAfterPadding();
        int absClosest = Integer.MAX_VALUE;

        for (int i = 0; i < childCount; i++) {
            final View child = layoutManager.getChildAt(i);
            int childPosition = helper.getDecoratedStart(child);

            int absDistance = Math.abs(childPosition - containerPosition);
            if (helper.getDecoratedStart(child) == 0
                    && previousClosestPosition != 0
                    && layoutManager.getPosition(child) == 0) {

                closestChild = child;
                closestPosition = layoutManager.getPosition(closestChild);
                break;
            }
            if (helper.getDecoratedEnd(child) == helper.getTotalSpace()
                    && previousClosestPosition != layoutManager.getItemCount() - 1
                    && layoutManager.getPosition(child) == layoutManager.getItemCount() - 1) {
                closestChild = child;
                closestPosition = layoutManager.getPosition(closestChild);
                break;
            }
            if (previousClosestPosition == layoutManager.getPosition(child) && distanceToStart(child, helper) == 0) {
                closestChild = child;
                closestPosition = layoutManager.getPosition(closestChild);
                break;
            }
            if (layoutManager.getPosition(child) % snapCount != 0) {
                continue;
            }

            if (absDistance < absClosest) {
                absClosest = absDistance;
                closestChild = child;
                closestPosition = layoutManager.getPosition(closestChild);
            }
        }
        previousClosestPosition = closestPosition == RecyclerView.NO_POSITION ? previousClosestPosition : closestPosition;
        return closestChild;
    }

    private int distanceToStart(View targetView, OrientationHelper helper) {
        return helper.getDecoratedStart(targetView) - helper.getStartAfterPadding();
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        OrientationHelper helper = OrientationHelper.createVerticalHelper(layoutManager);

        boolean forwardDirection = velocityY > 0;
        int firstExpectedPosition = forwardDirection ? 0 : layoutManager.getItemCount() - 1;

        for (int i = firstExpectedPosition;
             forwardDirection ? i <= layoutManager.getItemCount() - 1 : i >= 0;
             i = forwardDirection ? i + 1 : i - 1) {
            View view = layoutManager.findViewByPosition(i);
            if (view == null || shouldSkipTarget(view, helper, forwardDirection)) {
                continue;
            }
            if (forwardDirection) {
                int diff = i - previousClosestPosition;
                int factor = (diff % snapCount == 0) ? diff / snapCount : diff / snapCount + 1;

                int retval = previousClosestPosition + snapCount * factor;
                return retval;
            } else {
                int diff = previousClosestPosition - i;
                int factor = (diff % snapCount == 0) ? diff / snapCount : diff / snapCount + 1;
                if (previousClosestPosition == layoutManager.getItemCount() - 1 && previousClosestPosition % snapCount != 0) {
                    int retval = (previousClosestPosition - previousClosestPosition % snapCount + snapCount) - snapCount * factor;
                    return retval;
                }
                int retval = previousClosestPosition - snapCount * factor;
                return retval;
            }
        }
        return forwardDirection ? layoutManager.getItemCount() - 1 : 0;
    }


    private boolean shouldSkipTarget(View targetView, OrientationHelper helper, boolean forwardDirection) {
        return forwardDirection
                ? distanceToStart(targetView, helper) < 0
                : distanceToStart(targetView, helper) > 0;
    }
}
