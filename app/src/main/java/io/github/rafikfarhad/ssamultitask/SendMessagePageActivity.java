package io.github.rafikfarhad.ssamultitask;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_page_layout);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);


//        Database



//        db.deleteAll();
//
//        // Inserting Contacts
//        Log.d("Insert: ", "Inserting ..");
//        db.addContact(new Contact("Rifat", "9100000000", "President"));
//        db.addContact(new Contact("Farhad", "9199999999", "Vice President"));
//        db.addContact(new Contact("Preety", "9522222222", "GS"));
//        db.addContact(new Contact("Naeem", "9533333333", "OS"));db.addContact(new Contact("Rifat", "9100000000", "President"));
//        db.addContact(new Contact("Farhad", "9199999999", "Vice President"));
//        db.addContact(new Contact("Preety", "9522222222", "GS"));
//        db.addContact(new Contact("Naeem", "9533333333", "OS"));db.addContact(new Contact("Rifat", "9100000000", "President"));
//        db.addContact(new Contact("Farhad", "9199999999", "Vice President"));
//        db.addContact(new Contact("Preety", "9522222222", "GS"));
//        db.addContact(new Contact("Naeem", "9533333333", "OS"));db.addContact(new Contact("Rifat", "9100000000", "President"));
//        db.addContact(new Contact("Farhad", "9199999999", "Vice President"));
//        db.addContact(new Contact("Preety", "9522222222", "GS"));
//        db.addContact(new Contact("Naeem", "9533333333", "OS"));

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



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();

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


    }
}
