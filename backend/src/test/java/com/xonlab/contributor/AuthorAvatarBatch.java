package com.xonlab.contributor;

import org.junit.Test;
import org.junit.runner.RunWith;
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
    @Test
    public void fun() throws IOException {
        //TODO:这个类等到写前端的时候完善，因为现在还不能确定avatar文件夹应该放在哪个位置
        String in = new String(Files.readAllBytes(Paths.get("../convertor/prDetail_modified.json")), StandardCharsets.UTF_8);
        Stream<String> content = Stream.of(in.split(", \""));
        List<String> prAuthors = content.filter(s -> s.contains("authorName")).map(s -> s.substring(14,s.length()-1)).collect(Collectors.toList());
    }
}
