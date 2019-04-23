package com.youzan.ad;

import com.imooc.ad.search.vo.SearchRequest;
import com.imooc.ad.search.vo.SearchResponse;

/**
 * Created by Qinyi.
 * 冥 2019-04-20 14:41:35
 * ISender接口用于广告检索请求  作用:获取广告创意数据
 */
public interface ISearch {

    SearchResponse fetchAds(SearchRequest request);
}
