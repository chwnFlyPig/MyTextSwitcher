package chwn.wuba.com.mytextswitcher;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private PtTextSwitcher mPtTextSwitcher;
    private ImageView iv_arraw;
    private ArrayList<PtTextSwitcherBean> mSwitcherLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            initView();
            mockData();
            mPtTextSwitcher.startLoop();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if(mPtTextSwitcher != null){
            mPtTextSwitcher.stopLoop();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mPtTextSwitcher != null){
            mPtTextSwitcher.stopLoop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mPtTextSwitcher != null){
            mPtTextSwitcher.startLoop();
        }
    }

    private void initView(){
        mPtTextSwitcher = (PtTextSwitcher) findViewById(R.id.v_textswitcher);
        mPtTextSwitcher.setPtTextSwitcherClickListener(new PtTextSwitcher.PtTextSwitcherClickListener() {
            @Override
            public void onItemClick(int postion, PtTextSwitcherBean itemData) {
                Log.e("chwn","p:"+postion+";itemData:"+itemData.getTitle());
            }
        });
        iv_arraw = (ImageView)findViewById(R.id.iv_arraw);
        iv_arraw.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.e("chwn","onClick arraw");
                if(mPtTextSwitcher != null){
                    mPtTextSwitcher.performClick();
                }
            }
        });
    }

    private void mockData(){
        PtTextSwitcherBean bean1 = new PtTextSwitcherBean();
        bean1.setTitle("200元一天 家乐福超市导购");
        mSwitcherLists.add(bean1);
        PtTextSwitcherBean bean2 = new PtTextSwitcherBean();
        bean2.setTitle("100元一天 草莓音乐节传单发放");
        mSwitcherLists.add(bean2);
        PtTextSwitcherBean bean3 = new PtTextSwitcherBean();
        bean3.setTitle("600元一天 婚博会司仪");
        mSwitcherLists.add(bean3);
        PtTextSwitcherBean bean4 = new PtTextSwitcherBean();
        bean4.setTitle("300元一天 地铁一号线安全宣传员");
        mSwitcherLists.add(bean4);
        mPtTextSwitcher.setDatas(mSwitcherLists);
    }
}
