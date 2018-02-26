package orgs.androidtown.musicplayer2.fragment;

import java.util.List;

import orgs.androidtown.musicplayer2.domain.Music;

/**
 * Created by JisangYou on 2018-02-26.
 */

public interface IActivityInteract { // 액티비티와 프래그먼트가 통신하기 위한 과정
    public List<Music.Item> getList();
    public void openPlayer(int position);
}
