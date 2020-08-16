import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author:Gao
 * @Date:2020-08-16 14:39
 * PathExtractor用于将爬到的pr的详情页连接提取出来，并写入一个文件，这个文件中的内容就是pr_detail将要爬取的目标站点
 */

public class PathExtractor {
    public static void main(String[] args) throws IOException {

        //读取由pr_list爬到的文件
        String closed = new String(Files.readAllBytes(Paths.get("../crawler/pr_list/prlist/prlist/pr_closed.json")), StandardCharsets.UTF_8);
        String open = new String(Files.readAllBytes(Paths.get("../crawler/pr_list/prlist/prlist/pr_open.json")), StandardCharsets.UTF_8);

        //将文件中的字符串按"分割并放入Stream
        Stream<String> closedContent = Stream.of(closed.split("\""));
        Stream<String> openContent = Stream.of(open.split("\""));

        //取出连接，并去重
        String closedResult = closedContent.filter(s -> s.startsWith("/pingcap")).map(s -> "'https://github.com" + s + "'").distinct().collect(Collectors.joining(","));
        String openResult = openContent.filter(s -> s.startsWith("/pingcap")).map(s -> "'https://github.com" + s + "'").distinct().collect(Collectors.joining(","));

        String result = closedResult + openResult;

        try {
            File file = new File("PrListLink.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getName(), true);
            fileWriter.write(result);
            fileWriter.close();
            System.out.println("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
