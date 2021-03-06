package com.xonlab.contributor.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author:Gao
 * @Date:2020-08-19 19:44
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HistoryVo implements Serializable {

    private String time;

    private String title;

    private String detail;

    private String link;

}
