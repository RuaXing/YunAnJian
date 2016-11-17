package com.quanta.aj.yunanjian.mywegit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.MFragmentActivity;
import com.quanta.aj.yunanjian.orm.enforcement.EfCheck;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;
import java.util.Date;

public class Dialog_EfCheck extends MFragmentActivity {

    public static Intent getIntent(Context ctx, EfCheck efCheck) {
        Intent intent = new Intent(ctx, Dialog_EfCheck.class);
        intent.putExtra("efCheck", efCheck);
        return intent;
    }
    @ViewInject(R.id.zwsh)
    private EditText zwsh;
    @ViewInject(R.id.name)
    private EditText name;
    @ViewInject(R.id.no)
    private EditText no;
    @ViewInject(R.id.receiver)
    private EditText receiver;
    @ViewInject(R.id.address)
    private EditText address;
    @ViewInject(R.id.time)
    private TextView time;
    @ViewInject(R.id.type)
    private EditText type;
    @ViewInject(R.id.sender)
    private EditText sender;

    private EfCheck efCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog__ef_check);
        x.view().inject(this);
        efCheck = (EfCheck) getIntent().getSerializableExtra("efCheck");
        if (null!=efCheck)initData(efCheck);
    }

    private void initData(EfCheck efCheck) {
        zwsh.setText(efCheck.getZwsh());
        name.setText(efCheck.getName());
        no.setText(efCheck.getNo());
        receiver.setText(efCheck.getReceiver());
        address.setText(efCheck.getAddress());
        time.setText(getFmt().format(efCheck.getTime()));
        type.setText(efCheck.getType());
        sender.setText(efCheck.getSender());
    }

    @Event(value = {R.id.close_btn,R.id.comit,R.id.cansel,R.id.time})
    private void clink(View view){
        switch (view.getId()){
            case R.id.close_btn:
                if(null!=efCheck){
                    toComit();
                }else{
                    finish();
                }
                break;
            case R.id.comit:
                toComit();
                break;
            case R.id.cansel:
                zwsh.setText(null);
                name.setText(null);
                no.setText(null);
                receiver.setText(null);
                address.setText(null);
                time.setText(null);
                type.setText(null);
                sender.setText(null);
                break;
            case R.id.time:
                SlideDateTimeListener timelistener = new SlideDateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        time.setText(getFmt().format(date));
                    }
                };
                addTime(timelistener);
                break;
            default:break;
        }
    }

    private void toComit() {
        efCheck = new EfCheck();
        efCheck.setZwsh(zwsh.getText().toString());
        efCheck.setName(name.getText().toString());
        efCheck.setNo(no.getText().toString());
        efCheck.setReceiver(receiver.getText().toString());
        efCheck.setAddress(address.getText().toString());
        try {
            efCheck.setTime(getFmt().parse(time.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        efCheck.setType(type.getText().toString());
        efCheck.setSender(sender.getText().toString());
        Intent intent = new Intent();
        intent.putExtra("efCheck",efCheck);
        setResult(RESULT_OK,intent);
        finish();
    }
}
