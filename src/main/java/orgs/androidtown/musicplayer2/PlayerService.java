package orgs.androidtown.musicplayer2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import orgs.androidtown.musicplayer2.domain.Music;
import orgs.androidtown.musicplayer2.player.Player;
import orgs.androidtown.musicplayer2.player.SeekBarThread;

public class PlayerService extends Service {
    Player player = null;
    SeekBarThread thread = null;
    Music music = null;
    int current = -1;
    public PlayerService() {}

    @Override
    public void onCreate() {
        super.onCreate();
        player = Player.getInstance();
        thread = SeekBarThread.getInstance();
        thread.start();
        music = Music.getInstance();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            String action = intent.getAction();
            switch(action){
                case Const.ACTION_SET:
                    current = intent.getIntExtra(Const.KEY_POSITION, -1);
                    playerSet();
                    break;
                case Const.ACTION_START:
                    start();
                    break;
                case Const.ACTION_PAUSE:
                    pause();
                    break;
                case Const.ACTION_STOP:
                    stop();
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    void playerSet(){
        if(current > -1)
            player.set(getBaseContext(), music.data.get(current).musicUri);
    }
    void start(){
        player.start();
    }
    void pause(){
        player.pause();
    }
    void stop(){
        player.stop();
    }

    @Override
    public void onDestroy() {
        if(player != null)
            player = null;
        if(thread != null) {
            thread.setStop();
            thread = null;
        }
        super.onDestroy();
    }
}