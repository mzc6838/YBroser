package com.mzc6838.ybrowser;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.SpannableString;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

/**
 * Created by mzc6838 on 2018/4/2.
 */

public class WebViewFragment extends BackHandledFragment {

    private WebView webview;
    private WebSettings webSettings;
    private SharedPreferences sharedPreferences;
    private ContentLoadingProgressBar progressBar;
    private MainActivity mainActivity;
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastRec broadcastRec;
    private View view;
    private long exitTime = 0;
    private Bitmap pageIcon;
    private Bundle webViewState;

    public static String PRELOADURL = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.main_webview_fragment, container, false);

        init(savedInstanceState);

        return view;
    }

    public void init(@Nullable Bundle savedInstanceState) {

        mainActivity = (MainActivity) getActivity();

        webview = (WebView) view.findViewById(R.id.main_webview);
        progressBar = (ContentLoadingProgressBar) view.findViewById(R.id.progressBar);
        localBroadcastManager = LocalBroadcastManager.getInstance(mainActivity);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mainActivity);

        progressBar.bringToFront();
        progressBar.setMax(100);

        webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(sharedPreferences.getBoolean("allow_javascript", false));
        webSettings.setAppCacheEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);

        switch (sharedPreferences.getString("change_UA", "")) {
            case ("Android"): {
                webSettings.setUserAgent("Mozilla/5.0 (Linux; Android " + Build.VERSION.RELEASE + "; " + Build.MODEL + ") AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.109 Mobile Safari/537.36");
                break;
            }
            case ("iPhone"): {
                webSettings.setUserAgent("Mozilla/5.0 AppleWebKit/604.4.7 (KHTML, like Gecko) Version/11.0 Mobile/15C202 Safari/604.1");
                break;
            }
            case ("PC"): {
                webSettings.setUserAgent("Mozilla/5.0 AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
                break;
            }
            default:
                break;
        }
        webview.addJavascriptInterface(new WebViewFragment.InJavaScriptLocalObj(), "java_obj");
        webview.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
            @Override
            public void onReceivedSslError(com.tencent.smtt.sdk.WebView webView,
                                           com.tencent.smtt.export.external.interfaces.SslErrorHandler sslErrorHandler,
                                           com.tencent.smtt.export.external.interfaces.SslError sslError) {
                sslErrorHandler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                //return super.shouldOverrideUrlLoading(webView, s);
                if (!url.startsWith("http") && !url.startsWith("ftp://")) {
                    if (sharedPreferences.getBoolean("allow_outWindow", false)) {
                        try {
                            final Intent intent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(url));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    } else {
                        Toast.makeText(mainActivity, "已禁止打开外部应用，可在设置中允许", Toast.LENGTH_SHORT).show();
                        return true;
                    }

                }
                return false;
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {

                super.onPageStarted(webView, s, bitmap);
            }

            @Override
            public void onPageFinished(WebView web, String s) {

                web.loadUrl("javascript:window.java_obj.setColor("
                        + "new Function(\"var t = document.querySelector('meta[name=\\\"theme-color\\\"]');if(t == null){ return \\\"null\\\";}else{return t.getAttribute('content');}\")()"
                        + ");");

                mainActivity.pageLink = web.getUrl();
                mainActivity.pageTitle = web.getTitle();
//                ((EditText) view.findViewById(R.id.edit_box)).setHint(mainActivity.pageTitle);
                ((EditText)mainActivity.findViewById(R.id.edit_box)).setHint(mainActivity.pageTitle);

                //mainActivity.addWindowInfo(web.getTitle(), web.getUrl(), pageIcon);

                mainActivity.setWindowInfo(mainActivity.pageTitle, mainActivity.pageLink, pageIcon, mainActivity.getPositionNow());

                super.onPageFinished(webview, s);
            }
        });
        webview.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient() {
            @Override
            public void onReceivedTitle(com.tencent.smtt.sdk.WebView webView, String title) {
                mainActivity.setEdit_urlText("");
                mainActivity.setEdit_urlHint(title);
                if(isAdded())
                    mainActivity.setQRButtonImageDrawable(getResources().getDrawable(R.drawable.scanning));
                mainActivity.pageTitle = title;
                mainActivity.pageLink = webView.getUrl();
            }

            @Override
            public void onProgressChanged(WebView webView, int i) {
                if (i == 100) {
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(i);
                }
            }

            @Override
            public void onReceivedIcon(WebView webView, Bitmap bitmap) {
                pageIcon = bitmap;
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();

                int newWH = dp2px(35);

                float scaleWidth = ((float)newWH) / width;
                float scaleHeight = ((float)newWH) / height;

                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);

                mainActivity.setWindowInfo(getPageTitle(), getPageUrl(), Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true), mainActivity.getPositionNow());
                super.onReceivedIcon(webView, bitmap);
            }
        });
        webview.canGoBack();
        if(PRELOADURL.isEmpty())
            webview.loadUrl(sharedPreferences.getString("change_first_page", "http://www.baidu.com"));
        else {
            webview.loadUrl(PRELOADURL);
            PRELOADURL = "";
        }
        webview.setOnScrollChangeListener(new com.tencent.smtt.sdk.WebView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY - oldScrollY > 20) {
                    mainActivity.setFABHide();
                } else if (scrollY - oldScrollY < -20) {
                    mainActivity.setFABShow();
                }
            }
        });

        broadcastRec = new BroadcastRec();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.mzc6838.ybrowser.setting.SET_JAVASCRIPT_ENABLED");
        intentFilter.addAction("com.mzc6838.ybrowser.setting.SET_JAVASCRIPT_DISABLED");
        intentFilter.addAction("com.mzc6838.ybrowser.SET_UA_ANDROID");
        intentFilter.addAction("com.mzc6838.ybrowser.SET_UA_IPHONE");
        intentFilter.addAction("com.mzc6838.ybrowser.SET_UA_PC");
        localBroadcastManager.registerReceiver(broadcastRec, intentFilter);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public boolean onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
            return true;
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(mainActivity, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
                return true;
            } else {
                mainActivity.finish();
                ((NotificationManager) mainActivity.getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
                System.exit(0);
                return false;
            }
        }
    }

    public class BroadcastRec extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case ("com.mzc6838.ybrowser.setting.SET_JAVASCRIPT_ENABLED"): {
                    webview.getSettings().setJavaScriptEnabled(true);
                    break;
                }
                case ("com.mzc6838.ybrowser.setting.SET_JAVASCRIPT_DISABLED"): {
                    webview.getSettings().setJavaScriptEnabled(false);
                    break;
                }
                case ("com.mzc6838.ybrowser.SET_UA_ANDROID"): {
                    webview.getSettings().setUserAgent("Mozilla/5.0 (Linux; Android " + Build.VERSION.RELEASE + "; " + Build.MODEL + ") AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.109 Mobile Safari/537.36");
                    break;
                }
                case ("com.mzc6838.ybrowser.SET_UA_IPHONE"): {
                    webview.getSettings().setUserAgent("Mozilla/5.0 AppleWebKit/604.4.7 (KHTML, like Gecko) Version/11.0 Mobile/15C202 Safari/604.1");
                    break;
                }
                case ("com.mzc6838.ybrowser.SET_UA_PC"): {
                    webview.getSettings().setUserAgent("Mozilla/5.0 (X11; Linux) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
                    break;
                }
                default:
                    break;
            }
        }
    }

    /**
     * 获得<meta>中名为"theme-color"的值
     */
    public class InJavaScriptLocalObj {

        @JavascriptInterface
        public void setColor(final String str) {
            Log.d("setColor: ", str);
            if(str.equals("null")){
                mainActivity.setColor("#f2f2f2");
            }else {
                mainActivity.setColor(str);
            }
        }
    }

    public void makeWebViewLoadUrl(String url) {
        webview.loadUrl(url);
    }

    public String getPageUrl() {
        return webview.getUrl();
    }

    public String getPageTitle() {
        return webview.getTitle();
    }

    public boolean canWebViewGoBack() {
        return webview.canGoBack();
    }

    public void webViewGoBack() {
        webview.goBack();
    }

    public void webViewGoForward() {
        webview.goForward();
    }

    public String getOriginalUrl() {
        return webview.getOriginalUrl();
    }

    public Bitmap getPageIcon(){
        return pageIcon;
    }

    protected int dp2px(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,getResources().getDisplayMetrics());
    }

}