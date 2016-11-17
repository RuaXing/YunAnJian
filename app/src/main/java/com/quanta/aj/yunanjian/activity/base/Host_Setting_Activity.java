package com.quanta.aj.yunanjian.activity.base;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.constant.Constant;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class Host_Setting_Activity extends BaseActivity {

    @ViewInject(R.id.host)
    private EditText host;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        x.view().inject(this);
        mContext = Host_Setting_Activity.this;
        SharedPreferences sp = getSharedPreferences("Cabin", Activity.MODE_PRIVATE);
        String address = sp.getString("HostAddress",""+getResources().getString(R.string.host_address));
        host.setText(address);
    }

    @Event(value = {R.id.comit,R.id.cancel})
    private void clink(View v){
        switch (v.getId()){
            case R.id.comit:
                toComit();
                break;
            case R.id.cancel:
                host.setText(null);
                break;
           default:break;
        }
    }

    private void toComit() {
        SharedPreferences.Editor editor = getSharedPreferences("Cabin",Activity.MODE_PRIVATE).edit();
        editor.putString("HostAddress",host.getText().toString());
        editor.commit();
        Constant.setHOST("http://"+host.getText().toString()+"/"+getString(R.string.host_name));
        //Constant.setHOST("http://"+host.getText().toString()+"/Cabin");
        finish();
    }
}
