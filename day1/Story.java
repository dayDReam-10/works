package works.day1;

import java.io.File;
//故事有类型，标题，内容
//主要是这个故事集合
public class Story {
    private String type;// adventure,happy,sad
    private String title;
    private File storys = new File("day1/storys");// 优化小怪兽们的故事集，放个龙族www 我的随笔

    public Story() {
    }

    public Story(String type, String title, File storys) {
        this.type = type;
        this.title = title;
        this.storys = storys;
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

    public void setStorys(File storys) {
        this.storys = storys;
    }

}
