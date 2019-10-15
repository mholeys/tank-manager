package uk.co.mholeys.android.tankmanager.view.adapter;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import uk.co.mholeys.android.tankmanager.model.entity.Tank;

class TankDiffCallback extends DiffUtil.Callback {

    private List<Tank> oldList;
    private List<Tank> newList;

    public TankDiffCallback(List<Tank> oldList, List<Tank> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int i, int i1) {
        return oldList.get(i).getId() == newList.get(i1).getId();
    }

    @Override
    public boolean areContentsTheSame(int i, int i1) {
        return oldList.get(i).equals(newList.get(i1));
    }

}
