package com.quanta.aj.yunanjian.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by 罗鑫 on 2016/8/15.
 * 可复用的适配器
 */
public abstract class MyListAdapter<T> extends BaseAdapter {
    private List<T> mData;         //数据List类型参数
    private int mLayoutRes;           //布局id

    public MyListAdapter() {
    }

    public MyListAdapter(List<T> mData, int mLayoutRes) {
        this.mData = mData;
        this.mLayoutRes = mLayoutRes;
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.bind(parent.getContext(), convertView, parent, mLayoutRes
                , position);
        bindView(holder, getItem(position));
        return holder.getItemView();
    }

    public abstract void bindView(ViewHolder holder, T obj);

    //添加一个元素
    public void add(T data) {
        if (mData == null) {
            mData = new List<T>() {
                @Override
                public void add(int location, T object) {

                }

                @Override
                public boolean add(T object) {
                    return false;
                }

                @Override
                public boolean addAll(int location, Collection<? extends T> collection) {
                    return false;
                }

                @Override
                public boolean addAll(Collection<? extends T> collection) {
                    return false;
                }

                @Override
                public void clear() {

                }

                @Override
                public boolean contains(Object object) {
                    return false;
                }

                @Override
                public boolean containsAll(Collection<?> collection) {
                    return false;
                }

                @Override
                public T get(int location) {
                    return null;
                }

                @Override
                public int indexOf(Object object) {
                    return 0;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @NonNull
                @Override
                public Iterator<T> iterator() {
                    return null;
                }

                @Override
                public int lastIndexOf(Object object) {
                    return 0;
                }

                @Override
                public ListIterator<T> listIterator() {
                    return null;
                }

                @NonNull
                @Override
                public ListIterator<T> listIterator(int location) {
                    return null;
                }

                @Override
                public T remove(int location) {
                    return null;
                }

                @Override
                public boolean remove(Object object) {
                    return false;
                }

                @Override
                public boolean removeAll(Collection<?> collection) {
                    return false;
                }

                @Override
                public boolean retainAll(Collection<?> collection) {
                    return false;
                }

                @Override
                public T set(int location, T object) {
                    return null;
                }

                @Override
                public int size() {
                    return 0;
                }

                @NonNull
                @Override
                public List<T> subList(int start, int end) {
                    return null;
                }

                @NonNull
                @Override
                public Object[] toArray() {
                    return new Object[0];
                }

                @NonNull
                @Override
                public <T1> T1[] toArray(T1[] array) {
                    return null;
                }
            };
        }
        mData.add(data);
        notifyDataSetChanged();
    }

    //往特定位置，添加一个元素
    public void add(int position, T data) {
        if (mData == null) {
            mData = new List<T>() {
                @Override
                public void add(int location, T object) {

                }

                @Override
                public boolean add(T object) {
                    return false;
                }

                @Override
                public boolean addAll(int location, Collection<? extends T> collection) {
                    return false;
                }

                @Override
                public boolean addAll(Collection<? extends T> collection) {
                    return false;
                }

                @Override
                public void clear() {

                }

                @Override
                public boolean contains(Object object) {
                    return false;
                }

                @Override
                public boolean containsAll(Collection<?> collection) {
                    return false;
                }

                @Override
                public T get(int location) {
                    return null;
                }

                @Override
                public int indexOf(Object object) {
                    return 0;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @NonNull
                @Override
                public Iterator<T> iterator() {
                    return null;
                }

                @Override
                public int lastIndexOf(Object object) {
                    return 0;
                }

                @Override
                public ListIterator<T> listIterator() {
                    return null;
                }

                @NonNull
                @Override
                public ListIterator<T> listIterator(int location) {
                    return null;
                }

                @Override
                public T remove(int location) {
                    return null;
                }

                @Override
                public boolean remove(Object object) {
                    return false;
                }

                @Override
                public boolean removeAll(Collection<?> collection) {
                    return false;
                }

                @Override
                public boolean retainAll(Collection<?> collection) {
                    return false;
                }

                @Override
                public T set(int location, T object) {
                    return null;
                }

                @Override
                public int size() {
                    return 0;
                }

                @NonNull
                @Override
                public List<T> subList(int start, int end) {
                    return null;
                }

                @NonNull
                @Override
                public Object[] toArray() {
                    return new Object[0];
                }

                @NonNull
                @Override
                public <T1> T1[] toArray(T1[] array) {
                    return null;
                }
            };
        }
        mData.add(position, data);
        notifyDataSetChanged();
    }
//删除一个元素
    public void remove(T data) {
        if (mData != null) {
            mData.remove(data);
        }
        notifyDataSetChanged();
    }
//删除具体某个元素
    public void remove(int position) {
        if (mData != null) {
            mData.remove(position);
        }
        notifyDataSetChanged();
    }
//清空列表元素
    public void clear() {
        if (mData != null) {
            mData.clear();
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder {

        private SparseArray<View> mViews;   //存储ListView 的 item中的View
        private View item;                  //存放convertView
        private int position;               //游标
        private Context context;            //Context上下文

        //构造方法，完成相关初始化
        private ViewHolder(Context context, ViewGroup parent, int layoutRes) {
            mViews = new SparseArray<>();
            this.context = context;
            View convertView = LayoutInflater.from(context).inflate(layoutRes, parent, false);
            convertView.setTag(this);
            item = convertView;
        }

        //绑定ViewHolder与item
        public static ViewHolder bind(Context context, View convertView, ViewGroup parent,
                                      int layoutRes, int position) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder(context, parent, layoutRes);
            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.item = convertView;
            }
            holder.position = position;
            return holder;
        }

        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int id) {
            T t = (T) mViews.get(id);
            if (t == null) {
                t = (T) item.findViewById(id);
                mViews.put(id, t);
            }
            return t;
        }


        /**
         * 获取当前条目
         */
        public View getItemView() {
            return item;
        }

        /**
         * 获取条目位置
         */
        public int getItemPosition() {
            return position;
        }

        /**
         * 设置文字
         */
        public ViewHolder setText(int id, CharSequence text) {
            View view = getView(id);
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
            return this;
        }
        /**
         * 设置文字颜色
         */
        public ViewHolder setTextColor(int id, int color) {
            View view = getView(id);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(color);
            }
            return this;
        }
        /**
         * 设置图片
         */
        public ViewHolder setImageResource(int id, int drawableRes) {
            View view = getView(id);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(drawableRes);
            } else {
                view.setBackgroundResource(drawableRes);
            }
            return this;
        }


        /**
         * 设置点击监听
         */
        public ViewHolder setOnClickListener(int id, View.OnClickListener listener) {
            getView(id).setOnClickListener(listener);
            return this;
        }

        /**
         * 设置可见
         */
        public ViewHolder setVisibility(int id, int visible) {
            getView(id).setVisibility(visible);
            return this;
        }

        /**
         * 设置标签
         */
        public ViewHolder setTag(int id, Object obj) {
            getView(id).setTag(obj);
            return this;
        }

        /**
         *
         * @param id 控件id
         * @param bgid 背景颜色
         * @param drawable 背景图片
         * @return
         */
        public ViewHolder setBackground(int id, int bgid, Drawable drawable){
            if (null != drawable)getView(id).setBackground(drawable);
            if (bgid != 0)getView(id).setBackgroundColor(bgid);
            return this;
        }
        //其他方法可自行扩展

    }

}
