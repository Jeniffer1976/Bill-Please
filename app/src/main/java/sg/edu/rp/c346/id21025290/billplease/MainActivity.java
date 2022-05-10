package sg.edu.rp.c346.id21025290.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;


public class MainActivity extends AppCompatActivity {
    EditText amt, pax, disc;
    ToggleButton svs, gst;
    Button split, reset;
    TextView total, each, amtErr ,paxErr, discErr;
    RadioGroup payment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amt = findViewById(R.id.editTextAmount);
        pax = findViewById(R.id.editTextPax);
        disc = findViewById(R.id.editTextDiscount);
        svs = findViewById(R.id.toggleButtonSVS);
        gst = findViewById(R.id.toggleButtonGST);
        split = findViewById(R.id.buttonSplit);
        reset = findViewById(R.id.buttonReset);
        payment = findViewById(R.id.radioGroupPayment);
        total = findViewById(R.id.textViewTotal);
        each = findViewById(R.id.textViewEach);
        amtErr = findViewById(R.id.textViewAmtErr);
        paxErr= findViewById(R.id.textViewPaxErr);
        discErr = findViewById(R.id.textViewDiscErr);


        split.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double amtD;
                double paxD;
                double discD;
                double totalD;
                String paymentType;
                boolean isValid = true;

                // default values when split is clicked
                amtErr.setText("");
                paxErr.setText("");
                discErr.setText("");
                total.setText(R.string.total_bill);
                each.setText(R.string.each_pays);

                // Check for invalid entries
                if (amt.getText().toString().trim().length() == 0) {
                    amtErr.setText(R.string.error1);
                    isValid = false;
                }

                if (pax.getText().toString().trim().length() == 0) {
                    paxErr.setText(R.string.error1);
                    isValid = false;
                }
                if (disc.getText().toString().trim().length() == 0 || Double.parseDouble(disc.getText().toString()) >100 ) {
                    discErr.setText(R.string.error2);
                    isValid = false;
                }

                if(isValid == true) {
                    amtD = Double.parseDouble(amt.getText().toString());
                    paxD = Double.parseDouble(pax.getText().toString());
                    discD = Double.parseDouble(disc.getText().toString());
                    totalD = amtD * (1 - (discD / 100));

                    if (svs.isChecked()) {
                        totalD = totalD * 1.1;
                    }
                    if (gst.isChecked()) {
                        totalD = totalD * 1.07;
                    }

                    if (payment.getCheckedRadioButtonId() == R.id.radioButtonCash) {
                        paymentType = getString(R.string.cashDisplay);
                    } else {
                        paymentType = getString(R.string.paynowDisplay);
                    }

                    total.setText(String.format("Total Bill: $%.2f", totalD));
                    each.setText(String.format("Each Pays: $%.2f %s", totalD / paxD, paymentType));
                }

            }
        });


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amt.setText("");
                pax.setText("");
                disc.setText("");
                amtErr.setText("");
                paxErr.setText("");
                discErr.setText("");
                payment.check(R.id.radioButtonCash);
                svs.setChecked(false);
                gst.setChecked(false);
                total.setText(R.string.total_bill);
                each.setText(R.string.each_pays);

            }
        });
    }
}