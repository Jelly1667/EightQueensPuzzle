package miner.chessfun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //btnPost Onclick
        final Button OpenAct = (Button) findViewById(R.id.OpenActivity);
        OpenAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startPostPage = new Intent(v.getContext(), BoardScreen.class);
                startActivity(startPostPage);
            }
        });

    }
}
