package works.day1;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

//要不要用Jframe做个UI？
public class MonsterAndStory {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        ArrayList<Monster> monsters=new ArrayList<>();
        while(true){
            System.out.println("欢迎来到小怪兽的故事图书馆！");
            System.out.println("请选择操作：");
            System.out.println("1. 添加小怪兽");
            System.out.println("2. 查看小怪兽列表");
            System.out.println("3. 为选中的小怪兽添加故事");
            System.out.println("4. 查看小怪兽的故事");
            System.out.println("5.查看图书馆大小");
            System.out.println("6. 退出程序");
            try {
                int choice = sc.nextInt();
                switch (choice) {
                    case 1 -> {
                    try {
                    System.out.print("请输入小怪兽名称和年龄：");
                    String name = sc.next();
                    int age = sc.nextInt();
                        monsters.add(new Monster(name, age));
                        System.out.println("小怪兽 " + name + " 已添加！");
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
                    for (Monster m : monsters) {
                        System.out.println(m.getName() + " - 年龄: " + m.getAge());
                    }
                }
                case 3 -> {//trycatch
                    if (monsters.isEmpty()) {
                        System.out.println("列表为空");
                        break;
                    }
                    for (int i = 0; i < monsters.size(); i++) {
                        System.out.println((i + 1) + ". " + monsters.get(i).getName());
                    }
                    System.out.println("请选择要添加故事的小怪兽：");
                    String monsterName = sc.next();
                    boolean found = false;
                    for (int i = 0; i < monsters.size(); i++) {
                        if (monsterName.equals(monsters.get(i).getName())) {
                            found = true;//允许调用addStory
                            
                            Story story = new Story();
                            
                            try {
                                System.out.println("请依次输入故事标题、类型和内容文件路径：");
                                story.setTitle(sc.next());
                                story.setType(sc.next());
                                story.setStorys(new File(sc.next()));
                                monsters.get(i).addStory(story);
                                System.out.println("故事添加成功！");
                                
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
                    System.out.print("请输入要查看故事的小怪兽名称：");
                    String monsterName = sc.next();
                    boolean found = false;//给个标记得了
                    for (Monster m : monsters) {
                        if (m.getName().equals(monsterName)) {
                            System.out.println(m.getName() + "的故事有：" + m.printStoriesTitle());
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("未找到名为 " + monsterName + " 的小怪兽");
                    }
                }
                case 5 -> {
                    int sum = 0;
                    for (Monster m : monsters) {
                        sum += m.printStoriesTitle().size();
                    }
                    System.out.println("图书馆共有 " + sum + " 个小怪兽的故事。");}
                case 6 -> {
                        System.out.println("感谢使用小怪兽故事图书馆！");
                        sc.close();//记得
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
