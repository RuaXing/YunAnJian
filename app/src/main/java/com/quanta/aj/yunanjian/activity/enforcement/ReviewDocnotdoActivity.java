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
import com.quanta.aj.yunanjian.constant.DocumentType;
import com.quanta.aj.yunanjian.constant.URLs;
import com.quanta.aj.yunanjian.orm.CorpInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfCaseInfo;
import com.quanta.aj.yunanjian.orm.enforcement.EfReview;
import com.quanta.aj.yunanjian.page.enforcement.EfDocumentPage;
import com.quanta.aj.yunanjian.page.enforcement.EfDocumentPageItem;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

@ContentView(R.layout.activity_ef_doc_not_do)
public class ReviewDocnotdoActivity extends BaseActivity {
    private static final String TAG = "ReviewDocnotdoActivity";
    public static Intent getIntent(Context ctx, EfReview efReview) {
        Intent intent = new Intent(ctx, ReviewDocnotdoActivity.class);
        intent.putExtra("efReview", efReview);
        return intent;
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    initData(docmentesN);
                    break;
            }
        }
    };
    @ViewInject(R.id.efDocNotdoList)
    private ListView efDocNotdoList;

    private EfReview efReview;
    private List<EfDocumentPageItem> docmentesN = null;
    private MyListAdapter docListAdapter;
    private CorpInfo corpInfo;
    private Context mContext;
    private int navId = 100;
    private EfCaseInfo caseinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mContext = ReviewDocnotdoActivity.this;
        efReview = (EfReview)getIntent().getSerializableExtra("efReview");
        getDocListN();
        getCorp();
    }
    private void getDocListN() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.DOMS_URL);
                params.addBodyParameter("reviewId",efReview.getId());
                params.addBodyParameter("status","0");
                params.addBodyParameter("buildType","3");
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        EfDocumentPage page = getGson().fromJson(s,EfDocumentPage.class);
                        docmentesN = page.getRows();
                        handler.sendEmptyMessage(1);
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Log.e(TAG,"请求失败！",throwable);
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
    private void getCorp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.GETCORP_URL);
                params.addBodyParameter("corpId",efReview.getCorp());
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Log.d(TAG,"企业信息获取成功");
                        corpInfo = getGson().fromJson(s,CorpInfo.class);
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
    private void initData(List<EfDocumentPageItem> docmentesN) {
        docListAdapter = new MyListAdapter<EfDocumentPageItem>(docmentesN,R.layout.item_ef_list) {
            @Override
            public void bindView(ViewHolder holder, final EfDocumentPageItem obj) {
                holder.setText(R.id.ef_name,obj.getName());
                holder.setText(R.id.ef_time,"");
                holder.setText(R.id.status,"未做");
                holder.setTextColor(R.id.status,mContext.getResources().getColor(R.color.green));
                if (holder.getItemPosition()%2==0)holder.setBackground(R.id.list_item,0,mContext.getResources().getDrawable(R.drawable.bg_list_0));
                if (holder.getItemPosition()%2!=0)holder.setBackground(R.id.list_item,0,mContext.getResources().getDrawable(R.drawable.bg_list_1));
                holder.setOnClickListener(R.id.list_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击相应文书进入其对应的制作页面
                        toItemClink(obj);
                    }
                });
            }
        };
        efDocNotdoList.setAdapter(docListAdapter);
    }
    private void toItemClink(EfDocumentPageItem obj) {
        String docId = obj.getId();
        String docName = obj.getName();
        if (DocumentType.d1.name.equals(docName)){
            startActivityForResult(Document01Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d2.name.equals(docName)){
            startActivityForResult(Document02Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d3.name.equals(docName)){
            startActivityForResult(Document03Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d4.name.equals(docName)){
            startActivityForResult(Document04Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d5.name.equals(docName)){
            startActivityForResult(Document05Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d6.name.equals(docName)){
            startActivityForResult(Document06Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d7.name.equals(docName)){
            startActivityForResult(Document07Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d8.name.equals(docName)){
            startActivityForResult(Document08Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d9.name.equals(docName)){
            startActivityForResult(Document09Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d10.name.equals(docName)){
            startActivityForResult(Document10Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d11.name.equals(docName)){
            startActivityForResult(Document11Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d12.name.equals(docName)){
            startActivityForResult(Document12Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d13.name.equals(docName)){
            startActivityForResult(Document13Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d14.name.equals(docName)){
            startActivityForResult(Document14Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d15.name.equals(docName)){
            startActivityForResult(Document15Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d16.name.equals(docName)){
            startActivityForResult(Document16Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d17.name.equals(docName)){
            startActivityForResult(Document17Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d18.name.equals(docName)){
            startActivityForResult(Document18Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d19.name.equals(docName)){
            startActivityForResult(Document19Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d20.name.equals(docName)){
            startActivityForResult(Document20Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d21.name.equals(docName)){
            startActivityForResult(Document21Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d22.name.equals(docName)){
            startActivityForResult(Document22Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d23.name.equals(docName)){
            startActivityForResult(Document23Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d24.name.equals(docName)){
            startActivityForResult(Document24Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d25.name.equals(docName)){
            startActivityForResult(Document25Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d26.name.equals(docName)){
            startActivityForResult(Document26Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d27.name.equals(docName)){
            startActivityForResult(Document27Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d28.name.equals(docName)){
            startActivityForResult(Document28Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d29.name.equals(docName)){
            startActivityForResult(Document29Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d30.name.equals(docName)){
            startActivityForResult(Document30Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d31.name.equals(docName)){
            startActivityForResult(Document31Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d32.name.equals(docName)){
            startActivityForResult(Document32Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d33.name.equals(docName)){
            startActivityForResult(Document33Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d34.name.equals(docName)){
            startActivityForResult(Document34Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d35.name.equals(docName)){
            startActivityForResult(Document35Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }else if (DocumentType.d36.name.equals(docName)){
            startActivityForResult(Document36Activity.getIntent(mContext,corpInfo,docId,caseinfo),navId);
        }
    }
    @Event(value = {R.id.leftbtn})
    private void clink(View v){
        switch (v.getId()){
            case R.id.leftbtn:
                finish();
                break;
            default:break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 100:
                getDocListN();
                break;
        }
    }
}
