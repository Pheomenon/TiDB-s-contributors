package com.xonlab.contributor.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author:Gao
 * @Date:2020-08-18 15:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PrListVo {

    private String title;

    private String author;

    private String locateAvatar;

    private String link;

    private String tag;

    private Date time;

    private String prDetail;
}
