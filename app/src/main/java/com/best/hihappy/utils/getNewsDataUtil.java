package com.best.hihappy.utils;

import com.best.hihappy.bean.NewsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FuKaiqiang on 2017-10-26.
 */

public class getNewsDataUtil {
    public static List<NewsBean.ResultBean.DataBean> getDatas(final int firstIndex, final int lastIndex,List<NewsBean.ResultBean.DataBean> mNewsBeanList) {
        List<NewsBean.ResultBean.DataBean> resList = new ArrayList<>();
        for (int i = firstIndex; i < lastIndex; i++) {
            if (i < mNewsBeanList.size()) {
                resList.add(mNewsBeanList.get(i));
            }
        }
        return resList;
    }
}
