package io.github.rafikfarhad.ssamultitask;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import static io.github.rafikfarhad.ssamultitask.MainActivity.EXTRA_MESSAGE;

public class SendMessagePageActivity extends AppCompatActivity {

    boolean visit[] = new boolean[1000];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message_page);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
//        TextView textView = (TextView) findViewById(R.id.mytextview);
//        textView.setText(message);

        int i = 0;
        TableLayout my_table_layout = (TableLayout) findViewById(R.id.my_table_layout);
        View mTableRow = null;
        for(i=0; i<10; i++)
        {
            mTableRow = (TableRow) View.inflate(this, R.layout.m_row_layout, null);
            CheckBox cb = (CheckBox)mTableRow.findViewById(R.id.checkBoxServEmail);
            TextView rowTextView = (TextView) mTableRow.findViewById(R.id.rowTextView);
            rowTextView.setText("This a demo!!!");
            cb.setId(i);
//            cb.setText( "Checkbox: " + i);
            final int finalI = i;
            visit[finalI] ^= false;
            cb.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    if (((CheckBox) v).isChecked())
                    {
                        visit[finalI] ^= true;
//                        Toast.makeText(SendMessagePageActivity.this, finalI + " clicked!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        visit[finalI] ^= true;
                    }
                }
            });
            mTableRow.setTag(i);
            //add TableRows to TableLayout
            my_table_layout.addView(mTableRow);
        }

    }



    public void Send_SMS_Button_Pressed(View view) {

        for(int i=0; i<10; i++)
        {
            Log.d("result", visit[i]? i + " -> YES\n" : i + " NO\n");
        }


    }
}
