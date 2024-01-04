package com.lut.student.customerconactmgmt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomerAdapter extends ArrayAdapter<Customer> {

    private Context context;
    private ArrayList<Customer> customerList;

    public CustomerAdapter(Context context, ArrayList<Customer> customerList) {
        super(context, 0, customerList);
        this.context = context;
        this.customerList = customerList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item_customer, parent, false);
        }

        Customer currentCustomer = customerList.get(position);

        TextView textViewName = listItem.findViewById(R.id.textViewName);
        textViewName.setText(currentCustomer.getName());
        TextView textViewEmail = listItem.findViewById(R.id.textViewEmail);
        textViewEmail.setText(currentCustomer.getEmail());
        TextView textViewAddress = listItem.findViewById(R.id.textViewAddress);
        textViewAddress.setText(currentCustomer.getAddress());
        TextView textViewPhone = listItem.findViewById(R.id.textViewPhone);
        textViewPhone.setText(currentCustomer.getPhone());

        TextView textViewCountry = listItem.findViewById(R.id.textViewCountry);
        textViewCountry.setText(currentCustomer.getCountry());



        // Similarly set other fields like email, address, phone, country in the list item layout

        return listItem;
    }
}

