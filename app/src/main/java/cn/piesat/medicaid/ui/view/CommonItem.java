package cn.piesat.medicaid.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.piesat.medicaid.R;

/**
 * @author lq
 * @fileName common_item
 * @data on  2019/2/15 10:03
 * @describe 通用个人中心item项
 */
public class CommonItem extends LinearLayout {
    private final boolean isbootom;
    private final TextView bottomview;
    private final ImageView imageView;
    private final TextView textView;


    public CommonItem(Context context, AttributeSet attrs) {
        super(context);
        LayoutInflater.from(getContext()).inflate(R.layout.common_item, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.common_item_style);
        isbootom = ta.getBoolean(R.styleable.common_item_style_show_bottomline, true);
        bottomview = findViewById(R.id.item_bottom_line);
        imageView = findViewById(R.id.item_img);
        textView = findViewById(R.id.item_text);
        bottomview.setVisibility(isbootom ? VISIBLE : VISIBLE);
        textView.setText(ta.getString(R.styleable.common_item_style_show_text));
        imageView.setImageResource(ta.getResourceId(R.styleable.common_item_style_show_leftimg, R.mipmap.ic_launcher));
        ta.recycle();
    }
}
