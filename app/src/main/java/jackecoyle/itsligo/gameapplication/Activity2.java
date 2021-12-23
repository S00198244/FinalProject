package jackecoyle.itsligo.gameapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activity2 extends AppCompatActivity implements SensorEventListener {
    private final int BLUE = 1; // North
    private final int RED = 2; // East
    private final int YELLOW = 3; // South
    private final int GREEN = 4; // West

    public static  final String SHARED_PREFS = "sharedPrefers";
    public static final String SCORE = "score";

    private int score;

    int[] arrayB;
    List<Integer> mySequence = new ArrayList<Integer>(); // = new int[4];
    //int arrayIndex = 0;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    TextView tvSequence;
    TextView tvScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        Bundle extras = getIntent().getExtras();
        arrayB = extras.getIntArray("numbers");


        tvSequence = findViewById(R.id.tvSequence);
        tvScore = findViewById(R.id.tvScore);

//        tvSequence.append(Integer.toString(arrayB[0]) + Integer.toString(arrayB[1]) + Integer.toString(arrayB[2]) + Integer.toString(arrayB[3]));

        // we are going to use the sensor service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        loadData();
        updateViews();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        //int a = event.accuracy;

        if (Math.abs(x) > 0.1f) {

        }

        if (Math.abs(y) > 0.1f) {

        }

        if (Math.abs(z) > 0.1f) {
//            tvz.setText(String.valueOf(z));
//            tvAccur.setText(String.valueOf(a));
        }

        if (mySequence.size() != 4) {

            if (x < -0.2f) {
                //North tilt
                tvSequence.append("1");
                mySequence.add(BLUE);

            } else if (x > 0.3f) {
                //South Tilt
                tvSequence.append("3");
                mySequence.add(YELLOW);

            } else if (y < -0.2f) {

                //West tilt
                tvSequence.append("4");
                mySequence.add(GREEN);

            } else if (y > 0.15f) {
                //East tilt
                tvSequence.append("2");
                mySequence.add(RED);
            }

        }
        else
        {
            int[] arrayA = mySequence.stream().mapToInt(i->i).toArray();

            if (Arrays.equals(arrayA, arrayB))
            {

                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();

                score++;

                saveData();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);

            }
            else if (!Arrays.equals(arrayA, arrayB)){

                Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), Activity3.class);
                intent.putExtra("score", score);
                startActivity(intent);

                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt(SCORE, 0);
            }

        }
    }

    public void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(SCORE, score);

        editor.apply();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        score = sharedPreferences.getInt(SCORE, 0);
    }

    public void updateViews() {
        tvScore.setText(String.valueOf(score));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /*
     * When the app is brought to the foreground - using app on screen
     */
    protected void onResume() {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    /*
     * App running but not on screen - in the background
     */
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);    // turn off listener to save power    }
    }
}