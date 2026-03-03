package works.day1;

import java.io.File;
//故事有类型，标题，内容
//主要是这个故事集合
public class Story {
    private String type;// adventure,happy,sad
    private String title;
    private File[] storys;// 优化小怪兽们的故事集，放个龙族www 我的随笔

    public Story() {
    }

    public Story(String type, String title) {
        this.type = type;
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public File getStorys(int number) {
        return storys[number];
    }

    public void setStorys(File[] storys) {
        this.storys = storys;
    }

}
