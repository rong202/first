package com.youzan.ad.index.creativeunit;

import com.youzan.ad.index.IndexAware;
import com.youzan.ad.index.adunit.AdUnitObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by baimugudu on 2019/4/2
 * String:adId-unitId
 * 创意-推广单元中间表：正向索引
 */
@Component
@Slf4j
public class CreativeUnitIndex implements IndexAware<String,CreativeUnitObject> {

    //<adId-unitid,object>
    private static Map<String,CreativeUnitObject> objectMap;

   // <adId,unit set>
    private static Map<Long, Set<Long>> creativeUnitMap;

    // <unitId,adId set>
    private static Map<Long, Set<Long>> unitCreativeMap;

    static{
        objectMap = new ConcurrentHashMap<>();
        creativeUnitMap = new ConcurrentHashMap<>();
        unitCreativeMap = new ConcurrentHashMap<>();
    }



    @Override
    public CreativeUnitObject get(String key) {
        return objectMap.get(key);
    }

    @Override
    public void add(String key, CreativeUnitObject value) {
        objectMap.put(key,value);
        Set unitSet =  creativeUnitMap.get(value.getAdId());

        if(CollectionUtils.isEmpty(unitSet)){
            unitSet = new ConcurrentSkipListSet<>();
            creativeUnitMap.put(value.getAdId(),unitSet);
        }

        unitSet.add(value.getUnitId());

        Set adIdSet = unitCreativeMap.get(value.getUnitId());
        if(CollectionUtils.isEmpty(adIdSet)){
            adIdSet = new ConcurrentSkipListSet<>();
            unitCreativeMap.put(value.getUnitId(),adIdSet);
        }

        adIdSet.add(value.getAdId());



    }

    @Override
    public void update(String key, CreativeUnitObject value) {

        log.error("no support update");
    }

    @Override
    public void delete(String key, CreativeUnitObject value) {

        objectMap.remove(key);
        Set<Long> unitSet = creativeUnitMap.get(value.getAdId());

        if(!CollectionUtils.isEmpty(unitSet)){
            unitSet.remove(value.getUnitId());
        }

        Set<Long> adIdSet = unitCreativeMap.get(value.getUnitId());
        if(CollectionUtils.isNotEmpty(adIdSet)){
            adIdSet.remove(value.getAdId());
        }



    }

    public List<Long> selectAds(List<AdUnitObject> unitObjects) {

        if (CollectionUtils.isEmpty(unitObjects)) {
            return Collections.emptyList();
        }

        List<Long> result = new ArrayList<>();

        for (AdUnitObject unitObject : unitObjects) {

            Set<Long> adIds = unitCreativeMap.get(unitObject.getUnitId());
            if (CollectionUtils.isNotEmpty(adIds)) {
                result.addAll(adIds);
            }
        }

        return result;
    }
}
