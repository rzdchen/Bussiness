package net.cc.business.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.cc.business.R;
import net.cc.business.activity.base.BaseActivity;
import net.cc.business.fragment.HomeFragment;
import net.cc.business.fragment.MessageFragment;
import net.cc.business.fragment.MineFragment;
import net.cc.business.fragment.base.BaseFragment;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private FragmentManager fm;
    private HomeFragment mHomeFragment;

    private BaseFragment mCommonFragmentOne;
    private MessageFragment mMessageFragment;
    private MineFragment mMineFragment;
    private BaseFragment mCurrent;

    private RelativeLayout mHomeLayout;
    private RelativeLayout mPondLayout;
    private RelativeLayout mMessageLayout;
    private RelativeLayout mMineLayout;
    private TextView mHomeView;
    private TextView mPondView;
    private TextView mMessageView;
    private TextView mMineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStatusBarColor(R.color.color_fed952);
        setContentView(R.layout.activity_main);
        //启动后台产品服务更新
        initView();

        mHomeFragment = new HomeFragment();
        fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.content_layout, mHomeFragment);
        fragmentTransaction.commit();

        initView();
    }

    private void initView() {
        findViewById(R.id.home_layout_view).setOnClickListener(this);
        findViewById(R.id.pond_layout_view).setOnClickListener(this);
        findViewById(R.id.message_layout_view).setOnClickListener(this);
        findViewById(R.id.mine_layout_view).setOnClickListener(this);

        mHomeView = findViewById(R.id.home_image_view);
        mPondView = findViewById(R.id.fish_image_view);
        mMessageView = findViewById(R.id.message_image_view);
        mMineView = findViewById(R.id.mine_image_view);
        mHomeView.setBackgroundResource(R.mipmap.comui_tab_home_selected);
    }
    private void hideFragment(BaseFragment fragment, FragmentTransaction ft) {
        if (fragment != null) {
            ft.hide(fragment);
        }
    }
    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.home_layout_view:
                changeStatusBarColor(R.color.color_fed952);
                mHomeView.setBackgroundResource(R.mipmap.comui_tab_home_selected);
                mPondView.setBackgroundResource(R.mipmap.comui_tab_pond);
                mMessageView.setBackgroundResource(R.mipmap.comui_tab_message);
                mMineView.setBackgroundResource(R.mipmap.comui_tab_person);

                hideFragment(mCommonFragmentOne, fragmentTransaction);
                hideFragment(mMessageFragment, fragmentTransaction);
                hideFragment(mMineFragment, fragmentTransaction);
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.content_layout, mHomeFragment);
                } else {
                    mCurrent = mHomeFragment;
                    fragmentTransaction.show(mHomeFragment);
                }
                break;
            case R.id.message_layout_view:
                changeStatusBarColor(R.color.color_e3e3e3);
                mMessageView.setBackgroundResource(R.mipmap.comui_tab_message_selected);
                mHomeView.setBackgroundResource(R.mipmap.comui_tab_home);
                mPondView.setBackgroundResource(R.mipmap.comui_tab_pond);
                mMineView.setBackgroundResource(R.mipmap.comui_tab_person);

                hideFragment(mCommonFragmentOne, fragmentTransaction);
                hideFragment(mHomeFragment, fragmentTransaction);
                hideFragment(mMineFragment, fragmentTransaction);
                if (mMessageFragment == null) {
                    mMessageFragment = new MessageFragment();
                    fragmentTransaction.add(R.id.content_layout, mMessageFragment);
                } else {
                    mCurrent = mMessageFragment;
                    fragmentTransaction.show(mMessageFragment);
                }
                break;
            case R.id.mine_layout_view:
                changeStatusBarColor(R.color.color_ffffff);
                mMineView.setBackgroundResource(R.mipmap.comui_tab_person_selected);
                mHomeView.setBackgroundResource(R.mipmap.comui_tab_home);
                mPondView.setBackgroundResource(R.mipmap.comui_tab_pond);
                mMessageView.setBackgroundResource(R.mipmap.comui_tab_message);
                hideFragment(mCommonFragmentOne, fragmentTransaction);
                hideFragment(mMessageFragment, fragmentTransaction);
                hideFragment(mHomeFragment, fragmentTransaction);
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                    fragmentTransaction.add(R.id.content_layout, mMineFragment);
                } else {
                    mCurrent = mMineFragment;
                    fragmentTransaction.show(mMineFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }
}
