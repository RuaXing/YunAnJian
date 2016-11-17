package com.quanta.aj.yunanjian.activity.gps;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.quanta.aj.yunanjian.R;
import com.quanta.aj.yunanjian.activity.base.BaseActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class GalleryTestActivity extends BaseActivity {

    private int[] imageIds = new int[]{
            R.drawable.action_aqsjfx,
            R.drawable.action_aqyj,R.drawable.action_aqzsk,R.drawable.action_close,
            R.drawable.action_gps,R.drawable.action_msdscx,R.drawable.action_wxyjg
    };
    @ViewInject(R.id.gallery)
    private Gallery gallery;
    @ViewInject(R.id.switcher)
    private ImageSwitcher switcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_test);
        x.view().inject(this);
        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(GalleryTestActivity.this);
                imageView.setBackgroundColor(0xfff0000);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ImageSwitcher.LayoutParams.WRAP_CONTENT,ImageSwitcher.LayoutParams.WRAP_CONTENT));
                return imageView;
            }
        });
        switcher.setInAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_in));
        switcher.setOutAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_out));
        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return imageIds.length;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView imageView = new ImageView(GalleryTestActivity.this);
                imageView.setImageResource(imageIds[position%imageIds.length]);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new Gallery.LayoutParams(75,100));
                TypedArray typedArray = obtainStyledAttributes(R.styleable.Gallery);
                imageView.setBackgroundResource(typedArray.getResourceId(R.styleable.Gallery_android_galleryItemBackground,0));
                return imageView;
            }
        };
        gallery.setAdapter(adapter);
        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switcher.setImageResource(imageIds[position%imageIds.length]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}
