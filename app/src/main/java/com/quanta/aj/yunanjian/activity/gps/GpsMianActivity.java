package com.quanta.aj.yunanjian.activity.gps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

public class GpsMianActivity extends BaseActivity {
    private static final String TAG = "GpsMianActivity";
    public static final int SHOW_LOCATION = 0;
    @ViewInject(R.id.gps_position)
    private TextView gps_position;

    private LocationManager locationManager;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_mian);
        x.view().inject(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //获取所以可用的位置信息
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = locationManager.GPS_PROVIDER;
        } else if (providerList.contains(locationManager.NETWORK_PROVIDER)) {
            provider = locationManager.NETWORK_PROVIDER;
        } else {
            //当前没有可用的位置信息时，提示用户
            showShortToast(3, "没有可用的位置信息");
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            showLocation(location);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            //关闭程序时移除监听器
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(locationListener);
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void showLocation(final Location location) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&sensor=false";
                String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng="+location.getLatitude()+","+location.getLongitude()+"&sensor=false";
                RequestParams params = new RequestParams(url);
                params.addHeader("Accept-Language","zh-CN");
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            JSONArray resultArray = object.getJSONArray("results");
                            if (resultArray.length()>0){
                                JSONObject jsonObject = resultArray.getJSONObject(0);
                                String address = jsonObject.getString("formatted_address");
                                Message message = new Message();
                                message.what = SHOW_LOCATION;
                                message.obj = address;
                                handler.sendMessage(message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable throwable, boolean b) {
                        Log.e(TAG,"连接失败",throwable);
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
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_LOCATION:
                    String currentPosition = msg.obj.toString();
                    gps_position.setText(currentPosition);
                    break;
                default:break;
            }
        }
    };
}
