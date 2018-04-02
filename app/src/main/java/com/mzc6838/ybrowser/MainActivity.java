package com.mzc6838.ybrowser;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.google.zxing.client.android.CaptureActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.view.KeyEvent.KEYCODE_BACK;

public class MainActivity extends FragmentActivity implements View.OnClickListener, BackHandledFragment.BackHandledInterface {

    //private ImageButton QRButton;
    private ImageView button_back, button_forward, button_home, button_refresh, button_hide, QRButton, button_more;
    private EditText edit_url;
    private InputMethodManager imm;
    public static String pageLink = "", pageTitle = "";
    private FABToolbarLayout fabToolbarLayout;
    private FloatingActionButton fab;
    private long exitTime = 0;
    private NotificationRec notificationRec;
    private SharedPreferences sharedPreferences;
    private DrawerLayout drawerLayout;
    private WebViewFragment webViewFragment;
    private FrameLayout frameLayout;
    private FragmentManager fragmentManager;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private NotificationManager notificationManager;
    private BackHandledFragment backHandledFragment;

    public static int WELCOME_SHOULD_END = 0;
    private static int ICON_COLOR = 0xff000000;
    private static int BROADCAST_TAG = 0;
    private boolean hadIntercept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        }).start();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        GetPermission();

        Init();

    }

    public void GetPermission() {

    }

    public void Init() {
        QRButton = (ImageView) findViewById(R.id.QRButton);
        button_back = (ImageView) findViewById(R.id.button_back);
        button_forward = (ImageView) findViewById(R.id.button_forward);
        button_home = (ImageView) findViewById(R.id.button_home);
        button_hide = (ImageView) findViewById(R.id.button_hide);
        button_refresh = (ImageView) findViewById(R.id.button_refresh);
        button_more = (ImageView) findViewById(R.id.button_more_list);
        edit_url = (EditText) findViewById(R.id.edit_box);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        fabToolbarLayout = (FABToolbarLayout) findViewById(R.id.fabtoolbar);
        fab = (FloatingActionButton) findViewById(R.id.fabtoolbar_fab);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        webViewFragment = new WebViewFragment();
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);

        /*edit_url*/
        edit_url.clearFocus();
        edit_url.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent != null
                        && KeyEvent.KEYCODE_ENTER == keyEvent.getKeyCode()
                        && KeyEvent.ACTION_DOWN == keyEvent.getAction()) {
                    if (isHalfCompleteUrl(edit_url.getText().toString())) {
                        if (edit_url.getText().toString().startsWith("http") || edit_url.getText().toString().startsWith("ftp://")) {
                            webViewFragment.makeWebViewLoadUrl(pageLink = edit_url.getText().toString());
                        } else {
                            webViewFragment.makeWebViewLoadUrl("http://" + (pageLink = edit_url.getText().toString()));
                            pageLink = "http://" + pageLink;
                        }
                    } else {
                        webViewFragment.makeWebViewLoadUrl(pageLink = toSearchResult(edit_url.getText().toString()));
                    }
                    if (imm.isActive() && getCurrentFocus() != null) {
                        if (getCurrentFocus().getWindowToken() != null) {
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }
                    edit_url.clearFocus();
                }
                return false;
            }
        });
        edit_url.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus)//有焦点时
                {
                    edit_url.setText(pageLink);
                    if (edit_url.getText().toString().isEmpty()) {
                        QRButton.setImageDrawable(getResources().getDrawable(R.drawable.scanning));
                        QRButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                    Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                                    startActivityForResult(intent, 111);
                                } else {
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                                }
                            }
                        });
                    } else {
                        QRButton.setImageDrawable(getResources().getDrawable(R.drawable.delete));
                        QRButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                edit_url.setText("");
                            }
                        });
                    }
                } else//无焦点时
                {
                    edit_url.setText("");
                    edit_url.setHint(pageTitle);
                    QRButton.setImageDrawable(getResources().getDrawable(R.drawable.scanning));
                    QRButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                                startActivityForResult(intent, 111);
                            } else {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                            }
                        }
                    });
                }
            }
        });
        edit_url.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    QRButton.setImageDrawable(getResources().getDrawable(R.drawable.delete));
                    QRButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            edit_url.setText("");
                        }
                    });
                } else {
                    QRButton.setImageDrawable(getResources().getDrawable(R.drawable.scanning));
                    QRButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                                startActivityForResult(intent, 111);
                            } else {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                            }
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        button_more.setOnClickListener(this);
        button_back.setOnClickListener(this);
        button_forward.setOnClickListener(this);
        button_home.setOnClickListener(this);
        button_refresh.setOnClickListener(this);
        button_hide.setOnClickListener(this);
        fab.setOnClickListener(this);

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                fab.setVisibility(View.INVISIBLE);
                fabToolbarLayout.setVisibility(View.INVISIBLE);

                sendNotification();

                return true;
            }
        });

        fragmentTransaction.add(R.id.frame_layout, webViewFragment);
        fragmentTransaction.show(webViewFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.button_more_list): {
                popMenu(view);
                button_more.startAnimation(AnimationUtils.loadAnimation(this, R.anim.more_rotate_to));
                break;
            }
            case (R.id.button_back):   //返回按钮
            {
                webViewFragment.webViewGoBack();
                edit_url.clearFocus();
                pageLink = webViewFragment.getOriginalUrl();
                edit_url.setHint(pageTitle = webViewFragment.getPageTitle());
                break;
            }
            case (R.id.button_forward): //前进按钮
            {
                webViewFragment.webViewGoForward();
                edit_url.clearFocus();
                pageLink = webViewFragment.getOriginalUrl();
                edit_url.setHint(pageTitle = webViewFragment.getPageTitle());
                break;
            }
            case (R.id.button_home):  //返回主页按钮
            {
                webViewFragment.makeWebViewLoadUrl(sharedPreferences.getString("change_first_page", "http://www.baidu.com"));
                pageLink = "";
                break;
            }
            case (R.id.fabtoolbar_fab): {
                fabToolbarLayout.show();
                break;
            }
            case (R.id.button_hide):  //隐藏ToolBar按钮
            {
                fabToolbarLayout.hide();
                break;
            }
            case (R.id.button_refresh): //刷新按钮
            {
                webViewFragment.makeWebViewLoadUrl(webViewFragment.getPageUrl());
                break;
            }
            default:
                break;
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
//            webView.goBack();
//            edit_url.clearFocus();
//            if (edit_url.getHint() == "首页") {
//                pageLink = "";
//            } else {
//                pageLink = webView.getOriginalUrl();
//                edit_url.setHint(pageTitle = webViewFragment.getPageTitle());
//            }
//            return true;
//        } else if ((keyCode == KEYCODE_BACK) && !webView.canGoBack()) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
//                this.finish();
//                ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
//                System.exit(0);
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MainActivity.this, com.google.zxing.client.android.CaptureActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "扫码需要权限...", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 111) {
            String t;
            if (isHalfCompleteUrl((t = data.getStringExtra("codedContent")))) {
                if (t.startsWith("http://") || t.startsWith("https://")) {
                    edit_url.setText(t);
                    pageLink = t;
                } else {
                    t = "http://" + t;
                    edit_url.setText(t);
                    pageLink = t;
                }
                webViewFragment.makeWebViewLoadUrl(pageLink);
            } else {
                webViewFragment.makeWebViewLoadUrl(toSearchResult(t));
                pageLink = webViewFragment.getPageUrl();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (BROADCAST_TAG == 1) {
            Log.d("Broadcast ", "onDestroy: 1");
            unregisterReceiver(notificationRec);
            BROADCAST_TAG = 0;
        }
        notificationManager.cancelAll();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        this.WELCOME_SHOULD_END = 999;
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onResume() {
        this.WELCOME_SHOULD_END = 999;
        super.onResume();
    }

    @Override
    protected void onPause() {
        this.WELCOME_SHOULD_END = 999;
        super.onPause();
    }

    @Override
    public void setSelectedFragment(BackHandledFragment selectedFragment) {
        this.backHandledFragment = selectedFragment;
    }

    @Override
    public void onBackPressed() {
        if(backHandledFragment == null || !backHandledFragment.onBackPressed()){
            if(getSupportFragmentManager().getBackStackEntryCount() == 0){
                super.onBackPressed();
            }else{
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    /**
     * 正则表达式判断输入url
     *
     * @param text 被检测的输入数据;
     * @return true  是一个链接
     * false 不是一个链接
     */
    public static boolean isHalfCompleteUrl(String text) {
        Pattern pattern = Pattern.compile("(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    /**
     * 生成百度搜索链接
     *
     * @param text 输入数据
     * @return String
     */
    public String toSearchResult(String text) {
        if (sharedPreferences.getString("change_search_engine", "baidu").equals("baidu")) {
            String result = "https://www.baidu.com/s?wd=";
            text.replace(" ", "+");
            return result + text;
        } else {
            String result = "https://www.google.com/search?q=";
            text.replace(" ", "+");
            return result + text;
        }
    }

    /**
     * 右上角菜单弹出
     *
     * @param v View
     */
    private void popMenu(View v) {
        final PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.getMenuInflater().inflate(R.menu.main_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case (R.id.setting): {
                        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    case (R.id.help): {
                        Log.d("onMenuItemClick:  ", "help");
                        return true;
                    }
                    default:
                        return false;
                }
            }
        });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                button_more.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.more_rotate_back));
            }
        });

        popupMenu.show();
    }

    public void sendNotification() {
        notificationRec = new NotificationRec();
        IntentFilter intentFilter = new IntentFilter("com.mzc6838.ybrowser.action.SHOW_FABTOOLBAR");

        Intent intent = new Intent("com.mzc6838.ybrowser.action.SHOW_FABTOOLBAR");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        this.registerReceiver(notificationRec, intentFilter);
        BROADCAST_TAG = 1;

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("工具栏已隐藏")
                .setContentText("点击这里恢复工具栏")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.notific)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.notific))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOngoing(true)
                .build();
        notificationManager.notify(1, notification);
        Toast.makeText(this, "工具栏已隐藏，在通知中可以开启哦~", Toast.LENGTH_SHORT).show();
    }

    public void setFABVisible() {
        this.fabToolbarLayout.setVisibility(View.VISIBLE);
        this.fab.setVisibility(View.VISIBLE);
    }

    public class NotificationRec extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            switch (intent.getAction()) {
                case ("com.mzc6838.ybrowser.action.SHOW_FABTOOLBAR"): {
                    setFABVisible();
                    break;
                }
                default:
                    break;
            }

        }

    }

    public void setFABShow() {
        this.fabToolbarLayout.show();
    }

    public void setFABHide() {
        this.fabToolbarLayout.hide();
    }

    public void setColor(final String str) {
        if (!str.isEmpty()) {
            ICON_COLOR = ~mParseColor(str);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.main_toolbar).setBackgroundColor(Color.parseColor(str));
                            getWindow().setStatusBarColor(Color.parseColor(str));
                            QRButton.setColorFilter(~mParseColor(str));
                            button_more.setColorFilter(~mParseColor(str));
                        }
                    });
                }
            }).run();
        }
    }

    public int mParseColor(String colorString) {
        if (colorString.charAt(0) == '#') {
            long color = Long.parseLong(colorString.substring(1), 16);
            if (colorString.length() == 7) {
                color |= 0x0000000000000000;
            } else if (colorString.length() != 9) {
                throw new IllegalArgumentException("Unknown color");
            }
            return (int) color;
        }
        return 0;
    }

    public void setEdit_urlText(String str) {
        edit_url.setText(str);
    }

    public void setEdit_urlHint(String str) {
        edit_url.setHint(str);
    }

    public void setQRButtonImageDrawable(Drawable d) {
        QRButton.setImageDrawable(d);
    }

    public interface BackHandledInterface{
        void setSelectedFragment(BackHandledFragment selectedFragment);
    }
}


