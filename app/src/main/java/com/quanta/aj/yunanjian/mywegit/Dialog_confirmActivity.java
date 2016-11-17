package com.quanta.aj.yunanjian.mywegit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.BaseActivity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class Dialog_confirmActivity extends BaseActivity {
    private static final String TAG = "ReviewActivity";
    public static Intent getIntent(Context ctx, String text) {
        Intent intent = new Intent(ctx, Dialog_confirmActivity.class);
        intent.putExtra("text", text);
        return intent;
    }
    @ViewInject(R.id.text)
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_confirm);
        x.view().inject(this);
        String s = getIntent().getStringExtra("text");
        text.setText(s);
    }
    @Event(value = {R.id.comit,R.id.cancel})
    private void clink(View v){
        switch (v.getId()){
            case R.id.comit:
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.cancel:
                finish();
                break;
            default:break;
        }
    }
}
