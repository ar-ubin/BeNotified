package ar_ubin.benotified.utils;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

import ar_ubin.benotified.R;

public class ScrollingFABBehavior extends CoordinatorLayout.Behavior<FloatingActionButton>
{
    private int mToolbarHeight;

    public ScrollingFABBehavior( Context context, AttributeSet attrs ) {
        super( context, attrs );
        this.mToolbarHeight = getToolbarHeight( context );
    }

    @Override
    public boolean layoutDependsOn( CoordinatorLayout parent, FloatingActionButton fab, View dependency ) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged( CoordinatorLayout parent, FloatingActionButton fab, View dependency ) {
        if( dependency instanceof AppBarLayout ) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
            int fabBottomMargin = lp.bottomMargin;
            int distanceToScroll = fab.getHeight() + fabBottomMargin;
            float ratio = dependency.getY() / (float) mToolbarHeight;
            fab.setTranslationY( -distanceToScroll * ratio );
        }
        return true;
    }

    private static int getToolbarHeight( Context context ) {
        final TypedArray styledAttributes = context.getTheme()
                .obtainStyledAttributes( new int[]{ R.attr.actionBarSize } );
        int toolbarHeight = (int) styledAttributes.getDimension( 0, 0 );
        styledAttributes.recycle();

        return toolbarHeight;
    }
}
