package com.mlclassifier.ecgclaissfier.ui.history;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mlclassifier.ecgclaissfier.MainActivity;
import com.mlclassifier.ecgclaissfier.R;
import com.mlclassifier.ecgclaissfier.model.Constants;
import com.mlclassifier.ecgclaissfier.model.EcgResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;
import static com.mlclassifier.ecgclaissfier.model.Constants.MY_PREFS_NAME;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;
    Unbinder unbinder;

    SharedPreferences prefs;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                ViewModelProviders.of(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        if(unbinder == null){
            unbinder = ButterKnife.bind(this, root);
        }
        prefs = ((MainActivity)getActivity()).getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        retrieveData();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);



        historyViewModel.getHistory().observe(getViewLifecycleOwner(), new Observer<List<EcgResult>>() {
            @Override
            public void onChanged(List<EcgResult> list) {
                settingAdapter(list);
            }
        });
        return root;
    }



    private void retrieveData(){
        String id  = prefs.getString(Constants.ID, "not-found");
        historyViewModel.retrieveHistory(((MainActivity)getActivity()), id, historyViewModel);
    }

    private void settingAdapter(List<EcgResult> list) {
        String email = prefs.getString(Constants.EMAIL, "not-found");
        HistoryAdapter adapter = new HistoryAdapter(getContext(), ((MainActivity)getActivity()),email, list);
        recyclerView.setAdapter(adapter);
    }
}
