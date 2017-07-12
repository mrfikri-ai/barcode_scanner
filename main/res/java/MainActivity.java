package com.example.newstudent.barcode_v12;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    private Image button;
    private ImageButton barcode, searching, food, help;
    private  String barcodeStr = new String();

    //Checkbox program by Cesar
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private CheckBox chkAlcohol, chkPork, chkVeg, chkBeef, chkHalalB, chkSea;

    //this side is for database
    DatabaseHelper myDb, dbHelper;
    EditText editName, editAddress, editComment;
    Button btnAddData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create an instance of the database helper
        // Create an instance of the database helper
        dbHelper = new DatabaseHelper(getApplicationContext());
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

    /* This is program listing created by Fikri
    */
        //definisi untuk database
        myDb = new DatabaseHelper(this);
        editName = (EditText)findViewById(R.id.edithelp1);
        editAddress = (EditText)findViewById(R.id.edithelp2);
        editComment = (EditText)findViewById(R.id.textcomment);
        btnAddData = (Button)findViewById(R.id.sendButton);
        // AddData();

        /* This is for scanning option
        ** scanning will active the Zxing library
        **
         */
        barcode = (ImageButton) this.findViewById(R.id.scan);
        final Activity activity = this;
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        /* This is for searching option
        ** searching will active after imagebutton is clicked
        **
         */
        searching = (ImageButton) this.findViewById(R.id.searching);
        final Activity a = this;
        searching.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.searching)
                {
                    // Intent  a = new Intent(MainActivity.this, Searching.class);
                    Intent i = new Intent();
                    i.setClassName(MainActivity.this, "com.example.newstudent.barcode_v12");
                    startActivity(i);
                }
            }
        });


        /* This is for scanning option
        ** scanning will active the Zxing library
        **
         */
        help = (ImageButton) this.findViewById(R.id.help);
        final Activity b = this;
        help.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.help)
                {
                    Intent b = new Intent(MainActivity.this, Help.class);
                    startActivity(b);
                }
            }
        });


        /* This is for opening food page
        **
        **
        **/
        food = (ImageButton) this.findViewById(R.id.food);
        final Activity c = this;
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.food)
                {
                    Intent c = new Intent(MainActivity.this, Food.class);
                    startActivity(c);
                }
            }
        });

        //for advances purpose
    }

    /** Called when the user clicks the search by barcode button */
    /*-----------------------------------------------------------*/
    public void searchByBarcode(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.barcode_edit_message);
        //String message = editText.getText().toString();
        if(editText.getText().toString().trim().length() == 0)
        {
            showMessage("Error", "Not available in database");
            return;
        }

        String product = dbHelper.getProductByBarcode(editText.getText().toString());
        intent.putExtra(EXTRA_MESSAGE, product);
        startActivity(intent);
    }

    /** Called when the user clicks the search by product name button */
    public void searchByName(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.barcode_edit_message);
        if(editText.getText().toString().trim().length() == 0)
        {
            showMessage("Error", "Not available in database");
            return;
        }
        String product = dbHelper.getProductByName(editText.getText().toString());
        intent.putExtra(EXTRA_MESSAGE, product);
        startActivity(intent);
    }

    /** Called when the user clicks the search by checkboxes*/
    public void Searching(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);


        chkVeg = (CheckBox) findViewById(R.id.chkVeg);
        chkPork = (CheckBox) findViewById(R.id.chkPork);
        chkAlcohol = (CheckBox) findViewById(R.id.chkAlcohol);
        chkBeef = (CheckBox) findViewById(R.id.chkBeef);
        chkHalalB = (CheckBox) findViewById(R.id.chkHalalB);
        chkSea = (CheckBox) findViewById(R.id.chkSea);

        StringBuffer result = new StringBuffer();
        result.append("alchohol= ")
                .append(chkAlcohol.isChecked());
        result.append(" and pork= ").append(
                chkPork.isChecked());
        result.append(" and vegetarian= ").append(
                chkVeg.isChecked());
        result.append(" and beef= ").append(
                chkBeef.isChecked());
        result.append(" and halal_beef= ").append(
                chkHalalB.isChecked());
        result.append(" and seafood= ").append(
                chkSea.isChecked());
        result.append(";");


        String query = result.toString();
        query = query.replaceAll("true", "\\1");
        query = query.replaceAll("false", "\\0");

        String products = dbHelper.getProdcutByCategories(query);

        intent.putExtra(EXTRA_MESSAGE, products);
        startActivity(intent);
    }

    //Show the message
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    //Mendefinisikan AddData untuk databases
    /*
    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        boolean isInserted = myDb.insertData(editName.getText().toString(),
                                editAddress.getText().toString(),editComment.getText().toString());
                        if (isInserted)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    */

    //Mendefinisikan data untuk ZXing Library
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                //Log.d("MainActivity", "Barcode")
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");

                barcodeStr = result.getContents();
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
