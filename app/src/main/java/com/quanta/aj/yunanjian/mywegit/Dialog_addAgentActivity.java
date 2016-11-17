package com.quanta.aj.yunanjian.mywegit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.BaseActivity;
import com.quanta.aj.yunanjian.orm.enforcement.EfAgent;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class Dialog_addAgentActivity extends BaseActivity {

    @ViewInject(R.id.name)
    private EditText name;
    @ViewInject(R.id.sex)
    private Spinner sex;
    @ViewInject(R.id.age)
    private EditText age;
    @ViewInject(R.id.corp)
    private EditText corp;
    @ViewInject(R.id.zw)
    private EditText zw;

    private EfAgent efAgent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_add_agent);
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
                efAgent = new EfAgent();
                efAgent.setName(name.getText().toString());
                efAgent.setSex(String.valueOf(sex.getSelectedItemPosition()));
                efAgent.setCorp(corp.getText().toString());
                efAgent.setAge(age.getText().toString());
                efAgent.setZw(zw.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("efAgent",efAgent);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.cansel:
                name.setText(null);
                age.setText(null);
                corp.setText(null);
                zw.setText(null);
                break;
        }
    }
}
