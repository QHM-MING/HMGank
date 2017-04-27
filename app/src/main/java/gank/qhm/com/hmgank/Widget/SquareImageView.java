package gank.qhm.com.hmgank.Widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by liuyang on 16/5/27.
 */
public class SquareImageView extends AppCompatImageView {

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredWidth() <= getMeasuredHeight()) {
            setMeasuredDimension(getMeasuredWidth(),getMeasuredWidth());
        } else {
            setMeasuredDimension(getMeasuredHeight(),getMeasuredHeight());
        }
    }
}
