package br.edu.utfpr.lucasreisalunos.mobilefinalproject.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.DecimalFormat;

import br.edu.utfpr.lucasreisalunos.mobilefinalproject.R;
import br.edu.utfpr.lucasreisalunos.mobilefinalproject.controller.DatabaseConnection;
import br.edu.utfpr.lucasreisalunos.mobilefinalproject.model.Operation;
import br.edu.utfpr.lucasreisalunos.mobilefinalproject.utils.UtilsGUI;

public class ShowOperations extends AppCompatActivity {

    public static final double DAY_AMOUNT = 100.0, CURRENT_AMOUNT = 100.0, OPERATION_PERCENTAGE = 1.8;

    public double opPercentage;
    public double dayAmount;
    public double currentAmount;
    public double opValue;

    private ListView listViewOperation;
    private ArrayAdapter<Operation> listAdapter;

    public static final String MODE    = "MODE";
    public static final String ID      = "ID";
    public static final int NEW = 1;
    public static final int UPDATE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_operations);
        setTitle(getString(R.string.activity_show_operations));

        DatabaseConnection connection = DatabaseConnection.getInstance(this);
        connection.operationDao.loadAll();

        listViewOperation = (ListView) findViewById(R.id.listViewOperations);

        listViewOperation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Operation operation = (Operation) parent.getItemAtPosition(position);

                openOperation(operation);
            }
        });

        populateList();

        registerForContextMenu(listViewOperation);


        //RECEIVING FROM PREVIOUS ACTIVITY
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        this.opPercentage = intent.getDoubleExtra("OPERATION_PERCENTAGE",1.2);
        this.dayAmount = intent.getDoubleExtra("DAY_AMOUNT",140.0);
        this.currentAmount = intent.getDoubleExtra("CURRENT_AMOUNT", 100.0);

        this.opValue = (opPercentage*dayAmount)/100;

        //Formatting decimal houses
        DecimalFormat format = new DecimalFormat("#.##");
        this.opValue = Double.valueOf(format.format(this.opValue));

        //RECEIVING FROM PREVIOUS ACTIVITY
    }

    private void populateList(){
        DatabaseConnection connection = DatabaseConnection.getInstance(this);

        listAdapter = new ArrayAdapter<Operation>(this,
                android.R.layout.simple_list_item_1,
                connection.operationDao.operations);

        listViewOperation.setAdapter(listAdapter);
    }

    private void newOperation(){
        Intent intent = new Intent(this, NewOperation.class);

        intent.putExtra(MODE, NEW);

        intent.putExtra("DAY_AMOUNT", this.dayAmount);
        intent.putExtra("CURRENT_AMOUNT", this.currentAmount);
        intent.putExtra("OPERATION_PERCENTAGE", this.opPercentage);
        intent.putExtra("OPERATION_VALUE", this.opValue);

        startActivityForResult(intent, NEW);
    }

    private void openOperation(Operation operation){
        Intent intent = new Intent(this, UpdateOperation.class);

        intent.putExtra(MODE, UPDATE);
        intent.putExtra(ID, operation.getId());

        startActivityForResult(intent, UPDATE);
    }

    private void removeOperation(final Operation operation){

        String message = getString(R.string.delete_confirmation_message)
                + "\n" + operation.getTimeStamp();

        DialogInterface.OnClickListener listener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:
                                DatabaseConnection connection =
                                        DatabaseConnection.getInstance(ShowOperations.this);

                                connection.operationDao.removeDb(operation);

                                listAdapter.notifyDataSetChanged();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

        UtilsGUI.confirmAction(this, message, listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == NEW || requestCode == UPDATE) &&
                resultCode == Activity.RESULT_OK){

            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.menuItemNewOperation:
                newOperation();
                return true;
            case R.id.menuItemCancel:
                cancel();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.selected_operation, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info;

        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Operation operation = (Operation) listViewOperation.getItemAtPosition(info.position);

        switch(item.getItemId()){
            case R.id.menuItemOpen:
                openOperation(operation);
                return true;
            case R.id.menuItemApagar:
                removeOperation(operation);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    private void cancel(){
        setResult(Activity.RESULT_OK);
        finish();
    }
}