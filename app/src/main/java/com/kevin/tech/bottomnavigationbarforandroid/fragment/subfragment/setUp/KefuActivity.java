package com.kevin.tech.bottomnavigationbarforandroid.fragment.subfragment.setUp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kevin.tech.bottomnavigationbarforandroid.R;

public class KefuActivity extends AppCompatActivity {
    private WebView webView;
    static String url_s = "https://kefu.qycn.com/vclient/chat/?m=m&websiteid=79979&visitorid=853796491&opctwdTime=1482981674&sessionid=visitor-1482981668&originPageLocationUrl=https%3A%2F%2Fwww.china-statestreet.com%2F&addCloseBtn=1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kefu);
        webView = (WebView) findViewById(R.id.kefu_web);
        openWebView();
    }
    public void openWebView(){
        webView.setWebViewClient(new WebViewClient() {
            //设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);  //设置WebView属性,运行执行js脚本
        webView.loadUrl(url_s);
        //title
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });

    }
    
}
