package chwn.wuba.com.mytextswitcher;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by changwenna on 2017/4/11.
 */
public class PtTextSwitcher extends TextSwitcher implements ViewSwitcher.ViewFactory {
    private Context mContext;
    private ArrayList<PtTextSwitcherBean> mDatas = new ArrayList<>();
    private PtTextSwitcherClickListener mPtTextSwitcherClickListener;
    private int mCurIndex;
    private Timer mTimer;
    private boolean isCancel = true;
    private static final long mTimeSpan = 3 * 1000;

    public PtTextSwitcher(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public PtTextSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        setFactory(this);
        setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_top_in));
        setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_top_out));
        setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mPtTextSwitcherClickListener != null) {
                    PtTextSwitcherBean itemData = getCurItemData();
                    if (itemData == null) {
                        return;
                    }
                    mPtTextSwitcherClickListener.onItemClick(getCurIndex(), itemData);
                }
            }
        });
    }

    @Override
    public View makeView() {
        TextView tv = (TextView) View.inflate(mContext, R.layout.pt_home_textswitcher_item_view, null);
        return tv;
    }

    public void setDatas(ArrayList<PtTextSwitcherBean> datas) {
        stopLoop();
        mDatas.clear();
        mDatas.addAll(datas);
    }

    public void startLoop() {
        try {
            if (!isCancel) return;

            if (null != mTimer) {
                mTimer.cancel();
                mTimer = null;
            }

            isCancel = false;
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    loop();
                }
            }, 0, mTimeSpan);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopLoop() {
        isCancel = true;
        if (null != mTimer) {
            mTimer.cancel();
        }
    }

    public void setPtTextSwitcherClickListener(PtTextSwitcherClickListener listener) {
        mPtTextSwitcherClickListener = listener;
    }

    private void next() {
        try {
            if (getVisibility() != View.VISIBLE) {
                return;
            }
            mCurIndex++;
            PtTextSwitcherBean itemData = getCurItemData();
            if (itemData == null) {
                return;
            }
            setText(itemData.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loop() {
        post(new Runnable() {
            @Override
            public void run() {
                next();
            }
        });
    }

    private int getCurIndex(){
        if (mDatas == null || mDatas.isEmpty()) {
            mCurIndex = -1;
            return mCurIndex;
        }
        if (mCurIndex >= mDatas.size()) {
            mCurIndex = 0;
        }
        return mCurIndex;
    }
    private PtTextSwitcherBean getCurItemData() {
        if (mDatas == null || mDatas.isEmpty()) {
            return null;
        }
        int index = getCurIndex();
        if (index < 0 || index >= mDatas.size()) {
            return null;
        }
        return mDatas.get(index);
    }

    public interface PtTextSwitcherClickListener {
        void onItemClick(int postion, PtTextSwitcherBean itemData);
    }
}
