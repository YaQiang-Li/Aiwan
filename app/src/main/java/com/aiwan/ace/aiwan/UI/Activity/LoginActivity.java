package com.aiwan.ace.aiwan.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aiwan.ace.aiwan.Config.IntentConstant;
import com.aiwan.ace.aiwan.Imservice.event.LoginEvent;
import com.aiwan.ace.aiwan.Imservice.event.SocketEvent;
import com.aiwan.ace.aiwan.Imservice.service.IMService;
import com.aiwan.ace.aiwan.UI.Base.BaseActivity;
import com.aiwan.ace.aiwan.Utils.Logger;
import com.aiwan.ace.aiwan.R;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ACE on 2016/3/9.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private  String Tag = "LoginActivity";

    private Logger logger = Logger.getLogger(LoginActivity.class);
    private Handler uiHandler = new Handler();
    private EditText mNameView;
    private EditText mPasswordView;
    private View loginPage;
    private View splashPage;
    private View mLoginStatusView;
    private TextView mLoginUserRegister;
    private InputMethodManager inputMethodManager;

    private IMService imService;
    private boolean autoLogin = true;
    private boolean loginSuccess = false;

    /**
     * 跳转到登陆的页面
     */
    private void handleNoLoginIdentity() {
        logger.i(Tag, "handleNoLoginIdentity");
        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showLoginPage();
            }
        }, 1000);
    }

    private void showLoginPage() {
        splashPage.setVisibility(View.GONE);
        loginPage.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputMethodManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        logger.i(Tag, "onCreate");

        //EventBus.getDefault().register(this);

        setContentView(R.layout.activity_login);
        mLoginUserRegister = (TextView) findViewById(R.id.sign_user_register);
        mLoginUserRegister.setOnClickListener(this);

        mNameView = (EditText) findViewById(R.id.name);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mLoginStatusView = findViewById(R.id.login_status);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        initAutoLogin();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                inputMethodManager.hideSoftInputFromWindow(mPasswordView.getWindowToken(),0);
                attemptLogin();
                break;
            case R.id.sign_user_register:
                break;
            case R.id.sign_retrieve_password:
                break;
            default:
                break;
        }
    }

    private void initAutoLogin() {
        logger.i(Tag, "initAutoLogin");

        splashPage = findViewById(R.id.splash_page);
        loginPage = findViewById(R.id.login_page);
        autoLogin = shouldAutoLogin();

        splashPage.setVisibility(autoLogin ? View.VISIBLE : View.GONE);
        loginPage.setVisibility(autoLogin ? View.GONE : View.VISIBLE);

        loginPage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mPasswordView != null) {
                    inputMethodManager.hideSoftInputFromWindow(mPasswordView.getWindowToken(), 0);
                }

                if (mNameView != null) {
                    inputMethodManager.hideSoftInputFromWindow(mNameView.getWindowToken(), 0);
                }
                return false;
            }
        });

        if (autoLogin) {
            Animation splashAnimation = AnimationUtils.loadAnimation(this, R.anim.login_splash);
            if (splashAnimation == null) {
                logger.e(Tag, "loadAnimation login_splash failed");
                return;
            }

            splashPage.startAnimation(splashAnimation);
        }
    }

    // 主动退出的时候， 这个地方会有值,更具pwd来判断
    private boolean shouldAutoLogin() {
        Intent intent = getIntent();
        if (intent != null) {
            boolean notAutoLogin = intent.getBooleanExtra(IntentConstant.KEY_LOGIN_NOT_AUTO, true);
            logger.d(Tag, "notAutoLogin:%s", notAutoLogin);
            if (notAutoLogin) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //EventBus.getDefault().unregister(this);
        splashPage = null;
        loginPage = null;
    }

    public void attemptLogin() {
        String loginName = mNameView.getText().toString();
        String mPassword = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(mPassword)) {
            Toast.makeText(this, getString(R.string.error_pwd_required), Toast.LENGTH_SHORT).show();
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel == false && TextUtils.isEmpty(loginName)) {
            Toast.makeText(this, getString(R.string.error_name_required), Toast.LENGTH_SHORT).show();
            focusView = mNameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            //连接到服务器
        }
    }

    private void showProgress(final boolean show) {
        if (show) {
            mLoginStatusView.setVisibility(View.VISIBLE);
        } else {
            mLoginStatusView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent enent) {
        return super.onKeyDown(keyCode, enent);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void onEventMainThread(LoginEvent event) {
        switch (event) {
            case LOCAL_LOGIN_SUCCESS:
            case LOGIN_OK:
                onLoginSuccess();
                break;
            case LOGIN_AUTH_FAILED:
            case LOGIN_INNER_FAILED:
                if (!loginSuccess)
                    onLoginFailure(event);
                break;
        }
    }

    public void onEventMainThread(SocketEvent event) {
        switch (event) {
            case CONNECT_MSG_SERVER_FAILED:
            case REQ_MSG_SERVER_ADDRS_FAILED:
                if (!loginSuccess)
                    onSocketFailure(event);
                break;
        }
    }

    private void onLoginSuccess() {
        logger.i(Tag, "onLoginSuccess");
        loginSuccess = true;
/*        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
*/
    }

    private void onLoginFailure(LoginEvent event) {
        logger.e(Tag, "onLoginError -> errorCode:%s", event.name());
        showLoginPage();
        //String errorTip = getString(IMUIHelper.getLoginErrorTip(event));
        //logger.d("login#errorTip:%s", errorTip);
        mLoginStatusView.setVisibility(View.GONE);
        Toast.makeText(this, "onLoginFailure", Toast.LENGTH_SHORT).show();
    }

    private void onSocketFailure(SocketEvent event) {
        logger.e(Tag, "onLoginError -> errorCode:%s,", event.name());
        showLoginPage();
        //String errorTip = getString(IMUIHelper.getSocketErrorTip(event));
        //logger.d("login#errorTip:%s", errorTip);
        mLoginStatusView.setVisibility(View.GONE);
        Toast.makeText(this, "onSocketFailure", Toast.LENGTH_SHORT).show();
    }
}
