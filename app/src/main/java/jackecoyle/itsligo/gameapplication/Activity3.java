package jackecoyle.itsligo.gameapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Activity3 extends AppCompatActivity {
    private int score;

    TextView tvUserScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        Bundle extras = getIntent().getExtras();
        score = extras.getInt("score");

        tvUserScore = findViewById(R.id.tvUserScore);
        tvUserScore.append(String.valueOf(score));
    }

    public void playAgain(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    public void viewHighScores(View view) {
        Intent intent = new Intent(getApplicationContext(), Activity4.class);
        intent.putExtra("score", score);
        startActivity(intent);
    }

}