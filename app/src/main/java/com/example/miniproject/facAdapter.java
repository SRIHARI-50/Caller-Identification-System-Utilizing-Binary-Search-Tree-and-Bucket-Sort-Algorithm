package com.example.miniproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class facAdapter extends RecyclerView.Adapter<facAdapter.ContactViewHolder> {

    private final Context context;
    private final ArrayList<facmodel> contactList;


    public facAdapter(Context context, ArrayList<facmodel> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_contact_item,parent,false);
        ContactViewHolder vh = new ContactViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

        facmodel modelContact = contactList.get(position);

        String id = modelContact.getId();
        String name = modelContact.getName();
        String phone= modelContact.getPhone();

        holder.contactName.setText(name);

        holder.contactDial.setOnClickListener(v -> {

            String no = "tel:" + phone;

            Uri number = Uri.parse(no);
            Intent callIntent = new Intent(Intent.ACTION_DIAL,number);

            context.startActivity(callIntent);

        });

        holder.contactWhat.setOnClickListener(v -> {

            try {
                String num = "91" + phone;

                Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + num));
                sendIntent.setPackage("com.whatsapp");
                context.startActivity(sendIntent);
            }
            catch (Exception e) {
                Toast.makeText(context, "Whatsapp not installed", Toast.LENGTH_SHORT).show();
            }

        });


        holder.relativeLayout.setOnClickListener(v -> {

            Intent intent = new Intent(context,facContact.class);
            intent.putExtra("contactId",id);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{

        ImageView contactDial,contactWhat;
        TextView contactName;
        RelativeLayout relativeLayout;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            contactDial = itemView.findViewById(R.id.dial);
            contactWhat = itemView.findViewById(R.id.what);
            contactName = itemView.findViewById(R.id.contact_name);
            relativeLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}

