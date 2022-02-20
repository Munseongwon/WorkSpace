package com.ioa.msw.recyclerview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView peopleRv;
    ArrayList<Person> peopleList = new ArrayList<Person>();
    PeopleAdapter peopleAdapter;

    int REQ_ADDPERSON = 456789;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        peopleRv = findViewById(R.id.people_rv);
        Button addBtn = findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPersonActivity.class);
                startActivityForResult(intent,REQ_ADDPERSON);
            }
        });

//         for(int i=1; i<=10000; i++){
//            Person person = new Person();
//            person.setName("문성원"+i);
//            person.setPhoneNumber(String.format("000-0000-%04d",i));
//            person.setAddress("경기도 군포시"+i);
//            peopleList.add(person);
//        }

        peopleAdapter = new PeopleAdapter(this, peopleList);
        peopleRv.setAdapter(peopleAdapter);

        peopleAdapter.setOnItemClickListener(new PeopleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Person person) {
                Log.d("onItemClick",person.getName());
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+person.getPhoneNumber()));
                startActivity(intent);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        peopleRv.setLayoutManager(layoutManager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_ADDPERSON && resultCode==RESULT_OK){
            Person person = (Person) data.getSerializableExtra("person");
            peopleList.add(person);
            peopleAdapter.notifyDataSetChanged(); //데이터가 바뀐 것을 어탭터에 알려주어라.
        }

    }
}