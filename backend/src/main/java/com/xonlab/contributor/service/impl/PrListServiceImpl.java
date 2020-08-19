package com.xonlab.contributor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xonlab.contributor.entity.PrList;
import com.xonlab.contributor.mapper.PrListMapper;
import com.xonlab.contributor.service.PrListService;
import com.xonlab.contributor.vo.HistoryVo;
import com.xonlab.contributor.vo.MostContributorVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Gao
 * @since 2020-08-16
 */
@Service
public class PrListServiceImpl extends ServiceImpl<PrListMapper, PrList> implements PrListService {

    @Override
    public List<MostContributorVo> getMostContributors() {
        return baseMapper.getMostContributors();
    }

    @Override
    public List<HistoryVo> getHistory(String start, String end) {
        System.err.println("start:"+start);
        System.err.println("end:"+end);
        return baseMapper.getHistory(start,end);
    }
}
