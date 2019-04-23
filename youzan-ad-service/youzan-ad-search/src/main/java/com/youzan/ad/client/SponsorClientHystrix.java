package com.youzan.ad.client;


import com.youzan.ad.client.vo.AdPlan;
import com.youzan.ad.client.vo.AdPlanGetRequest;
import com.youzan.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Qinyi.
 * springboot的一个组件
 */
@Component
public class SponsorClientHystrix implements SponsorClient {

    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(
            AdPlanGetRequest request) {
        return new CommonResponse<>(-1,
                "eureka-client-ad-sponsor error");
    }
}
