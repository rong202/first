package com.youzan.ad.index.district;

import com.youzan.ad.index.IndexAware;
import com.youzan.ad.utils.CommonUtils;
import com.youzan.ad.vo.feature.DistrictFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * Created by baimugudu on 2019/4/3
 * 地域限制：倒排索引
 */
@Slf4j
@Component
public class UnitDistrictIndex implements IndexAware<String, Set<Long>> {

    //< 地域（省-市），set unitid>
    private static Map<String, Set<Long>> districtUnitMap;

    //<unitId,Set 地域（省-市）>
    private static Map<Long,Set<String>> unitDistrictMap;

    static {
        districtUnitMap = new ConcurrentHashMap<>();
        unitDistrictMap = new ConcurrentHashMap<>();
    }



    @Override
    public Set<Long> get(String key) {
        return districtUnitMap.get(key);
    }

    @Override
    public void add(String key, Set<Long> value) {
       Set<Long> unitIds =  CommonUtils.getorCreate(
                key,
                districtUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.addAll(value);

        value.forEach(
                i->CommonUtils.getorCreate(
                        i,
                        unitDistrictMap,
                        ConcurrentSkipListSet::new
                ).add(key)

        );



    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("no support update");

    }

    @Override
    public void delete(String key, Set<Long> value) {
        Set<Long> unitIds =  CommonUtils.getorCreate(
                key,
                districtUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.removeAll(value);

        value.forEach(
                i->CommonUtils.getorCreate(
                        i,
                        unitDistrictMap,
                        ConcurrentSkipListSet::new
                ).remove(key)

        );
    }
    public boolean match(Long adUnitId,
                         List<DistrictFeature.ProvinceAndCity> districts) {

        if (unitDistrictMap.containsKey(adUnitId) &&
                CollectionUtils.isNotEmpty(unitDistrictMap.get(adUnitId))) {

            Set<String> unitDistricts = unitDistrictMap.get(adUnitId);

            List<String> targetDistricts = districts.stream()
                    .map(
                            d -> CommonUtils.stringConcat(
                                    d.getProvince(), d.getCity()
                            )
                    ).collect(Collectors.toList());
            //前一个是否是后一个的子集
            return CollectionUtils.isSubCollection(targetDistricts, unitDistricts);
        }

        return false;
    }
}
