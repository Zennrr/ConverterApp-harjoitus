package com.example.converterapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ConverterActivity extends AppCompatActivity {

    private EditText sourceCurrencyEditText, targetCurrencyEditText;
    private Button convertButton, settingsButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        sourceCurrencyEditText = findViewById(R.id.sourceCurrencyEditText);
        targetCurrencyEditText = findViewById(R.id.targetCurrencyEditText);
        convertButton = findViewById(R.id.convertButton);
        resultTextView = findViewById(R.id.resultTextView);
        settingsButton = findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Luo Intent siirtymistä varten
                Intent intent = new Intent(ConverterActivity.this, SettingsActivity.class);
                startActivity(intent); // Käynnistä SettingsActivity
            }
        });

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hae syötetyt lähde- ja kohdevaluutat
                String sourceAmountStr = sourceCurrencyEditText.getText().toString();
                double sourceAmount = Double.parseDouble(sourceAmountStr);

                // Hae tallennetut lähde- ja kohdevaluutat SharedPreferencesista
                String sourceCurrency = getSourceCurrency();
                String targetCurrency = getTargetCurrency();

                // Hae tallennettu muuntokerroin SharedPreferencesista
                double conversionRate = getConversionRate();

                // Tee valuuttamuunnos
                double targetAmount = sourceAmount * conversionRate;

                // Näytä tulos TextView:ssä
                String resultText = sourceAmountStr + " " + sourceCurrency + " = " + targetAmount + " " + targetCurrency;
                resultTextView.setText(resultText);
            }
        });
    }

    private String getSourceCurrency() {
        SharedPreferences sharedPreferences = getSharedPreferences("CurrencyPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("sourceCurrency", "EUR");
    }

    private String getTargetCurrency() {
        SharedPreferences sharedPreferences = getSharedPreferences("CurrencyPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("targetCurrency", "USD");
    }

    private double getConversionRate() {
        SharedPreferences sharedPreferences = getSharedPreferences("CurrencyPrefs", MODE_PRIVATE);
        return sharedPreferences.getFloat("conversionRate", 1.0f);
    }
}
