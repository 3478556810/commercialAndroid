package com.example.carouselfigure.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carouselfigure.R;
import com.example.carouselfigure.adapter.RecyclerAdapterForPurchaseItem;
import com.example.carouselfigure.entity.Commodity;
import com.example.carouselfigure.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;


public class shopping extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String text;
    //recyclerView
    private RecyclerView recyclerViewForPurchase;
    private RecyclerAdapterForPurchaseItem rcAdapterForPurchase;
    private LinearLayoutManager layoutManagerForPurchase;
    private List<Commodity> pList = new ArrayList();
    //
    private Handler handler = new Handler();
    //purchase
    private TextView aggregatePrize;
    private Button allCheckedBtn;

    public shopping(String text) {
        // Required empty public constructor
        this.text = text;
    }

    public shopping() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchase, container, false);
        aggregatePrize = view.findViewById(R.id.aggregatePrize);
        allCheckedBtn = view.findViewById(R.id.allChecked);

        DBHelper dBHelper = new DBHelper(getActivity(), "Data.db", null, 1);
        dBHelper.getWritableDatabase();
        SQLiteDatabase db = dBHelper.getReadableDatabase();
        recyclerViewForPurchase = view.findViewById(R.id.purchase_rcView);
//        pList.add(new Commodity(0, db));
//        pList.add(new Commodity(1, db));
        rcAdapterForPurchase = new RecyclerAdapterForPurchaseItem(pList);
        layoutManagerForPurchase = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerViewForPurchase.setLayoutManager(layoutManagerForPurchase);
        recyclerViewForPurchase.setAdapter(rcAdapterForPurchase);
        recyclerViewForPurchase.setItemAnimator(new DefaultItemAnimator());
        handler.post(taskForPurchaseItem);
        return view;
    }

    Double sum = 0.0;
    private Runnable taskForPurchaseItem = new Runnable() {
        public void run() {
            handler.postDelayed(this, 100);
            //purchaseItem遍历
            for (int i = 0; i < recyclerViewForPurchase.getChildCount(); i++) {
                ConstraintLayout layout = (ConstraintLayout) recyclerViewForPurchase.getChildAt(i);
                Button addBtn = layout.findViewById(R.id.addBtn);
                Button subBtn = layout.findViewById(R.id.subBtn);
                TextView purchaseSingleTypeItemCount = layout.findViewById(R.id.purchaseSingleTypeItemCount);
                TextView purchasePrize = layout.findViewById(R.id.purchase_prizeView);
                Button checkedBtn = layout.findViewById(R.id.itemCheckBtn);
                //增减按钮点击事件
                int finalI = i;
                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        purchaseSingleTypeItemCount.setText(String.valueOf(Integer.parseInt((String) purchaseSingleTypeItemCount.getText()) + 1));
                        purchasePrize.setText(String.valueOf(Double.parseDouble((String) purchasePrize.getText()) + pList.get(finalI).getPrize()));
                    }
                });
                subBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt((String) purchaseSingleTypeItemCount.getText()) > 1) {
                            purchaseSingleTypeItemCount.setText(String.valueOf(Integer.parseInt((String) purchaseSingleTypeItemCount.getText()) - 1));
                            purchasePrize.setText(String.valueOf(Double.parseDouble((String) purchasePrize.getText()) - pList.get(finalI).getPrize()));
                        } else
                            Toast.makeText(getActivity(), "真是王八办走读，鳖不住校了", Toast.LENGTH_LONG).show();
                    }
                });
                //合计事件
                aggregatePrize.setText(String.valueOf(sum += Double.parseDouble((String) purchasePrize.getText())));
                //选择事件
                checkedBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkedBtn.setBackgroundResource(R.drawable.checked);
                    }
                });

            }
            sum = 0.0;

            allCheckedBtn.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     allCheckedBtn.setBackgroundResource(R.drawable.checked);
                                                     for (int i = 0; i < recyclerViewForPurchase.getChildCount(); i++) {
                                                         ConstraintLayout layout = (ConstraintLayout) recyclerViewForPurchase.getChildAt(i);
                                                         Button addBtn = layout.findViewById(R.id.addBtn);
                                                         Button subBtn = layout.findViewById(R.id.subBtn);
                                                         TextView purchaseSingleTypeItemCount = layout.findViewById(R.id.purchaseSingleTypeItemCount);
                                                         TextView purchasePrize = layout.findViewById(R.id.purchase_prizeView);
                                                         Button checkedBtn = layout.findViewById(R.id.itemCheckBtn);
                                                         checkedBtn.setBackgroundResource(R.drawable.checked);
                                                     }
                                                 }
                                             }
            );
        }


    };
}