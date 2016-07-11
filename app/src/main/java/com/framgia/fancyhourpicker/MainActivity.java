package com.framgia.fancyhourpicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.framgia.hourpicker.FancyHourPicker;

public class MainActivity extends AppCompatActivity {
    private FancyHourPicker mFancyHourPicker;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFancyHourPicker = (FancyHourPicker) findViewById(R.id.hour_picker);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mFancyHourPicker.setHour(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
