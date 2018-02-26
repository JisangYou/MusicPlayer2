package orgs.androidtown.musicplayer2.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import orgs.androidtown.musicplayer2.fragment.AlbumFragment;
import orgs.androidtown.musicplayer2.fragment.ArtistFragment;
import orgs.androidtown.musicplayer2.fragment.GenreFragment;
import orgs.androidtown.musicplayer2.fragment.IActivityInteract;
import orgs.androidtown.musicplayer2.fragment.TitleFragment;
import orgs.androidtown.musicplayer2.util.Const;
import orgs.androidtown.musicplayer2.adapter.ListPagerAdapter;
import orgs.androidtown.musicplayer2.R;
import orgs.androidtown.musicplayer2.domain.Music;
import orgs.androidtown.musicplayer2.player.Player;

/**
 * 뮤직 플레이어 만들기
 * 1. 권한설정 : Read_External_storage > BaseActivity
 * 2. 화면만들기 : 메인 -> TabLayout, ViewPager
 * -> 목록프래그먼트 -> RecyclerView
 * -> RecyclerAdapter
 * -> item_layout.xml
 * Music-> load() : 음악목록 가져오기
 * <p>
 * Player -> ViewPager, Button, SeekBar
 * PagerAdapter
 * SeekBarThread
 */
public class MainActivity extends BaseActivity
        implements IActivityInteract {
    private ViewPager viewPager;
    private TabLayout tablayout;

    Music music = null;

    @Override
    public void init() {
        load();
        initView();
        initTab();
        initViewPager();
        conTabWithViewPager();
        checkPlayer();
    }

    void checkPlayer() {
        if (Player.getInstance().isPlay())
            openPlayer(-1);
    }

    void initView() { // 메인 액티비티를 셋해준다.
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tablayout = (TabLayout) findViewById(R.id.tablayout);
    }

    void initTab() { //탭을 add해준다.
        tablayout.addTab(tablayout.newTab().setText(getString(R.string.tab_title)));
        tablayout.addTab(tablayout.newTab().setText(getString(R.string.tab_artist)));
        tablayout.addTab(tablayout.newTab().setText(getString(R.string.tab_album)));
        tablayout.addTab(tablayout.newTab().setText(getString(R.string.tab_genre)));
    }

    void initViewPager() {// 프래그먼트를 new해주면서 객체 생성한다.
        Fragment title = new TitleFragment();
        Fragment artist = new ArtistFragment();
        Fragment album = new AlbumFragment();
        Fragment Genre = new GenreFragment();
        List<Fragment> fragments = new ArrayList<>(); //fragments는 arraylist타입
        fragments.add(title);
        fragments.add(artist);
        fragments.add(album);
        fragments.add(Genre); // arraylist에 프래그먼트를 add한다.
        ListPagerAdapter adapter = new ListPagerAdapter(getSupportFragmentManager(), fragments); // 프래그먼트
        viewPager.setAdapter(adapter);
    }

    void conTabWithViewPager() {// 탭 레이아웃과 뷰페이저를 연결한다.
        tablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
    }

    void load() {
        music = Music.getInstance(); //뮤직 객체를 맏아온다.
        music.load(this); // 뮤직 데이터를 불러와 컨텍스트를 인자로 넣어준다.

    }

    @Override
    public List<Music.Item> getList() {
        return music.data;
    }

    @Override
    public void openPlayer(int position) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra(Const.KEY_POSITION, position);
        startActivity(intent);
    }
}