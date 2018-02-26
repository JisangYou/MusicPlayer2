package orgs.androidtown.musicplayer2.util;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

/**
 * Created by JisangYou on 2018-02-26.
 */

public class SquareImage extends android.support.v7.widget.AppCompatImageView {

    public SquareImage(Context context) {
        super(context);
    }

    public SquareImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels / 2;
        setPadding(2, 2, 2, 2);
        setMeasuredDimension(width, width);
    }
}
