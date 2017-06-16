package io.github.rafikfarhad.ssamultitask;

import android.Manifest;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static io.github.rafikfarhad.ssamultitask.MainActivity.EXTRA_MESSAGE;

public class SendMessagePageActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    boolean visit[] = new boolean[1000];
    List<Contact> contacts;
    DatabaseHandler db = new DatabaseHandler(this);
    int total = 0;
    Dialog progress;

    private Toolbar toolbar;                              // Declaring the Toolbar Object




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_page_layout);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        progress = new Dialog(this);
        progress.setContentView(R.layout.progress);



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
        total = contacts.size();
        int i = 0;
        visit = new boolean[total+5];
        for(i=0; i<total; i++)
        {
            visit[i] = false;
        }

        ListView listView = (ListView) findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setOnItemClickListener(this);
        listView.setAdapter(customAdapter);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        listView.setStackFromBottom(true);

    }

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
            checkBox.setClickable(false);
            textView.setText(contacts.get(position).getPost());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                                case R.id.detail:{
                                    final Dialog myDialog = new Dialog(SendMessagePageActivity.this);
                                    myDialog.setContentView(R.layout.add_pop_up);
                                    Button save = (Button) myDialog.findViewById(R.id.save_people);
                                    Button cancel = (Button) myDialog.findViewById(R.id.cancel_people);
                                    save.setVisibility(View.GONE);
                                    cancel.setVisibility(View.GONE);

                                    Contact info = contacts.get(position);

                                    EditText name_ = (EditText) myDialog.findViewById(R.id.name_);
                                    EditText post_ = (EditText) myDialog.findViewById(R.id.post_);
                                    EditText phone_ = (EditText) myDialog.findViewById(R.id.phone_);
                                    name_.setText(info.getName());
                                    post_.setText(info.getPost());
                                    phone_.setText(info.getPhoneNumber());
                                    disableEditText(name_);
                                    disableEditText(post_);
                                    disableEditText(phone_);
                                    myDialog.show();
                                }
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
    void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.WHITE);
        editText.setTextColor(Color.BLACK);
        editText.setGravity(Gravity.CENTER);
    }

//    DELETE A SINGLE ENTRY
    public void DeleteData(int position){
        db.deleteContact(contacts.get(position));
        finish();
        startActivity(getIntent());
    }



    public void Send_SMS_Button_Pressed(View view) {
        EditText the_sms = (EditText) findViewById(R.id.the_sms);
        String smstext = the_sms.getText().toString();
        int sent = 0;
        for(int i=0; i<total; i++){
//            Toast.makeText(getApplicationContext(), "Sent to: " + visit[i], Toast.LENGTH_SHORT).show();

            if(visit[i]==true) {
                String text = smstext.replaceAll("###", contacts.get(i).getName());
                Toast.makeText(getApplicationContext(), "Sent to: " + contacts.get(i).getName(), Toast.LENGTH_SHORT).show();
                sent++;
                Log.d("result", text);
                sendSMS(contacts.get(i).getPhoneNumber(), text);
            }
        }
        if(sent==0){
            Toast.makeText(getApplicationContext(), "No Selecion", Toast.LENGTH_SHORT).show();

        }
//        Toast.makeText(getApplicationContext(), "Sending text message", Toast.LENGTH_SHORT).show();
    }

    //Sends an SMS message to another device

    private void sendSMS(String phoneNumber, String message) {
        try{
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber, null, message, null, null);
        }
        catch (Exception e){
            Log.d("Error", e.toString());
        }
    }




    public void ADD_Button_Pressed(View view) {

        // Create an instance of the dialog fragment and show it
//        DialogFragment newFragment = new AddPeople();
//        newFragment.show(this.getSupportFragmentManager(), "missiles");
        callLoginDialog();
//        Toast.makeText(getApplicationContext(), "Add People", Toast.LENGTH_SHORT).show();
    }


    private void callLoginDialog()
    {
        final Dialog myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.add_pop_up);
//        myDialog.setCancelable(false);
        Button save = (Button) myDialog.findViewById(R.id.save_people);
        Button cancel = (Button) myDialog.findViewById(R.id.cancel_people);

        final EditText name_ = (EditText) myDialog.findViewById(R.id.name_);
        final EditText post_ = (EditText) myDialog.findViewById(R.id.post_);
        final EditText phone_ = (EditText) myDialog.findViewById(R.id.phone_);
        myDialog.show();

        save.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if(name_.getText().toString().length()==0 || post_.getText().toString().length()==0)
                {
                    Toast.makeText(getApplicationContext(), "Empty value not allowed.", Toast.LENGTH_SHORT).show();
                }
                else if(phone_.getText().toString().length()<=10)
                {
                    Toast.makeText(getApplicationContext(), "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                }
                else {
                    db.addContact(new Contact(name_.getText().toString(), phone_.getText().toString(), post_.getText().toString()));
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                    myDialog.dismiss();
                    finish();
                    startActivity(getIntent());
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
//                Toast.makeText(getApplicationContext(), "Cancel People", Toast.LENGTH_SHORT).show();
                myDialog.dismiss();
            }
        });



    }


}
