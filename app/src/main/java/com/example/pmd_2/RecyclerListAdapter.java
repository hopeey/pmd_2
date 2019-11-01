package com.example.pmd_2;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pmd_2.helper.ItemTouchHelperAdapter;
import com.example.pmd_2.helper.ItemTouchHelperViewHolder;
import com.google.common.collect.Ordering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {

    public interface OnStartDragListener {
        void onStartDrag(RecyclerView.ViewHolder viewHolder);
    }

    private static final List<Integer> numbers = new ArrayList<>(10);

    private final OnStartDragListener mDragStartListener;

    public RecyclerListAdapter(OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.textView.setText(numbers.get(position).toString());
        holder.handleView.setOnTouchListener((v, event) -> {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                mDragStartListener.onStartDrag(holder);
            }
            return false;
        });
    }

    @Override
    public void onItemDismiss(int position) {
        numbers.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(numbers, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(numbers, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemMoved() {
        if (Ordering.natural().isOrdered(numbers) ||
                Ordering.natural().reverse().isOrdered(numbers)) {
            Toast.makeText(MainActivity.getAppContext(), "Ordered", Toast.LENGTH_LONG)
                    .show();
            Collections.shuffle(numbers);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return numbers.size();
    }

    public void fillNumbers(int max) {
        numbers.clear();
        for (int i = 0; i < max; i++) {
            numbers.add(i + 1);
        }
        Collections.shuffle(numbers);
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder
    implements ItemTouchHelperViewHolder {


        public final TextView textView;
        public final ImageView handleView;


        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
            handleView = itemView.findViewById(R.id.handle);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.YELLOW);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

}


