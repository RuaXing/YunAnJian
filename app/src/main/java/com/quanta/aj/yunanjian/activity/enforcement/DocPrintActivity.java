package com.quanta.aj.yunanjian.activity.enforcement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.BaseActivity;
import com.quanta.aj.yunanjian.constant.Constant;
import com.quanta.aj.yunanjian.mywegit.Dialog_confirmActivity;
import com.quanta.aj.yunanjian.orm.enforcement.EfDocument;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class DocPrintActivity extends BaseActivity {
    private static final String TAG = "DocPrintActivity";
    public static Intent getIntent(Context ctx, EfDocument efDocument) {
        Intent intent = new Intent(ctx, DocPrintActivity.class);
        intent.putExtra("efDocument", efDocument);
        return intent;
    }
    @ViewInject(R.id.mwebview)
    private WebView mwebview;

    private EfDocument efDocument;
    private Context mContext;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        x.view().inject(this);
        mContext = DocPrintActivity.this;
        efDocument = (EfDocument)getIntent().getSerializableExtra("efDocument") ;
        String seq = efDocument.getSeq().toString();
        if (efDocument.getSeq()<10)seq = "0"+seq;
        url = Constant.getHOST(mContext)+"/enforcement/"+seq+"/print.do?"+"id="+efDocument.getDocument()+"&documentId="+efDocument.getId()+"&caseId="+efDocument.getCaseId()+"&JSESSIONID="+ Constant.JSESSIONID+"&ZW_SESSION="+Constant.JSESSIONID;
        initWebSettting();
        mwebview.loadUrl(url);
    }

    private void initWebSettting() {
        mwebview.getSettings().setDefaultTextEncodingName("utf-8");
        mwebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        mwebview.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        mwebview.getSettings().setSupportZoom(true);//是否可以缩放，默认true
        mwebview.getSettings().setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        mwebview.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        mwebview.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        mwebview.getSettings().setAppCacheEnabled(true);//是否使用缓存
        mwebview.getSettings().setDomStorageEnabled(true);//DOM Storage
        // displayWebview.getSettings().setUserAgentString("User-Agent:Android");//设置用户代理，一般不用
    }

    @Event(value = {R.id.print,R.id.cancel})
    private void clink(View v){
        switch (v.getId()){
            case R.id.print:
                startActivityForResult(Dialog_confirmActivity.getIntent(DocPrintActivity.this,"终端是否已连上蓝牙打印机？"),1);
                break;
            case R.id.cancel:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK) print();
                break;
        }
    }

    private void print() {
    }
}
