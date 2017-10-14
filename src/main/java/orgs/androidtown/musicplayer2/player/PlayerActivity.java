package orgs.androidtown.musicplayer2.player;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import orgs.androidtown.musicplayer2.BaseActivity;
import orgs.androidtown.musicplayer2.Const;
import orgs.androidtown.musicplayer2.PlayerPagerAdapter;
import orgs.androidtown.musicplayer2.PlayerService;
import orgs.androidtown.musicplayer2.R;
import orgs.androidtown.musicplayer2.domain.Music;

/**
 * Created by Jisang on 2017-10-12.
 */

public class PlayerActivity extends BaseActivity
        implements View.OnClickListener, SeekBarThread.IObserver { // 인터페이스를 임플리먼트했기에 오버라이드되는 메소드가 밑에 있음.
    Music music;
    int current = -1;
    private android.support.v4.view.ViewPager viewPager;
    private android.widget.RelativeLayout controller;
    private android.widget.SeekBar seekBar;
    private android.widget.TextView textCurrentTime;
    private android.widget.TextView textDuration;
    private android.widget.ImageButton btnPlay;
    private android.widget.ImageButton btnFf;
    private android.widget.ImageButton btnRew;
    private android.widget.ImageButton btnNext;
    private android.widget.ImageButton btnPrev;

    Intent serviceIntent;

    @Override
    public void init() {
        setContentView(R.layout.activity_player);
        Intent intent = getIntent();
        if (intent != null)
            current = intent.getIntExtra(Const.KEY_POSITION, 0); //key, value값!
        serviceIntent = new Intent(this, PlayerService.class);

        load();
        initView();
        initViewPager();
    }

    void load() { //데이터를 불러와 세팅해줌
        music = Music.getInstance();
        music.load(this);
        playerSet();
    }

    void initViewPager() {
        PlayerPagerAdapter adapter = new PlayerPagerAdapter(this, music.data);
        viewPager.setAdapter(adapter);
        // 뷰페이저에 리스너를 달기전에 페이지를 변경해서 onPageSelected가 호출되지 않는다
        if (current > 0) // 인텐트를 통해 전달해온 포지션값의 할당에따라....그런데 아이템 최초의 값이 0이 아닌가?
            viewPager.setCurrentItem(current);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                current = position;
                playerSet();
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        controller = (RelativeLayout) findViewById(R.id.controller);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textCurrentTime = (TextView) findViewById(R.id.textCurrentTime);
        textDuration = (TextView) findViewById(R.id.textDuration);

        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnFf = (ImageButton) findViewById(R.id.btnFf);
        btnRew = (ImageButton) findViewById(R.id.btnRew);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPrev = (ImageButton) findViewById(R.id.btnPrev);

        btnPlay.setOnClickListener(this);
        btnFf.setOnClickListener(this);
        btnRew.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlay:
                if (Player.getInstance().getStatus() == Const.STAT_PLAY) {
                    pause();
                } else {
                    start();
                }
                break;
            case R.id.btnFf:
                break;
            case R.id.btnRew:
                break;
            case R.id.btnNext:
                break;
            case R.id.btnPrev:
                break;
        }
    }

    void playerSet() {
        serviceIntent.setAction(Const.ACTION_SET);
        serviceIntent.putExtra(Const.KEY_POSITION, current);
        startService(serviceIntent);
    }

    void start() {
        serviceIntent.setAction(Const.ACTION_START);
        startService(serviceIntent);
        togglePlayButton(Const.STAT_PLAY);
    }

    void pause() {
        serviceIntent.setAction(Const.ACTION_PAUSE);
        startService(serviceIntent);
        togglePlayButton(Const.STAT_PAUSE);
    }

    void stop() {
        startService(serviceIntent);
    }

    void togglePlayButton(int status) {
        if (status == Const.STAT_PLAY) {
            btnPlay.setImageResource(android.R.drawable.ic_media_pause);
        } else if (status == Const.STAT_PAUSE) {
            btnPlay.setImageResource(android.R.drawable.ic_media_play);
        }
    }

    SeekBarThread thread = null;

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayer();
        seekBar.setMax(Player.getInstance().getDuration()); // 싱글턴
        thread = SeekBarThread.getInstance();
        thread.add(this);
    }

    @Override
    protected void onPause() {
        thread.remove(this);
        super.onPause();
    }

    void checkPlayer() {
        if (Player.getInstance().isPlay()) {
            // 플레이중에 앱을 실행하면 버튼과 viewPager를 상태에 맞게 갱신
            togglePlayButton(Const.STAT_PLAY);
        }
    }

    /**
     * 이 함수는 서브 thread에서 호출되기 때문에 Activity에 코드가 있지만
     * 실행은 서브에서 된다.
     */
    @Override
    public void setProgress() {
        // runOnUiThread : 코드를 main Thread에서 실행한다.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(Player.getInstance().getCurrent());
            }
        });
    }
}