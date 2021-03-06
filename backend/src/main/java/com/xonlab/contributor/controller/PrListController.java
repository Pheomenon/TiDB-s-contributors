package com.xonlab.contributor.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xonlab.contributor.common.R;
import com.xonlab.contributor.entity.PrList;
import com.xonlab.contributor.service.PrListService;
import com.xonlab.contributor.vo.HistoryVo;
import com.xonlab.contributor.vo.MostContributorVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
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

    /**
     * 传递start和end两个参数来构成时间区间，
     * 利用这个作为查询条件来返回在某时间段内的pr的详情
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/history/{start}/{end}")
    public R getHistory(@PathVariable String start, @PathVariable String end) {
        //如果能在Redis中查到直接则直接返回结果，否则才去查询数据库
        List<HistoryVo> queryResult = (List<HistoryVo>) redisTemplate.opsForValue().get(start + "_" + end);
        if (queryResult == null) {
            queryResult = prListService.getHistory(start, end);
            redisTemplate.opsForValue().set(start + "_" + end, queryResult, 24, TimeUnit.HOURS);
        }
        return R.ok().data("rows", queryResult);
    }

    /**
     * 按照提交的次数来排序，返回那些在某一天里提交次数最多的Contributor及他们提交的次数
     * @return
     */
    @GetMapping("/most")
    public R getMost() {
        List<Map> result = new ArrayList<>();
        Set<String> nameSet = new HashSet<>();
        if (redisTemplate.opsForValue().get("most_rows") == null || redisTemplate.opsForValue().get("most_columns") == null) {
            List<MostContributorVo> queryResult = prListService.getMostContributors();
            Set<String> dateSet = new TreeSet<>();
            for (MostContributorVo item : queryResult) {
                dateSet.add(item.getDate());
            }
            Map<Object, Object> frequency;
            for (String date : dateSet) {
                frequency = queryResult.stream().filter(item -> item.getDate().equals(date)).collect(Collectors.toMap(MostContributorVo::getName, MostContributorVo::getTimes));
                frequency.put("date", date);
                result.add(frequency);
            }
            nameSet.add("date");
            for (MostContributorVo item : queryResult) {
                nameSet.add(item.getName());
            }
            redisTemplate.opsForValue().set("most_rows", result, 24, TimeUnit.HOURS);
            redisTemplate.opsForValue().set("most_columns", nameSet, 24, TimeUnit.HOURS);
        } else {
            result = (List<Map>) redisTemplate.opsForValue().get("most_rows");
            nameSet = (Set<String>) redisTemplate.opsForValue().get("most_columns");
        }
        return R.ok().data("rows", result).data("columns", nameSet);
    }

    /**
     * 根据时间或者姓名来分页查询Contributor的信息
     * @param current
     * @param limit
     * @param condition
     * @return
     */
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
                wrapper.ge("time", dates.get(0));
                wrapper.le("time", dates.get(1));
            }
            wrapper.orderByDesc("time");
            prListService.page(listPage, wrapper);
            long total = listPage.getTotal();
            records = listPage.getRecords();
            redisTemplate.opsForValue().set(condition, records, 24, TimeUnit.HOURS);
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

