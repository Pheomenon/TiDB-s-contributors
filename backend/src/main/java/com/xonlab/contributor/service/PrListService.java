package com.xonlab.contributor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xonlab.contributor.entity.PrList;
import com.xonlab.contributor.vo.HistoryVo;
import com.xonlab.contributor.vo.MostContributorVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Gao
 * @since 2020-08-16
 */
public interface PrListService extends IService<PrList> {

    List<MostContributorVo> getMostContributors();

    List<HistoryVo> getHistory(String start, String end);

}
