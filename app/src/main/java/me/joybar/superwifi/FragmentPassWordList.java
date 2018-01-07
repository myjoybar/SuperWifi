package me.joybar.superwifi;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.joybar.library.common.log.L;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.joybar.superwifi.adapter.WifiPasswordAdapter;
import me.joybar.superwifi.base.BaseFragment;
import me.joybar.superwifi.data.WifiCustomInfo;
import me.joybar.superwifi.data.db.WifiInfoDbManager;
import me.joybar.superwifi.utils.WifiPasswordManager;
import me.joybar.superwifi.utils.anni.TransitionHelper;
import me.joybar.superwifi.utils.root.RootCheck;

/**
 * Created by joybar on 23/12/2017.
 */

public class FragmentPassWordList extends BaseFragment {

    private static final int REQUEST_CATEGORY = 0x2300;
    public Unbinder unbinder;

    @BindView(R2.id.rv_content)
    RecyclerView recyclerView;

    @BindView(R2.id.tv_error)
    TextView tvError;

    @BindView(R2.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private WifiPasswordAdapter mListAdapter;

    private WifiInfoDbManager wifiInfoDbManager;


    private List<WifiCustomInfo> mList = new ArrayList<>(0);

    public FragmentPassWordList() {
        // Requires empty public constructor
    }

    public static FragmentPassWordList newInstance() {
        return new FragmentPassWordList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mList = getArguments().getParcelableArrayList("wifi_list");
        }
        wifiInfoDbManager = new WifiInfoDbManager(getHostActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rv_refresh, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initView(view);
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView(View view) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2,
                GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        recyclerView.setAdapter(mListAdapter = new WifiPasswordAdapter(mList) {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onBindViewHolder(WifiPasswordAdapter.WifiPasswordViewHolder holder, final
            int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Activity activity = getActivity();
                        startQuizActivityWithTransition(activity, v.findViewById(R.id.wifi_title)
                                , mListAdapter.getItem(position));
                    }
                });

            }
        });
        recyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver
                .OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                getActivity().supportStartPostponedEnterTransition();
                return true;
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                requestWifiList(true);
            }
        });
        refreshLayout.setEnabled(false);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                refreshLayout.setRefreshing(true);
//                requestWifiList();
//            }
//        }, 200);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestWifiList(false);
            }
        },200);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void requestWifiList(boolean isRefresh) {
        boolean mRootAccess = RootCheck.canRunRootCommands();
        if (mRootAccess) {
            if (isRefresh) {
                new AsyncTask<Void, Void, ArrayList<WifiCustomInfo>>() {

                    @Override
                    protected ArrayList<WifiCustomInfo> doInBackground(Void... voids) {
                        ArrayList<WifiCustomInfo> list = new WifiPasswordManager()
                                .readWifiConfigFile();
                        Collections.sort(list, new Comparator<WifiCustomInfo>() {
                            public int compare(WifiCustomInfo wifiCustomInfo1, WifiCustomInfo
                                    wifiCustomInfo2) {
                                return wifiCustomInfo1.getSSIDName().compareTo
                                        (wifiCustomInfo2.getSSIDName());
                            }
                        });
                        return list;
                    }

                    @Override
                    protected void onPostExecute(ArrayList<WifiCustomInfo> wifiCustomInfos) {
                        super.onPostExecute(wifiCustomInfos);
                        // L.d(TAG,wifiCustomInfos);
                        refreshLayout.setEnabled(true);
                        refreshLayout.setRefreshing(false);
                        mListAdapter.replaceData(wifiCustomInfos);
                        getActivity().findViewById(R.id.progress).setVisibility(View.GONE);
                        if (null != wifiCustomInfos && wifiCustomInfos.size() != 0) {
                            wifiInfoDbManager.insertWifiDataList(wifiCustomInfos);
                        }
                    }
                }.execute();
            } else {
                ArrayList<WifiCustomInfo> wifiCustomInfos = (ArrayList<WifiCustomInfo>)
                        wifiInfoDbManager.queryAllWifiData();
                if (null != wifiCustomInfos && wifiCustomInfos.size() != 0) {
                    L.d("not null");
                    refreshLayout.setEnabled(true);
                    refreshLayout.setRefreshing(false);
                    mListAdapter.replaceData(wifiCustomInfos);
                    getActivity().findViewById(R.id.progress).setVisibility(View.GONE);
                } else {
                    L.d("null");
                    requestWifiList(true);
                }
            }
        } else {
            Snackbar.make(refreshLayout, getContext().getString(R.string.no_root_access),
                    Snackbar.LENGTH_LONG).show();
        }

    }


    private void startQuizActivityWithTransition(Activity activity, View toolbar, WifiCustomInfo
            category) {

        final Pair[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
                new Pair<>(toolbar, activity.getString(R.string.transition_toolbar)));
        @SuppressWarnings("unchecked") ActivityOptionsCompat sceneTransitionAnimation =
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs);

        // Start the activity with the participants, animating from one to the other.
        final Bundle transitionBundle = sceneTransitionAnimation.toBundle();
        Intent startIntent = WifiPasswordActivity.getStartIntent(activity, category);
//        ActivityCompat.startActivity(activity,
//                startIntent,
//                transitionBundle);
        ActivityCompat.startActivityForResult(activity, startIntent, REQUEST_CATEGORY,
                transitionBundle);
    }


}
