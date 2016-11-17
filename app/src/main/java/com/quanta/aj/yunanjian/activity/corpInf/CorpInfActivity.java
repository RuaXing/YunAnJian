package com.quanta.aj.yunanjian.activity.corpInf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.BaseActivity;
import com.quanta.aj.yunanjian.activity.enforcement.CaseAddActivity;
import com.quanta.aj.yunanjian.activity.enforcement.InspectInfoNewActivity;
import com.quanta.aj.yunanjian.adapter.MyAdapter;
import com.quanta.aj.yunanjian.constant.MCode;
import com.quanta.aj.yunanjian.dao.EfCaseInfoDAO;
import com.quanta.aj.yunanjian.mywegit.Title_Big_Layout;
import com.quanta.aj.yunanjian.orm.Actionmenu;
import com.quanta.aj.yunanjian.orm.CorpInfo;
import com.quanta.aj.yunanjian.orm.KeyValue;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

public class CorpInfActivity extends BaseActivity {
    private static final String TAG = "CorpInfActivity";

    /**
     * 参数传递
     * @param ctx
     * @param corpInfo 企业信息
     * @return
     */
    public static Intent getIntent(Context ctx, CorpInfo corpInfo,String who) {
        Intent intent = new Intent(ctx, CorpInfActivity.class);
        intent.putExtra("corpInfo", corpInfo);
        intent.putExtra("who", who);
        return intent;
    }


    @ViewInject(R.id.corpinftt)//标题栏
    private Title_Big_Layout corpinftt;
    @ViewInject(R.id.corpinf)//信息数据
    private ListView corpinf;
    @ViewInject(R.id.menus)//按钮栏
    private GridView menus;

    private int navId;
    private EfCaseInfoDAO caseDao;
    //企业信息
    private CorpInfo item;
    //企业id
    //private String id;
    private Context mContext;
    private BaseAdapter mAdapter = null;
    private BaseAdapter mMenuAdapter = null;
    private ArrayList<KeyValue> mData = null;//企业信息数据组
    private ArrayList<Actionmenu> mMenu = null;//按钮
    private String who;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corp_inf);
        x.view().inject(this);
        mContext = CorpInfActivity.this;
        who = getIntent().getStringExtra("who");
        if (null!=who){
            menus.setNumColumns(2);
        }
        //corpinftt.setTextColor(R.color.hei);
        corpinftt.setTitle(getString(R.string.company_info));
        corpinftt.setTextSize(20);
        //获取前一个activity传递的参数
        item = (CorpInfo)getIntent().getSerializableExtra("corpInfo");
        setCorpInfo();
        setMenu(who);
    }

    private void setMenu(final String who) {
        mMenu = new ArrayList<Actionmenu>();
        if (null!=who){
            mMenu.add(new Actionmenu(R.drawable.comit_tj,getString(R.string.confirm)));
            mMenu.add(new Actionmenu(R.drawable.arrowleft_icon,getString(R.string.back)));
        }else {
            mMenu.add(new Actionmenu(R.drawable.action_ydzfgl,getString(R.string.ins)));
            mMenu.add(new Actionmenu(R.drawable.action_xzzf,getString(R.string.inf_case)));
            mMenu.add(new Actionmenu(R.drawable.arrowleft_icon,getString(R.string.back)));
        }
        mMenuAdapter = new MyAdapter<Actionmenu>(mMenu,R.layout.item_corpinf_menus) {
            @Override
            public void bindView(ViewHolder holder, Actionmenu obj) {
                holder.setImageResource(R.id.menuimg,obj.getiId());
                holder.setText(R.id.menuname,obj.getActionName());
            }
        };
        menus.setAdapter(mMenuAdapter);
        menus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0://安全检查
                        if (null!=who&&who.equals("EFC")){
                            startActivity(CaseAddActivity.getIntent(mContext,item));
                        }else if (null!=who&&who.equals("INS")){
                            startActivity(InspectInfoNewActivity.getIntent(mContext,item,true));
                        }else {
                            startActivity(InspectInfoNewActivity.getIntent(mContext,item,true));
                        }
                        break;
                    case 1://行政处罚
                        if (null!=who){
                            ((Activity)mContext).finish();
                        }else {
                            startActivity(CaseAddActivity.getIntent(mContext,item));
                        }
                        break;
                    case 2://返回
                        ((Activity)mContext).finish();
                        break;
                    default:break;
                }
            }
        });
    }

    Handler codeHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                   Object codeObject=msg.obj;
                            if (codeObject!=null&&mData!=null&&mAdapter!=null){
                                String qygm= (String) codeObject;
                                mData.add(new KeyValue("企业规模",qygm));
                                mAdapter.notifyDataSetChanged();
                            }
                    break;
                    case 1:
                        Object codeObject1=msg.obj;
                        if (codeObject1!=null&&mData!=null&&mAdapter!=null){
                            String dwlx= (String) codeObject1;
                            mData.add(new KeyValue("单位类型",dwlx));
                            mAdapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
    };
    private void setCorpInfo() {

        if (corpinf == null) {
            Toast.makeText(this, "未找到提供的参数", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if(item==null || item.getQymc()==null){
            Toast.makeText(this, "选择的企业信息不存在", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        MCode mCode = new MCode();
        mData = new ArrayList<KeyValue>();
        mData.add(new KeyValue("企业名称",item.getQymc()));
        mData.add(new KeyValue("工商注册号",item.getNo()));
        mData.add(new KeyValue("法人代表",item.getFrdb()));
        mData.add(new KeyValue("法人代表联系电话",item.getFrdblxdh()));
        mData.add(new KeyValue("单位地址",item.getZcdz()));
        mData.add(new KeyValue("注册资金",item.getZczj()+"万"));
        mData.add(new KeyValue("企业人数",(null!=item.getZgrs()?item.getZgrs():0)+"人"));
        mData.add(new KeyValue("安全负责人",item.getZyfzr()));
        mData.add(new KeyValue("安全负责人联系电话",item.getZyfzrlxdh()));

        mAdapter = new MyAdapter<KeyValue>(mData,R.layout.item_corpinf) {

            @Override
            public void bindView(ViewHolder holder, KeyValue obj) {
                holder.setText(R.id.name,obj.getKey());
                holder.setText(R.id.value,obj.getValue());
            }
        };
        mCode.getCode("QYGM",Integer.parseInt(item.getQygm()),0,codeHandler,mContext);
        mCode.getCode("DWLX",Integer.parseInt(item.getDwlx()),1,codeHandler,mContext);
        corpinf.setAdapter(mAdapter);
    }
}
