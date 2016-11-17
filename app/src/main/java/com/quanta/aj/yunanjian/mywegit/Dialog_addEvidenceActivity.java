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

public class Dialog_addEvidenceActivity extends BaseActivity {

    @ViewInject(R.id.name)
    private EditText name;
    @ViewInject(R.id.type)
    private EditText type;
    @ViewInject(R.id.number)
    private EditText number;
    @ViewInject(R.id.remark)
    private EditText remark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_add_evidence);
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
                String iname = String.valueOf(name.getText());
                String itype = String.valueOf(type.getText());
                String inumber = String.valueOf(number.getText());
                String iremark = String.valueOf(remark.getText());
                Intent intent = new Intent();
                intent.putExtra("name",iname);
                intent.putExtra("type",itype);
                intent.putExtra("number",inumber);
                intent.putExtra("remark",iremark);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.cansel:
                name.setText("");
                type.setText("");
                number.setText("");
                remark.setText("");
                break;
        }
    }
}
