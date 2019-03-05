package cn.piesat.medicaid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.modeBean.SubstanceScale;

public class SearchUnkwonAdapter extends RecyclerView.Adapter<SearchUnkwonAdapter.ViewHolder> {
    Context context;
    List<SubstanceScale> mInfolist;


    private LayoutInflater mInflater;


    public SearchUnkwonAdapter(Context context1, List<SubstanceScale> data) {
        this.mInflater = LayoutInflater.from(context1);
        this.context = context1;
        this.mInfolist = data;

    }

    /**
     * 刷新数据
     *
     * @param mData
     */
    public void refreshData(List<SubstanceScale> mData) {
        if (null != mInfolist) {
            mInfolist.clear();
        }
        mInfolist.addAll(mData);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_unkown, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        SubstanceScale mInfo = mInfolist.get(position);
        holder.itemName.setText(mInfo.substanceName);
        holder.loadProgressBar.setProgress((int) mInfo.scale);
        holder.tvProgressBar.setText(mInfo.scale+"%");
        if (position % 2 == 0) {
            holder.itemBackgroud.setBackgroundResource(R.color.them_color_F5F8FD);
        } else {
            holder.itemBackgroud.setBackgroundResource(R.color.white);

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.loadProgressBar)
        ProgressBar loadProgressBar;
        @BindView(R.id.item_bg)
        LinearLayout itemBackgroud;
        @BindView(R.id.tv_ProgressBar)
        TextView tvProgressBar;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
