import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Operation op = new Operation();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- 学生成绩管理系统 ---");
            System.out.println("1. 录入成绩");
            System.out.println("2. 删除学生及其所有成绩");
            System.out.println("3. 按学生ID查询成绩");
            System.out.println("4. 按姓名模糊查询成绩");
            System.out.println("0. 退出");
            System.out.print("请选择操作: ");

            String choice = sc.next();

            try {
                switch (choice) {
                    case "1":
                        Score s = new Score();
                        System.out.print("学生ID: ");
                        s.setStudent_id(sc.nextInt());
                        System.out.print("科目名: ");
                        s.setSubject(sc.next());
                        System.out.print("分数: ");
                        s.setScore(sc.nextDouble());
                        
                        op.addScore(s);
                        System.out.println("操作成功");
                        break;

                    case "2":
                        System.out.print("要删除的学生ID: ");
                        int delId = sc.nextInt();
                        op.deleteStudent(delId);
                        System.out.println("删除成功");
                        break;

                    case "3":
                        System.out.print("查询的学生ID: ");
                        int qId = sc.nextInt();
                        List<Score> res1 = op.query(qId);
                        printScores(res1);
                        break;

                    case "4":
                        System.out.print("输入学生姓名关键词: ");
                        String name = sc.next();
                        List<Score> res2 = op.query_like(name);
                        printScores(res2);
                        break;

                    case "0":
                        System.out.println("程序退出");
                        System.exit(0);

                    default:
                        System.out.println("输入无效，请重新输入");
                }
            } catch (Exception e) {
                System.out.println("发生错误: " + e.getMessage());
            }
        }
    }

    /**
     * 遍历并打印成绩列表
     */
    private static void printScores(List<Score> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("未找到相关记录");
            return;
        }
        System.out.println("--------------------------------------------------");
        for (Score s : list) {
            System.out.println("ID: " + s.getStudent_id() + 
                               " | 科目: " + s.getSubject() + 
                               " | 分数: " + s.getScore() + 
                               " | 考试时间: " + s.getExam_time());
        }
        System.out.println("--------------------------------------------------");
    }
}