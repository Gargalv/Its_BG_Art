package com.bga.its_bg_art;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        openApp();

        ImageView logo=findViewById(R.id.imageViewlogo);
        Animation myanim= AnimationUtils.loadAnimation(this,R.anim.fadein);
        logo.startAnimation(myanim);


        ImageView fondo=findViewById(R.id.imageViewlogo);



        Glide.with(this)
                .load(R.drawable.logo_splash_2)
                .transition(DrawableTransitionOptions.withCrossFade(0))
                .centerCrop()
                .placeholder(new ColorDrawable(this.getResources().getColor(R.color.light_brown)))
                .into(fondo);
    }

    private void openApp() {
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splash.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        },4000);
    }
}