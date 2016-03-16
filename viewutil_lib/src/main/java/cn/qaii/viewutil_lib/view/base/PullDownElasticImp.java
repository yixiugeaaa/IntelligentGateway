package cn.qaii.viewutil_lib.view.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.qaii.viewutil_lib.R;


public class PullDownElasticImp implements IPullDownElastic {
    private View refreshView;
    private ImageView arrowImageView;
    private int headContentHeight;
    private ProgressBar progressBar;
    private TextView tipsTextview;
    private TextView lastUpdatedTextView;
    
    private Context mContext;
    public PullDownElasticImp(Context context) {
        mContext = context;
        init();
    }
    

    private void init() {
        refreshView = LayoutInflater.from(mContext).inflate(
                R.layout.s_pull_to_refresh_head, null);

        arrowImageView = (ImageView) refreshView
                .findViewById(R.id.head_arrowImageView);
        arrowImageView.setMinimumWidth(70);
        arrowImageView.setMinimumHeight(50);
        progressBar = (ProgressBar) refreshView
                .findViewById(R.id.head_progressBar);
        tipsTextview = (TextView) refreshView.findViewById(R.id.head_tipsTextView);
        lastUpdatedTextView = (TextView) refreshView
                .findViewById(R.id.head_lastUpdatedTextView);

//        headContentHeight = dip2px(mContext, 50);
        measureView(refreshView);
        headContentHeight = refreshView.getMeasuredHeight();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * @return
     * 
     */
    @Override
    public View getElasticLayout() {
        return refreshView;
    }

    /**
     * @return
     * 
     */
    @Override
    public int getElasticHeight() {
        return headContentHeight;
    }

    /**
     * @param show
     * 
     */
    @Override
    public void showArrow(int visibility) {
        arrowImageView.setVisibility(visibility);
    }

    /**
     * @param animation
     * 
     */
    @Override
    public void startAnimation(Animation animation) {
        arrowImageView.startAnimation(animation);
    }

    /**
     * 
     * 
     */
    @Override
    public void clearAnimation() {
        arrowImageView.clearAnimation();
    }

    /**
     * @param show
     * 
     */
    @Override
    public void showProgressBar(int visibility) {
        progressBar.setVisibility(visibility);
    }

    /**
     * @param tips
     * 
     */
    @Override
    public void setTips(String tips) {
        tipsTextview.setText(tips);
    }

    /**
     * @param show
     * 
     */
    @Override
    public void showLastUpdate(int visibility) {
        lastUpdatedTextView.setVisibility(visibility);
    }

    /**
     * @param text
     * 
     */
    public void setLastUpdateText(String text) {
        lastUpdatedTextView.setText(text);
    }


    /**
     * @param state
     * @param isBack
     * 
     */
    @Override
    public void changeElasticState(int state, boolean isBack) {
        
    }
    
    /**
	 * 测量HeadView宽高(注意：此方法仅适用于LinearLayout，请读者自己测试验证。)
	 * 
	 * @param pChild
	 * @date 2013-11-20 下午4:12:07
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void measureView(View pChild) {
		ViewGroup.LayoutParams p = pChild.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;

		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
		}
		pChild.measure(childWidthSpec, childHeightSpec);
	}

}
