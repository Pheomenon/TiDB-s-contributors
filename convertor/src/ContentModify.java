import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @Author:Gao
 * @Date:2020-08-17 11:08
 * 为了能在导入数据库的时候更方便的分割字符串所以用这个类来为每一行加上分隔符
 */
public class ContentModify {
    public static void main(String[] args) throws IOException {
        String in = new String(Files.readAllBytes(Paths.get("../crawler/pr_detail/prdetail/prdetail/prDetail.json")), StandardCharsets.UTF_8);
        String out = in.replace("\n",", \"\n");

        try {
            File file = new File("prDetail_modified.json");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getName(), true);
            fileWriter.write(out);
            fileWriter.close();
            System.out.println("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
