package family_reich.com.todoapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends ActionBarActivity {

    public static final String KEY = "ID";  //Variable zur Übergabe der ID

    Time today = new Time(Time.getCurrentTimezone());
    DBAdapter myDB;
    EditText etTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Zuordnung Eingabe

        etTasks = (EditText) findViewById(R.id.editTextTask);

        //Starten der benötigten Methoden
        openDB();               //Öffnen der DB
        populateListView();     //ListView generieren
        listViewItemClick();    //Aktion durch Tippen auf jeweiligen Eintrag im ListView
        // listViewItemLongClick();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openDB(){

        myDB = new DBAdapter(this);
        myDB.open();

    }

    public void onClick_AddTask(View view){

        today.setToNow();
        String timestamp = today.format("%Y-%m-%d %H:%M:%S");

        if(!TextUtils.isEmpty(etTasks.getText().toString())){

            myDB.insertRow(etTasks.getText().toString(), timestamp);

        }

        populateListView();

    }

    private void populateListView() {

        Cursor cursor = myDB.getAllRows();

        //Das Array "fromFieldNames" definiert sie Spalten die im später im ListView ausgelesen werden sollen.
        String[] fromFieldNames = new String[]{DBAdapter.KEY_ROWID, DBAdapter.KEY_TASK};

//        System.out.println(fromFieldNames.length);


        //Das Array "toViewIDs" hinterlegt für die TextViews Nummerierungen.
        int[] toViewIDs = new int[]{R.id.textViewItemNumber, R.id.textViewItemTask};

        // getBaseContext()

        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.item_layout, cursor, fromFieldNames, toViewIDs, 0);

        ListView myList = (ListView) findViewById(R.id.listViewTasks);
        myList.setAdapter(myCursorAdapter);

    }

    private void updateTask(long id){

        Cursor cursor = myDB.getRow(id);

        if(cursor.moveToFirst()){

            String task = etTasks.getText().toString();
            today.setToNow();
            String date = today.format("%Y-%m-%d %H:%M:%S");
            myDB.updateRow(id,task,date);
        }

        cursor.close();

    }

    private void listViewItemClick(){

        ListView myList = (ListView) findViewById(R.id.listViewTasks);


        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /*

                updateTask(id);
                populateListView();

                */

                String pos = String.valueOf(id);

                System.out.println(pos);

                Intent i = new Intent(MainActivity.this, Register.class);
                i.putExtra(KEY, pos);
                startActivity(i);

            }
        });

    }

    public void  onClick_DeleteTasks(View view){

        myDB.deleteAll();
        populateListView();

    }


    private void listViewItemLongClick(){

        ListView myList = (ListView) findViewById(R.id.listViewTasks);
        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                myDB.deleteRow(id);
                populateListView();

                return false;
            }
        });



    }

}
