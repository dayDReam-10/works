package works.day1;

import java.util.ArrayList;

//每个小怪兽有自己的故事集（复数个）
//每个记得判断空不空！
public class Monster {
    private String name;
    private int age;
    private ArrayList<Story> stories = new ArrayList<>();// 优化小怪兽们的故事集，放个龙族啥的 我的随笔

    public Monster() {
    }

    public Monster(String name, int age) throws InputNotRightException {
        if (name == null || name.trim().isEmpty()) {
            throw new InputNotRightException("小怪兽名称不能为空");
        }
        if (age < 0) {
            throw new InputNotRightException("小怪兽年龄不能为负数！");
        }
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ArrayList<String> printStoriesTitle() {
        ArrayList<String> titles = new ArrayList<>();
        for (Story s : stories) {
            titles.add(s.getTitle());
        }
        return titles;// 返回每个故事的标题
    }

    public int getStoryCount() {
        return stories.size();
    }

    public Story getStory(int num) {
        if (num >= 0 && num < stories.size()) {
            return stories.get(num);
        }
        return null;
    }

    public void addStory(Story story) throws StoryNotRightException {
        if (story == null) {//验证写到Story类，换了个思路
            throw new StoryNotRightException("故事对象不能为空");
        }
        this.stories.add(story);
    }
}
