package com.quanta.aj.yunanjian.mywegit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.BaseActivity;
import com.quanta.aj.yunanjian.orm.enforcement.EfAgent;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class Dialog_AgentActivity extends BaseActivity {
    public static Intent getIntent(Context ctx, EfAgent efAgent) {
        Intent intent = new Intent(ctx, Dialog_AgentActivity.class);
        intent.putExtra("efAgent", efAgent);
        return intent;
    }
    @ViewInject(R.id.name)
    private TextView name;
    @ViewInject(R.id.sex)
    private TextView sex;
    @ViewInject(R.id.age)
    private TextView age;
    @ViewInject(R.id.corp)
    private TextView corp;
    @ViewInject(R.id.zw)
    private TextView zw;

    private EfAgent efAgent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog__agent);
        x.view().inject(this);
        efAgent = (EfAgent) getIntent().getSerializableExtra("efAgent");
        initdate(efAgent);
    }

    private void initdate(EfAgent efAgent) {
        name.setText(efAgent.getName());
        sex.setText(efAgent.getSex().equals("0")?"男":"女");
        age.setText(efAgent.getAge());
        corp.setText(efAgent.getCorp());
        zw.setText(efAgent.getZw());
    }

    @Event(value = {R.id.close_btn})
    private void clink(View view){
        switch (view.getId()){
            case R.id.close_btn:
                setResult(RESULT_CANCELED);
                finish();
                break;
            default:break;
        }
    }
}
