package com.develop.android.contacts_map.view.fragment;

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
import com.develop.android.contacts_map.helper.NetworkHelper;
import com.develop.android.contacts_map.interfaces.ResponseInterface;
import com.develop.android.contacts_map.model.Contacts;
import com.develop.android.contacts_map.utils.AppConstants;
import com.develop.android.contacts_map.utils.AppUtils;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by sakshiagarwal on 18/01/17.
 */

public class AllContactsFragment extends Fragment {
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_all_contacts, container, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getContactsFromServer();
    }

    private void getContactsFromServer() {
        AppUtils.getInstance().showProgressDialog(getActivity(), "", getString(R.string.loading));
        new NetworkHelper().getRequestForServer(AppConstants.URL_CONTACTS, new ResponseInterface() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                AppUtils.getInstance().dismissProgressDialog();
                List<Contacts> contacts = new MainActivityHelper().fetchContactsFromServer(jsonArray);
                new MainActivityHelper().addContactsToContactList(contacts, getActivity());
                showAllContactsFromPhone();
            }

            @Override
            public void onFailure(String message) {
                AppUtils.getInstance().dismissProgressDialog();
                AppUtils.getInstance().showAlertDialogBox(getActivity(), message);
            }
        });
    }

    private void showAllContactsFromPhone() {
        AppUtils.getInstance().showProgressDialog(getActivity(), "", "Fetching contacts from phonebook");
        List<Contacts> contacts = new MainActivityHelper().readContactsFromPhone(getActivity());
        ContactsAdapter adapter = new ContactsAdapter();
        recyclerView.setAdapter(adapter);
        AppUtils.getInstance().dismissProgressDialog();
        adapter.setDataList(contacts);
        adapter.notifyDataSetChanged();
    }

    private class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

        private List<Contacts> mList;

        void setDataList(List<Contacts> contactsList) {
            mList = contactsList;
        }

        @Override
        public void onBindViewHolder(ContactsAdapter.ViewHolder holder, int position) {
            Contacts contacts = mList.get(position);
            holder.name.setText(contacts.getName());
            holder.email.setText(contacts.getEmail());
            holder.phone.setText(contacts.getPhone());
            holder.office.setText(contacts.getOfficePhone());
        }

        @Override
        public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contacts, parent, false);
            return new ContactsAdapter.ViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView name, email, phone, office;

            ViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                email = (TextView) itemView.findViewById(R.id.email);
                phone = (TextView) itemView.findViewById(R.id.phone);
                office = (TextView) itemView.findViewById(R.id.office);
            }
        }
    }
}
