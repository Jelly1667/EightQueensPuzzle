package miner.chessfun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BoardScreen extends AppCompatActivity {

    // UI thread
    private static final String TAG1 = "Board Screen Thread";

    // Save handle to thread
    public Thread Work;

    //
    // Main program
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queens_placement);

        // Cancel Button
        final Button btnCancel = (Button) findViewById(R.id.btnCancel);

        // Run Button
        final Button btnRun = (Button) findViewById(R.id.btnRun);

        // User Input
        final EditText userInput = (EditText)findViewById(R.id.InputSrtPostition);

        //
        // Handle run event
        //

        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Disable button
                btnRun.setEnabled(false);

                // Set input to the Square
                String Square;
                Square = userInput.getText().toString();

                // Log steps
                Log.v(TAG1, "Start Playing The Game.");

                // Call game thread with square
                PlayGameThread Game = new PlayGameThread(BoardScreen.this, Square);
                Work = new Thread(Game, "Queens Back Tracking Function");
                Work.start();

            }
        });

        //
        // Handle cancel event
        //

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Log steps
                Log.v(TAG1, "Cancel playing The Game.");

                // Stop the thread with a message
                Work.interrupt();
            }
        });
    }
}



