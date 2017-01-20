package com.develop.android.contacts_map.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.develop.android.contacts_map.R;
import com.develop.android.contacts_map.helper.MainActivityHelper;
import com.develop.android.contacts_map.model.Contacts;
import com.develop.android.contacts_map.view.MapsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sakshiagarwal on 18/01/17.
 */

public class ContactsMapFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_contacts_map, container, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showContactsWithAddress();
    }

    private void showContactsWithAddress() {
        List<Contacts> contacts = new MainActivityHelper().readContactsFromPhone(getActivity());
        List<Contacts> contactsWithAddress = new ArrayList<>();
        for (int i = 0; i < contacts.size(); i++) {
            if (null != contacts.get(i).getAddress())
                if (!contacts.get(i).getAddress().isEmpty())
                    contactsWithAddress.add(contacts.get(i));

        }
        ContactsMapAdapter adapter = new ContactsMapAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setDataList(contactsWithAddress);
        adapter.notifyDataSetChanged();
    }

    public class ContactsMapAdapter extends RecyclerView.Adapter<ContactsMapAdapter.ViewHolder> {

        private List<Contacts> mList;

        void setDataList(List<Contacts> contactsList) {
            mList = contactsList;
        }

        @Override
        public void onBindViewHolder(ContactsMapAdapter.ViewHolder holder, int position) {
            Contacts contacts = mList.get(position);
            holder.name.setText(contacts.getName());
            holder.email.setText(contacts.getEmail());
            holder.phone.setText(contacts.getPhone());
            holder.office.setText(contacts.getOfficePhone());
            holder.contacts = contacts;
        }

        @Override
        public ContactsMapAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contacts, parent, false);
            return new ContactsMapAdapter.ViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView name, email, phone, office;
            Contacts contacts;

            ViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                email = (TextView) itemView.findViewById(R.id.email);
                phone = (TextView) itemView.findViewById(R.id.phone);
                office = (TextView) itemView.findViewById(R.id.office);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String contactDetails = contacts.getName() + "\n" + contacts.getEmail() + "\n" + contacts.getPhone();
                        getActivity().startActivity(new Intent(getActivity(), MapsActivity.class)
                                                        .putExtra(getString(R.string.intent_address), contacts.getAddress())
                                                        .putExtra(getString(R.string.intent_contact), contactDetails));
                    }
                });
            }
        }
    }
}