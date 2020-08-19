package com.xonlab.contributor.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Gao
 * @since 2020-08-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pr_list")
@ApiModel(value="PrList对象", description="")
public class PrList implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String title;

    private String author;

    private String link;

    private String status;

    private String tag;

    private Date time;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间", example = "2019-01-01 8:00:00")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间", example = "2019-01-01 8:00:00")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
