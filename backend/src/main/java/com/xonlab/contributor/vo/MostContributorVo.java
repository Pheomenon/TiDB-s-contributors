package com.xonlab.contributor.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author:Gao
 * @Date:2020-08-19 02:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MostContributorVo {
    private String name;
    private String date;
    private Integer times;
}
