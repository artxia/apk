package com.tv.leanback;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Optional facet provided by {@link RecyclerView.Adapter} or {@link RecyclerView.ViewHolder} for
 * than Presenter or Adapter.
 * <p>
 * ItemAlignmentFacet contains single or multiple {@link ItemAlignmentDef}s. First
 * {@link ItemAlignmentDef} describes the default alignment position for ViewHolder, it also
 * overrides the default item alignment settings on {@link VerticalGridView} and
 * {@link HorizontalGridView}. When there are multiple {@link ItemAlignmentDef}s, the extra
 * {@link ItemAlignmentDef}s are used to calculate deltas from first alignment position. When a
 * descendant view is focused within the ViewHolder, grid view will visit focused view and its
 * ancestors till the root of ViewHolder to match extra {@link ItemAlignmentDef}s'
 * {@link ItemAlignmentDef#getItemAlignmentViewId()}. Once a match found, the
 * {@link ItemAlignmentDef} is used to adjust a scroll delta from default alignment position.
 */
public final class ItemAlignmentFacet {

    /**
     * Value indicates that percent is not used.
     */
    public final static float ITEM_ALIGN_OFFSET_PERCENT_DISABLED = -1;

    /**
     * Definition of an alignment position under a view.
     */
    public static class ItemAlignmentDef {
        int mViewId = View.NO_ID;
        int mFocusViewId = View.NO_ID;
        int mOffset = 0;
        float mOffsetPercent = 50f;
        boolean mOffsetWithPadding = false;
        private boolean mAlignToBaseline;

        /**
         * Sets number of pixels to offset. Can be negative for alignment from the high edge, or
         * positive for alignment from the low edge.
         */
        public final void setItemAlignmentOffset(int offset) {
            mOffset = offset;
        }

        /**
         * Gets number of pixels to offset. Can be negative for alignment from the high edge, or
         * positive for alignment from the low edge.
         */
        public final int getItemAlignmentOffset() {
            return mOffset;
        }

        /**
         * Sets whether to include left/top padding for positive item offset, include
         * right/bottom padding for negative item offset.
         */
        public final void setItemAlignmentOffsetWithPadding(boolean withPadding) {
            mOffsetWithPadding = withPadding;
        }

        /**
         * When it is true: we include left/top padding for positive item offset, include
         * right/bottom padding for negative item offset.
         */
        public final boolean isItemAlignmentOffsetWithPadding() {
            return mOffsetWithPadding;
        }

        /**
         * Sets the offset percent for item alignment in addition to offset.  E.g., 40
         * means 40% of the width from the low edge. Use {@link #ITEM_ALIGN_OFFSET_PERCENT_DISABLED}
         * to disable.
         */
        public final void setItemAlignmentOffsetPercent(float percent) {
            if ((percent < 0 || percent > 100)
                    && percent != ITEM_ALIGN_OFFSET_PERCENT_DISABLED) {
                throw new IllegalArgumentException();
            }
            mOffsetPercent = percent;
        }

        /**
         * Gets the offset percent for item alignment in addition to offset. E.g., 40
         * means 40% of the width from the low edge. Use {@link #ITEM_ALIGN_OFFSET_PERCENT_DISABLED}
         * to disable.
         */
        public final float getItemAlignmentOffsetPercent() {
            return mOffsetPercent;
        }

        /**
         * Sets Id of which child view to be aligned.  View.NO_ID refers to root view and should
         * be only used in first one.  Extra ItemAlignmentDefs should provide view id to match
         * currently focused view.
         */
        public final void setItemAlignmentViewId(int viewId) {
            mViewId = viewId;
        }

        /**
         * Gets Id of which child view to be aligned.  View.NO_ID refers to root view and should
         * be only used in first one.  Extra ItemAlignmentDefs should provide view id to match
         * currently focused view.
         */
        public final int getItemAlignmentViewId() {
            return mViewId;
        }

        /**
         * Sets Id of which child view take focus for alignment.  When not set, it will use
         * use same id of {@link #getItemAlignmentViewId()}
         */
        public final void setItemAlignmentFocusViewId(int viewId) {
            mFocusViewId = viewId;
        }

        /**
         * Returns Id of which child view take focus for alignment.  When not set, it will use
         * use same id of {@link #getItemAlignmentViewId()}
         */
        public final int getItemAlignmentFocusViewId() {
            return mFocusViewId != View.NO_ID ? mFocusViewId : mViewId;
        }

        /**
         * Align to baseline if {@link #getItemAlignmentViewId()} is a TextView and
         * alignToBaseline is true.
         * @param alignToBaseline Boolean indicating whether to align the text to baseline.
         */
        public final void setAlignedToTextViewBaseline(boolean alignToBaseline) {
            this.mAlignToBaseline = alignToBaseline;
        }

        /**
         * Returns true when TextView should be aligned to the baseline.
         */
        public boolean isAlignedToTextViewBaseLine() {
            return mAlignToBaseline;
        }
    }

    private ItemAlignmentDef[] mAlignmentDefs = new ItemAlignmentDef[]{new ItemAlignmentDef()};

    public boolean isMultiAlignment() {
        return mAlignmentDefs.length > 1;
    }

    /**
     * Sets definitions of alignment positions.
     */
    public void setAlignmentDefs(ItemAlignmentDef[] defs) {
        if (defs == null || defs.length < 1) {
            throw new IllegalArgumentException();
        }
        mAlignmentDefs = defs;
    }

    /**
     * Returns read only definitions of alignment positions.
     */
    public ItemAlignmentDef[] getAlignmentDefs() {
        return mAlignmentDefs;
    }

}
