package cn.piesat.medicaid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.modeBean.PkBean;
import cn.piesat.medicaid.tabmode.Reactant;

/**
 * 物质详情对比
 */
public class PkAdapter extends RecyclerView.Adapter<PkAdapter.ViewHolder> {

    Context context;
    List<PkBean> mInfolist;

    private LayoutInflater mInflater;


    public PkAdapter(Context context1, List<PkBean> data) {
        this.mInflater = LayoutInflater.from(context1);
        this.context = context1;
        this.mInfolist = data;

    }

    /**
     * 刷新数据
     *
     * @param mData
     */
    public void refreshData(List<PkBean> mData) {
        if (null != mInfolist) {
            mInfolist.clear();
        }
        mInfolist.addAll(mData);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_pk, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PkBean pkBean = mInfolist.get(position);
        Log.e("----------","----pkBean------"+new Gson().toJson(pkBean));
        holder.tvLeft.setText(Html.fromHtml(pkBean.itemName1));
        holder.tvRight.setText(Html.fromHtml(pkBean.itemName2));

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
        @BindView(R.id.tv_left)
        TextView tvLeft;
        @BindView(R.id.tv_right)
        TextView tvRight;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
