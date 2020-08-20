package com.xonlab.contributor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xonlab.contributor.entity.PrDetail;
import com.xonlab.contributor.service.PrDetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author:Gao
 * @Date:2020-08-18 23:17
 * 由于detail字段是直接爬的HTML标签和标签包裹的文字，所以里面有大量的'\'
 * 利用DataFormatter这个类来将这些反斜杠消除
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataFormatter {
    @Autowired
    private PrDetailService prDetailService;
    @Test
    public void fun(){
        QueryWrapper<PrDetail> queryWrapper = new QueryWrapper<>();
        List<PrDetail> prs = prDetailService.list(queryWrapper);
        for (PrDetail pr:prs) {
            Stream<String> stream = Stream.of(pr.getPrDetail());
            String newPrDetail = stream.map(s -> s.replace("\"\\\"","\"")).map(s -> s.replace("\\\"","\"")).collect(Collectors.joining());
            pr.setPrDetail(newPrDetail);
            prDetailService.updateById(pr);
        }
    }
}
