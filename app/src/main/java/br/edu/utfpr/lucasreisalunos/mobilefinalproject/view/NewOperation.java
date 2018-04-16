package br.edu.utfpr.lucasreisalunos.mobilefinalproject.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.edu.utfpr.lucasreisalunos.mobilefinalproject.R;
import br.edu.utfpr.lucasreisalunos.mobilefinalproject.controller.DatabaseConnection;
import br.edu.utfpr.lucasreisalunos.mobilefinalproject.model.Operation;

public class NewOperation extends AppCompatActivity {

    public static final double OP_VALUE = 100.0, PAYOUT = 100.0, OP_RESULT = 100.0;
    public static final int MARTINGALE = 0;
    public static final String WIN_LOSS = "Result";

    public EditText editTextValuePayout;
    public TextView txtViewValueOpValue, txtViewValueOpResult;

    private RadioGroup radioGroupWinLoss;

    private Spinner spinnerValueMartingale;

    private double opValue, opResult, payoutValue;

    private int mode;
    private Operation operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_operation);
        setTitle(getString(R.string.activity_new_operation));

        //Getting extras from previous activity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mode = bundle.getInt(ShowOperations.MODE);

        //Naming the fields
        editTextValuePayout = (EditText) findViewById(R.id.editTextValuePayout);

        txtViewValueOpValue = (TextView) findViewById(R.id.txtViewValueOpValue);
        txtViewValueOpResult = (TextView) findViewById(R.id.txtViewValueOpResult);

        radioGroupWinLoss = (RadioGroup) findViewById(R.id.radioGroupWinLoss);

        spinnerValueMartingale = (Spinner) findViewById(R.id.spinnerValueMartingale);

        //Using data from previous activity
        double opPercentage = intent.getDoubleExtra("OPERATION_PERCENTAGE",1.5);
        double dayAmount = intent.getDoubleExtra("DAY_AMOUNT",150.0);

        //Calculating base value
        this.opValue = (opPercentage*dayAmount)/100;

        //Formatting decimal houses
        DecimalFormat format = new DecimalFormat("#.##");
        this.opValue = Double.valueOf(format.format(this.opValue));

        txtViewValueOpValue.setText(String.valueOf(this.opValue));

    }

    public String getRadioButton() {
        View rdButtonWinLossView = radioGroupWinLoss.findViewById(radioGroupWinLoss.getCheckedRadioButtonId());
        int rdButtonWinLossId = radioGroupWinLoss.indexOfChild(rdButtonWinLossView);
        RadioButton rdButtonSelected = (RadioButton) radioGroupWinLoss.getChildAt(rdButtonWinLossId);
        return (String) rdButtonSelected.getText();
    }

    public void menuCalculateResultOnClick(View view){

        this.payoutValue = Double.parseDouble(editTextValuePayout.getText().toString());

        if(getRadioButton().equals("Acerto")||getRadioButton().equals("Win")){
            //In case of win
            switch(spinnerValueMartingale.getSelectedItemPosition()){
                case 0:
                    this.opResult = (this.opValue * this.payoutValue)/100;
                    break;
                case 1:
                    this.opResult = (this.opValue*2 * this.payoutValue)/100;
                    break;
                case 2:
                    this.opResult = (this.opValue*4 * this.payoutValue)/100;
                    break;
                case 3:
                    this.opResult = (this.opValue*8 * this.payoutValue)/100;
                    break;
                default:
                    this.opResult = (this.opValue * this.payoutValue)/100;
                    break;
            }

        }else {
            if (getRadioButton().equals("Erro")||getRadioButton().equals("Loss")) {
                //In case of loss
                switch(spinnerValueMartingale.getSelectedItemPosition()){
                    case 0:
                        this.opResult = 0-this.opValue;
                        break;
                    case 1:
                        this.opResult = 0-this.opValue*2;
                        break;
                    case 2:
                        this.opResult = 0-this.opValue*4;
                        break;
                    case 3:
                        this.opResult = 0-this.opValue*8;
                        break;
                    default:
                        this.opResult = 0-this.opValue;
                        break;
                }
            }
        }
        DecimalFormat format = new DecimalFormat("#.##");
        this.opResult = Double.valueOf(format.format(this.opResult));
        txtViewValueOpResult.setText(String.valueOf(this.opResult));
    }

    private void saveUpdate(){

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date currentTime = Calendar.getInstance().getTime();
        String timeStamp = df.format(currentTime);

        Operation operation = new Operation(timeStamp);

        double baseValue = Double.parseDouble(txtViewValueOpValue.getText().toString());
        int martingale = spinnerValueMartingale.getSelectedItemPosition();
        String winLoss = getRadioButton();
        double result = this.opResult;

        DatabaseConnection connection = DatabaseConnection.getInstance(this);

        operation.setTimeStamp(timeStamp);
        operation.setBaseValue(baseValue);
        operation.setPayout(this.payoutValue);
        operation.setMartingale(martingale);
        operation.setWinLoss(winLoss);
        operation.setResult(result);

        connection.operationDao.insertDb(operation);

        setResult(Activity.RESULT_OK);
        finish();
    }

    private void cancelUpdate(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.operation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.menuItemSave:
                saveUpdate();
                return true;
            case R.id.menuItemCancel:
                cancelUpdate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
