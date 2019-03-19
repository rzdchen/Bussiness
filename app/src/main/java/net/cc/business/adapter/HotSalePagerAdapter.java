package net.cc.business.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import net.cc.business.R;
import net.cc.business.module.recommand.RecommandBodyValue;
import net.cc.common.utils.ImageLoaderManager;

import java.util.ArrayList;

/**
 * Created by renzhiqiang on 16/9/5.
 */
public class HotSalePagerAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<RecommandBodyValue> mData;
    private LayoutInflater mInflate;
    private ImageLoaderManager mImageLoader;

    public HotSalePagerAdapter(Context context, ArrayList<RecommandBodyValue> list) {
        mContext = context;
        mData = list;
        mInflate = LayoutInflater.from(mContext);
        mImageLoader = ImageLoaderManager.getInstance(mContext);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final RecommandBodyValue value = mData.get(position % mData.size());
        View rootView = mInflate.inflate(R.layout.item_hot_product_pager_layout, null);
        TextView titleView = rootView.findViewById(R.id.title_view);
        TextView infoView = rootView.findViewById(R.id.info_view);
        TextView gonggaoView = rootView.findViewById(R.id.gonggao_view);
        TextView saleView = rootView.findViewById(R.id.sale_num_view);
        ImageView[] imageViews = new ImageView[3];
        imageViews[0] = rootView.findViewById(R.id.image_one);
        imageViews[1] = rootView.findViewById(R.id.image_two);
        imageViews[2] = rootView.findViewById(R.id.image_three);
        rootView.setOnClickListener(v -> {
//                Intent intent = new Intent(mContext, CourseDetailActivity.class);
//                intent.putExtra(CourseDetailActivity.COURSE_ID, value.adid);
//                mContext.startActivity(intent);
        });

        titleView.setText(value.title);
        infoView.setText(value.price);
        gonggaoView.setText(value.info);
        saleView.setText(value.text);
        for (int i = 0; i < imageViews.length; i++) {
            mImageLoader.displayImage(imageViews[i], value.url.get(i));
        }
        container.addView(rootView, 0);
        return rootView;
    }
}
