package orgs.androidtown.musicplayer2.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.media.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;

import orgs.androidtown.musicplayer2.R;
import orgs.androidtown.musicplayer2.util.Const;
import orgs.androidtown.musicplayer2.domain.Music;
import orgs.androidtown.musicplayer2.player.Player;
import orgs.androidtown.musicplayer2.player.SeekBarThread;

public class PlayerService extends Service { // 하나의 서비스에 많은 액티비티가 사용될 수 있다.
    Player player = null;
    SeekBarThread thread = null;
    Music music = null;
    int current = -1;

    public PlayerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = Player.getInstance();
        thread = SeekBarThread.getInstance();
        thread.start();
        music = Music.getInstance();
    }

    @Override
    public IBinder onBind(Intent intent) { // 바인드가 되면서 서로 통신을 할 수 있다. 액티비티는 클라이언트 역할이고, 서비스는 서버 역할이다. 결국
        // 액티비티가 어떤 정보를 요구하면, 서비스는 그 정보를 준다. 앱내에서.
        // iBinder는 인터페이스이자 콜백 메서드
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) { // null체크를 해주지 않으면, nullpointexception
            String action = intent.getAction();
            switch (action) {
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
            startForeground(11, makeNotification());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private Notification makeNotification() {

        android.support.v4.app.NotificationCompat.Builder builder = new android.support.v4.app.NotificationCompat.Builder(this);
        Bitmap largeIcon = null;
        try {
            largeIcon
                    = MediaStore.Images.Media.getBitmap
                    (getContentResolver(), music.data.get(current).albumUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        builder.setSmallIcon(R.drawable.down_arrow2)
                .setContentTitle("하이")
                .setContentText("룰루")
                .setLargeIcon(largeIcon);

//        builder.addAction(makeButtonInNotification(Const.ACTION_START));
        builder.setProgress(Player.getInstance().getDuration(), 0, false);

        return builder.build();

    }

    // 알림바 버튼 생성 메소드(Prev, Play, Pause, Next)
    private android.support.v4.app.NotificationCompat.Action makeButtonInNotification(String action) {
        int iconId = 0;

        // 현재 액션 후 다음 클릭 시 다음 액션 설정
        // 임의의 버튼 아이콘 등록
        switch (action) {
            case Const.ACTION_START:
                iconId = android.R.drawable.ic_media_pause;
                action = Const.ACTION_PAUSE;
                break;
            case Const.ACTION_PAUSE:
                iconId = android.R.drawable.ic_media_play;
                action = Const.ACTION_START;
                break;
            case Const.ACTION_MUSIC_NEXT:
                iconId = android.R.drawable.ic_media_next;
                break;
            case Const.ACTION_MUSIC_PREV:
                iconId = android.R.drawable.ic_media_previous;
                break;
        }

        // PendingIntent로 등록될 Intent 생성
        Intent intent = new Intent(getBaseContext(), PlayerService.class);

        // Intent로 전달될 액션 설정
        intent.setAction(action);

        // PendingIntent 생성
        PendingIntent pendingIntent
                = PendingIntent.getService(getBaseContext(), 1, intent, 0);

        // 버튼 타이틀 등록
        String btnTitle = action;

        // 해당 버튼 액션 설정
        NotificationCompat.Action notifAction
                = new NotificationCompat.Action.Builder
                (iconId, btnTitle, pendingIntent).build();

        return notifAction;
    }

    void playerSet() {
        if (current > -1)
            player.set(getBaseContext(), music.data.get(current).musicUri);
    }

    void start() {
        player.start();
    }

    void pause() {
        player.pause();
    }

    void stop() {
        player.stop();
    }

    @Override
    public void onDestroy() {
        if (player != null)
            player = null;
        if (thread != null) {
            thread.setStop();
            thread = null;
        }
        super.onDestroy();
    }
    private String miliToSec(int mili) {
        int sec = mili / 1000;
        int min = sec / 60;
        sec = sec % 60;

        return String.format("%02d", min) + ":" + String.format("%02d", sec); // %02d의 의미: % 명령의 시작, 0 채워질문자, 2 총자리수 d 십진정수
    }

}