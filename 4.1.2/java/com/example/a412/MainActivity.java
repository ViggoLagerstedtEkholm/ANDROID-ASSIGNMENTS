package com.example.a412;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
/**
 * This program will show the user 2 buttons. Each button leads to 2 different websites.
 *
 * @author  Viggo Lagerstedt Ekholm
 * @date   2021-01-29
 */
public class MainActivity extends AppCompatActivity {
    private WebView webView;

    /**
     * This method gets called once we start the activity.
     * We get our buttons and WebView from the UI by id.
     * We add OnClickListeners to all of our buttons.
     * @param savedInstanceState We pass a Bundle to this method so we can get the most recent saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = this.findViewById(R.id.button1);
        Button button2 = this.findViewById(R.id.button2);
        webView = this.findViewById(R.id.webView);

        button1.setOnClickListener(v -> openURL("https://www.google.se"));
        button2.setOnClickListener(v -> openURL("https://www.amazon.com"));
    }

    /**
     * This method will create a new WebViewClient inside our application with the given URL.
     * @param url the destination
     */
    private void openURL(String url){
        webView.setWebViewClient( new WebViewClient());
        webView.loadUrl(url);
    }
}