package com.xonlab.contributor;

import com.xonlab.contributor.entity.PrList;
import com.xonlab.contributor.mapper.PrListMapper;
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
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author:Gao
 * @Date:2020-08-16 21:40
 * DataBatch用于将爬到的数据存入数据库
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PrListDataBatch {
    @Autowired
    private PrListMapper prListMapper;
    @Test
    public void fun() throws IOException, ParseException {
        //读取由pr_list爬到的文件
        String closed = new String(Files.readAllBytes(Paths.get("../crawler/pr_list/prlist/prlist/pr_closed2.json")), StandardCharsets.UTF_8);
        String open = new String(Files.readAllBytes(Paths.get("../crawler/pr_list/prlist/prlist/pr_open2.json")), StandardCharsets.UTF_8);

        //将文件中的字符串按, "分割并放入Stream,并取出相应字段
        List<String> prNames = Stream.of(open.split(", \"")).filter(s -> s.contains("prName")).map(s -> s.substring(46,s.length()-1)).collect(Collectors.toList());
        List<String> prAuthors = Stream.of(open.split(", \"")).filter(s -> s.contains("prAuthor")).map(s -> s.substring(12,s.length()-1)).collect(Collectors.toList());
        List<String> prLinks = Stream.of(open.split(", \"")).filter(s -> s.contains("prLink")).map(s -> s.substring(10,s.length()-1)).map(s -> "https://github.com" + s).collect(Collectors.toList());
        List<String> prTimes = Stream.of(open.split(", \"")).filter(s -> s.contains("prTime")).map(s -> s.substring(10,s.length()-2)).map(s -> s.replace("T"," ")).map(s -> s.replace("Z","")).collect(Collectors.toList());
        //TODO：因为有的pr没有Tag所以应该最后用Map来收集结果，key是链接，value是Tag
        insert(prNames, prAuthors, prLinks, prTimes);
    }

    //写入数据库
    private void insert(List<String> prNames, List<String> prAuthors, List<String> prLinks, List<String> prTimes) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (prNames.size() == prAuthors.size() && prAuthors.size() == prLinks.size() && prLinks.size() == prTimes.size()) {
            for (int i = 0; i < prNames.size(); i++) {
                PrList pr = new PrList();
                pr.setTitle(prNames.get(i));
                pr.setAuthor(prAuthors.get(i));
                pr.setLink(prLinks.get(i));
                pr.setTime(simpleDateFormat.parse(prTimes.get(i)));
                pr.setStatus("open");
//                pr.setTag(prTags.get(i));
                prListMapper.insert(pr);
            }
        } else
            System.err.println("FAIL");
    }
}
