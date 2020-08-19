package com.xonlab.contributor.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xonlab.contributor.common.R;
import com.xonlab.contributor.entity.PrList;
import com.xonlab.contributor.service.PrListService;
import com.xonlab.contributor.vo.MostContributorVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Gao
 * @since 2020-08-16
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/list")
public class PrListController {
    @Autowired
    private PrListService prListService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/most")
    public R getMost() {
        List<MostContributorVo> queryResult = prListService.getMostContributors();
        Set<String> dateSet = new TreeSet<>();
        for (MostContributorVo item:queryResult) {
            dateSet.add(item.getDate());
        }
        Map<Object, Object> frequency;
        List<Map> result = new ArrayList<>();
        for (String date: dateSet) {
            frequency = queryResult.stream().filter(item -> item.getDate().equals(date)).collect(Collectors.toMap(MostContributorVo::getName, MostContributorVo::getTimes));
            frequency.put("date", date);
            result.add(frequency);
        }
        Set<String> nameSet = new HashSet<>();
        nameSet.add("date");
        for (MostContributorVo item : queryResult) {
            nameSet.add(item.getName());
        }
        return R.ok().data("rows", result).data("columns",nameSet);
    }

    @PostMapping("/{current}/{limit}")
    public R getList(@PathVariable long current,
                     @PathVariable long limit,
                     @RequestBody(required = false) Map condition) {
        List<PrList> records = (List<PrList>) redisTemplate.opsForValue().get(condition);
        if (records == null) {
            Page<PrList> listPage = new Page<>(current, limit);
            QueryWrapper<PrList> wrapper = new QueryWrapper<>();
            if (condition.get("key") != null) {
                wrapper.like("author", condition.get("key"));
            }
            if (condition.get("dates") != null) {
                Stream<String> dateStream = Stream.of(condition.get("dates").toString().split(", "));
                List<String> dates = dateStream.map(s -> s.replace("[", "")).map(s -> s.replace("]", "")).sorted().collect(Collectors.toList());
                wrapper.gt("time", dates.get(0));
                wrapper.le("time", dates.get(1));
            }
            wrapper.orderByDesc("time");
            prListService.page(listPage, wrapper);
            long total = listPage.getTotal();
            records = listPage.getRecords();
            redisTemplate.opsForValue().set(condition, records);
            Map<String, Object> map = new HashMap<>();
            map.put("total", total);
            map.put("rows", records);
            return R.ok().data(map);
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("rows", records);
            return R.ok().data(map);
        }
    }
}

