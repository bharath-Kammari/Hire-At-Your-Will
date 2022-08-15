package com.example.workatyourwill;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.DateFormat;
import java.util.Date;


public class ServicesFragment extends Fragment {
    class NoteViewHolder extends RecyclerView.ViewHolder{
        public TextView ServiceTitle;
        public TextView ServiceDesc;
        LinearLayout mNoteLayout;
        CardView cardView;
        ImageView popupButton;
        TextView businessName;
        TextView cost;
        TextView rating;
        TextView bookings;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            ServiceTitle=itemView.findViewById(R.id.noteTitle);
            ServiceDesc=itemView.findViewById(R.id.noteContent);
            mNoteLayout=itemView.findViewById(R.id.noteLayout);
            cardView=itemView.findViewById(R.id.notecard);
            businessName=itemView.findViewById(R.id.noteBusinessName);
            cost=itemView.findViewById(R.id.serv_cost);
            popupButton=itemView.findViewById(R.id.menupopupButton);
            rating=itemView.findViewById(R.id.rating);
            bookings=itemView.findViewById(R.id.booking_month);


        }


    }
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;
    FirebaseUser firebaseUser;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    FirestoreRecyclerAdapter serviceAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_services,container,false);
        recyclerView=view.findViewById(R.id.recyclerView);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();

        staggeredGridLayoutManager=new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        Query query=firebaseFirestore.collection("requests").whereEqualTo("requestFrom",firebaseUser.getUid());

        FirestoreRecyclerOptions<RequestModel> allServices=new FirestoreRecyclerOptions.Builder<RequestModel>().setQuery(query,RequestModel.class).build();

        serviceAdapter=new FirestoreRecyclerAdapter<RequestModel, NoteViewHolder>(allServices) {
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull RequestModel model) {

                String serv_title=model.getS_name();
                String serv_desc=model.getS_desc();
                String data="jflsjfse";
                String businessName=model.getBusiness_name();
                String cost=model.getS_cost();
                Toast.makeText(getContext(),"Success",Toast.LENGTH_SHORT).show();
//                Date date_added=model.getDate_added();
//                String worker_id=model.getWorker_id();
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DocumentSnapshot documentSnapshot=getSnapshots().getSnapshot(position);
                        String docId=documentSnapshot.getId();
                        Toast.makeText(getContext(),docId,Toast.LENGTH_SHORT).show();


                    }
                });


                String rating=model.getS_rating()+"";
                String bookings_month=model.getBookings_month()+"";
//                data= DateFormat.getDateInstance().format(date_added);
                holder.ServiceTitle.setText(serv_title);
                holder.ServiceDesc.setText(serv_desc);

                holder.cost.setText(cost+"");
                holder.rating.setText(rating);
                holder.bookings.setText(bookings_month);
                holder.businessName.setText(businessName);



//                String docId=serviceAdapter.getSnapshots().getSnapshot(position).getId();

            }

            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
                return new NoteViewHolder(view1);
            }
        };





//                String docId=serviceAdapter.getSnapshots().getSnapshot(position).getId();
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(serviceAdapter);



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        serviceAdapter.startListening();
    }
}
