package org.weread.callable;

/**
 * Created by mrgaver on 15-6-6.
 */
public interface SwipeViewCallback {

    void setRefreshing(boolean refreshing);

    boolean pullToRefreshEnabled();

    int[] getPullToRefreshColorResources();
}
