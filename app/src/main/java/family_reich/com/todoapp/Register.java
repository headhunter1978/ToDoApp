package family_reich.com.todoapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


public class Register extends ActionBarActivity {

    DBAdapter myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /*

        TextView textView = (TextView) findViewById(R.id.text_id);
        textView.setText(getIntent().getExtras().getString(MainActivity.KEY));

        long test = Long.parseLong(getIntent().getExtras().getString(MainActivity.KEY));

        System.out.println(test);

        myDB = new DBAdapter(this);
        myDB.open();
        Cursor cursor = myDB.getRow(test);

        System.out.println(cursor.getString(1));

        TextView textTask = (TextView) findViewById(R.id.textTask);
        textTask.setText(cursor.getString(1));

        */

        openRegister();
    }

    private void openRegister(){

        //Übergabe und Umwandlung id

        long id = Long.parseLong(getIntent().getExtras().getString(MainActivity.KEY));

        //Bestimmen der GUI-Elemente

        EditText textID = (EditText) findViewById(R.id.editTextID);
        EditText textTask = (EditText) findViewById(R.id.editTextTask);
        TextView textDate = (TextView) findViewById(R.id.textDate);

        //Öffnen der DB und Vorbereitung des Cursors

        myDB = new DBAdapter(this);
        myDB.open();
        Cursor cursor = myDB.getRow(id);

        //Ausgabe des jeweiligen Datensatzes

        textID.setText(cursor.getString(0));
        textTask.setText(cursor.getString(1));
        textDate.setText(cursor.getString(2));

        System.out.println(cursor.getString(2));



    }

    public void backToListView(View view) {

//        Intent resultData = new Intent();
        finish();



    }
}


