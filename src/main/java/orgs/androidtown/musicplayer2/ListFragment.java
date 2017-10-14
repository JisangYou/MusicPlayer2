package orgs.androidtown.musicplayer2;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import orgs.androidtown.musicplayer2.domain.Music;


public class ListFragment extends Fragment {
    IActivityInteract listener;
    public ListFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof IActivityInteract)
            listener = (IActivityInteract) context;
        else
            throw new RuntimeException("must implement IActivityInteract!!!!!");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.list);
        ListFragmentAdapter adapter = new ListFragmentAdapter(listener.getList(), listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    public interface IActivityInteract {
        public List<Music.Item> getList();
        public void openPlayer(int position);
    }
}
