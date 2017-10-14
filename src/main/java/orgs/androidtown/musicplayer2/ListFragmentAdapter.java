package orgs.androidtown.musicplayer2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import orgs.androidtown.musicplayer2.domain.Music;

/**
 * Created by Jisang on 2017-10-12.
 */

public class ListFragmentAdapter
        extends RecyclerView.Adapter<ListFragmentAdapter.Holder>{

    ListFragment.IActivityInteract listener;
    List<Music.Item> data;

    public ListFragmentAdapter(List<Music.Item> data,ListFragment.IActivityInteract listener) {
        this.data = data;
        this.listener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Music.Item item = data.get(position);
        holder.position = position;
        holder.id.setText(item.id);
        holder.content.setText(item.title);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        int position;
        TextView id;
        TextView content;
        public Holder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            content = itemView.findViewById(R.id.content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.openPlayer(position);
                }
            });
        }
    }
}
