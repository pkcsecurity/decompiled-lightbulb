package com.android.tedcoder.wkvideoplayer.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.android.tedcoder.wkvideoplayer.R;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MediaController extends FrameLayout implements OnClickListener, OnSeekBarChangeListener {

   private ImageView mExpandImg;
   private MediaController.MediaControlImpl mMediaControl;
   private View mMenuViewPlaceHolder;
   private ImageView mPlayImg;
   private SeekBar mProgressSeekBar;
   private ImageView mShrinkImg;
   private TextView mTimeTxt;


   public MediaController(Context var1) {
      super(var1);
      this.initView(var1);
   }

   public MediaController(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.initView(var1);
   }

   public MediaController(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.initView(var1);
   }

   @SuppressLint({"SimpleDateFormat"})
   private String formatPlayTime(long var1) {
      return (new SimpleDateFormat("mm:ss")).format(new Date(var1));
   }

   private String getPlayTime(int var1, int var2) {
      String var3 = "00:00";
      String var4 = "00:00";
      if(var1 > 0) {
         var3 = this.formatPlayTime((long)var1);
      }

      if(var2 > 0) {
         var4 = this.formatPlayTime((long)var2);
      }

      StringBuilder var5 = new StringBuilder();
      var5.append(var3);
      var5.append("/");
      var5.append(var4);
      return var5.toString();
   }

   private void initData() {
      this.mProgressSeekBar.setOnSeekBarChangeListener(this);
      this.mPlayImg.setOnClickListener(this);
      this.mShrinkImg.setOnClickListener(this);
      this.mExpandImg.setOnClickListener(this);
      this.setPageType(MediaController.a.SHRINK);
      this.setPlayState(MediaController.b.PAUSE);
   }

   private void initView(Context var1) {
      View.inflate(var1, R.layout.vplayer_biz_video_media_controller, this);
      this.mPlayImg = (ImageView)this.findViewById(R.id.pause);
      this.mProgressSeekBar = (SeekBar)this.findViewById(R.id.media_controller_progress);
      this.mTimeTxt = (TextView)this.findViewById(R.id.time);
      this.mExpandImg = (ImageView)this.findViewById(R.id.expand);
      this.mShrinkImg = (ImageView)this.findViewById(R.id.shrink);
      this.mMenuViewPlaceHolder = this.findViewById(R.id.view_menu_placeholder);
      this.initData();
   }

   public void forceLandscapeMode() {
      this.mExpandImg.setVisibility(4);
      this.mShrinkImg.setVisibility(4);
   }

   public void initTrimmedMode() {
      this.mMenuViewPlaceHolder.setVisibility(8);
      this.mExpandImg.setVisibility(4);
      this.mShrinkImg.setVisibility(4);
   }

   public void onClick(View var1) {
      if(var1.getId() == R.id.pause) {
         this.mMediaControl.a();
      } else if(var1.getId() == R.id.expand) {
         this.mMediaControl.b();
      } else {
         if(var1.getId() == R.id.shrink) {
            this.mMediaControl.b();
         }

      }
   }

   public void onProgressChanged(SeekBar var1, int var2, boolean var3) {
      if(var3) {
         this.mMediaControl.a(MediaController.c.DOING, var2);
      }

   }

   public void onStartTrackingTouch(SeekBar var1) {
      this.mMediaControl.a(MediaController.c.START, 0);
   }

   public void onStopTrackingTouch(SeekBar var1) {
      this.mMediaControl.a(MediaController.c.STOP, 0);
   }

   public void playFinish(int var1) {
      this.mProgressSeekBar.setProgress(100);
      this.setPlayProgressTxt(var1, var1);
      this.setPlayState(MediaController.b.PAUSE);
   }

   public void setMediaControl(MediaController.MediaControlImpl var1) {
      this.mMediaControl = var1;
   }

   public void setPageType(MediaController.a var1) {
      ImageView var5 = this.mExpandImg;
      boolean var4 = var1.equals(MediaController.a.EXPAND);
      byte var3 = 0;
      byte var2;
      if(var4) {
         var2 = 8;
      } else {
         var2 = 0;
      }

      var5.setVisibility(var2);
      var5 = this.mShrinkImg;
      var2 = var3;
      if(var1.equals(MediaController.a.SHRINK)) {
         var2 = 8;
      }

      var5.setVisibility(var2);
   }

   public void setPlayProgressTxt(int var1, int var2) {
      this.mTimeTxt.setText(this.getPlayTime(var1, var2));
   }

   public void setPlayState(MediaController.b var1) {
      ImageView var3 = this.mPlayImg;
      int var2;
      if(var1.equals(MediaController.b.PLAY)) {
         var2 = R.drawable.vplayer_biz_video_pause;
      } else {
         var2 = R.drawable.vplayer_biz_video_play;
      }

      var3.setImageResource(var2);
   }

   public void setProgressBar(int var1, int var2) {
      int var3 = var1;
      if(var1 < 0) {
         var3 = 0;
      }

      int var4 = var3;
      if(var3 > 100) {
         var4 = 100;
      }

      var1 = var2;
      if(var2 < 0) {
         var1 = 0;
      }

      var2 = var1;
      if(var1 > 100) {
         var2 = 100;
      }

      this.mProgressSeekBar.setProgress(var4);
      this.mProgressSeekBar.setSecondaryProgress(var2);
   }

   public static enum b {

      // $FF: synthetic field
      private static final MediaController.b[] $VALUES = new MediaController.b[]{PLAY, PAUSE};
      PAUSE("PAUSE", 1),
      PLAY("PLAY", 0);


      private b(String var1, int var2) {}
   }

   public static enum c {

      // $FF: synthetic field
      private static final MediaController.c[] $VALUES = new MediaController.c[]{START, DOING, STOP};
      DOING("DOING", 1),
      START("START", 0),
      STOP("STOP", 2);


      private c(String var1, int var2) {}
   }

   public static enum a {

      // $FF: synthetic field
      private static final MediaController.a[] $VALUES = new MediaController.a[]{EXPAND, SHRINK};
      EXPAND("EXPAND", 0),
      SHRINK("SHRINK", 1);


      private a(String var1, int var2) {}
   }

   public interface MediaControlImpl {

      void a();

      void a(MediaController.c var1, int var2);

      void b();
   }
}
