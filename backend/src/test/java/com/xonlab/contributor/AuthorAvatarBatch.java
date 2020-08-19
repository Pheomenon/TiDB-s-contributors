package com.xonlab.contributor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xonlab.contributor.entity.PrDetail;
import com.xonlab.contributor.service.PrDetailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author:Gao
 * @Date:2020-08-17 11:25
 * AuthorAvatarBatch用于将作者头像路径存入数据库
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorAvatarBatch {
    @Autowired
    private PrDetailService prDetailService;

    @Test
    public void fun() throws IOException {
        //得到所有作者的名字
        String in = new String(Files.readAllBytes(Paths.get("../convertor/prDetail_modified.json")), StandardCharsets.UTF_8);
        Stream<String> content = Stream.of(in.split(", \""));
        List<String> prAuthors = content.filter(s -> s.contains("authorName")).map(s -> s.substring(14, s.length() - 1)).distinct().collect(Collectors.toList());

        //以作者名字作为查询条件，将/avatars/"+author+"/.jpg拼接后存入locate_avatar字段
        for (String author : prAuthors) {
            QueryWrapper<PrDetail> wrapper = new QueryWrapper<>();
            wrapper.eq("pr_author", author);
            List<PrDetail> prs = prDetailService.list(wrapper);
            for (PrDetail pr : prs) {
                pr.setAuthorAvatar("/avatars/"+author+".jpg");
                prDetailService.updateById(pr);
            }
        }
    }
}
