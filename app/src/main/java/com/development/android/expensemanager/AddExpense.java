package com.development.android.expensemanager;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.widget.Toast;

/**
 * Created by ragult on 10/31/2017.
 */

public class AddExpense extends AppCompatActivity {

    private Calendar myCalendar;
    private EditText editText, amount, eDesc, eDate;
    private DatePickerDialog.OnDateSetListener date;
    private Button add_exp;
    private Intent intent;
    private static float add_To_Food = 0f, add_To_Transportation = 0f, add_To_Health = 0f;
    private ArrayList<Integer> amt;
    private ArrayList<String> category, desc;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Toast toast;
    private String expense_date, date_exp;
    private DateFormat formatter;
    private Date dateObject;
    private int selectedId = -1;
    private float db_amt = 0;
    public static String selectedCategory, exp_desc;
    public StoreData data_obj;
    // public static String pie_Food, pie_Transportation, pie_Health;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenses_addition);

        myCalendar = Calendar.getInstance();
        editText = (EditText) findViewById(R.id.eDate);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddExpense.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        data_obj = new StoreData();
        add_exp = (Button) findViewById(R.id.add_exp);
        amount = (EditText) findViewById(R.id.amount);
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        intent = new Intent();
        add_exp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        try{
            radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
            // get selected radio button from radioGroup
            selectedId = radioGroup.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            radioButton = (RadioButton) findViewById(selectedId);
            selectedCategory = radioButton.getText().toString();
            if ( Float.parseFloat(amount.getText().toString()) == 0 ){
                throw new NumberFormatException("Invalid amount");
            }
            else{
            switch (selectedCategory) {
                case "Food":
                    add_To_Food = Float.parseFloat(amount.getText().toString());
                    db_amt = add_To_Food;
                    // Log.i("Food1: ", String.valueOf(add_To_Food));
                    break;
                case "Transportation":
                    add_To_Transportation = Float.parseFloat(amount.getText().toString());
                    db_amt = add_To_Transportation;
                    break;
                case "Health":
                    add_To_Health = Float.parseFloat(amount.getText().toString());
                    db_amt = add_To_Health;
                    break;
                default:    // Not needed. Exceptions are handled before
            }}
            eDesc = (EditText) findViewById(R.id.eDesc);
            exp_desc = eDesc.getText().toString();
            eDate = (EditText) findViewById(R.id.eDate);
            expense_date = eDate.getText().toString();
            dateObject = formatter.parse(expense_date);
            date_exp = new SimpleDateFormat("dd/MM/yyyy").format(dateObject);
            intent.putExtra("pie_Food", add_To_Food);
            intent.putExtra("pie_Transportation", add_To_Transportation);
            intent.putExtra("pie_Health", add_To_Health);
            // data_obj.getWritableDatabase();
            data_obj.insertExpData(db_amt, selectedCategory, exp_desc, date_exp, AddExpense.this);
            add_To_Food = add_To_Transportation = add_To_Health = 0f;
            setResult(RESULT_OK, intent);
            finish();
        }
        catch (NumberFormatException nfE){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddExpense.this);
                alertDialogBuilder.setMessage("Please enter the amount");
                alertDialogBuilder.setNeutralButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                moveTaskToBack(false);
                                // Toast.makeText(AddExpense.this,"You clicked OK button",Toast.LENGTH_LONG).show();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
        }
        catch (NullPointerException npE){
            if ( radioGroup.getCheckedRadioButtonId() == -1 ){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddExpense.this);
                alertDialogBuilder.setMessage("No category is selected");
                        alertDialogBuilder.setNeutralButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        moveTaskToBack(false);
                                        // Toast.makeText(AddExpense.this,"You clicked OK button",Toast.LENGTH_LONG).show();
                                    }
                                });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            }
        }
        catch( java.text.ParseException pE){
            if ( date_exp == null ){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddExpense.this);
                alertDialogBuilder.setMessage("Please select a date");
                alertDialogBuilder.setNeutralButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                moveTaskToBack(false);
                                // Toast.makeText(AddExpense.this,"You clicked OK button",Toast.LENGTH_LONG).show();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }
        /* intent.putExtra(total, add_To_Total);
        intent.putExtra(selectedCategory, selectedCategory);
        intent.putExtra(exp_desc, exp_desc); */
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        editText.setText(sdf.format(myCalendar.getTime()));
    }
}
