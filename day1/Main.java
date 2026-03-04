package works.day1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

//可以考虑Jframe？
public class Main {
    private static final String DATA_FILE = "works/day1/monsters.dat";
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        Scanner sc=new Scanner(System.in);
        ArrayList<Monster> monsters = new ArrayList<>();
        
        // 我突然发现我关掉之后我的故事全没了。。。，于是想着加了个保留数据的功能
        File dataFile = new File(DATA_FILE);
        if (dataFile.exists()) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile));
            monsters = (ArrayList<Monster>) ois.readObject();
            ois.close();//记得记得close，之前忘了，结果文件被占用删不了
        }

        while(true){//主菜单优化
            System.out.println("\n\u001B[36m========================================\u001B[0m");
            System.out.println("\u001B[93m             欢迎来到小怪兽的故事集        \u001B[0m");
            System.out.println("\u001B[36m========================================\u001B[0m");
            System.out.println("\u001B[97m              请选择操作：                \u001B[0m");
            System.out.println("\u001B[91m           1. 添加小怪兽                  \u001B[0m");
            System.out.println("\u001B[92m           2. 查看小怪兽列表              \u001B[0m");
            System.out.println("\u001B[94m           3. 为选中的小怪兽（添加/删除）故事   \u001B[0m");
            System.out.println("\u001B[95m           4. 查看小怪兽的故事            \u001B[0m");
            System.out.println("\u001B[33m           5. 查看图书馆大小              \u001B[0m");
            System.out.println("\u001B[36m           6. 删除小怪兽                  \u001B[0m");
            System.out.println("\u001B[31m           7. 退出程序                    \u001B[0m");
            System.out.println("\u001B[36m========================================\u001B[0m");
            System.out.print("\u001B[92m请输入您的选择: \u001B[0m");
            try {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1 -> {
                    try {//记得格式异常格式异常！
                    System.out.print("请输入小怪兽名称和年龄：");
                    String name = sc.next();
                    int age = sc.nextInt();
                        monsters.add(new Monster(name, age));
                        System.out.println("小怪兽 " + name + " 已添加");
                    }catch (java.util.InputMismatchException e) {
                        System.out.println("你在干啥？");
                        sc.nextLine(); }
                     catch (InputNotRightException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 2 -> {//考虑倒空的情况
                    if (monsters.isEmpty()) {
                        System.out.println("列表为空");
                        break;
                    }
                    for (Monster m : monsters) {//遍历方便看
                        System.out.println(m.getName() + " - 年龄: " + m.getAge());
                    }
                }
                case 3 -> {//trycatch体系准备
                    if (monsters.isEmpty()) {
                        System.out.println("列表为空");
                        break;
                    }
                    for (int i = 0; i < monsters.size(); i++) {
                        System.out.println((i + 1) + ". " + monsters.get(i).getName());
                    }
                    System.out.println("请选择要管理故事的小怪兽：");
                    String monsterName = sc.next();
                    boolean found = false;
                    for (int i = 0; i < monsters.size(); i++) {
                        if (monsterName.equals(monsters.get(i).getName())) {
                            found = true;
                            
                            System.out.println("你想 ...?(1. 添加故事 2. 删除故事)");
                            try {
                                int subChoice = sc.nextInt();
                                if (subChoice == 1) {
                                    System.out.println("请依次输入故事标题、类型（adventure/happy/sad）和内容文件路径：");
                                    String title = sc.next();
                                    String type = sc.next();
                                    File file = new File(sc.next());//拉到这里做验证，调用Story的构造器，验证写在Story类里，用链式调用
                                    Story story = new Story(type, title, file);//先检查，后添加
                                    monsters.get(i).addStory(story);//时间生成
                                    System.out.println("故事添加成功！");
                                } else if (subChoice == 2) {
                                    if (monsters.get(i).getStoryCount() == 0) {
                                        System.out.println("列表为空");
                                    } else {
                                        for (int k = 0; k < monsters.get(i).getStoryCount(); k++) {
                                            System.out.println((k + 1) + ". " + monsters.get(i).getStory(k).getTitle());
                                        }
                                        System.out.println("请输入要删除的故事编号：");
                                        int deleteIndex = sc.nextInt() - 1;//index注意
                                        if (deleteIndex >= 0 && deleteIndex < monsters.get(i).getStoryCount()) {
                                            monsters.get(i).removeStory(deleteIndex);
                                            System.out.println("故事删除成功！");
                                        } else {
                                            System.out.println("无效的编号");
                                        }
                                    }
                                } else {
                                    System.out.println("输入有误");
                                }
                            } catch (java.util.InputMismatchException e) {
                                System.out.println("请输入有效的数字");
                                sc.nextLine();
                            } catch (InputNotRightException e) {
                                System.out.println(e.getMessage());
                            } catch (StoryNotRightException e) {
                                System.out.println(e.getMessage());
                            }
                            
                            break; 
                        }
                    }
                    if (!found) {
                        System.out.println("未找到名为 " + monsterName );
                    }
                }
                case 4 -> {//还可以优化，一个小怪兽不一定只有一个故事
                    if (monsters.isEmpty()) {
                        System.out.println("列表为空");
                        break;
                    }
                    for (int i = 0; i < monsters.size(); i++) {
                        System.out.println((i + 1) + ". " + monsters.get(i).getName());
                    }
                    System.out.print("请选择要查看故事的小怪兽名称：");
                    String monsterName = sc.next();
                    boolean found = false;//给个标记得了
                    for (Monster m : monsters) {
                        try{
                            if (m.getName().equals(monsterName)) {
                                if(m.getStoryCount() == 0){
                                    System.out.println(m.getName() + "还没有故事");
                                    found = true;
                                    break;
                                }
                                System.out.println(m.getName() + "的故事有：");
                                for (int k = 0; k < m.getStoryCount(); k++) {
                                    Story s = m.getStory(k);
                                    System.out.println((k + 1) + ". " + s.getTitle() + " (类型: " + s.getType() + " | 时间: " + s.getCreateTime() + ")");
                                }
                                System.out.println("你想阅读...?(输入编号读取，输入 0 退回菜单)");
                                int readChoice = sc.nextInt();
                                if (readChoice > 0 && readChoice <= m.getStoryCount()) {
                                    Story toRead = m.getStory(readChoice - 1);
                                    System.out.println("\n==========【" + toRead.getTitle() + "】==========");
                                    try {
                                        // stream流读取并打印
                                        Files.lines(toRead.getStorys().toPath()).forEach(System.out::println);
                                    } catch (IOException e) {//异常也搞搞
                                        System.out.println("读取故事文件内容出错：" + e.getMessage());
                                    }
                                    System.out.println("==============================\n");
                                }
                                found = true;
                                break;
                            }
                        }catch (java.util.InputMismatchException e) {
                            System.out.println("请输入数字");
                            sc.nextLine();
                        }
                    }
                    if (!found) {
                        System.out.println("未找到名为 " + monsterName + " 的小怪兽");
                    }
                }
                case 5 -> {
                    int sum = 0;
                    for (Monster m : monsters) {//统计故事数量
                        sum += m.printStoriesTitle().size();
                    }
                    System.out.println("图书馆共有 " + sum + " 个小怪兽的故事。");}
                case 6 -> {
                    if (monsters.isEmpty()) {
                        System.out.println("列表为空");
                        break;
                    }
                    for (int i = 0; i < monsters.size(); i++) {
                        System.out.println((i + 1) + ". " + monsters.get(i).getName());
                    }
                    System.out.print("请选择要删除的小怪兽名称：");
                    String monsterName = sc.next();
                    boolean found = false;
                    for (int i = 0; i < monsters.size(); i++) {
                        if (monsters.get(i).getName().equals(monsterName)) {
                            monsters.remove(i);
                            System.out.println("成功删除了小怪兽 " + monsterName + "！");
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("未找到名为 " + monsterName + " 的小怪兽");
                    }
                }
                case 7 -> {
                        // 退出前保存数据
                        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
                            oos.writeObject(monsters);
                        }
                        System.out.println("感谢使用小怪兽故事集");
                        sc.close();//记得关，我就说怎么关不掉。。。
                        return ;
                    }
                    default -> System.out.println("何意味？");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("菜单选择请输入有效的数字");
                sc.nextLine(); 
            }
        }
    }
}
