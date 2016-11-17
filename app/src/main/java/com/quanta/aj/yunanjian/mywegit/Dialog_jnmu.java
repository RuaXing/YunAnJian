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
import com.quanta.aj.yunanjian.orm.enforcement.Document36List;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;
import java.util.Date;

public class Dialog_jnmu extends MFragmentActivity {
    public static Intent getIntent(Context ctx, Document36List document36List) {
        Intent intent = new Intent(ctx, Dialog_jnmu.class);
        intent.putExtra("document36List", document36List);
        return intent;
    }
    @ViewInject(R.id.title)
    private EditText title;
    @ViewInject(R.id.no)
    private EditText no;
    @ViewInject(R.id.time)
    private TextView time;
    @ViewInject(R.id.page)
    private EditText page;
    @ViewInject(R.id.remark)
    private EditText remark;

    private Document36List document36List;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_jnmu);
        x.view().inject(this);
        document36List = (Document36List)getIntent().getSerializableExtra("document36List");
        if (null!=document36List)initData(document36List);
    }

    private void initData(Document36List document36List) {
        title.setText(document36List.getTitle());
        no.setText(document36List.getNo());
        time.setText(getFmt().format(document36List.getTime()));
        page.setText(document36List.getPage());
        remark.setText(document36List.getRemark());
    }
    @Event(value = {R.id.close_btn,R.id.comit,R.id.cansel,R.id.time})
    private void clink(View view){
        switch (view.getId()){
            case R.id.close_btn:
                if(null!=document36List){
                    toComit();
                }else{
                    finish();
                }
                break;
            case R.id.comit:
                toComit();
                break;
            case R.id.cansel:
                title.setText(null);
                no.setText(null);
                time.setText(null);
                page.setText(null);
                remark.setText(null);
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
        document36List = new Document36List();
        document36List.setTitle(title.getText().toString());
        document36List.setNo(no.getText().toString());
        try {
            document36List.setTime(getFmt().parse(time.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        document36List.setPage(page.getText().toString());
        document36List.setRemark(remark.getText().toString());
        Intent intent = new Intent();
        intent.putExtra("document36List",document36List);
        setResult(RESULT_OK,intent);
        finish();
    }
}
