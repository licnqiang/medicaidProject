package cn.piesat.medicaid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.modeBean.AttriAndsymType;
import cn.piesat.medicaid.tabmode.Reactant;
import cn.piesat.medicaid.ui.view.OnItemClickListener;

/**
 * 一级属性/症状列表
 */
public class OneAttriAndTypeAdapter extends RecyclerView.Adapter<OneAttriAndTypeAdapter.ViewHolder> {

    Context context;
    List<AttriAndsymType> mInfolist;
    private LayoutInflater mInflater;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 刷新数据
     *
     * @param mData
     */
    public void refreshData(List<AttriAndsymType> mData) {
        if (null != mInfolist) {
            mInfolist.clear();
        }
        mInfolist.addAll(mData);
        notifyDataSetChanged();
    }


    public OneAttriAndTypeAdapter(Context context1, List<AttriAndsymType> data) {
        this.mInflater = LayoutInflater.from(context1);
        this.context = context1;
        this.mInfolist = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_one_attr_type, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        AttriAndsymType attriAndsymType = mInfolist.get(position);
        holder.itemName.setText(attriAndsymType.typeName);
        holder.itemState.setSelected(attriAndsymType.isSelect);
        holder.itemName.setSelected(attriAndsymType.isSelect);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mInfolist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.item_state)
        RelativeLayout itemState;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            ButterKnife.bind(this, view);


        }

        @Override
        public void onClick(View v) {
            if (null != onItemClickListener) {
                onItemClickListener.onItemClick(v,getLayoutPosition());
            }
        }
    }
}
