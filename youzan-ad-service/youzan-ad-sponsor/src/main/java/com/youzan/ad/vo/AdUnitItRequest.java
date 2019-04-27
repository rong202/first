package com.youzan.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by baimugudu on 2019/3/29
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitItRequest {

    private List<unitIts> unitIts;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class unitIts{


        private Long unitId;

        /**
         * 兴趣标签
         */

        private String itTag;
    }




}
