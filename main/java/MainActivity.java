package com.example.newstudent.barcode_v13;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.Image;
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

public class MainActivity extends AppCompatActivity {

    // This is for button pages
    // private Image button;
    private ImageButton barcode, Searching, Information, help;
    private  String barcodeStr = new String();
    private Button button;

    //Checkbox program by Cesar
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private CheckBox chkAlcohol, chkPork, chkVeg, chkBeef, chkHalalB, chkSea;

    //this side is for database
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create an instance of the database helper
        dbHelper = new DatabaseHelper(getApplicationContext());
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // This is program listing created by Fikri

        //definisi untuk database
        //myDb = new DatabaseHelper(this);
        //editName = (EditText)findViewById(R.id.edithelp1);
        //editAddress = (EditText)findViewById(R.id.edithelp2);
        //editComment = (EditText)findViewById(R.id.textcomment);
        //btnAddData = (Button)findViewById(R.id.sendButton);
        // AddData();

        Searching = (ImageButton)findViewById(R.id.searching);
        Searching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.searching){
                    Intent i = new Intent(MainActivity.this, Searching.class);
                    startActivity(i);
                }

            }
        });

        Information = (ImageButton)findViewById(R.id.food);
        Information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.food){
                    Intent i = new Intent(MainActivity.this, Information.class);
                    startActivity(i);
                }
            }
        });

        help = (ImageButton)findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.help){
                    Intent i = new Intent(MainActivity.this, Help.class);
                    startActivity(i);
                }
            }
        });

        button = (Button)findViewById(R.id.idButton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (view.getId() == R.id.idButton){
                    Intent i = new Intent(MainActivity.this, Show.class);
                    startActivity(i);
                }
            }
        });

    }

    // This is to search through the number of barcode                                            //
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
        EditText editText = (EditText) findViewById(R.id.name_edit_message);
        if(editText.getText().toString().trim().length() == 0)
        {
            showMessage("Error", "Enter some text nigga");
            return;
        }
        String product = dbHelper.getProductByName(editText.getText().toString());
        intent.putExtra(EXTRA_MESSAGE, product);
        startActivity(intent);
    }


    /** Called when the user clicks the search by checkboxes*/
     public void searchByCheckbox(View view) {
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

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

//================================================================================================//
    //This is for declaration to call new page when the button on Main Activity is pressed
    public void searchPressBarcode(View view){
        // Do something in response to button and open the barcode scanner
        final Activity activity = this ;
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }
/*
    //This is declaration to call searching menu when the button is pressed
    public void searchPressSearching(View view){
        Intent intent = new Intent(this, Searching.class);
        startActivity(intent);
    }

    //This is declaration to call Information menu when the button is pressed
    public void searchFindInformation(View view){
        Intent intent = new Intent(this, Information.class );
        startActivity(intent);
    }

    //This is declaration to call Information menu when the button is pressed
    public void searchContactus(View view){
        Intent intent = new Intent(this, Help.class );
        startActivity(intent);
    }
*/
//================================================================================================//
//===========================THIS IS FOR ZXING LIBRARY CODE SECTION===============================//
//================================================================================================//
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

                Intent intent = new Intent(this, DisplayMessageActivity.class);
                String product = dbHelper.getProductByBarcode(barcodeStr);
                intent.putExtra(EXTRA_MESSAGE, product);
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
//================================================================================================//

}
