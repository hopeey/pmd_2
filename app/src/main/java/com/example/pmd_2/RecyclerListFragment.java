package com.example.pmd_2;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pmd_2.helper.SimpleItemTouchHelperCallback;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerListFragment extends Fragment
        implements RecyclerListAdapter.OnStartDragListener {

    private RecyclerListAdapter adapter;
    private ItemTouchHelper mItemTouchHelper;

    private Button plusButton;
    private Button minusButton;
    private TextView quantityView;
    private static int quantity = 10;


    public RecyclerListFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new RecyclerListAdapter(this);
        adapter.fillNumbers(quantity);

        plusButton = view.findViewById(R.id.button_plus);
        minusButton = view.findViewById(R.id.button_minus);
        quantityView = view.findViewById(R.id.quantity);
        setText();

        setOnClickListeners();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    private void setOnClickListeners() {
        plusButton.setOnClickListener(v -> {
            quantity++;
            if (quantity > Integer.MAX_VALUE - 1) {
                quantity = Integer.MAX_VALUE - 1;
            }
            adapter.fillNumbers(quantity);
            setText();
        });

        minusButton.setOnClickListener(v -> {
            quantity--;
            if (quantity < 3) {
                quantity = 3;
                return;
            }
            adapter.fillNumbers(quantity);
            setText();
        });
    }

    private void setText() {
        quantityView.setText(String.valueOf(quantity));
    }
}
