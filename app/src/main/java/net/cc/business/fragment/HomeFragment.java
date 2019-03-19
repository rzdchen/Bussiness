package net.cc.business.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.cc.business.R;
import net.cc.business.adapter.CourseAdapter;
import net.cc.business.contants.Constant;
import net.cc.business.fragment.base.BaseFragment;
import net.cc.business.module.recommand.BaseRecommandModel;
import net.cc.business.module.recommand.RecommandBodyValue;
import net.cc.business.network.http.RequestCenter;
import net.cc.business.view.home.HomeHeaderLayout;
import net.cc.business.zxing.app.CaptureActivity;
import net.cc.common.okhttp.listener.DisposeDataListener;

/**
 * created by CC on 2019/3/18.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final int REQUEST_QRCODE = 0x01;
    /**
     * UI
     */
    private View mContentView;
    private ListView mListView;
    private TextView mQRCodeView;
    private TextView mCategoryView;
    private TextView mSearchView;
    private ImageView mLoadingView;
    /**
     * data
     */
    private CourseAdapter mAdapter;
    private BaseRecommandModel mRecommandData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mContentView = inflater.inflate(R.layout.fragment_home_layout, container, false);
        initView();
        return mContentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestRecommandData();
    }

    private void initView() {
        mQRCodeView = mContentView.findViewById(R.id.qrcode_view);
        mQRCodeView.setOnClickListener(this);
        mCategoryView = mContentView.findViewById(R.id.category_view);
        mCategoryView.setOnClickListener(this);
        mSearchView = mContentView.findViewById(R.id.search_view);
        mSearchView.setOnClickListener(this);
        mListView = mContentView.findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);
        mLoadingView = mContentView.findViewById(R.id.loading_view);
        AnimationDrawable anim = (AnimationDrawable) mLoadingView.getDrawable();
        anim.start();
    }
    //发送推荐产品请求
    private void requestRecommandData() {
        RequestCenter.requestRecommandData(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                mRecommandData = (BaseRecommandModel) responseObj;
                //更新UI
                showSuccessView();
            }

            @Override
            public void onFailure(Object reasonObj) {
                //显示请求失败View
                showErrorView();
            }
        });
    }

    //显示请求成功UI
    private void showSuccessView() {
        //判断数据是否为空
        if (mRecommandData.data.list != null && mRecommandData.data.list.size() > 0) {
            mLoadingView.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            //为listview添加头
            mListView.addHeaderView(new HomeHeaderLayout(mContext, mRecommandData.data.head));
            //创建adapter
            mAdapter = new CourseAdapter(mContext, mRecommandData.data.list);
            mListView.setAdapter(mAdapter);
            mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    mAdapter.updateAdInScrollView();
                }
            });
        } else {
            showErrorView();
        }
    }

    private void showErrorView() {
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qrcode_view:
                if (hasPermission(Constant.HARDWEAR_CAMERA_PERMISSION)) {
                    doOpenCamera();
                } else {
                    requestPermission(Constant.HARDWEAR_CAMERA_CODE, Constant.HARDWEAR_CAMERA_PERMISSION);
                }
                break;
            case R.id.category_view:
                //与我交谈
//                Intent intent2 = new Intent(Intent.ACTION_VIEW, Util.createQQUrl("277451977"));
//                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent2);
                break;
            case R.id.search_view:
//                Intent searchIntent = new Intent(mContext, SearchActivity.class);
//                mContext.startActivity(searchIntent);
                break;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        RecommandBodyValue value = (RecommandBodyValue) mAdapter.getItem(position - mListView.getHeaderViewsCount());
//        if (value.type != 0) {
//            Intent intent = new Intent(mContext, PhotoViewActivity.class);
//            intent.putStringArrayListExtra(PhotoViewActivity.PHOTO_LIST, value.url);
//            startActivity(intent);
//        }
    }

    @Override
    public void doOpenCamera() {
        Intent intent = new Intent(mContext, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_QRCODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_QRCODE:
                if (resultCode == Activity.RESULT_OK) {
                    String code = data.getStringExtra("SCAN_RESULT");
                    if (code.contains("http") || code.contains("https")) {
//                        Intent intent = new Intent(mContext, AdBrowserActivity.class);
//                        intent.putExtra(AdBrowserActivity.KEY_URL, code);
//                        startActivity(intent);
                    } else {
                        Toast.makeText(mContext, code, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

}
