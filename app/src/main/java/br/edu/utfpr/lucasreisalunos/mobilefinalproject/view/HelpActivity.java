package br.edu.utfpr.lucasreisalunos.mobilefinalproject.view;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.edu.utfpr.lucasreisalunos.mobilefinalproject.R;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public void goBackOnClick(View view){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
