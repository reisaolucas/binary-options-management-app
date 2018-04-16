package br.edu.utfpr.lucasreisalunos.mobilefinalproject.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

import br.edu.utfpr.lucasreisalunos.mobilefinalproject.R;

public class DayInfos extends AppCompatActivity {

    public static final double DAY_AMOUNT = 100.0, CURRENT_AMOUNT = 100.0, OPERATION_PERCENTAGE = 1.8, DAY_PROFIT = 100.0, DAY_PROFIT_PERCENTAGE = 100.0;

    public EditText editTextValueDayAmount, editTextValueCurrentAmount, editTextValueOpPercentage;
    public TextView txtViewValueDayProfit, txtViewValuePercentDayProfit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_infos);
        setTitle(getString(R.string.activity_day_infos_title));

        editTextValueDayAmount = (EditText) findViewById(R.id.editTextValueDayAmount);
        editTextValueCurrentAmount = (EditText) findViewById(R.id.editTextValueCurrentAmount);
        editTextValueOpPercentage = (EditText) findViewById(R.id.editTextValueOpPercentage);

        txtViewValueDayProfit = (TextView) findViewById(R.id.txtViewValueDayProfit);
        txtViewValuePercentDayProfit = (TextView) findViewById(R.id.txtViewValuePercentDayProfit);


    }

    public void menuCalculateProfitOnClick(View view){

        double dayAmount = Double.parseDouble(editTextValueDayAmount.getText().toString());
        double currentAmount = Double.parseDouble(editTextValueCurrentAmount.getText().toString());

        double dayProfit = currentAmount - dayAmount;
        double dayProfitPercentage = (dayProfit/dayAmount)*100;

        DecimalFormat format = new DecimalFormat("##.##");
        dayProfit = Double.valueOf(format.format(dayProfit));
        dayProfitPercentage = Double.valueOf(format.format(dayProfitPercentage));

        txtViewValueDayProfit.setText(String.valueOf(dayProfit));
        txtViewValuePercentDayProfit.setText(String.valueOf(dayProfitPercentage));
    }


    public void buttonShowOperationsOnClick (View view){
        Intent showOperations = new Intent(this, ShowOperations.class);

        double dayAmount = Double.parseDouble(editTextValueDayAmount.getText().toString());
        double currentAmount = Double.parseDouble(editTextValueCurrentAmount.getText().toString());
        double operationPercentage = Double.parseDouble(editTextValueOpPercentage.getText().toString());

        showOperations.putExtra("DAY_AMOUNT", dayAmount);
        showOperations.putExtra("CURRENT_AMOUNT", currentAmount);
        showOperations.putExtra("OPERATION_PERCENTAGE", operationPercentage);

        startActivity(showOperations);
    }
}
