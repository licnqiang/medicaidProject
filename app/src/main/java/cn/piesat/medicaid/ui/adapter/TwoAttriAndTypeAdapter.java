package cn.piesat.medicaid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.tabmode.Attrs;
import cn.piesat.medicaid.ui.activity.SearchUnkwonActivity;
import cn.piesat.medicaid.ui.view.OnItemCheckClickListener;
import cn.piesat.medicaid.ui.view.OnItemClickListener;

/**
 * 一级属性/症状列表
 */
public class TwoAttriAndTypeAdapter extends RecyclerView.Adapter<TwoAttriAndTypeAdapter.ViewHolder> {

    SearchUnkwonActivity context;
    List<Attrs> mInfolist;
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
    public void refreshData(List<Attrs> mData) {
        if (null != mInfolist) {
            mInfolist.clear();
        }
        mInfolist.addAll(mData);
        notifyDataSetChanged();
    }


    public TwoAttriAndTypeAdapter(SearchUnkwonActivity context1, List<Attrs> data) {
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
        Attrs attrs = mInfolist.get(position);
        holder.itemName.setText(Html.fromHtml(attrs.comment));
        if (context.searchKeyWords.contains(attrs)) {
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
                onItemCheckClickListener.onItemClick(checkbox, getLayoutPosition(), isChecked);
            }
        }
    }
}
