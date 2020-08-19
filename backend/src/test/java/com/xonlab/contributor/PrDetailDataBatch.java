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
        String in = new String(Files.readAllBytes(Paths.get("../convertor/prDetail_modified2.json")), StandardCharsets.UTF_8);

        //将文件中的字符串按, "分割并放入Stream
        Stream<String> closedContent1 = Stream.of(in.split(", \""));
        Stream<String> closedContent2 = Stream.of(in.split(", \""));
        Stream<String> closedContent3 = Stream.of(in.split(", \""));

        //取出相应字段
        List<String> prAuthors = closedContent1.filter(s -> s.contains("authorName")).map(s -> s.substring(17,s.length()-1)).collect(Collectors.toList());
        List<String> prDetails = closedContent2.filter(s -> s.contains("prdetail")).map(s -> s.substring(12,s.length()-1)).map(s -> s.replace("<pre>","<p>")).map(s -> s.replace("</pre>","</p>")).map(s -> s.replace("\\n","")).collect(Collectors.toList());
        List<String> prTitles = closedContent3.filter(s -> s.contains("prTitle")).map(s -> s.substring(21,s.length()-10)).collect(Collectors.toList());

        insert(prAuthors,prDetails,prTitles);
    }

    private void insert(List<String> prAuthors, List<String> prDetails, List<String> prTitles) {
        if (prAuthors.size() == prDetails.size() && prDetails.size() == prTitles.size()) {
            for (int i = 0; i < prAuthors.size(); i++) {
                PrDetail pr = new PrDetail();
                pr.setAuthorAvatar("/avatars/"+prAuthors.get(i)+".jpg");
                pr.setPrAuthor(prAuthors.get(i));
                pr.setPrDetail(prDetails.get(i));
                pr.setPrTitle(prTitles.get(i));
                prDetailMapper.insert(pr);
            }
        } else
            System.err.println("FAIL");
    }
}

