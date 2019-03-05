package cn.piesat.medicaid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.tabmode.Attrs;
import cn.piesat.medicaid.ui.view.OnItemClickListener;

/**
 * 搜索关键字列表
 */
public class SearchKeyWordAdapter extends RecyclerView.Adapter<SearchKeyWordAdapter.ViewHolder> {

    Context context;
    List<Attrs> mInfolist;

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
    public void refreshData(List<Attrs> mData) {
        if (null != mInfolist) {
            mInfolist.clear();
        }
        mInfolist.addAll(mData);
        notifyDataSetChanged();
    }


    public SearchKeyWordAdapter(Context context1, List<Attrs> data) {
        this.mInflater = LayoutInflater.from(context1);
        this.context = context1;
        this.mInfolist = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_search, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemName.setText(mInfolist.get(position).comment);

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
        @BindView(R.id.del_key_word)
        ImageView delKeyWord;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            delKeyWord.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != onItemClickListener) {
                onItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }
}
