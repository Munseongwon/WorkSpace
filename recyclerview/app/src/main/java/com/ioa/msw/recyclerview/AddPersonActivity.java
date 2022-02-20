package com.ioa.msw.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddPersonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addperson);

        EditText nameEt = findViewById(R.id.name_Et);
        EditText phoneNumberEt = findViewById(R.id.phonenumber_Et);
        EditText addressEt = findViewById(R.id.address_Et);
        Button addBtn = findViewById(R.id.add_Btn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEt.getText().toString();
                String phoneNumber = phoneNumberEt.getText().toString();
                String address = addressEt.getText().toString();
                
                if(name.equals("")){
                    Toast.makeText(AddPersonActivity.this, "Add Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(phoneNumber.equals("")){
                    Toast.makeText(AddPersonActivity.this, "Add PhoneNumber", Toast.LENGTH_SHORT).show();
                    return;
                }
                Person person = new Person();
                person.setName(name);
                person.setPhoneNumber(phoneNumber);
                person.setAddress(address);
                Intent intent = new Intent();
                intent.putExtra("person", person);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
