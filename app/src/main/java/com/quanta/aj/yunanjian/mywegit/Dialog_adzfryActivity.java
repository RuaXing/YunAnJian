package com.quanta.aj.yunanjian.mywegit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.BaseActivity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


public class Dialog_adzfryActivity extends BaseActivity {

    @ViewInject(R.id.username)
    private EditText username;
    @ViewInject(R.id.cardnum)
    private EditText cardnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_adzfry);
        x.view().inject(this);
    }
    @Event(value = {R.id.close_btn,R.id.comit,R.id.cansel})
    private void clink(View view){
        switch (view.getId()){
            case R.id.close_btn:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.comit:
                String name = String.valueOf(username.getText());
                String cert = String.valueOf(cardnum.getText());
                Intent intent = new Intent();
                intent.putExtra("name",name);
                intent.putExtra("cert",cert);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.cansel:
                username.setText("");
                cardnum.setText("");
                break;
        }
    }
}
