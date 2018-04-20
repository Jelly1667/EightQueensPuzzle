package miner.chessfun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class QueensPlacement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queens_placement);

        //Image Test
        final ImageButton btn21 = (ImageButton) findViewById(R.id.r21);
        final ImageButton btn98 = (ImageButton) findViewById(R.id.r98);
        final Button ImageTst = (Button) findViewById(R.id.ImageTest);
        ImageTst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn98.setImageResource(R.drawable.ic_knight);
                btn21.setImageResource(R.drawable.ic_queen);
            }
        });
    }
}
