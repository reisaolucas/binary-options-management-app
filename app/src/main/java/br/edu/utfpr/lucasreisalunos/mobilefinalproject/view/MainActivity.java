package br.edu.utfpr.lucasreisalunos.mobilefinalproject.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.edu.utfpr.lucasreisalunos.mobilefinalproject.R;
import br.edu.utfpr.lucasreisalunos.mobilefinalproject.view.DayInfos;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.activity_main_title));
    }

    public void menuOperateOnClick(View view){
        Intent dayInfos = new Intent(this, DayInfos.class);
        startActivity(dayInfos);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuHelp:
                Intent intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
