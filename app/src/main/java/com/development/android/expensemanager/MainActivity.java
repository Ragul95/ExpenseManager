package com.development.android.expensemanager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button add_expense;
    private static float newFoodAmount = 0f, newTransportationAmount = 0f, newHealthAmount = 0f;
    private String newText;
    private PieChart pieChart;
    private PieDataSet dataSet;
    private PieData data_X;
    private ArrayList<Entry> yvalues;
    private ArrayList<String> xVals;
    private int addFoodData = 0, addTransportationData = 0, addHealthData = 0;
    private TextView chart_title;
    private CharSequence pie_chart_title = "Monthly_expenses";
    private Button exp_history;
    // public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.setNoDataText("");
        yvalues = new ArrayList<Entry>();
        xVals = new ArrayList<String>();
        /* xVals.add("Food");
        xVals.add("Transportation");
        xVals.add("Health"); */

        add_expense = (Button)findViewById(R.id.add_expense);
        // textView = (TextView) findViewById(R.id.total_expenses);
        add_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddExpense.class);
                startActivityForResult(intent, 100);
            }
        });

        exp_history = (Button) findViewById(R.id.history);
        exp_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_history = new Intent(MainActivity.this, DisplayExpenses.class);
                startActivityForResult(intent_history, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                newFoodAmount += data.getExtras().getFloat("pie_Food");
                newTransportationAmount += data.getExtras().getFloat("pie_Transportation");
                newHealthAmount += data.getExtras().getFloat("pie_Health");

                /* Log.i("Food:", String.valueOf(newFoodAmount));
                Log.i("Trans:", String.valueOf(newTransportationAmount));
                Log.i("Health:", String.valueOf(newHealthAmount)); */
                // System.out.print("Food: " + newFoodAmount + " Trans: " + newTransportationAmount + " Health: " + newHealthAmount);
                /* textView.setText(newAmount + "\n");
                textView.append(System.getProperty("line.separator"));
                newText = data.getStringExtra(AddExpense.selectedCategory);
                textView.append(newText + "\n");
                newText = data.getStringExtra(AddExpense.exp_desc);
                textView.append(newText); */
                chart_title = (TextView) findViewById(R.id.pie_chart_title);
                chart_title.setText(pie_chart_title);

                if ( newFoodAmount > 0 ) {
                    if ( addFoodData == 0 ) {
                        xVals.add("Food");
                        addFoodData = 1;
                        yvalues.add(new Entry(newFoodAmount, 0));
                    }
                    else
                        yvalues.set(0, new Entry(newFoodAmount, 0));
                }
                if ( newTransportationAmount > 0 ) {
                    if ( addTransportationData == 0 ) {
                        xVals.add("Transportation");
                        addTransportationData = 1;
                        yvalues.add(new Entry(newTransportationAmount, 0));
                    }
                    else
                        yvalues.set(1, new Entry(newTransportationAmount, 0));
                }
                if ( newHealthAmount > 0 ) {
                    if ( addHealthData == 0 ) {
                        xVals.add("Health");
                        addHealthData = 1;
                        yvalues.add(new Entry(newHealthAmount, 0));
                    }
                    else
                        yvalues.set(2, new Entry(newHealthAmount, 0));
                }
                /* yvalues.add(new Entry(25f, 3));
                yvalues.add(new Entry(23f, 4));
                yvalues.add(new Entry(17f, 5));
                xVals.add("April");
                xVals.add("May");
                xVals.add("June"); */

                dataSet = new PieDataSet(yvalues, "Expense_Categories");
                data_X = new PieData(xVals, dataSet);
                // In percentage Term
                data_X.setValueFormatter(new PercentFormatter());
                data_X.setValueTextSize(20f);
                // Default value
                //data.setValueFormatter(new DefaultValueFormatter(0));
                pieChart.setData(data_X);
                // pieChart.invalidate();
                dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
                pieChart.setDescription("");
                /* pieChart.setDescriptionPosition(2f, 2f);
                pieChart.setDescriptionTextSize(20f); */
                // pieChart.setDescriptionPosition(10f, 10f);
                //Disable Hole in the Pie Chart
                pieChart.setDrawHoleEnabled(false);
            }
        }
    }
}
