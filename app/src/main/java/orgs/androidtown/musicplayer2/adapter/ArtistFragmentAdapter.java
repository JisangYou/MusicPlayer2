package orgs.androidtown.musicplayer2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import orgs.androidtown.musicplayer2.R;
import orgs.androidtown.musicplayer2.domain.Music;
import orgs.androidtown.musicplayer2.fragment.IActivityInteract;

/**
 * Created by JisangYou on 2018-02-26.
 */

public class ArtistFragmentAdapter extends RecyclerView.Adapter<ArtistFragmentAdapter.Holder> {

    IActivityInteract listener;
    List<Music.Item> data;

    public ArtistFragmentAdapter( List<Music.Item> data, IActivityInteract listener) {
        this.listener = listener;
        this.data = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_artist, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Music.Item item = data.get(position);
        holder.position = position;
        holder.artistItemTitle.setText(item.artist);
        holder.artistItemCount.setText(item.album);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        int position;
        TextView artistItemTitle;
        TextView artistItemCount;

        public Holder(View itemView) {
            super(itemView);
            artistItemTitle = itemView.findViewById(R.id.artistItemTitle);
            artistItemCount = itemView.findViewById(R.id.artistItemCount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.openPlayer(position);
                }
            });
        }
    }
}
