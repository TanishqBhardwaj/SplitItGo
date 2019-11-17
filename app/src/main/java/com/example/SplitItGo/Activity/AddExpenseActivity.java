package com.example.SplitItGo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SplitItGo.Interface.JsonPlaceHolderApi;
import com.example.SplitItGo.Model.ExpensesResponse;
import com.example.SplitItGo.Model.PostExpense;
import com.example.SplitItGo.R;
import com.example.SplitItGo.Utils.PreferenceUtils;
import com.example.SplitItGo.Utils.RetrofitInstance;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddExpenseActivity extends AppCompatActivity {

    EditText editTextDescription;
    EditText editTextAmount;

    String description;
    String amount;

    ImageView imageViewBackButton;
    TextView textViewSave;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    PreferenceUtils pref;

    static int id;

    public AddExpenseActivity() {

    }

    public AddExpenseActivity(int id) {
        this.id = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        pref = new PreferenceUtils(AddExpenseActivity.this);

        editTextDescription = findViewById(R.id.editTextExpenseDescription);
        editTextAmount = findViewById(R.id.editTextExpenseAmount);
        imageViewBackButton = findViewById(R.id.imageViewBackButtonExpense);
        textViewSave = findViewById(R.id.textViewSaveExpense);

        textViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description = editTextDescription.getText().toString();
                amount = editTextAmount.getText().toString();
                createExpense();
                finish();
            }
        });

        imageViewBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void createExpense() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        jsonPlaceHolderApi = RetrofitInstance.getRetrofit(okHttpClient).create(JsonPlaceHolderApi.class);
        String token = "JWT " + pref.getToken();
        PostExpense postExpense = new PostExpense(description, "fuck", String.valueOf(id), amount, pref.getKeyUserId());
        Call<ExpensesResponse> call = jsonPlaceHolderApi.createExpense(String.valueOf(id), token, postExpense);
        call.enqueue(new Callback<ExpensesResponse>() {
            @Override
            public void onResponse(Call<ExpensesResponse> call, Response<ExpensesResponse> response) {
                try {
                    if (!response.isSuccessful()) {
                        Toast.makeText(AddExpenseActivity.this, "Code: " + response.code(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(AddExpenseActivity.this, "Expense Added", Toast.LENGTH_SHORT).show();
                }
                catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ExpensesResponse> call, Throwable t) {
                Toast.makeText(AddExpenseActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}