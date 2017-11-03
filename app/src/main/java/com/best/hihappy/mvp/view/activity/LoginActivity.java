package com.best.hihappy.mvp.view.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.best.hihappy.R;
import com.best.hihappy.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by FuKaiqiang on 2017-10-22.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_mobileNum)
    TextInputEditText etMobileNum;
    @BindView(R.id.iv_cancel_ok)
    ImageView ivCancelOk;
    @BindView(R.id.view_first)
    View viewFirst;
    @BindView(R.id.ed_code)
    TextInputEditText edCode;
    @BindView(R.id.tv_sendcode)
    TextView tvSendcode;
    @BindView(R.id.view_second)
    View viewSecond;
    @BindView(R.id.bt_login)
    Button btLogin;

    private String mPhoneNum;
    private String mCode;


    @Override
    protected int getLayoutResource() {
        return R.layout.mycenter_login_layout;
    }

    @Override
    protected void initView() {

        initSMS();
        initCheck();
    }

    private void initCheck() {
        initPhoneNum();
        initCode();
    }

    private void initCode() {
        edCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mCode = edCode.getText().toString();
                if (!TextUtils.isEmpty(mCode)) {


                    if (!TextUtils.isEmpty(mPhoneNum)) {
                        btLogin.setClickable(true);
                        btLogin.setBackgroundColor(getResources().getColor(R.color.sendCode));
                        btLogin.setTextColor(getResources().getColor(R.color.white));
                    }
                } else {
                    btLogin.setBackgroundColor(getResources().getColor(R.color.colorPrimaryTab));
                    btLogin.setClickable(false);
                }
            }
        });
    }

    private void initPhoneNum() {
        etMobileNum.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPhoneNum = etMobileNum.getText().toString();
                String telRegex = "[1][358]\\d{9}";
                if (!TextUtils.isEmpty(mPhoneNum)) {
                    boolean matches = mPhoneNum.matches(telRegex);
                    if (matches) {
                        tvSendcode.setTextColor(getResources().getColor(R.color.sendCode));
                        tvSendcode.setClickable(true);
                        if (!TextUtils.isEmpty(mCode)) {
                            btLogin.setClickable(true);
                        }
                        ivCancelOk.setImageResource(R.drawable.ok);
                        ivCancelOk.setClickable(false);
                    } else {
                        tvSendcode.setTextColor(getResources().getColor(R.color.colorPrimaryTab));
                        tvSendcode.setClickable(false);
                        btLogin.setClickable(false);
                        ivCancelOk.setImageResource(R.drawable.cancel);
                        ivCancelOk.setClickable(true);
                    }
                }
            }
        });
    }

    private void initSMS() {
        // 创建EventHandler对象
        EventHandler mEventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    try {
                        JSONObject obj = new JSONObject(throwable.getMessage());
                        final String des = obj.optString("detail");
                        if (!TextUtils.isEmpty(des)) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, des, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(mEventHandler);
    }

    @OnClick({R.id.tv_sendcode, R.id.bt_login, R.id.iv_back, R.id.iv_cancel_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_cancel_ok:
                etMobileNum.setText("");
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_sendcode:
                if (!TextUtils.isEmpty(mPhoneNum)) {
                    SMSSDK.getVerificationCode("86", mPhoneNum);
                } else {
                    Toast.makeText(LoginActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_login:
                if (!TextUtils.isEmpty(mCode) && !TextUtils.isEmpty(mPhoneNum)) {
                    SMSSDK.submitVerificationCode("86", mPhoneNum, mCode);
                } else {
                    Toast.makeText(LoginActivity.this, "请输入手机号码和验证码", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}