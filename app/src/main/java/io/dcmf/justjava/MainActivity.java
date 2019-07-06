package io.dcmf.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    boolean hasWhippedCream = false;
    boolean hasChocolate = false;
    String name = "";

    static int PRICE_OF_COFFEE = 5;
    static int PRICE_OF_WHIPPED_CREAM = 1;
    static int PRICE_OF_CHOCOLATE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {
        getName(view);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_subject, this.name));
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    public void increaseQuantity(View view) {
        if (quantity >= 100) {
            Toast.makeText(this, "Sorry, we can't handle orders with more then 100 cup of coffees.", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }

    public void decreaseQuantity(View view) {
        if (quantity < 2) {
            Toast.makeText(this, "You should order at least one cup of coffee.", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }

    private int calculatePrice() {
        return this.quantity * (PRICE_OF_COFFEE +
                (hasWhippedCream ? PRICE_OF_WHIPPED_CREAM : 0) +
                (hasChocolate ? PRICE_OF_CHOCOLATE : 0));
    }

    private String createOrderSummary() {
        return getString(R.string.order_summary_name, this.name)
                + "\n" + getString(R.string.order_summary_whipped_cream, this.hasWhippedCream ? "yes" : "no")
                + "\n" + getString(R.string.order_summary_chocholate, this.hasChocolate ? "yes" : "no")
                + "\n" + getString(R.string.order_summary_quantity, this.quantity)
                + "\n" + getString(R.string.order_summary_total, NumberFormat.getCurrencyInstance().format(this.calculatePrice()))
                + "\n" + getString(R.string.thank_you);
    }

    public void handleWhippedCreamCheckbox(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.has_whipped_cream);
        this.hasWhippedCream = whippedCream.isChecked();
    }

    public void handleChocolateCheckbox(View view) {
        CheckBox chocolate = (CheckBox) findViewById(R.id.has_chocolate);
        this.hasChocolate = chocolate.isChecked();
    }

    public void getName(View view) {
        EditText name = (EditText) findViewById(R.id.name);
        this.name = name.getText().toString().trim();
    }

    @Override
    public String toString() {
        return "MainActivity{" +
                "quantity:" + quantity +
                ", hasWhippedCream:" + hasWhippedCream +
                ", hasChocolate:" + hasChocolate +
                ", name:'" + name + '\'' +
                '}';
    }
}
