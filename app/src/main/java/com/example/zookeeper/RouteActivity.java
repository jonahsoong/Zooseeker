package com.example.zookeeper;
//Obselete Class and Page!
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class RouteActivity extends AppCompatActivity {
    private Button addButton;
    private EditText toAddText;
    private RouteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route2);
        viewModel = new ViewModelProvider(this)
                .get(RouteViewModel.class);
        this.toAddText = this.findViewById(R.id.text);
        this.addButton = this.findViewById(R.id.add_button);

    }

    public void addButtonClicked(View view) {
        String text = toAddText.getText().toString();
        //Log.d("err", "loadText");
        toAddText.setText("");
        //Log.d("err", "setText");
        viewModel.createRouteItem(text, "");
        //Log.d("err", "createItem");
    }

    public void planButtonClicked(View view) {
        //List<RouteItem> planned = viewModel.getList();
       // Log.d("Planned", planned.toString());
       Intent intent = new Intent(this,PlanActivity.class);
        startActivity(intent);
    }

}