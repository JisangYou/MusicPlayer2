package orgs.androidtown.musicplayer2.player;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Jisang on 2017-10-12.
 */

public class SeekBarThread extends Thread {
    // Singleton
    private static SeekBarThread instance = null;

    private SeekBarThread() {

    }

    public static SeekBarThread getInstance() {
        if (instance == null)
            instance = new SeekBarThread();
        return instance;
    }

    private boolean runFlag = true;

    public void setStop() {

        runFlag = false;
    }
    // CopyOnWriteArrayList <= 동기화를 지원하는 컬렉션
    // run() 함수의 향상된 for문에서 observers를 읽고 있으면
    //       대기하고 있다가 읽기가 끝나면 add(), remove()를 실행해서
    //       충돌을 방지해준다.

    private List<IObserver> observers = new CopyOnWriteArrayList<>(); // 리스트를 복사하는 함수임.

    public void add(IObserver observer) { //옵져버인터페이스에 의해 플레이어 제어? 옵져버 어레이리스트 객체를 만들었다?
        observers.add(observer);
    }

    public void remove(IObserver observer) {
        observers.remove(observer);
    }

    public void run() {
        while (runFlag) {
            for (IObserver o : observers)
                o.setProgress();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Observer
    public interface IObserver {  // 옵져버 인터페이스를 쓰레드에 정의했다라?
        public void setProgress();
    }
}