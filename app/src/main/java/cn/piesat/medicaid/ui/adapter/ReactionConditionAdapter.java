package cn.piesat.medicaid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.tabmode.ReactionCondition;
import cn.piesat.medicaid.ui.view.OnItemCheckClickListener;
import cn.piesat.medicaid.ui.view.OnItemClickListener;

/**
 * 反应条件
 */
public class ReactionConditionAdapter extends RecyclerView.Adapter<ReactionConditionAdapter.ViewHolder> {

    Context context;
    List<ReactionCondition> mInfolist;
    private LayoutInflater mInflater;
    private OnItemCheckClickListener onItemCheckClickListener;

    public void setOnItemCheckClickListener(OnItemCheckClickListener onItemClickListener) {
        this.onItemCheckClickListener = onItemClickListener;
    }

    /**
     * 刷新数据
     *
     * @param mData
     */
    public void refreshData(List<ReactionCondition> mData) {
        if (null != mInfolist) {
            mInfolist.clear();
        }
        mInfolist.addAll(mData);
        notifyDataSetChanged();
    }


    public ReactionConditionAdapter(Context context1, List<ReactionCondition> data) {
        this.mInflater = LayoutInflater.from(context1);
        this.context = context1;
        this.mInfolist = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_reactant, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ReactionCondition reactionCondition = mInfolist.get(position);
        holder.itemName.setText(reactionCondition.reactionConName);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mInfolist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.checkbox)
        CheckBox checkbox;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            checkbox.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (null != onItemCheckClickListener) {
                onItemCheckClickListener.onItemClick(checkbox,getLayoutPosition(),isChecked);
            }

        }
    }
}
