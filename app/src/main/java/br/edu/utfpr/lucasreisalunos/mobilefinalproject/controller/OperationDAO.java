package br.edu.utfpr.lucasreisalunos.mobilefinalproject.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.utfpr.lucasreisalunos.mobilefinalproject.model.Operation;

public class OperationDAO {

    public static final String TABLE = "OPERATIONS";
    public static final String ID = "ID";
    public static final String TIMESTAMP   = "TIMESTAMP";
    public static final String BASEVALUE     = "BASEVALUE";
    public static final String PAYOUT  = "PAYOUT";
    public static final String MARTINGALE  = "MARTINGALE";
    private static final String WINLOSS = "WINLOSS";
    private static final String RESULT = "RESULT";


    private DatabaseConnection connection;
    public List<Operation> operations;

    public OperationDAO(DatabaseConnection conexaoDatabase){
        connection = conexaoDatabase;
        operations = new ArrayList<Operation>();
    }

    public void createTable(SQLiteDatabase database){

        String sql = "CREATE TABLE " + TABLE + "(" +
           ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
           TIMESTAMP  + " TEXT NOT NULL, " +
           BASEVALUE + " REAL, " +
           PAYOUT  + " REAL, " +
           MARTINGALE  + " INTEGER, " +
           WINLOSS  + " TEXT, " +
           RESULT  + " REAL)";

        database.execSQL(sql);
    }

    public void dropTable(SQLiteDatabase database){

        String sql = "DROP TABLE IF EXISTS " + TABLE;

        database.execSQL(sql);
    }

    public boolean insertDb(Operation operation){

        ContentValues values = new ContentValues();

        values.put(TIMESTAMP, operation.getTimeStamp());
        values.put(BASEVALUE, operation.getBaseValue());
        values.put(PAYOUT, operation.getPayout());
        values.put(MARTINGALE, operation.getMartingale());
        values.put(WINLOSS, operation.getWinLoss());
        values.put(RESULT, operation.getResult());

        long id = connection.getWritableDatabase().insert(TABLE,
                                                       null,
                                                       values);

        operation.setId(id);

        operations.add(operation);

        orderList();

        return true;
    }

    public boolean updateDb(Operation operation){

        ContentValues values = new ContentValues();

        values.put(TIMESTAMP, operation.getTimeStamp());
        values.put(BASEVALUE, operation.getBaseValue());
        values.put(PAYOUT, operation.getPayout());
        values.put(MARTINGALE, operation.getMartingale());
        values.put(WINLOSS, operation.getWinLoss());
        values.put(RESULT, operation.getResult());

        String[] args = {String.valueOf(operation.getId())};

        connection.getWritableDatabase().update(TABLE,
                                             values,
                                             ID + " = ?",
                                             args);

        orderList();

        return true;
    }

    public boolean removeDb(Operation operation){

        String[] args = {String.valueOf(operation.getId())};

        connection.getWritableDatabase().delete(TABLE,
                                             ID + " = ?",
                                             args);
        operations.remove(operation);

        return true;
    }

    public void loadAll(){

        operations.clear();

        String sql = "SELECT * FROM " + TABLE + " ORDER BY " + TIMESTAMP;

        Cursor cursor = connection.getReadableDatabase().rawQuery(sql, null);

        int columnTimestamp  = cursor.getColumnIndex(TIMESTAMP);
        int columnId    = cursor.getColumnIndex(ID);
        int columnResult = cursor.getColumnIndex(RESULT);

        while(cursor.moveToNext()){

            Operation operation = new Operation(cursor.getString(columnTimestamp));

            operation.setId(cursor.getLong(columnId));
            operation.setResult(cursor.getInt(columnResult));

            operations.add(operation);
        }

        cursor.close();
    }

    public Operation operationById(long id){

        for (Operation op: operations){

            if (op.getId() == id){
                return op;
            }
        }

        return null;
    }

    public void orderList(){
        Collections.sort(operations, Operation.comparator);
    }
}
