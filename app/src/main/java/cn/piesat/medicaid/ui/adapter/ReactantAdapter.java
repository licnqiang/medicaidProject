package cn.piesat.medicaid.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.tabmode.Reactant;
import cn.piesat.medicaid.ui.view.OnItemCheckClickListener;
import cn.piesat.medicaid.util.ShowUtil;

/**
 * 反应物列表
 */
public class ReactantAdapter extends RecyclerView.Adapter<ReactantAdapter.ViewHolder> implements Filterable {

    Context context;
    List<Reactant> mInfolist;
    List<Reactant> originalInfolist= new ArrayList<Reactant>();
    private MyFilter myFilter;
    private LayoutInflater mInflater;
    private OnItemCheckClickListener onItemCheckClickListener;
    private String keyWord="";

    public void setOnItemCheckClickListener(OnItemCheckClickListener onItemClickListener) {
        this.onItemCheckClickListener = onItemClickListener;
    }



    /**
     * 刷新数据
     *
     * @param mData
     */
    public void refreshData(List<Reactant> mData) {
        if (null != mInfolist) {
            originalInfolist.clear();
            mInfolist.clear();
        }
        originalInfolist.addAll(mData);
        mInfolist.addAll(mData);
        notifyDataSetChanged();
    }


    public ReactantAdapter(Context context1, List<Reactant> data) {
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
        Reactant Reactant = mInfolist.get(position);
        holder.itemName.setText(ShowUtil.matcherSearchTitle(R.color.them_color, Reactant.reactantName, keyWord));

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mInfolist.size();
    }

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter();
        }
        return myFilter;
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
                onItemCheckClickListener.onItemClick(buttonView, getLayoutPosition(), isChecked);
            }

        }
    }

    class MyFilter extends Filter {
        private ArrayList<Reactant> reactantArrayList = new ArrayList<Reactant>();

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // 创建FilterResults对象
            FilterResults results = new FilterResults();
            if (TextUtils.isEmpty(constraint)) {
                results.values = originalInfolist;
                results.count = originalInfolist.size();
            } else {
                reactantArrayList.clear();
                for (Reactant reactant : originalInfolist) {
                    if (reactant.reactantName.contains(constraint.toString())) {
                        reactantArrayList.add(reactant);
                    }
                }
                results.values = reactantArrayList;
                results.count = reactantArrayList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (null != results) {
                keyWord=constraint.toString();
                mInfolist.clear();
                mInfolist.addAll((List<Reactant>) results.values);
                // 刷新数据源显示
                notifyDataSetChanged();
            }
        }
    }
}
