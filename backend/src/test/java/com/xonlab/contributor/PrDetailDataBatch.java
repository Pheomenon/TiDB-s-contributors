package com.xonlab.contributor;

import com.xonlab.contributor.entity.PrDetail;
import com.xonlab.contributor.mapper.PrDetailMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author:Gao
 * @Date:2020-08-17 10:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PrDetailDataBatch {
    @Autowired
    private PrDetailMapper prDetailMapper;
    @Test
    public void fun() throws IOException, ParseException {
        //读取由pr_detail爬到的文件
        String in = new String(Files.readAllBytes(Paths.get("../convertor/prDetail_modified.json")), StandardCharsets.UTF_8);

        //将文件中的字符串按, "分割并放入Stream
        Stream<String> closedContent1 = Stream.of(in.split(", \""));
        Stream<String> closedContent2 = Stream.of(in.split(", \""));
        Stream<String> closedContent3 = Stream.of(in.split(", \""));

        //取出相应字段
        List<String> authorAvatars = closedContent1.filter(s -> s.contains("authorAvatar")).map(s -> s.substring(19,s.length()-1)).collect(Collectors.toList());
        List<String> prAuthors = closedContent2.filter(s -> s.contains("authorName")).map(s -> s.substring(14,s.length()-1)).collect(Collectors.toList());
        List<String> prDetails = closedContent3.filter(s -> s.contains("prdetail")).map(s -> s.substring(12,s.length()-3)).collect(Collectors.toList());

        insert(authorAvatars,prAuthors,prDetails);
    }

    private void insert(List<String> authorAvatars, List<String> prAuthors, List<String> prDetails) throws ParseException {
        if (authorAvatars.size() == prAuthors.size() && prAuthors.size() == prDetails.size()) {
            for (int i = 0; i < authorAvatars.size(); i++) {
                PrDetail pr = new PrDetail();
                pr.setAuthorAvatar(authorAvatars.get(i));
                pr.setPrAuthor(prAuthors.get(i));
                pr.setPrDetail(prDetails.get(i));
                prDetailMapper.insert(pr);
            }
        } else
            System.err.println("FAIL");
    }
}

