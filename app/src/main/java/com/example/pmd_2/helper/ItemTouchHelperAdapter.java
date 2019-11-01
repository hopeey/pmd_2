package com.example.pmd_2.helper;

public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);

    void onItemMoved();
}
