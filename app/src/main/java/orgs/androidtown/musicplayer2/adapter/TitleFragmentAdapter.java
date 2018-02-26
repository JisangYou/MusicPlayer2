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

import de.hdodenhof.circleimageview.CircleImageView;
import orgs.androidtown.musicplayer2.R;
import orgs.androidtown.musicplayer2.fragment.IActivityInteract;
import orgs.androidtown.musicplayer2.fragment.TitleFragment;
import orgs.androidtown.musicplayer2.domain.Music;

/**
 * Created by Jisang on 2017-10-12.
 */

public class TitleFragmentAdapter
        extends RecyclerView.Adapter<TitleFragmentAdapter.Holder> {

    IActivityInteract listener;
    List<Music.Item> data;
    Context context;

    public TitleFragmentAdapter(List<Music.Item> data, IActivityInteract listener, Context context) {
        this.data = data;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_title, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Music.Item item = data.get(position);
        holder.position = position;
        holder.id.setText(item.title);
        holder.content.setText(item.artist);
        Glide.with(context).load(item.albumUri).into(holder.circleImageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        int position;
        TextView id;
        TextView content;
        CircleImageView circleImageView;

        public Holder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            content = itemView.findViewById(R.id.content);
            circleImageView = itemView.findViewById(R.id.musicThumbNail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.openPlayer(position);
                }
            });
        }
    }
}
