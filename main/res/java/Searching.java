package com.example.newstudent.barcode_v12;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Created by new student on 2/1/2017.
 */

public class Searching extends Activity  {

    Button btnTombol1, btnTombol2, btnTombol3;
    EditText edtTulis1, edtTulis2;

    private CheckBox ckpork, ck_emulsifier, ck_alcohol, ck_shortening, ck_gelatin, ck_margarin, ck_butter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching);

        btnTombol1 = (Button)findViewById(R.id.tombol1);
        btnTombol2 = (Button)findViewById(R.id.tombol2);
        btnTombol3 = (Button)findViewById(R.id.tombol3);

        edtTulis1 = (EditText)findViewById(R.id.tulis1);
        edtTulis2 = (EditText)findViewById(R.id.tulis2);

    }


    /** Called when the user clicks the search by checkboxes*/
    /*
    public void searchByCheckbox(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);


        ckpork = (CheckBox) findViewById(R.id.checkbox_pork);
        ck_emulsifier = (CheckBox) findViewById(R.id.checkbox_emulsifier);
        ck_alcohol = (CheckBox) findViewById(R.id.checkbox_alcohol);
        ck_shortening = (CheckBox) findViewById(R.id.checkbox_shortening);
        ck_gelatin = (CheckBox) findViewById(R.id.checkbox_gelatin);
        ck_margarin = (CheckBox) findViewById(R.id.checkbox_margarin);
        ck_butter = (CheckBox) findViewById(R.id.checkbox_butter);

        StringBuffer result = new StringBuffer();
        result.append("alchohol= ")
                .append(ck_alcohol.isChecked());
        result.append(" and pork= ").append(
                ckpork.isChecked());
        result.append(" and emulsifier= ").append(
                ck_emulsifier.isChecked());
        result.append(" and gelatin= ").append(
                ck_gelatin.isChecked());
        result.append(" and margarin= ").append(
                ck_margarin.isChecked());
        result.append(" and butter= ").append(
                ck_butter.isChecked());
        result.append(" and shortening= ").append(
                ck_shortening.isChecked());
        result.append(";");


        String query = result.toString();
        query = query.replaceAll("true", "\\1");
        query = query.replaceAll("false", "\\0");

        String products = dbHelper.getProdcutByCategories(query);

        intent.putExtra(EXTRA_MESSAGE, products);
        startActivity(intent);
    }
    */



}
