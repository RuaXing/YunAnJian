package com.quanta.aj.yunanjian.activity.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.adapter.MyListAdapter;
import com.quanta.aj.yunanjian.constant.Constant;
import com.quanta.aj.yunanjian.constant.URLs;
import com.quanta.aj.yunanjian.page.AppUserPage;
import com.quanta.aj.yunanjian.page.UserPageItem;
import com.quanta.aj.yunanjian.util.JsonUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class SelectUserActivity extends BaseActivity {
    private static final String TAG = "SelectUserActivity";

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    initData();
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        }
    };

    @ViewInject(R.id.name)
    private EditText name;
    @ViewInject(R.id.mobile)
    private EditText mobile;
    @ViewInject(R.id.deptName)
    private EditText deptName;
    @ViewInject(R.id.userlist)
    private ListView uerlist;

    private List<UserPageItem> appusers;
    private MyListAdapter mlistAdapter;
    private HashMap<Integer,UserPageItem> map = new HashMap<>();
    private List<UserPageItem> users = new LinkedList<>();
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
        x.view().inject(this);
        mContext = SelectUserActivity.this;
        getUsers(null,null,null);
    }

    private void getUsers(final String name, final String mobile, final String deptName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestParams params = new RequestParams(Constant.getHOST(mContext)+URLs.GETUSERS_URL);
                params.addBodyParameter("name",name);
                params.addBodyParameter("mobile",mobile);
                params.addBodyParameter("deptName",deptName);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        AppUserPage page = JsonUtils.parseObject(s,AppUserPage.class);
                        appusers = page.getRows();
                        handler.sendEmptyMessage(1);
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Log.e(TAG,"请求出错！",throwable);
                    }

                    @Override
                    public void onCancelled(CancelledException e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        }).start();
    }
    private void initData() {
        mlistAdapter = new MyListAdapter<UserPageItem>(appusers,R.layout.item_userinfo) {
            @Override
            public void bindView(final ViewHolder holder, final UserPageItem obj) {
                holder.setText(R.id.name,obj.getName());
                holder.setText(R.id.mobile,obj.getMobile());
                holder.setText(R.id.deptName,obj.getDeptName());
                holder.setOnClickListener(R.id.check, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox box = (CheckBox)v;
                        if (box.isChecked()){
                            map.put(holder.getItemPosition(),obj);
                        }else if (!box.isChecked()){
                            if (null != map.get(holder.getItemPosition())){
                                map.remove(holder.getItemPosition());
                            }
                        }
                    }
                });
            }
        };
        uerlist.setAdapter(mlistAdapter);
    }
    @Event(value = {R.id.search,R.id.comit,R.id.cancel})
    private void clink(View v){
        switch (v.getId()){
            case R.id.search:
                getUsers(name.getText().toString(),mobile.getText().toString(),deptName.getText().toString());
                break;
            case R.id.comit:
                for (int i : map.keySet()){
                    users.add(map.get(i));
                }
                Intent intent = new Intent();
                intent.putExtra("users",JsonUtils.toString(users));
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.cancel:
                finish();
                break;
        }
    }
}
