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
            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.print("请输入小怪兽名称和年龄：");
                    String name = sc.next();
                    int age = sc.nextInt();
                    monsters.add(new Monster(name, age));
                }
                case 2 -> {
                    for (Monster m : monsters) {
                        System.out.println(m.getName() + " - 年龄: " + m.getAge());
                    }
                }
                case 3 -> {
                    for (int i = 0; i < monsters.size(); i++) {
                        System.out.println((i + 1) + ". " + monsters.get(i).getName());
                    }
                    System.out.println("请选择要添加故事的小怪兽：");
                    try{}catch();
                }
                case 4 -> {
                    System.out.print("请输入要查看故事的小怪兽名称：");
                    String monsterName = sc.next();
                    for (Monster m : monsters) {
                        if (m.getName().equals(monsterName)) {
                            System.out.println(m.getName() + "的故事：" + m.getStory());
                            break;
                        }
                    }
                }
                case 5 -> System.out.println("图书馆共有 " + monsters.size() + " 个小怪兽的故事。");
                case 6 -> {
                    System.out.println("感谢使用小怪兽故事图书馆！");
                    return;
                }
                default -> System.out.println("何意味？");
            }
        }
    }
}
