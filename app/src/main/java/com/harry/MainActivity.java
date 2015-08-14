package com.harry;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    SQLiteDatabase db;
    String[] branches = {"computer", "mechanical", "chemical", "electronics"};
    ArrayList<String> listData = new ArrayList<String>();
    ArrayAdapter<String> listAdapter;
    ListView list;

    ArrayList<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DBHelper helper = new DBHelper(this);
////        helper.onCreate(db);
////        for (int i = 0; i < 5; i++) {
//            helper.addContact("training",30);
////        }
//        helper.readContact();
        // addtion of student
        list = (ListView) findViewById(R.id.list);
        listAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listData);
        list.setAdapter(listAdapter);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                helper.deleteContact(studentList.get(position).getId());
                listAdapter.notifyDataSetChanged();
                return false;
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                helper.updateContact(studentList.get(position));
            }
        });

        Button create = (Button) findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit1 = (EditText) findViewById(R.id.edit1);
                EditText edit2 = (EditText) findViewById(R.id.edit2);
                EditText edit3 = (EditText) findViewById(R.id.edit3);
                String e1 = edit1.getText().toString();
                String e2 = edit2.getText().toString();
                String e3 = edit3.getText().toString();
                if (e1.length() > 0 &&
                        e2.length() > 0 &&
                        e3.length() > 0) {
                    helper.addContact(new Student(e1, e2, e3));
                } else {
                    Toast.makeText(MainActivity.this,"enter the data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button all = (Button) findViewById(R.id.all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentList = helper.readParticularContact("");
                listData.clear();
                for (Student student : studentList) {
                    listData.add(student.getFirstName()+ " "+student.getLastName()+" "+student.getBranch());
                }
                listAdapter.notifyDataSetChanged();
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, branches);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);


        Button show = (Button) findViewById(R.id.show);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spinnerString = spinner.getSelectedItem().toString();
                ArrayList<Student> studentList = helper.readParticularContact(spinnerString);
                listData.clear();
                for (Student student : studentList) {
                    listData.add(student.getFirstName()+ " "+student.getLastName()+" "+student.getBranch());
                }
                listAdapter.notifyDataSetChanged();
            }
        });




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
}
