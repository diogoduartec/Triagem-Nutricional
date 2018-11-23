package com.infokmg.hugv.triagemnutricional;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.infokmg.hugv.triagemnutricional.service.MainConfigConstants;

public class SplashScreen extends AppCompatActivity {
    ImageView logo;
    String id;
    Button btNewTriage, btMyTriages, btAboutApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        logo = (ImageView) findViewById(R.id.img_logo);
        logo.setImageResource(R.drawable.logo_splash);

        btNewTriage = (Button) findViewById(R.id.bt_new_triage);
        btMyTriages = (Button) findViewById(R.id.bt_my_triage);
        btAboutApp = (Button) findViewById(R.id.bt_about_app);

        Bundle extra = getIntent().getExtras();
        id = extra.getString("id");

        btNewTriage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SplashScreen.this, MainActivity.class);
                it.putExtra("id", id);
                startActivity(it);
//                finish();
            }
        });

        btMyTriages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
            }
        });

        btAboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(SplashScreen.this, AboutAppScreen.class);
                startActivity(it);
            }
        });

    }

    public void share() {
        //see ShareContentProvider class
        Uri fileUri = Uri.parse("content://"+getPackageName()+"/"+ MainConfigConstants.XLSX_SHEET_NAME);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        shareIntent.setType("application/octet-stream");
        startActivity(Intent.createChooser(shareIntent, "Enviando..."));
    }

}
