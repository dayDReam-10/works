package works.day1;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

//故事有类型，标题，内容
//主要是这个故事集合
public class Story implements Serializable {
    private static final long serialVersionUID = 1L;
    private String type;// adventure,happy,sad
    private String title;
    private File storys = new File("day1/storys");
    private String createTime;

    public Story() {
    }

    public Story(String type, String title, File sourceFile) throws InputNotRightException {
        // 复用做验证
        this.setType(type);
        this.setTitle(title);
        this.setStorys(sourceFile);
        this.createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public void setType(String type) throws InputNotRightException {
        if (type == null || type.trim().isEmpty() || (!type.equals("adventure") && !type.equals("happy") && !type.equals("sad"))){
            throw new InputNotRightException("故事类型只能是 adventure/happy/sad 请重新输入");
        }
        this.type = type;
    }

    public void setTitle(String title) throws InputNotRightException {
        if (title == null || title.trim().isEmpty()) {
            throw new InputNotRightException("故事标题不能为空，请重新输入");
        }
        this.title = title;
    }

    public File getStorys() {
        return storys;
    }

    public void setStorys(File sourceFile) throws InputNotRightException {
        if (!sourceFile.exists() || !sourceFile.isFile()) {
            throw new InputNotRightException("指定的故事文件不存在");
        }

        // 保持原文件名
        File targetFile = new File("works/day1/storys", sourceFile.getName());

        // 确保目标文件夹存在，防止缺少空文件夹报错
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }

        try {
            // 将内容保存到 storys 文件夹下
            Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            this.storys = targetFile; 
        } catch (IOException e) {
            throw new InputNotRightException("文件上传保存失败，原因: " + e.getMessage());
        }
    }

}
