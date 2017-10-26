package com.best.hihappy.utils;

import com.best.hihappy.adapter.NewsAdapter;
import com.best.hihappy.bean.NewsBean;

import java.util.List;

/**
 * Created by FuKaiqiang on 2017-10-26.
 */

public class updateRecyUtil {
    public static void updateRecyclerView(int fromIndex, int toIndex, List<NewsBean.ResultBean.DataBean> mNewsBeanList, NewsAdapter adapter) {
        List<NewsBean.ResultBean.DataBean> newDatas = getNewsDataUtil.getDatas(fromIndex, toIndex,mNewsBeanList);
        if (newDatas.size() > 0) {
            adapter.updateList(newDatas, true);
        } else {
            adapter.updateList(null, false);
        }
    }
}
