package cn.piesat.medicaid.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.tabmode.SubstanceInfo;
import cn.piesat.medicaid.ui.activity.SearchActivity;
import cn.piesat.medicaid.ui.view.OnItemCheckClickListener;
import cn.piesat.medicaid.ui.view.OnItemClickListener;
import cn.piesat.medicaid.util.ShowUtil;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private String searchKey;
    SearchActivity context;
    List<SubstanceInfo> mInfolist;
    private LayoutInflater mInflater;
    private OnItemClickListener onItemClickListener;
    private OnItemCheckClickListener onItemCheckClickListener;


    public void setOnItemCheckClickListener(OnItemCheckClickListener onItemClickListener) {
        this.onItemCheckClickListener = onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 增加数据
     *
     * @param mData
     */
    public void AllData(List<SubstanceInfo> mData) {
        mInfolist.addAll(mData);
        notifyDataSetChanged();
    }

    /**
     * 刷新数据
     *
     * @param mData
     */
    public void refreshData(List<SubstanceInfo> mData) {
        if (null != mInfolist) {
            mInfolist.clear();
        }
        mInfolist.addAll(mData);
        notifyDataSetChanged();
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public SearchAdapter(SearchActivity context1, List<SubstanceInfo> data) {
        this.mInflater = LayoutInflater.from(context1);
        this.context = context1;
        this.mInfolist = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_search_keyword, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        SubstanceInfo mInfo = mInfolist.get(position);
        holder.itemName.setText(ShowUtil.matcherSearchTitle(Color.parseColor("#1FAC93"), mInfo.substanceName, searchKey));
        if (position % 2 == 0) {
            holder.itemBackgroud.setBackgroundResource(R.color.them_color_F5F8FD);
        } else {
            holder.itemBackgroud.setBackgroundResource(R.color.white);
        }
        if (null != context.selectSubstance && context.selectSubstance.contains(mInfo)) {
            holder.checkbox.setChecked(true);
        } else {
            holder.checkbox.setChecked(false);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mInfolist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.checkbox)
        CheckBox checkbox;
        @BindView(R.id.item_backgroud)
        LinearLayout itemBackgroud;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            ButterKnife.bind(this, view);
            checkbox.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View view) {
            if (null != onItemClickListener) {
                onItemClickListener.onItemClick(view, getLayoutPosition());
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (null != onItemCheckClickListener) {
                onItemCheckClickListener.onItemClick(buttonView, getLayoutPosition(), isChecked);
            }

        }
    }
}
