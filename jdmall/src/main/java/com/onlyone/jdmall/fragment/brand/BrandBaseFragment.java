package com.onlyone.jdmall.fragment.brand;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlyone.jdmall.R;
import com.onlyone.jdmall.bean.BrandBean;
import com.onlyone.jdmall.constance.Url;
import com.onlyone.jdmall.utils.ResUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目名: JDMall
 * @包名: com.onlyone.jdmall.fragment.brand
 * @创建者: Administrator
 * @创建时间: 2016/3/6 15:57
 * @描述: ${TODO}
 */
public class BrandBaseFragment extends Fragment {

    private  List<BrandBean.BrandList.BrandValue> mData;

    public BrandBaseFragment(List<BrandBean.BrandList.BrandValue> mBaseData) {
        mData = new ArrayList<>();
        mData = mBaseData;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = View.inflate(ResUtil.getContext(), R.layout.brand_item_fragment, null);
        GridView gridView = (GridView) view.findViewById(R.id.brand_gridview);
        gridView.setAdapter(new GridAdapter());

        return view;
    }

    private class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mData != null) {
                return mData.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mData != null) {
                return mData.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(ResUtil.getContext(), R.layout.item_brand_gridview, null);
                holder = new ViewHolder();
                convertView.setTag(holder);
                holder.image = (ImageView) convertView.findViewById(R.id.brand_gridview_image);
                holder.text = (TextView) convertView.findViewById(R.id.brand_gridview_text);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String imageUrl = Url.ADDRESS_SERVER + mData.get(position).getPic();
            Picasso.with(ResUtil.getContext()).load(imageUrl).into(holder.image);
            holder.text.setText(mData.get(position).getName());
            return convertView;
        }

        class ViewHolder {
            ImageView image;
            TextView  text;
        }
    }
}
