package orgs.androidtown.musicplayer2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import orgs.androidtown.musicplayer2.R;
import orgs.androidtown.musicplayer2.domain.Music;
import orgs.androidtown.musicplayer2.fragment.IActivityInteract;

/**
 * Created by JisangYou on 2018-02-26.
 */

public class AlbumFragmentAdapter extends RecyclerView.Adapter<AlbumFragmentAdapter.Holder> {
    IActivityInteract listener;
    List<Music.Item> data;
    Context context;

    public AlbumFragmentAdapter(IActivityInteract listener, List<Music.Item> data, Context context) {
        this.listener = listener;
        this.data = data;
        this.context = context;
    }

    @Override
    public AlbumFragmentAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(AlbumFragmentAdapter.Holder holder, int position) {
        Music.Item item = data.get(position);
        holder.position = position;
        holder.title.setText(item.album);
        holder.artist.setText(item.artist);
        Glide.with(context).load(item.albumUri).into(holder.cover);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView title;
        TextView artist;
        int position;

        public Holder(View itemView) {
            super(itemView);

            cover = (ImageView) itemView.findViewById(R.id.albumItemImage);
            title = (TextView) itemView.findViewById(R.id.albumItemTitle);
            artist = (TextView) itemView.findViewById(R.id.albumItemArtist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.openPlayer(position);
                }
            });
        }
    }
}
