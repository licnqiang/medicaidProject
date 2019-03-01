package cn.piesat.medicaid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.tabmode.SubstanceInfo;
import cn.piesat.medicaid.ui.view.OnItemClickListener;
import cn.piesat.medicaid.util.ShowUtil;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private String searchKey;
    Context context;
    List<SubstanceInfo> mInfolist;
    private LayoutInflater mInflater;
    private OnItemClickListener onItemClickListener;

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

    public SearchAdapter(Context context1, List<SubstanceInfo> data) {
        this.mInflater = LayoutInflater.from(context1);
        this.context = context1;
        this.mInfolist = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_search, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        SubstanceInfo mInfo = mInfolist.get(position);
        holder.itemName.setText(ShowUtil.matcherSearchTitle(R.color.them_color, mInfo.substanceName, searchKey));
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

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View view) {
            if (null != onItemClickListener) {
                onItemClickListener.onItemClick(view, getLayoutPosition());
            }
        }
    }
}
