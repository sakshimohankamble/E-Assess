package com.example.e_assess.ui.dashboard;

import android.content.Context;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_assess.R;
//import com.example.e_assess.adapterGroups;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    RecyclerView review;
    Context context;
    private DatabaseReference reference;
    private  AdapterDashboard adapter;
    private ArrayList<ModelDashboard> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        review = (RecyclerView) root.findViewById(R.id.recyclerview);
        review.setLayoutManager(new LinearLayoutManager(getContext()));
        context = container.getContext();
        list = new ArrayList<>();
        adapter = new AdapterDashboard(context,list);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        review.setAdapter(adapter);
        reference = FirebaseDatabase.getInstance().getReference().child("Groups").child(uid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                     for(DataSnapshot dat :dataSnapshot.getChildren()) {

                             //   ModelDashboard mod = data.getValue(ModelDashboard.class);
                             // list.add(mod);
                             String stud1, stud2, stud3, topic, grpno;
                             //for(DataSnapshot data : dataSnapshot.getChildren()){
                             grpno = dat.child("GroupNo").getValue(String.class);
                             stud1 = dat.child("Student1").getValue(String.class);
                             stud2 = dat.child("Student2").getValue(String.class);
                             stud3 = dat.child("Student3").getValue(String.class);
                             topic = dat.child("TopicName").getValue(String.class);
                             ModelDashboard model = new ModelDashboard(grpno, stud1, stud2, stud3, topic);
                             list.add(model);
                             // adapter.notifyDataSetChanged();

                             //}

                     }
                     adapter.notifyDataSetChanged();
                 }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return root;

    }
}