package orgs.androidtown.musicplayer2;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import orgs.androidtown.musicplayer2.domain.Music;
import orgs.androidtown.musicplayer2.player.Player;
import orgs.androidtown.musicplayer2.player.PlayerActivity;
/**
 * 뮤직 플레이어 만들기
 * 1. 권한설정 : Read_External_storage > BaseActivity
 * 2. 화면만들기 : 메인 -> TabLayout, ViewPager
 *                    -> 목록프래그먼트 -> RecyclerView
 *                                    -> RecyclerAdapter
 *                                    -> item_layout.xml
 *                Music-> load() : 음악목록 가져오기
 *
 *                Player -> ViewPager, Button, SeekBar
 *                          PagerAdapter
 *                          SeekBarThread
 */
public class MainActivity extends BaseActivity
        implements ListFragment.IActivityInteract{
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
    void checkPlayer(){
        if(Player.getInstance().isPlay())
            openPlayer(-1);
    }
    void initView(){
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tablayout = (TabLayout) findViewById(R.id.tablayout);
    }
    void initTab(){
        tablayout.addTab(tablayout.newTab().setText(getString(R.string.tab_title)));
        tablayout.addTab(tablayout.newTab().setText(getString(R.string.tab_artist)));
        tablayout.addTab(tablayout.newTab().setText(getString(R.string.tab_album)));
        tablayout.addTab(tablayout.newTab().setText(getString(R.string.tab_genre)));
    }
    void initViewPager(){
        Fragment one = new ListFragment();
        Fragment two = new ListFragment();
        Fragment three = new ListFragment();
        Fragment four = new ListFragment();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(one); fragments.add(two); fragments.add(three); fragments.add(four);
        ListPagerAdapter adapter = new ListPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }

    void conTabWithViewPager(){
        tablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
    }

    void load(){
        music = Music.getInstance();
        music.load(this);
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