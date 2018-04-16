package br.edu.utfpr.lucasreisalunos.mobilefinalproject.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseConnection extends SQLiteOpenHelper {

    private static final String DB_NAME    = "sqlite1.db";
    private static final int    DB_VERSION = 1;

    private static DatabaseConnection instance;

    private Context context;
    public OperationDAO operationDao;

    public static DatabaseConnection getInstance(Context contexto){

        if (instance == null){
            instance = new DatabaseConnection(contexto);
        }

        return instance;
    }

    private DatabaseConnection(Context context){
        super(context, DB_NAME, null, DB_VERSION);

        this.context = context;

        operationDao = new OperationDAO(this);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        operationDao.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        operationDao.dropTable(db);

        onCreate(db);
    }
}
