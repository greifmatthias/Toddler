package be.greifmatthias.toddler.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import be.greifmatthias.toddler.Models.User;
import be.greifmatthias.toddler.R;

public class ToddlerDetailActivity extends Activity {

    private TextView _tvName;

    private ImageView _ivMore;
    private View _llActions;
    private View _rlOverlay;

    private User _toddler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toddler_detail);

        this._toddler = User.get(this.getIntent().getIntExtra("toddlerId", 0));

//        Get controls
        this._tvName = findViewById(R.id.tvName);
        this._ivMore = findViewById(R.id.ivMore);
        this._llActions = findViewById(R.id.llActions);
        this._rlOverlay = findViewById(R.id.rlOverlay);

        this._ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleActions(_llActions.getVisibility() == View.VISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        this._tvName.setText(this._toddler.getName() + " " + this._toddler.getFamname());
    }

    private void toggleActions(boolean close){
        if(close){
            this._llActions.setVisibility(View.GONE);
            this._rlOverlay.setVisibility(View.GONE);
            this._ivMore.setImageResource(R.drawable.ic_round_more_vert);
        }else{
            this._llActions.setVisibility(View.VISIBLE);
            this._rlOverlay.setVisibility(View.VISIBLE);
            this._ivMore.setImageResource(R.drawable.ic_round_close);
        }
    }
}
