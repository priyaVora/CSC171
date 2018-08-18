package com.example.priya.socialmediaapp.Activities.Camera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.priya.socialmediaapp.R;

public class PhotoVideoRedirectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_photo_video_redirect);

        init();

    }
    VideoView videoView;
    private void init() {

        ImageView imgShow = (ImageView) findViewById(R.id.imgShow);
        videoView = (VideoView) findViewById(R.id.vidShow);

        if(getIntent().getStringExtra("WHO").equalsIgnoreCase("Image")){

            imgShow.setVisibility(View.VISIBLE);

            Glide.with(PhotoVideoRedirectActivity.this)
                    .load(getIntent().getStringExtra("PATH"))
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    }).placeholder(R.drawable.ic_photo_cont)
                    .into(imgShow);
        }else {

            videoView.setVisibility(View.VISIBLE);
            try {
                videoView.setMediaController(null);
                videoView.setVideoURI(Uri.parse(getIntent().getStringExtra("PATH")));
            } catch (Exception e){
                e.printStackTrace();
            }
            videoView.requestFocus();
            //videoView.setZOrderOnTop(true);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {

                    videoView.start();
                }
            });
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    videoView.start();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (videoView.isPlaying()) {
            videoView.pause();
        }
        super.onBackPressed();
    }
}
