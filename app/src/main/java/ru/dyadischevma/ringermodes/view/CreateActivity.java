package ru.dyadischevma.ringermodes.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import ru.dyadischevma.ringermodes.R;
import ru.dyadischevma.ringermodes.model.DataViewModel;
import ru.dyadischevma.ringermodes.model.RingerMode;
import ru.dyadischevma.ringermodes.model.RingerModeItem;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        final DataViewModel viewModel = new ViewModelProvider(this).get(DataViewModel.class);

        final EditText editTextName = findViewById(R.id.editTextTextName);
        final EditText editTextRingerMode = findViewById(R.id.editTextTextRingerMode);

        Button button = findViewById(R.id.buttonAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.insertItem(new RingerModeItem(
                        editTextName.getText().toString(),
                        RingerMode.valueOf(editTextRingerMode.getText().toString()),
                        5
                ));
                finish();
            }
        });
    }
}