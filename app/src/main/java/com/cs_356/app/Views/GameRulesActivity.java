package com.cs_356.app.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.cs_356.app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import Entities.BoardGame;

public class GameRulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rules);

        FloatingActionButton backFAB = findViewById(R.id.backFAB);
        backFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        String url = getIntent().getStringExtra("URL");
        if (url.toLowerCase().contains("pdf") && !url.toLowerCase().contains("dropbox")) {
            url = "https://docs.google.com/gview?embedded=true&url=" + url;
        }
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}