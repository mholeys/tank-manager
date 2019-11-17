package uk.co.mholeys.android.tankmanager.view.adapter;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import uk.co.mholeys.android.tankmanager.model.entity.Readings;
import uk.co.mholeys.android.tankmanager.model.entity.Tank;

public class ReadingsDiffCallback extends DiffUtil.Callback {

    private List<Readings> oldList;
    private List<Readings> newList;

    public ReadingsDiffCallback(List<Readings> oldList, List<Readings> newList) {
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
        return oldList.get(i).readingId == newList.get(i1).readingId;
    }

    @Override
    public boolean areContentsTheSame(int i, int i1) {
        return oldList.get(i).equals(newList.get(i1));
    }

}
