package com.example.workatyourwill;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    ImageView carpenter;
    ImageView electrician;
    ImageView painter;
    ImageView photographer;
    ImageView internetProvider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        carpenter=view.findViewById(R.id.prof1);
        electrician=view.findViewById(R.id.prof2);
        painter=view.findViewById(R.id.prof3);
        photographer=view.findViewById(R.id.prof4);
        internetProvider=view.findViewById(R.id.prof5);
        carpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Display_Services.class);
                intent.putExtra("category","Carpenter");

                startActivity(intent);


//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();

            }
        });
        electrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Display_Services.class);
                intent.putExtra("category","Electrician");

                startActivity(intent);

            }
        });
        painter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Display_Services.class);
                intent.putExtra("category","Painter");

                startActivity(intent);

            }
        });
        photographer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Display_Services.class);
                intent.putExtra("category","Photographer");

                startActivity(intent);

            }
        });
        internetProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Display_Services.class);
                intent.putExtra("category","Internet Provider");

                startActivity(intent);

            }
        });
        return view;
    }
}
