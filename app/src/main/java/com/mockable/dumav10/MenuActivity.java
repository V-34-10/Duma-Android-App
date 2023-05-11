package com.mockable.dumav10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements RecyclerViewAdapterStory.ItemClickListener, View.OnClickListener {

    private RecyclerViewAdapterStory adapter;
    ImageView exitApp, settingsApp, storeApp, returnHistory, playHistory, runAdb;
    private InterstitialAd interstitialAd;
    private Intent nextActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        /*MobileAds.initialize(this, "ca-app-pub-7545669344888580~8855730429");
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("");*/



        initRecyclerView();

        initImageView();

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadInterAd();
    }

    private void  loadInterAd(){
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                interstitialAd = null;
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd Ad) {
                interstitialAd = Ad;
            }
        });
    }

    private void showInterAd(){
        if(interstitialAd != null)
        {
            interstitialAd.setFullScreenContentCallback( new FullScreenContentCallback() {
                @Override
                public void onAdClicked() {
                    interstitialAd = null;
                    loadInterAd();
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    interstitialAd = null;
                    loadInterAd();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    interstitialAd = null;
                    loadInterAd();
                }
            });
            interstitialAd.show(this);
        }else {
            Toast.makeText(this, "Відсутній доступ до мережі!", Toast.LENGTH_LONG).show();
        }
    }

    public void initImageView() {
        exitApp = this.findViewById(R.id.exit);
        settingsApp = this.findViewById(R.id.settings);
        storeApp = this.findViewById(R.id.storeBtn);
        returnHistory = this.findViewById(R.id.btn_return);
        playHistory = this.findViewById(R.id.btm_play);
        runAdb = this.findViewById(R.id.btm_adb);

        exitApp.setOnClickListener(this);
        settingsApp.setOnClickListener(this);
        storeApp.setOnClickListener(this);
        returnHistory.setOnClickListener(this);
        playHistory.setOnClickListener(this);
        runAdb.setOnClickListener(this);
    }

    public void initRecyclerView() {
        ArrayList<Integer> viewImages = new ArrayList<>();
        viewImages.add(R.drawable.history_public);
        viewImages.add(R.drawable.history_pipeline);
        viewImages.add(R.drawable.history_done);
        viewImages.add(R.drawable.history_public);
        viewImages.add(R.drawable.history_done);

        ArrayList<String> storyNames = new ArrayList<>();
        storyNames.add("Берегиня сказаний");
        storyNames.add("2 глава");
        storyNames.add("3 глава");
        storyNames.add("4 глава");
        storyNames.add("5 глава");

        RecyclerView recyclerView = this.findViewById(R.id.list_story);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MenuActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);

        adapter = new RecyclerViewAdapterStory(this, viewImages, storyNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, adapter.getItem(position), Toast.LENGTH_SHORT).show();
    }

    public void onExit() {
        Animation Animation = AnimationUtils.loadAnimation(MenuActivity.this, R.anim.alpha);
        Dialog dialog;
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_exit);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        ImageView btnNo = dialog.findViewById(R.id.dialog_exit_btn_no);
        btnNo.setOnClickListener(view -> {
            btnNo.startAnimation(Animation);
            dialog.dismiss();
        });

        ImageView btnYes = dialog.findViewById(R.id.dialog_exit_btn_yes);
        btnYes.setOnClickListener(view -> {
            btnYes.startAnimation(Animation);
            try {
                System.exit(0);
            }catch (Exception e){}
            dialog.dismiss();
        });

        dialog.show();

    }

    public Dialog onCreateExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_exit, null))
                // Add action buttons
                .setPositiveButton(R.string.textYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        System.exit(0);
                    }
                })
                .setNegativeButton(R.string.textCancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onClick(View view) {
        Animation Animation = AnimationUtils.loadAnimation(MenuActivity.this, R.anim.alpha);
        switch (view.getId()) {
            case R.id.exit:
                exitApp.startAnimation(Animation);
                onExit();
                break;
            case R.id.settings:
                settingsApp.startAnimation(Animation);
                nextActivity = new Intent(MenuActivity.this, SettingsActivity.class);
                startActivity(nextActivity);
                break;
            case R.id.storeBtn:
                storeApp.startAnimation(Animation);
                nextActivity = new Intent(MenuActivity.this, StoreActivity.class);
                startActivity(nextActivity);
                break;
            case R.id.btn_return:
                returnHistory.startAnimation(Animation);

                break;
            case R.id.btm_play:
                playHistory.startAnimation(Animation);
                nextActivity = new Intent(MenuActivity.this, HistoryActivity.class);
                startActivity(nextActivity);
                finish();
                break;
            case R.id.btm_adb:
                runAdb.startAnimation(Animation);
                /*nextActivity = new Intent(MenuActivity.this, AdsActivity.class);
                startActivity(nextActivity);
                finish();*/
                showInterAd();
                break;
        }
    }
}