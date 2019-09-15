package com.example.socialmediaconsolidator.Data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.socialmediaconsolidator.Model.Contact;
import com.example.socialmediaconsolidator.R;

import java.util.ArrayList;

public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Contact> contactList;

    public ContactRecyclerViewAdapter(Context context, ArrayList<Contact> listContacts) {
        this.context = context;
        contactList = listContacts;
    }

    @NonNull
    @Override
    public ContactRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.entry_row, viewGroup, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactRecyclerViewAdapter.ViewHolder viewHolder, int i) {

        Contact contact = contactList.get(i);

        String contactUsername = contact.getUsername();
        viewHolder.rowUsername.setText(contactUsername);

        boolean contactFB = contact.isFacebook();

        boolean contactTW = contact.isTwitter();

        boolean contactIG = contact.isInstagram();

    }

    @Override
    public int getItemCount() {
        if (contactList != null) {
            return contactList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView rowUsername;
        ImageView rowFB;
        ImageView rowTW;
        ImageView rowIG;

        public ViewHolder(View itemView, final Context ctx) {
            super(itemView);
            context = ctx;

            rowUsername = itemView.findViewById(R.id.contactUsernameId);
            rowFB = itemView.findViewById(R.id.fbEntryId);
            rowTW = itemView.findViewById(R.id.twEntryId);
            rowIG = itemView.findViewById(R.id.igEntryId);
        }
    }
}
