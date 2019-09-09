package multi.item.select.in.grid.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class FullScreenActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String text;
    private int image;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        initView();
    }

    public void initView()
    {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        text = bundle.getString("text");
        image = bundle.getInt("image");

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        imageView=findViewById(R.id.full_screen_image_view);

        toolbar.setTitle(text);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageView.setImageResource(image);
    }
}