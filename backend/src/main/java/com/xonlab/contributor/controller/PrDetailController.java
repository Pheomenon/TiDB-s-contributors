package com.xonlab.contributor.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xonlab.contributor.common.R;
import com.xonlab.contributor.entity.PrDetail;
import com.xonlab.contributor.service.PrDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Gao
 * @since 2020-08-17
 */
@RestController
@CrossOrigin
@RequestMapping("/detail")
public class PrDetailController {
    @Autowired
    private PrDetailService prDetailService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/{current}/{limit}")
    public R getList(@PathVariable long current,
                     @PathVariable long limit,
                     @RequestBody(required = false) Map condition){
        List<PrDetail> records = null;
        if(records == null){
            Page<PrDetail> listPage = new Page<>(current,limit);
            QueryWrapper<PrDetail> wrapper = new QueryWrapper<>();
            if(condition.get("key") != null){
                wrapper.like("pr_author",condition.get("key"));
            }
            prDetailService.page(listPage,wrapper);
            long total = listPage.getTotal();
            records = listPage.getRecords();
            redisTemplate.opsForValue().set(condition+"prDetail",records);
            Map<String,Object> map = new HashMap<>();
            map.put("total",total);
            map.put("rows",records);
            return R.ok().data(map);
        }
        else {
            Map<String,Object> map = new HashMap<>();
            map.put("rows",records);
            return R.ok().data(map);
        }
    }
}

