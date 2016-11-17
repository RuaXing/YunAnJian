package com.quanta.aj.yunanjian.activity.enforcement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.BaseActivity;
import com.quanta.aj.yunanjian.adapter.MyListAdapter;
import com.quanta.aj.yunanjian.constant.Constant;
import com.quanta.aj.yunanjian.constant.URLs;
import com.quanta.aj.yunanjian.mywegit.Title_Big_Layout;
import com.quanta.aj.yunanjian.orm.Page;
import com.quanta.aj.yunanjian.page.enforcement.EfReviewPage;
import com.quanta.aj.yunanjian.util.JsonUtils;
import com.quanta.aj.yunanjian.vo.enforcement.MEFReview;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.List;

public class ReviewList extends BaseActivity {
    private static final String TAG = "ReviewList";
    @ViewInject(R.id.reviewtt)
    private Title_Big_Layout reviewtt;
    @ViewInject(R.id.reviewlist)
    private ListView reviewlist;

    private Context mContext;
    private List<Object> reviews = null;
    private MyListAdapter myListAdapter;
    private MEFReview mEfreview;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    initData(reviews);
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);
        x.view().inject(this);
        mContext = ReviewList.this;
        reviewtt.setTitle("整改复查");
        reviewtt.setTextSize(20);
        //reviewtt.setTextColor(R.color.hei);
        getData("0");
    }
    @Event(value = {R.id.lookall,R.id.lookwz})
    private void clink(View v) {
        switch (v.getId()){
            case R.id.lookall:
                getData(null);
                break;
            case R.id.lookwz:
                getData("0");
                break;
        }
    }
    private void getData(final String status) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EfReviewPage page = new EfReviewPage();
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.REVIW_URL);
                params.addParameter("page",page);
                params.addBodyParameter("status",status);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Page<Object> page = JsonUtils.parseObject(s,Page.class);
                        reviews = page.getRows();
                        handler.sendEmptyMessage(1);
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Log.e(TAG,"请求出错！",throwable);
                    }

                    @Override
                    public void onCancelled(CancelledException e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        }).start();
    }

    private void initData(final List<Object> reviews) {
        myListAdapter = new MyListAdapter<Object>(reviews,R.layout.item_review) {
            @Override
            public void bindView(ViewHolder holder, Object obj) {
                final String mEfreviewJson = JsonUtils.toString(obj);
                MEFReview mEfreview = new MEFReview();
                mEfreview = getGson().fromJson(mEfreviewJson,MEFReview.class);
                holder.setText(R.id.corp_name,mEfreview.getCorpName());
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                holder.setText(R.id.time,fmt.format(mEfreview.getLimitTime()));
                holder.setText(R.id.status,mEfreview.getStatusName());
                if (holder.getItemPosition()%2==0)holder.setBackground(R.id.list_item,0,mContext.getResources().getDrawable(R.drawable.bg_list_0));
                if (holder.getItemPosition()%2!=0)holder.setBackground(R.id.list_item,0,mContext.getResources().getDrawable(R.drawable.bg_list_1));
                if (mEfreview.getStatus().equals("0")){
                    holder.setImageResource(R.id.img,R.drawable.review_y);
                    holder.setText(R.id.title,"待复查");
                    holder.setTextColor(R.id.title,mContext.getResources().getColor(R.color.errtext));
                }else if (mEfreview.getStatus().equals("1")){
                    holder.setImageResource(R.id.img,R.drawable.review_n);
                    holder.setText(R.id.title,"已复查");
                    holder.setTextColor(R.id.title,mContext.getResources().getColor(R.color.green));
                }
                holder.setOnClickListener(R.id.list_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(ReviewActivity.getIntent(mContext,mEfreviewJson),1);
                    }
                });
            }
        };
        reviewlist.setAdapter(myListAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                getData("0");
                break;
        }
    }
}
