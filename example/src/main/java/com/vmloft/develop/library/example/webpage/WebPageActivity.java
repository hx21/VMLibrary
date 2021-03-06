package com.vmloft.develop.library.example.webpage;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.vmloft.develop.library.example.R;
import com.vmloft.develop.library.tools.VMActivity;
import com.vmloft.develop.library.tools.utils.VMLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lzan13 on 2018/4/28.
 * WebView 测试界面
 */
public class WebPageActivity extends VMActivity {

    @BindView(R.id.btn_call_js) Button callJSBtn;
    @BindView(R.id.layout_web_container) LinearLayout webContainer;

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_page);

        ButterKnife.bind(activity);

        init();
    }

    private void init() {
        webView = new WebView(activity);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(viewClient);
        webView.setWebChromeClient(chromeClient);

        webContainer.addView(webView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        webView.loadUrl("file:///android_asset/index.html");

    }

    @OnClick({R.id.btn_call_js, R.id.btn_call_js_params})
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.btn_call_js:
            javaCallJs("javaCallJs");
            break;
        case R.id.btn_call_js_params:
            javaCallJs("javaCallJsArgs", "Hi Title 2\n======\n\n 你好啊2");
            break;
        }
    }

    /**
     * java 调用 js 无参方法
     *
     * @param jsMethod js 方法名
     */
    private void javaCallJs(String jsMethod) {
        String callJavascript = "javascript:" + jsMethod + "()";
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        //            webView.evaluateJavascript(callJavascript, new ValueCallback<String>() {
        //                @Override
        //                public void onReceiveValue(String value) {
        //                    VMLog.d("Java call js value:%s", value);
        //                }
        //            });
        //        } else {
        webView.loadUrl(callJavascript);
        //        }
    }

    /**
     * java 调用 js 带有参数的方法
     *
     * @param jsMethod js 方法名
     * @param params js 需要的参数
     */
    private void javaCallJs(String jsMethod, String... params) {
        StringBuilder totalParams = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            if (i == params.length - 1) {
                totalParams.append(params[i]);
            } else {
                totalParams.append(params[i] + "','");
            }
        }
        String callJavascript = "javascript:" + jsMethod + "('" + totalParams.toString() + "')";
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        //            webView.evaluateJavascript(callJavascript, new ValueCallback<String>() {
        //                @Override
        //                public void onReceiveValue(String value) {
        //                    VMLog.d("Java call js value:%s", value);
        //                }
        //            });
        //        } else {
        webView.loadUrl(callJavascript);
        //        }
    }


    private WebViewClient viewClient = new WebViewClient() {

        @Override
        public void onPageFinished(WebView view, String url) {
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request,
                WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
    };


    private WebChromeClient chromeClient = new WebChromeClient() {

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }
    };
}
