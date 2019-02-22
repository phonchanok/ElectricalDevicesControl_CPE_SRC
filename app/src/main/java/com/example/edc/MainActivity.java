package com.example.edc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton btnOne;
    private ImageButton btnTwo;
    private ImageButton btnThree;
    private ImageButton btnFour;
    private ToggleButton tbnOne;
    private ToggleButton tbTwo;
    private ToggleButton tbThree;
    private ToggleButton tbFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
