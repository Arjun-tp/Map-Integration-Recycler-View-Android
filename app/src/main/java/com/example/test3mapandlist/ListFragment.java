package com.example.test3mapandlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.test3mapandlist.Adapter.UserAdapter;
import com.example.test3mapandlist.EntityClass.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public static RecyclerView recyclerView;
    public static List<UserModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.rcView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();

        // Initialize variable with data in the db
        list = DatabaseClass.getDatabase(getContext()).getDao().getAllData();

        // Recycler view Adapter
        recyclerView.setAdapter(new UserAdapter(getContext(), list, new UserAdapter.DeleteItemClickListener(){
            @Override
            public void onItemDelete(int position, int id) {
                DatabaseClass.getDatabase(getContext()).getDao().deleteData(id);
                startActivity(new Intent(getContext(), MainActivity.class));
                DatabaseClass.getDatabase(getContext()).getDao().getAllData();
            }
        }));
       /* searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    recyclerView.setAdapter(new UserAdapter(getContext(), list, new UserAdapter.DeleteItemClickListener() {
                        @Override
                        public void onItemDelete(int position, int id) {
                            DatabaseClass.getDatabase(getContext()).getDao().deleteData(id);
                            startActivity(new Intent(getContext(), MainActivity.class));
                            DatabaseClass.getDatabase(getContext()).getDao().getAllData();
                        }
                    }));
                    adapter.notifyDataSetChanged();
                } else {
                    filter(s.toString());
                }
            }
        });
*/
        return view;
    }
    /*private void filter(String text) {
        for (UserModel user: list){
            if (Integer.toString(user.getKey()).equals(text)){
                filterList.add(user);
            }
        }
        recyclerView.setAdapter(new UserAdapter(getContext(), filterList, new UserAdapter.DeleteItemClickListener() {
            @Override
            public void onItemDelete(int position, int id) {
                DatabaseClass.getDatabase(getContext()).getDao().deleteData(id);
                startActivity(new Intent(getContext(), MainActivity.class));
                DatabaseClass.getDatabase(getContext()).getDao().getAllData();
            }
        }));

        adapter.notifyDataSetChanged();
    }*/

    void initData(){
        list = new ArrayList<>();
        list = DatabaseClass.getDatabase(getContext()).getDao().getAllData();
        initData();
    }
}