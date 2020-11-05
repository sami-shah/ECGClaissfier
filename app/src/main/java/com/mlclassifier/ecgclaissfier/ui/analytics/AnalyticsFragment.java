package com.mlclassifier.ecgclaissfier.ui.analytics;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.mlclassifier.ecgclaissfier.MainActivity;
import com.mlclassifier.ecgclaissfier.R;
import com.mlclassifier.ecgclaissfier.model.EcgResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;
import static com.mlclassifier.ecgclaissfier.model.Constants.MY_PREFS_NAME;

public class AnalyticsFragment extends Fragment {
    private static final String TAG = "AnalyticsFragment";
    Unbinder unbinder;

    private AnalyticsViewModel viewModel;
    List<String> positive = new ArrayList<String>();
    List<String> negative = new ArrayList<String>();


    @BindView(R.id.barChart)
    BarChart barChart;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =
                ViewModelProviders.of(this).get(AnalyticsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_analytics, container, false);
        if(unbinder == null){
            unbinder = ButterKnife.bind(this, root);
        }
        fetchAnalytics();

        viewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<EcgResult>>() {
            @Override
            public void onChanged(List<EcgResult> list) {
                settingStatistics(list);
            }
        });
        return root;
    }

    private void settingStatistics(List<EcgResult> list) {
        for (int i = 0; i < list.size(); i++) {
            Log.d(TAG, "settingStatistics: "+ list.get(i).getEcg_result());
            if(list.get(i).getEcg_result().equals("Negative")){
                negative.add(list.get(i).getEcg_result());
            }else {
                positive.add(list.get(i).getEcg_result());
            }
        }


        List<BarEntry> value0  = new ArrayList<>();
        List<BarEntry> value1  = new ArrayList<>();


        value0.add(new BarEntry(0, negative.size(), "Negative"));
        value1.add(new BarEntry(1, positive.size(), "Positive"));

        BarDataSet negativeDataset = new BarDataSet(value0, "Negative");
        BarDataSet positiveDataset = new BarDataSet(value1, "Positive");


        negativeDataset.setColor(getResources().getColor(R.color.red));
        positiveDataset.setColor(getResources().getColor(R.color.green));

        barChart.setTouchEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setDragEnabled(true);
        List<IBarDataSet> bars = new ArrayList<>();
        bars.add(negativeDataset);
        bars.add(positiveDataset);

        BarData data = new BarData(bars);
        barChart.setData(data);
        barChart.setFitBars(true);

        Description description = new Description();
        description.setText(getString(R.string.description));
        barChart.setDescription(description);
        barChart.invalidate();



    }

    private void fetchAnalytics() {
        viewModel.fetchAnalytics(((MainActivity)getActivity()), viewModel);
    }


}
