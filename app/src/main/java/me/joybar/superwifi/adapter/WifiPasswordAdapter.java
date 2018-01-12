package me.joybar.superwifi.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.joybar.superwifi.R;
import me.joybar.superwifi.R2;
import me.joybar.superwifi.data.WifiCustomInfo;

/**
 * Created by joybar on 24/12/2017.
 */

public class WifiPasswordAdapter extends RecyclerView.Adapter<WifiPasswordAdapter
        .WifiPasswordViewHolder> {

    private List<WifiCustomInfo> mList;

    public WifiPasswordAdapter(List<WifiCustomInfo> mList) {
        this.mList = mList;
    }

    public void replaceData(List<WifiCustomInfo> list) {
        setList(list);
    }


    public WifiCustomInfo getItem(int position) {
        return mList.get(position);
    }

    private void setList(List<WifiCustomInfo> list) {
        mList = list;
        notifyDataSetChanged();
    }


    @Override
    public WifiPasswordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_wifi, null);
        WifiPasswordViewHolder holder = new WifiPasswordViewHolder(view);
        return holder;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(WifiPasswordViewHolder holder, int position) {
        WifiCustomInfo wifiCustomInfo = mList.get(position);
//        holder.itemView.setBackgroundColor(getColor(holder.itemView.getContext(), ColorSelector.getRandomBgSortedColorID()));
//        holder.tvWifiTitle.setTextColor(getColor(holder.tvWifiTitle.getContext(), ColorSelector.getRandomTvSortedColorID()));
//        holder.tvWifiTitle.setBackgroundColor(getColor(holder.tvWifiTitle.getContext(), ColorSelector.getRandomTvBgSortedColorID()));
        holder.tvWifiTitle.setText(wifiCustomInfo.getSSIDName());
        holder.tvWifiTitle.setTransitionName(wifiCustomInfo.getSSIDName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public  static class WifiPasswordViewHolder extends RecyclerView.ViewHolder {

        @BindView(R2.id.wifi_title)
        public TextView tvWifiTitle;

        public WifiPasswordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * Convenience method for color loading.
     *
     * @param colorRes The resource id of the color to load.
     * @return The loaded color.
     */
    private int getColor(Context context,@ColorRes int colorRes) {
        return ContextCompat.getColor(context, colorRes);
    }
}
