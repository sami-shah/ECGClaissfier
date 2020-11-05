package com.mlclassifier.ecgclaissfier.ui.historydetail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mlclassifier.ecgclaissfier.BaseActivity;
import com.mlclassifier.ecgclaissfier.R;
import com.mlclassifier.ecgclaissfier.model.Constants;
import com.mlclassifier.ecgclaissfier.model.EcgResult;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HistoryDetailActivity extends BaseActivity {
    private static final String TAG = "HistoryDetailActivity";
    private Unbinder unbinder;
    private Bundle data;
    private EcgResult result;
    private String emailS;

    @BindView(R.id.email)
    TextView email;

    @BindView(R.id.results)
    TextView results;

    @BindView(R.id.date)
    TextView date;

    @BindView(R.id.testId)
    TextView testId;

    @BindView(R.id.userId)
    TextView userId;

    @BindView(R.id.imageClassify)
    ImageView imageClassify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        if(unbinder == null){
         unbinder = ButterKnife.bind(this);
        }
        data = getIntent().getExtras();
        emailS = data.getString(Constants.EMAIL);
        result = (EcgResult) data.getSerializable(Constants.HISTORY_DETAIL);
        settingValues(result);




    }

    private void settingValues(EcgResult result) {
        String dateS = TimeAgo(Long.parseLong(result.getCreated_at().getDate()));
        email.setText(emailS);
        results.setText(result.getEcg_result());
        date.setText(dateS);
        testId.setText(result.getId());
        userId.setText(result.getUser_id());
        Picasso.get().load(Constants.URL_IMAGES + result.getEcg_image()).into(imageClassify);
    }
}
