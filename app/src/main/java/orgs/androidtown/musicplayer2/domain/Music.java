package orgs.androidtown.musicplayer2.domain;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jisang on 2017-10-11.
 */

public class Music {
    private static Music music = null;
    public List<Item> data = new ArrayList<>();

    private Music() {
    } // new 사용방지를 위해 생성자를 private으로 만든다

    public static Music getInstance() { //싱글턴으로객체 하나 생성
        if (music == null)//뮤직이란 객체가 null이면,
            music = new Music(); // 그 객체를 돌려준다.
        return music;
    }

    // 음악 데이터를 불러오는 함수
    public void load(Context context) {
        ContentResolver resolver = context.getContentResolver();
        // 1. 테이블명 정의
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        // 2. 불러올 컬럼명 정의
        String proj[] = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.COMPOSER
        };

        // 3. 쿼리
        Cursor cursor = resolver.query(uri, proj, null, null, proj[4] + " ASC");
        // 4. 쿼리결과가 담긴 커서를 통해 데이터 꺼내기
        data.clear();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Item item = new Item();
                item.id = getValue(cursor, proj[0]);
                item.albumId = getValue(cursor, proj[1]);
                item.artist = getValue(cursor, proj[2]);
                item.title = getValue(cursor, proj[3]);
                item.album = getValue(cursor,proj[4]);
                item.composer = getValue(cursor,proj[5]);


                item.musicUri = makeMusicUri(item.id);
                item.albumUri = makeAlbumUri(item.albumId);
                data.add(item);
            }
            cursor.close();
        }
    }


    private String getValue(Cursor cursor, String name) {
        int index = cursor.getColumnIndex(name);
        return cursor.getString(index);
    }

    private Uri makeMusicUri(String musicId) {
        Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        return Uri.withAppendedPath(contentUri, musicId);
    }

    private Uri makeAlbumUri(String albumId) {
        String contentUri = "content://media/external/audio/albumart/";
        return Uri.parse(contentUri + albumId);
    }

    // 실제 뮤직 데이터
    public class Item {
        public String id;      // 음악의 아이디
        public String albumId; // 앨범 아이디
        public String artist;
        public String title;
        public String album;
        public String composer;


        public Uri musicUri; // 음악의 주소
        public Uri albumUri; // 앨범이미지의 주소


        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Item)
                return id.equals(((Item) obj).id);
            return false;
        }
    }
}