package io.github.rafikfarhad.ssamultitask;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static io.github.rafikfarhad.ssamultitask.MainActivity.EXTRA_MESSAGE;

public class SendMessagePageActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    boolean visit[] = new boolean[1000];
    List<Contact> contacts;
    DatabaseHandler db = new DatabaseHandler(this);

    private Toolbar toolbar;                              // Declaring the Toolbar Object




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_page_layout);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);


//        TOOLBAR

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call

//        Database

//
//
//        db.deleteAll();
//
//        // Inserting Contacts
//        Log.d("Insert: ", "Inserting ..");
//        db.addContact(new Contact("Mahir", "9100000000", "President"));
//        db.addContact(new Contact("Farhad", "9199999999", "Vice President"));
//        db.addContact(new Contact("Sadman", "9522222222", "GS"));
//        db.addContact(new Contact("Ozayer", "9533333333", "OS"));
//        db.addContact(new Contact("Palak", "9533333333", "OS"));
//        db.addContact(new Contact("Jakir", "9533333333", "OS"));
//        db.addContact(new Contact("Raju", "9533333333", "OS"));
//        db.addContact(new Contact("Fahad", "9533333333", "OS"));
//        db.addContact(new Contact("Meem", "9533333333", "OS"));
//        db.addContact(new Contact("Piya", "9533333333", "OS"));
//        db.addContact(new Contact("Samia", "9533333333", "OS"));

        // Reading all contacts
//        Log.d("Reading: ", "Reading all contacts..");
//        List<Contact> contacts = db.getAllContacts();
//
//        for (Contact cn : contacts) {
//            String log = "Id: " + cn.getID() + " , Name: " + contacts.get(1).getName() + " ,Phone: " +
//                    cn.getPhoneNumber();
//            // Writing Contacts to log
//            Log.d("Name: ", log);
//            Toast.makeText(SendMessagePageActivity.this, log, Toast.LENGTH_SHORT).show();
//
//        }
//        Toast.makeText(SendMessagePageActivity.this, contacts.size(), Toast.LENGTH_SHORT).show();

//        DATABASE END
            // Capture the layout's TextView and set the string as its text
//        TextView textView = (TextView) findViewById(R.id.mytextview);
//        textView.setText(message);
        contacts = db.getAllContacts();
        int total = contacts.size();
        int i = 0;

        ListView listView = (ListView) findViewById(R.id.listView);

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setOnItemClickListener(this);
        listView.setAdapter(customAdapter);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        listView.setStackFromBottom(true);



    }

//                                TOOLBAR
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.topbar_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.addnew) {
//            Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return contacts.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.list_element, null);

            final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            TextView textView = (TextView) convertView.findViewById(R.id.textView);
            checkBox.setText(contacts.get(position).getName());
            checkBox.setChecked(visit[position]);
//            Log.d("TEST", contacts.get(position).getPost());
            textView.setText(contacts.get(position).getPost());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(), "It's here " + position, Toast.LENGTH_SHORT).show();
                    checkBox.toggle();
                    visit[position]^=true;
                }
            });
            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final PopupMenu menu = new PopupMenu(SendMessagePageActivity.this, v);
                    menu.getMenuInflater().inflate(R.menu.popup_menu, menu.getMenu());
                    menu.show();

                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {

                                case R.id.delete:
                                    DeleteData(position);
                                    break;
                                case R.id.detail:
                                    Toast.makeText(SendMessagePageActivity.this, "your desire action is " + item.toString(), Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), "It's here " + position, Toast.LENGTH_SHORT).show();
                                    break;
                            }

                            return true;
                        }
                    });
                    return true;
                }
            });


            return convertView;
        }
    }

//    DELETE A SINGLE ENTRY
    public void DeleteData(int position){
        db.deleteContact(contacts.get(position));
        finish();
        startActivity(getIntent());
    }



    public void Send_SMS_Button_Pressed(View view) {

        for(int i=0; i<10; i++)
        {
            Log.d("result", visit[i]? i + " -> YES\n" : i + " NO\n");
        }
        Toast.makeText(getApplicationContext(), "Sending text message", Toast.LENGTH_SHORT).show();
    }
    public void ADD_Button_Pressed(View view) {

        Toast.makeText(getApplicationContext(), "Add People", Toast.LENGTH_SHORT).show();
    }




}
