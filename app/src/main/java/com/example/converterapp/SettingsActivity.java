package com.example.converterapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Spinner sourceCurrencySpinner, targetCurrencySpinner;
    private EditText conversionRateEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sourceCurrencySpinner = findViewById(R.id.sourceCurrencySpinner);
        targetCurrencySpinner = findViewById(R.id.targetCurrencySpinner);
        conversionRateEditText = findViewById(R.id.conversionRateEditText);
        saveButton = findViewById(R.id.saveButton);

        // Luo adapterit ja aseta ne spinnereihin
        ArrayAdapter<CharSequence> currencyAdapter = ArrayAdapter.createFromResource(this, R.array.currencies, android.R.layout.simple_spinner_item);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sourceCurrencySpinner.setAdapter(currencyAdapter);
        targetCurrencySpinner.setAdapter(currencyAdapter);

        sourceCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Käsittele valittu lähdevaluutta ja tallenna se SharedPreferencesiin
                String selectedSourceCurrency = parent.getItemAtPosition(position).toString();
                saveSourceCurrency(selectedSourceCurrency);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ei valittu mitään
            }
        });

        targetCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Käsittele valittu kohdevaluutta ja tallenna se SharedPreferencesiin
                String selectedTargetCurrency = parent.getItemAtPosition(position).toString();
                saveTargetCurrency(selectedTargetCurrency);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ei valittu mitään
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tallenna muuntokerroin SharedPreferencesiin
                String conversionRateStr = conversionRateEditText.getText().toString();
                double conversionRate = Double.parseDouble(conversionRateStr);
                saveConversionRate(conversionRate);

                // Palaa takaisin ConverterActivityyn
                finish();
            }
        });
    }

    private void saveSourceCurrency(String currency) {
        SharedPreferences sharedPreferences = getSharedPreferences("CurrencyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sourceCurrency", currency);
        editor.apply();
    }

    private void saveTargetCurrency(String currency) {
        SharedPreferences sharedPreferences = getSharedPreferences("CurrencyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("targetCurrency", currency);
        editor.apply();
    }

    private void saveConversionRate(double rate) {
        SharedPreferences sharedPreferences = getSharedPreferences("CurrencyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("conversionRate", (float) rate);
        editor.apply();
    }
}
