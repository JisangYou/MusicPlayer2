package orgs.androidtown.musicplayer2.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import orgs.androidtown.musicplayer2.adapter.TitleFragmentAdapter;
import orgs.androidtown.musicplayer2.R;


public class TitleFragment extends Fragment {
    IActivityInteract listener;
    public TitleFragment() {

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
        View view = inflater.inflate(R.layout.fragment_title, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.list);
        TitleFragmentAdapter adapter = new TitleFragmentAdapter(listener.getList(), listener, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }


}
