package com.quanta.aj.yunanjian.activity.gps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.quanta.aj.yunanjian.R;

/**
 * 此demo用来展示如何结合定位SDK实现定位，并使用MyLocationOverlay绘制定位位置 同时展示如何使用自定义图标绘制并点击时弹出泡泡
 */

public class GpsActivity extends Activity implements OnGetGeoCoderResultListener,OnGetPoiSearchResultListener, OnGetBusLineSearchResultListener,
        BaiduMap.OnMapClickListener{

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private Context mContext;
    GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
    MapView mMapView;
    BaiduMap mBaiduMap;
    String str1;
    String str2;
    // UI相关
    Button requestLocButton;
    boolean isFirstLoc = true; // 是否首次定位
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        mContext = GpsActivity.this;
        requestLocButton = (Button) findViewById(R.id.button1);
        mCurrentMode = LocationMode.NORMAL;
        requestLocButton.setText("普通");
        View.OnClickListener btnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (mCurrentMode) {
                    case NORMAL:
                        requestLocButton.setText("跟随");
                        mCurrentMode = LocationMode.FOLLOWING;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        break;
                    case COMPASS:
                        requestLocButton.setText("普通");
                        mCurrentMode = LocationMode.NORMAL;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        break;
                    case FOLLOWING:
                        requestLocButton.setText("罗盘");
                        mCurrentMode = LocationMode.COMPASS;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        break;
                    default:
                        break;
                }
            }
        };
        requestLocButton.setOnClickListener(btnClickListener);
        init();
        //搜索的处理
        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(mContext,SearchActivity.class),100);
            }
        });

        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
        findViewById(R.id.mypoint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBaiduMap.setMyLocationEnabled(true);//点击我的位置打开定位图层
                requestLocButton.setText("跟随");
                mCurrentMode = LocationMode.FOLLOWING;
                mBaiduMap
                        .setMyLocationConfigeration(new MyLocationConfiguration(
                                mCurrentMode, true, mCurrentMarker));
            }
        });
    }

    private void init() {
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmapview);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(getApplicationContext());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        option.setNeedDeviceDirect(true);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR){
            try {
                LatLng ptCenter = new LatLng((Float.valueOf(str1)),(Float.valueOf(str2)));
                //反Geo搜索
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ptCenter));
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(mContext,"抱歉，未能找到结果", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        mBaiduMap.setMyLocationEnabled(false);//关闭定位图层
        mBaiduMap.clear();
        mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation()).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marka)));
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result.getLocation()));
        String strInfo = String.format("纬度: %f 经度: %f",result.getLocation().latitude,result.getLocation().longitude);
        Toast.makeText(mContext,strInfo, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR){
            Toast.makeText(mContext,"抱歉，未能找到结果", Toast.LENGTH_SHORT).show();
            return;
        }
        mBaiduMap.setMyLocationEnabled(false);//关闭定位图层
        mBaiduMap.clear();
        mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation()).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marka)));
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result.getLocation()));
        Toast.makeText(mContext,result.getAddress(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetBusLineResult(BusLineResult busLineResult) {

    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }

    /**
     * 定位SDK监听函数
     */

    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        mSearch.destroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 100:
                if (resultCode == RESULT_OK){
                    str1 = data.getStringExtra("str1");
                    str2 = data.getStringExtra("str2");
                    toSearch();
                }
                break;
            default:break;
        }
    }

    private void toSearch() {
        mSearch.geocode(new GeoCodeOption().city(str1).address(str2));
    }
}

