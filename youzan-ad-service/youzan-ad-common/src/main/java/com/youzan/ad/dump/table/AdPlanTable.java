package com.youzan.ad.dump.table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

/**
 * Created by Qinyi.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanTable {

    public Long id;
    public Long userId;
    public Integer planStatus;
    public Date startDate;
    public Date endDate;
}
