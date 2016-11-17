package com.quanta.aj.yunanjian.mywegit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.BaseActivity;
import com.quanta.aj.yunanjian.orm.enforcement.EfList;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class Dialog_addEflist extends BaseActivity {
    public static Intent getIntent(Context ctx, EfList efList) {
        Intent intent = new Intent(ctx, Dialog_addEflist.class);
        intent.putExtra("efList", efList);
        return intent;
    }
    @ViewInject(R.id.name)
    private EditText name;
    @ViewInject(R.id.type)
    private EditText type;
    @ViewInject(R.id.address)
    private EditText address;
    @ViewInject(R.id.quality)
    private EditText quality;
    @ViewInject(R.id.corp)
    private EditText corp;
    @ViewInject(R.id.price)
    private EditText price;
    @ViewInject(R.id.quantity)
    private EditText quantity;
    @ViewInject(R.id.owner)
    private EditText owner;
    @ViewInject(R.id.personOne)
    private EditText personOne;
    @ViewInject(R.id.personTwo)
    private EditText personTwo;
    @ViewInject(R.id.remark)
    private EditText remark;

    private EfList efList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_add_eflist);
        x.view().inject(this);
        efList = (EfList) getIntent().getSerializableExtra("efList");
        if (null!=efList)initData(efList);
    }

    private void initData(EfList efList) {
        name.setText(efList.getName());
        type.setText(efList.getType());
        address.setText(efList.getAddress());
        quality.setText(efList.getQuality());
        corp.setText(efList.getCorp());
        price.setText(String.valueOf(efList.getPrice()));
        quantity.setText(String.valueOf(efList.getQuantity()));
        owner.setText(efList.getOwner());
        personOne.setText(efList.getPersonOne());
        personTwo.setText(efList.getPersonTwo());
        remark.setText(efList.getRemark());
    }

    @Event(value = {R.id.close_btn,R.id.comit,R.id.cansel})
    private void clink(View view){
        switch (view.getId()){
            case R.id.close_btn:
                if(null!=efList){
                    toComit();
                }else{
                 finish();
                }
                break;
            case R.id.comit:
                toComit();
                break;
            case R.id.cansel:
                name.setText(null);
                type.setText(null);
                address.setText(null);
                quality.setText(null);
                corp.setText(null);
                price.setText(null);
                quantity.setText(null);
                owner.setText(null);
                personOne.setText(null);
                personTwo.setText(null);
                remark.setText(null);
                break;
        }
    }

    private void toComit() {
        efList = new EfList();
        efList.setName(name.getText().toString());
        efList.setType(type.getText().toString());
        efList.setAddress(address.getText().toString());
        efList.setQuality(quality.getText().toString());
        efList.setCorp(corp.getText().toString());
        efList.setPrice(Double.parseDouble(price.getText().toString()));
        efList.setQuantity(Integer.parseInt(quantity.getText().toString()));
        efList.setOwner(owner.getText().toString());
        efList.setPersonOne(personOne.getText().toString());
        efList.setPersonTwo(personTwo.getText().toString());
        efList.setRemark(remark.getText().toString());
        Intent intent = new Intent();
        intent.putExtra("efList",efList);
        setResult(RESULT_OK,intent);
        finish();
    }
}
