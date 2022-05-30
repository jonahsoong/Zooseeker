package com.example.zookeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.Set;

public class SettingsActivity extends AppCompatActivity {
    private Switch dDir;
    private Switch bDir;
    private Button finish;
    public static int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        dDir = findViewById(R.id.ddir);
        bDir = findViewById(R.id.bdir);
        finish = findViewById(R.id.done);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dDir.setChecked(SettingsActivity.status == 0);
        bDir.setChecked(SettingsActivity.status != 0);
        //SettingsActivity.status = 0;

//        dDir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                dDir.toggle();
//                Log.d("print", "Detailed Clicked");
//                bDir.toggle();
//                //bDir.setChecked(!dDir.isChecked());
//                if (dDir.isChecked())
//                    SettingsActivity.status = 0;
//                else
//                    SettingsActivity.status = 1;
//            }
//        });
//        bDir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                dDir.toggle();
//                Log.d("print", "Brief Clicked");
//                bDir.toggle();
//                //dDir.setChecked(!bDir.isChecked());
//                if (dDir.isChecked())
//                    SettingsActivity.status = 0;
//                else
//                    SettingsActivity.status = 1;
//            }
//        });
//    }
//}
//        dDir.setOnCheckedChangeListener(new Vi
        dDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dDir.toggle();
                bDir.toggle();
                if(dDir.isChecked())
                    SettingsActivity.status = 0;
                else
                    SettingsActivity.status = 1;
                Log.d("print", "Detailed Clicked" + dDir.isChecked() + SettingsActivity.status);

//                if(dDir.isChecked()){
//                    //Log.d("print", "switch t")
//                    dDir.setChecked(false);
//                    bDir.setChecked(true);
//                    SettingsActivity.status = 1;
//                }
//                else{
//                    dDir.setChecked(true);
//                    bDir.setChecked(false);
//                    SettingsActivity.status = 0;
//                }
            }
        });

        bDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dDir.toggle();
                //bDir.toggle();
                if(dDir.isChecked())
                    SettingsActivity.status = 0;
                else
                    SettingsActivity.status = 1;
                Log.d("print", "Brief Clicked" + dDir.isChecked() + SettingsActivity.status);
//                if(bDir.isChecked()){
//                    bDir.setChecked(false);
//                    dDir.setChecked(true);
//                    SettingsActivity.status = 0;
//                }
//                else{
//                    bDir.setChecked(true);
//                    dDir.setChecked(false);
//                    SettingsActivity.status = 1;
//                }
            }
        });
    }
}



