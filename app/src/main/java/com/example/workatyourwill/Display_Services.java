package com.example.workatyourwill;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.DateFormat;
import java.util.Date;


public class Display_Services extends AppCompatActivity {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    RecyclerView recyclerView;
    String profession;

    FirestoreRecyclerAdapter<FirebaseModel,NoteViewHolder> serviceAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
//    public Display_Services() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment Services_Fragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static Display_Services newInstance(String param1, String param2) {
//        Display_Services fragment = new Display_Services();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
        setContentView(R.layout.display_services);

        recyclerView=findViewById(R.id.recyclerView);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();
        Intent intent=getIntent();
        profession=intent.getStringExtra("category");
        Toast.makeText(Display_Services.this,profession,Toast.LENGTH_SHORT).show();

        staggeredGridLayoutManager=new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        Query query=firebaseFirestore.collectionGroup("services").whereEqualTo("category",profession);

        FirestoreRecyclerOptions<FirebaseModel> allServices=new FirestoreRecyclerOptions.Builder<FirebaseModel>().setQuery(query,FirebaseModel.class).build();
        serviceAdapter=new FirestoreRecyclerAdapter<FirebaseModel, NoteViewHolder>(allServices) {
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull FirebaseModel model) {

                String serv_title=model.getS_name();
                String serv_desc=model.getS_desc();
                String data="jflsjfse";
                double cost=model.getS_cost();
                Date date_added=model.getDate_added();
                String worker_id=model.getWorker_id();
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    DocumentSnapshot documentSnapshot=getSnapshots().getSnapshot(position);
                        String docId=documentSnapshot.getId();
                        Toast.makeText(Display_Services.this,docId,Toast.LENGTH_SHORT).show();
                    Intent intent1=new Intent(Display_Services.this,Book_Service.class);
                    intent1.putExtra("docId",docId);
                    intent1.putExtra("workerId",worker_id);
                    intent1.putExtra("category",profession);
                    intent1.putExtra("business_name",holder.businessName.getText().toString());
                    startActivity(intent1);

                    }
                });

                DocumentReference documentReference=firebaseFirestore.collection("workers").document(worker_id);
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String businessName=(String)documentSnapshot.get("business_name");
                        holder.businessName.setText(businessName);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Display_Services.this,"Couldn't get Data!",Toast.LENGTH_SHORT).show();
                    }
                });
                String rating=model.getS_rating()+"";
                String bookings_month=model.getS_bookings_per_month()+"";
                data= DateFormat.getDateInstance().format(date_added);
                holder.ServiceTitle.setText(serv_title);
                holder.ServiceDesc.setText(serv_desc);

                holder.cost.setText(cost+"");
                holder.rating.setText(rating);
                holder.bookings.setText(bookings_month);



                String docId=serviceAdapter.getSnapshots().getSnapshot(position).getId();

            }

            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
                return new NoteViewHolder(view1);
            }
        };
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(serviceAdapter);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view=inflater.inflate(R.layout.display_services, container, false);
//
//        recyclerView=view.findViewById(R.id.recyclerView);
//        firebaseAuth=FirebaseAuth.getInstance();
//        firebaseUser=firebaseAuth.getCurrentUser();
//        firebaseFirestore=FirebaseFirestore.getInstance();
//        Intent intent
//        staggeredGridLayoutManager=new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
//        Query query=firebaseFirestore.collectionGroup("services").whereEqualTo("category",);
//
//        FirestoreRecyclerOptions<FirebaseModel> allServices=new FirestoreRecyclerOptions.Builder<FirebaseModel>().setQuery(query,FirebaseModel.class).build();
//        serviceAdapter=new FirestoreRecyclerAdapter<FirebaseModel, NoteViewHolder>(allServices) {
//            @Override
//            protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull FirebaseModel model) {
//                String serv_title=model.getS_name();
//                String serv_desc=model.getS_desc();
//                String data="jflsjfse";
//                double cost=model.getS_cost();
//                Date date_added=model.getDate_added();
//                String rating=model.getS_rating()+"";
//                String bookings_month=model.getS_bookings_per_month()+"";
//                data= DateFormat.getDateInstance().format(date_added);
//                holder.ServiceTitle.setText(serv_title);
//                holder.ServiceDesc.setText(serv_desc);
//                holder.dateAdded.setText(data);
//                holder.cost.setText(cost+"");
//                holder.rating.setText(rating);
//                holder.bookings.setText(bookings_month);
//
//
//
//                String docId=serviceAdapter.getSnapshots().getSnapshot(position).getId();
//
//            }
//
//            @NonNull
//            @Override
//            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view1=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
//                return new NoteViewHolder(view1);
//            }
//        };
//        recyclerView.setHasFixedSize(true);
//
//        recyclerView.setLayoutManager(staggeredGridLayoutManager);
//        recyclerView.setAdapter(serviceAdapter);
//
//        return view;
//
//
//    }
    public class NoteViewHolder extends RecyclerView.ViewHolder{
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

    @Override
    public void onStart() {
        super.onStart();
        serviceAdapter.startListening();
    }
}