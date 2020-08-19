package com.xonlab.contributor.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author:Gao
 * @Date:2020-08-19 19:44
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HistoryVo {
    String time;
    String title;
}
