package com.quanta.aj.yunanjian.activity.gps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.quanta.aj.yunanjian.R;

/**
 * 就是获取输入的值传回上个活动
 */
public class SearchActivity extends Activity {
    private EditText str1;
    private EditText str2;
    private Button search_do;
    private Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc_);
        str1 = (EditText) findViewById(R.id.str1);
        str2 = (EditText) findViewById(R.id.str2);
        search_do = (Button)findViewById(R.id.search_do);
        cancel = (Button)findViewById(R.id.cancel);
        search_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = str1.getText().toString();
                String s2 = str2.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("str1",s1);
                intent.putExtra("str2",s2);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
