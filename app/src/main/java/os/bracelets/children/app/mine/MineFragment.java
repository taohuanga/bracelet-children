package os.bracelets.children.app.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

import aio.health2world.glide_transformations.CropCircleTransformation;
import aio.health2world.http.HttpResult;
import aio.health2world.utils.SPUtils;
import os.bracelets.children.AppConfig;
import os.bracelets.children.MyApplication;
import os.bracelets.children.R;
import os.bracelets.children.app.about.AboutActivity;
import os.bracelets.children.app.about.FeedBackActivity;
import os.bracelets.children.app.account.LoginActivity;
import os.bracelets.children.app.personal.PersonalMsgActivity;
import os.bracelets.children.app.setting.UpdatePwdActivity;
import os.bracelets.children.common.BaseFragment;
import os.bracelets.children.http.ApiRequest;
import os.bracelets.children.http.HttpSubscriber;

/**
 * Created by lishiyou on 2019/3/20.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class MineFragment extends BaseFragment {

    private ImageView ivImage;

    private TextView tvName;

    private Button btnLogout;

    private View layoutUpdatePwd, layoutUpdateMsg, layoutFeedBack,layoutAbout;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        ivImage = findView(R.id.ivImage);
        tvName = findView(R.id.tvName);
        btnLogout = findView(R.id.btnLogout);
        layoutUpdatePwd = findView(R.id.layoutUpdatePwd);
        layoutUpdateMsg = findView(R.id.layoutUpdateMsg);
        layoutFeedBack = findView(R.id.layoutFeedBack);
        layoutAbout = findView(R.id.layoutAbout);
        getUserInfo();
    }

    @Override
    public void onResume() {
        super.onResume();

//        String nickName = (String) SPUtils.get(getActivity(), AppConfig.USER_NICK, "");
//        String userImage = (String) SPUtils.get(getActivity(), AppConfig.USER_IMG, "");
//        tvName.setText(nickName);
//        Glide.with(this)
//                .load(userImage)
//                .placeholder(R.mipmap.ic_default_portrait)
//                .error(R.mipmap.ic_default_portrait)
//                .bitmapTransform(new CropCircleTransformation(mContext))
//                .into(ivImage);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        setOnClickListener(btnLogout);
        setOnClickListener(layoutUpdatePwd);
        setOnClickListener(layoutUpdateMsg);
        setOnClickListener(layoutFeedBack);
        setOnClickListener(layoutAbout);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btnLogout:
                logout();
                break;
            case R.id.layoutUpdatePwd:
                startActivity(new Intent(getActivity(), UpdatePwdActivity.class));
                break;
            case R.id.layoutUpdateMsg:
                startActivity(new Intent(getActivity(), PersonalMsgActivity.class));
                break;
            case R.id.layoutFeedBack:
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;
            case R.id.layoutAbout:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
        }
    }

    private void logout() {
        new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setMessage("确认注销登录吗？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SPUtils.put(MyApplication.getInstance(), AppConfig.IS_LOGIN, false);
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
                })
                .create()
                .show();
    }

    private void getUserInfo(){
        ApiRequest.userInfo(new HttpSubscriber() {
            @Override
            public void onNext(HttpResult result) {
                super.onNext(result);
            }
        });
    }
}
