package com.pj567.movie.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.pj567.movie.event.ServerEvent;
import com.pj567.movie.ui.activity.SearchActivity;
import com.pj567.movie.util.AppManager;
import com.pj567.movie.util.L;

import org.greenrobot.eventbus.EventBus;

/**
 * @author pj567
 * @date :2021/1/5
 * @description:
 */
public class SearchReceiver extends BroadcastReceiver {
    public static String action = "android.content.movie.search.Action";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (action.equals(intent.getAction()) && intent.getExtras() != null) {
            if (AppManager.getInstance().getActivity(SearchActivity.class) != null) {
                AppManager.getInstance().backActivity(SearchActivity.class);
                EventBus.getDefault().post(new ServerEvent(ServerEvent.SERVER_SEARCH, intent.getExtras().getString("title")));
            } else {
                Intent newIntent = new Intent(context, SearchActivity.class);
                newIntent.putExtra("title", intent.getExtras().getString("title"));
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(newIntent);
            }
        }
    }
}