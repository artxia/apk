package com.huangyong.playerlib;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.dueeeke.videoplayer.player.IjkPlayer;
import com.dueeeke.videoplayer.player.PlayerConfig;

import java.io.File;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;


public class PlayerActivity extends Activity {

    private String title;
    private String urlMd5;
    private String movieProgress;
    private String progress="";
    private String url;
    private String poster;
    private CustomIjkplayer ijkVideoView;
    private String path;
    private CustomControler controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player_main);
        ijkVideoView = findViewById(R.id.exo_play_view);
        //四个参数，地址，海报，电影名称，需要到达的进度（第一次为0）
        url = getIntent().getStringExtra(Params.PROXY_PALY_URL);
        title = getIntent().getStringExtra(Params.TASK_TITLE_KEY);
        urlMd5 = getIntent().getStringExtra(Params.URL_MD5_KEY);
        movieProgress = getIntent().getStringExtra(Params.MOVIE_PROGRESS);
        poster = getIntent().getStringExtra(Params.POST_IMG_KEY);
        ijkVideoView.setTitle(title);

        controller = new CustomControler(this);
        controller.getThumb().setImageResource(R.drawable.preview_bg);
        controller.setOnCheckListener(listener );
        ijkVideoView.setVideoController(controller);

        IjkPlayer ijkPlayer = new IjkPlayer(this) {
            @Override
            public void setEnableMediaCodec(boolean isEnable) {
                int value = isEnable ? 1 : 0;
                mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-hevc", value);
            }

            @Override
            public void setOptions() {
                super.setOptions();
                //设置ijkplayer支持concat协议，以播放分段视频
                mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "protocol_whitelist", "rtmp,concat,ffconcat,file,subfile,http,https,tls,rtp,tcp,udp,crypto");
            }
        };
        PlayerConfig playerConfig = new PlayerConfig.Builder()
                //启用边播边缓存功能
               // .autoRotate() //启用重力感应自动进入/退出全屏功能
                .enableMediaCodec()//启动硬解码，启用后可能导致视频黑屏，音画不同步
                .usingSurfaceView() //启用SurfaceView显示视频，不调用默认使用TextureView
                .savingProgress() //保存播放进度
                .disableAudioFocus() //关闭AudioFocusChange监听
                .setLooping() //循环播放当前正在播放的视频
                .setCustomMediaPlayer(ijkPlayer)
                .build();
        ijkVideoView.setPlayerConfig(playerConfig);
        Log.e("exoplaypath--",url);

        if (url.startsWith("/storage")){
            File file = new File(url);
            if(file.exists()){
                path = Uri.parse("file://"+file.getAbsolutePath()).toString();
            }
        }else {
            path=url;
        }


        ijkVideoView.setUrl(path);
        ijkVideoView.startFullScreen();
        ijkVideoView.start();
//        exoPlayerManager.setPlaybackParameters(1f, 1f);
    }
     OncheckListener listener = new OncheckListener() {
        @Override
        public void onChecked(int index) {

            if (ijkVideoView!=null&&controller!=null){

                switch (index){
                    case 0:
                        ijkVideoView.setSpeed(1.0f);
                        controller.setCheckUpdate("正常");
                        break;
                    case 1:
                        ijkVideoView.setSpeed(1.25f);
                        controller.setCheckUpdate("1.25x");
                        break;
                    case 2:
                        ijkVideoView.setSpeed(1.5f);
                        controller.setCheckUpdate("1.5x");
                        break;
                    case 3:
                        ijkVideoView.setSpeed(1.75f);
                        controller.setCheckUpdate("1.75x");
                        break;
                    case 4:
                        ijkVideoView.setSpeed(2.0f);
                        controller.setCheckUpdate("2.0x");
                        break;
                        default:
                            ijkVideoView.setSpeed(1.0f);
                            controller.setCheckUpdate("1.0x");
                            break;
                }

            }
        }
    };
    public interface OncheckListener{
        void onChecked(int index);
    }
    @Override
    protected void onResume() {
        super.onResume();
        ijkVideoView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        ijkVideoView.pause();
    }


    @Override
    protected void onDestroy() {
        Intent intent = new Intent();
        intent.putExtra(Params.TASK_TITLE_KEY,title);
        intent.putExtra(Params.LOCAL_PATH_KEY,url);
        intent.putExtra(Params.MOVIE_PROGRESS, ijkVideoView.getCurrentPosition()+"");

        intent.putExtra(Params.URL_MD5_KEY,urlMd5);
        intent.putExtra(Params.POST_IMG_KEY,poster);
        intent.setAction(Params.HISTORY_SAVE);
        sendBroadcast(intent);
        super.onDestroy();
        ijkVideoView.release();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }



    /*@Override
    public void updateProgress(long position, long bufferedPosition, long duration) {
        Log.e("movieprogress",String.valueOf(position));
        this.progress = String.valueOf(position);
    }*/
}
