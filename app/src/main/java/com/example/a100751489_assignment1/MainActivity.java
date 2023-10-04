//Anish Patel
//100751489
//Mobile App Dev - Assignment 1
//Dr.Azim

package com.example.a100751489_assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    //Setup variables required for EMI calculation
    int amortizationPeriod;
    double loanAmount,interestRate;
    //Variables to pull what user enters in the 3 fields
    TextInputEditText loan;
    TextInputEditText interest;
    TextInputEditText amortization;
    TextView monthlyStatement;
    TextView TDHyperlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting the components
        loan = findViewById(R.id.loanAmountFieldET);
        interest = findViewById(R.id.interestRateFieldET);
        amortization = findViewById(R.id.amortizationFieldET);
        Button findEMI = (Button) findViewById(R.id.calculateEMI);

        TDHyperlink = findViewById(R.id.TDEMICalculator);

        //OnClickListener for the hyperlink
        TDHyperlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    openTDWebsite(view); //when user clicks on hyperlink it calls on openTDWebsite function with intent
                } catch(Exception e){
                    Snackbar.make(view, "Error opening TD EMI Calculator", Snackbar.LENGTH_LONG)
                            .setAnchorView(R.id.calculateEMI)
                            .show();
                }
            }
        });

        findEMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    double totalEMI;
                    loanAmount = Double.parseDouble(loan.getText().toString());
                    interestRate = Double.parseDouble(interest.getText().toString());
                    amortizationPeriod = Integer.parseInt(amortization.getText().toString());
                    monthlyStatement = findViewById(R.id.mortgageStatement);


                    totalEMI = calculateEMI(loanAmount, interestRate, amortizationPeriod);  //Calculates EMI by calling function
                    monthlyStatement.setText((String.format("Your monthly payment amount is $%.2f", totalEMI)));
                } catch(Exception e) {
                    Snackbar.make(view, "Please enter valid entries.", Snackbar.LENGTH_LONG)
                            .setAnchorView(R.id.calculateEMI)
                            .show();
                }

            }
        });
    }

    public double calculateEMI(double loanAmount, double interestRate, int amortizationPeriod) {

        int totalTermInMonths = amortizationPeriod*12;        //finds total amount of months you have to pay for
        double monthlyInterest = interestRate/ (12*100);        //finds monthly interest rate

        //calculating how much to pay monthly
        double monthlyPayment = (loanAmount * monthlyInterest * (float)Math.pow(1+monthlyInterest,totalTermInMonths))
                    / (float)(Math.pow(1+monthlyInterest,totalTermInMonths)-1);
        return monthlyPayment;      //returns the monthly payment value
    }

    //Function to utilize intent which redirects user to the TD EMI calculator
    public void openTDWebsite(View view){
        String TDWebsiteURL = "https://apps.td.com/mortgage-payment-calculator/";
        Intent openTDEMI = new Intent(Intent.ACTION_VIEW, Uri.parse(TDWebsiteURL));
        startActivity(openTDEMI);
    }
}