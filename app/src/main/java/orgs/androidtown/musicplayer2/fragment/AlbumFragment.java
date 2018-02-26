package orgs.androidtown.musicplayer2.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import orgs.androidtown.musicplayer2.R;
import orgs.androidtown.musicplayer2.adapter.AlbumFragmentAdapter;
import orgs.androidtown.musicplayer2.domain.Music;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment {
    IActivityInteract listener;
    View view;
    RecyclerView recyclerView;

    public AlbumFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IActivityInteract)
            listener = (IActivityInteract) context;
        else
            throw new RuntimeException("must implement IActivityInteract!!!!!");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_album, container, false);
        initView();
        setAlbumRecycler();
        return view;
    }

    private void initView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.albumRecycler);
    }

    private void setAlbumRecycler() {

        AlbumFragmentAdapter adapter = new AlbumFragmentAdapter(listener, listener.getList(), getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

}
