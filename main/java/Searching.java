package com.example.newstudent.barcode_v13;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.util.Log;

import java.io.IOException;

/**
 * Created by new student on 2/5/2017.
 */

public class Searching extends AppCompatActivity{

    //Checkbox program by Cesar
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private CheckBox chkAlcohol, chkPork, chkVeg, chkBeef, chkHalalB, chkSea;
    DatabaseHelper dbHelper;
    private  String barcodeStr = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching);

        // Create an instance of the database helper
        dbHelper = new DatabaseHelper(getApplicationContext());
        try {dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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
        if (editText.getText().toString().trim().length() == 0) {
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

}
