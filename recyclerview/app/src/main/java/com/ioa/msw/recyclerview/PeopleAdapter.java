package com.ioa.msw.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Person> mPeopleList;

    OnItemClickListener onItemClickListener;

    public PeopleAdapter(Context context, ArrayList<Person>peopleList){
        this.mContext=context;
        this.mPeopleList=peopleList;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    /*뷰 홀더에 어떠한 데이터를 넣어서 그릴 것인지 알려주는 함수 --> 즉 어떤 레이어를 호출할 것인가?*/
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("PeopleAdapter","onCreateViewHolder");
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_person,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /*뷰 홀더에 담을 아이템을 보여주되 몇 번째까지 보여줄 것인지를 알려주는 함수*/
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("PeopleAdapter","onBindViewHolder" +position);
        Person person = mPeopleList.get(position);
        holder.onBind(person);
    }

    /*몇 번째 뷰까지 보여줄 것인가*/
    @Override
    public int getItemCount() {
        Log.d("PeopleAdapter","getItemCount" + mPeopleList.size());
        return mPeopleList.size();
    }

    /*itemView 같은 경우에는 Inflater로 받은 layout 즉 TextView를 말함*/
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv,phoneNumberTv,addressTv;
        Person mPerson;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.name_tv);
            phoneNumberTv = itemView.findViewById(R.id.phonenumber_tv);
            addressTv = itemView.findViewById(R.id.address_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("onClick","onClick");
                    onItemClickListener.onItemClick(mPerson);
                }
            });
        }
        public void onBind(Person person){
            this.mPerson=person;
            nameTv.setText(person.getName());
            phoneNumberTv.setText(person.getPhoneNumber());
            addressTv.setText(person.getAddress());
        }
    }

    /*A에서 B로 보낼 때 클릭시 person 파라미터가 지니고 있는 데이터를 내보낸다.*/
    interface OnItemClickListener{
        public void onItemClick(Person person);
    }
}
