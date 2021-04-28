package com.example.punttilaskuri;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

/**
 * This class is attached to the activity that counts BMR meaning calories for
 * the user when the user has chosen all the parameters he/she desires.
 * @author Henri Johansson
 */
public class CalorieCalculator extends AppCompatActivity {

    private int minHeightInt;
    private int minWeightInt;
    private int minAgeInt;
    private int maxWeightInt;
    private int maxHeightInt;
    private int maxAgeInt;

    private BmrCalculator bmrCalc;
    private TextView age;
    private TextView weight;
    private TextView height;
    private TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_calculator);
        //Finding SeekBars
        SeekBar seekBarAge = findViewById(R.id.seekBarAge);
        SeekBar seekBarWeight = findViewById(R.id.seekBarWeight);
        SeekBar seekBarHeight = findViewById(R.id.seekBarHeight);
        //Finding the necessary textViews.
        height = findViewById(R.id.heightNow);
        weight = findViewById(R.id.weightNow);
        age = findViewById(R.id.ageNow);
        output = findViewById(R.id.output);
        TextView maxHeight = findViewById(R.id.heightMax);
        TextView maxWeight = findViewById(R.id.weightMax);
        TextView maxAge = findViewById(R.id.ageMax);

        defineRangeValues();
        //Implementing starting minimums and maximums to show for the user.
        height.setText(getString(R.string.height_unit, minHeightInt));
        weight.setText(getString(R.string.weight_unit, minWeightInt));
        age.setText(getString(R.string.age_unit, minAgeInt));
        maxHeight.setText(getString(R.string.height_unit,maxHeightInt));
        maxWeight.setText(getString(R.string.weight_unit,maxWeightInt));
        maxAge.setText(getString(R.string.age_unit, maxAgeInt));
        //Creating instance of
        bmrCalc = new BmrCalculator(minWeightInt, minHeightInt, 10, true);
        setSeekBarListener(seekBarHeight, seekBarType.HEIGHT, minHeightInt, maxHeightInt);
        setSeekBarListener(seekBarWeight, seekBarType.WEIGHT, minWeightInt, maxWeightInt);
        setSeekBarListener(seekBarAge, seekBarType.AGE, minAgeInt, maxAgeInt);
    }
    //Defining minimums and Maximums for Weight, Height and Age.
    private void defineRangeValues(){
        minHeightInt = 100;
        minWeightInt = 50;
        minAgeInt = 10;
        maxHeightInt = 160 + minWeightInt;
        maxWeightInt = 140 + minWeightInt;
        maxAgeInt = 100;
    }

    private enum seekBarType{
        HEIGHT,
        WEIGHT,
        AGE
    }

    public void setSeekBarListener(SeekBar seekBar, seekBarType typeofBar, int minSeek, int maxSeek){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar.setMax(maxSeek - minSeek);
                switch (typeofBar){
                    case HEIGHT:
                        bmrCalc.setHeightCm(progress + minSeek);
                        height.setText(getString(R.string.height_unit, progress + minSeek));
                        break;
                    case WEIGHT:
                        bmrCalc.setWeightKg(progress + minSeek);
                        weight.setText(getString(R.string.weight_unit, progress + minSeek));
                        break;
                    case AGE:
                        bmrCalc.setAge(progress + minSeek);
                        age.setText(getString(R.string.age_unit, progress + minSeek));
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // autogenerated function stump
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // autogenerated function stump
            }
        });
    }
    //onCLick functions for the buttons used on the view.
    public void onClickCalories(View view){
        output.setText( getString(R.string.output, String.format(Locale.ENGLISH,"%.2f", bmrCalc.calculateBmr())));
    }
    public void onClickMale(View view){
        bmrCalc.setMale(true);
    }
    public void onClickFemale(View view){
        bmrCalc.setMale(false);
    }
}