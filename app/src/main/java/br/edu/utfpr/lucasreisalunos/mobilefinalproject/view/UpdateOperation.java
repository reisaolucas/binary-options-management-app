package br.edu.utfpr.lucasreisalunos.mobilefinalproject.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import br.edu.utfpr.lucasreisalunos.mobilefinalproject.R;
import br.edu.utfpr.lucasreisalunos.mobilefinalproject.controller.DatabaseConnection;
import br.edu.utfpr.lucasreisalunos.mobilefinalproject.model.Operation;
import br.edu.utfpr.lucasreisalunos.mobilefinalproject.utils.UtilsGUI;

import static android.R.attr.mode;

public class UpdateOperation extends AppCompatActivity {

    public EditText editTextValueBaseValue, editTextValueOpResult;
    public TextView txtViewValueOpId, txtViewValueTimeStamp;

    private int mode;
    private Operation operation;

    private Spinner spinnerMartingale;

    private RadioGroup radioGroupWinLoss;
    private RadioButton radioButtonWin;
    private RadioButton radioButtonLoss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_operation);

        setTitle(R.string.activity_update_operation);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mode = bundle.getInt(ShowOperations.MODE);

        long id = bundle.getLong(ShowOperations.ID);

        DatabaseConnection connection = DatabaseConnection.getInstance(this);
        operation = connection.operationDao.operationById(id);

        txtViewValueOpId = (TextView) findViewById(R.id.txtViewValueOpId);
        txtViewValueOpId.setText(Long.toString(operation.getId()));

        txtViewValueTimeStamp = (TextView) findViewById(R.id.txtViewValueTimeStamp);
        txtViewValueTimeStamp.setText(operation.getTimeStamp());

        editTextValueBaseValue = (EditText)  findViewById(R.id.editTextValueBaseValue);
        editTextValueBaseValue.setText(Double.toString(operation.getBaseValue()));

        spinnerMartingale = (Spinner) findViewById(R.id.spinnerMartingale);
        if(operation.getMartingale()==0) spinnerMartingale.setSelection(0);
        if(operation.getMartingale()==1) spinnerMartingale.setSelection(1);
        if(operation.getMartingale()==2) spinnerMartingale.setSelection(2);
        if(operation.getMartingale()==3) spinnerMartingale.setSelection(3);

        radioGroupWinLoss = (RadioGroup) findViewById(R.id.radioGroupWinLoss);
        radioButtonWin = (RadioButton) findViewById(R.id.radioButtonWin);
        radioButtonLoss = (RadioButton) findViewById(R.id.radioButtonLoss);

        if(operation.getWinLoss().equals("Acerto")||operation.getWinLoss().equals("Win")){
            radioButtonWin.setChecked(true);
            radioButtonLoss.setChecked(false);
        }
        if(operation.getWinLoss().equals("Erro")||operation.getWinLoss().equals("Loss")){
            radioButtonWin.setChecked(false);
            radioButtonLoss.setChecked(true);
        }

        editTextValueOpResult = (EditText)  findViewById(R.id.editTextValueOpResult);
        editTextValueOpResult.setText(Double.toString(operation.getResult()));

    }

    public String getRadioButton() {
        View rdButtonWinLossView = radioGroupWinLoss.findViewById(radioGroupWinLoss.getCheckedRadioButtonId());
        int rdButtonWinLossId = radioGroupWinLoss.indexOfChild(rdButtonWinLossView);
        RadioButton rdButtonSelected = (RadioButton) radioGroupWinLoss.getChildAt(rdButtonWinLossId);
        return (String) rdButtonSelected.getText();
    }

    private void saveUpdate(){

        double baseValue = Double.parseDouble(editTextValueBaseValue.getText().toString());
        int martingale = spinnerMartingale.getSelectedItemPosition();
        String winLoss = getRadioButton();
        double result = Double.parseDouble(editTextValueOpResult.getText().toString());

        DatabaseConnection connection = DatabaseConnection.getInstance(this);

        operation.setBaseValue(baseValue);
        operation.setMartingale(martingale);
        operation.setWinLoss(winLoss);
        operation.setResult(result);

        connection.operationDao.updateDb(operation);

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
