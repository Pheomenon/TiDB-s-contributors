package com.xonlab.contributor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xonlab.contributor.entity.PrList;
import com.xonlab.contributor.vo.MostContributorVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Gao
 * @since 2020-08-16
 */
public interface PrListMapper extends BaseMapper<PrList> {
    List<MostContributorVo> getMostContributors();
}
