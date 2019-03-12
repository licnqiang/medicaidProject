package cn.piesat.medicaid.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.piesat.medicaid.R;
import cn.piesat.medicaid.modeBean.SubstanceDetail;
import cn.piesat.medicaid.ui.activity.HazardAssessmentActivity;
import cn.piesat.medicaid.ui.activity.SelectReactionActivity;
import cn.piesat.medicaid.ui.view.OnItemClickListener;

/**
 * 药物详情
 */
public class SubstanceDetailAdapter extends RecyclerView.Adapter<SubstanceDetailAdapter.ViewHolder> {

    Context context;
    List<SubstanceDetail> mInfolist;
    private LayoutInflater mInflater;

    public SubstanceDetailAdapter(Context context1, List<SubstanceDetail> data) {
        this.mInflater = LayoutInflater.from(context1);
        this.context = context1;
        this.mInfolist = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.common_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SubstanceDetail substanceDetail = mInfolist.get(position);
        holder.itemTitle.setText(substanceDetail.titleName);
        holder.detailInfo.setText(Html.fromHtml(substanceDetail.Content.isEmpty() ? "暂无该信息" : substanceDetail.Content));
        holder.itemIcon.setImageResource(substanceDetail.drawableRe);
        if (position % 2 == 0) {
            holder.itemLlBackgroud.setBackgroundResource(R.color.them_color_F5F8FD);
        } else {
            holder.itemLlBackgroud.setBackgroundResource(R.color.white);

        }


        holder.itemLlBackgroud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != 7 && position != 8) {
                    if (holder.lookState.isSelected()) {
                        holder.lookState.setSelected(false);
                        holder.itemDetail.setVisibility(View.GONE);
                        holder.itemIcon.setVisibility(View.VISIBLE);
                        holder.itemIcon.setImageResource(substanceDetail.drawableRe);
                        holder.itemTitle.setTextColor(Color.parseColor("#5B5B5B"));
                        if (position % 2 == 0) {
                            holder.itemLlBackgroud.setBackgroundResource(R.color.them_color_F5F8FD);
                        } else {
                            holder.itemLlBackgroud.setBackgroundResource(R.color.white);

                        }

                    } else {
                        holder.itemIcon.setVisibility(View.INVISIBLE);
                        holder.itemTitle.setTextColor(Color.WHITE);
                        holder.itemDetail.setVisibility(View.VISIBLE);
                        holder.lookState.setSelected(true);
                        holder.itemLlBackgroud.setBackgroundResource(R.color.them_color);
                    }
                } else if (position == 7) {
                    context.startActivity(new Intent(context, HazardAssessmentActivity.class));
                } else if (position == 8) {
                    context.startActivity(new Intent(context, SelectReactionActivity.class).putExtra("substanceNum", substanceDetail.substanceNum));
                }

            }
        });


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
        @BindView(R.id.look_state)
        ImageView lookState;
        @BindView(R.id.item_icon)
        ImageView itemIcon;
        @BindView(R.id.item_title)
        TextView itemTitle;
        @BindView(R.id.detail_info)
        TextView detailInfo;
        @BindView(R.id.item_detail)
        LinearLayout itemDetail;
        @BindView(R.id.item_ll_backgroud)
        LinearLayout itemLlBackgroud;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
