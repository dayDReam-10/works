这次任务使用了AI帮忙找bug，实在是不会处理了。前端页面也借助了 些AI

## 修改说明
**采取了类似 nano 的修改**  
这次整理时我尽量没有大幅改动原有内容，主要是在原来的基础上调整了结构和排版，让重点更清楚一些。

## 我的错误 / 开发踩坑笔记
这一部分记录的是我这次开发过程中实际遇到的问题。为了把这些错误一个个找出来并解决，我前后折腾了差不多 1 天。现在回头看，有些问题本身不算特别复杂，但当时定位起来还是花了不少时间

### 1. 编译失败：无法覆盖 doPost 方法
**错误现象：**  
ERROR: `doPost(...) in Login cannot override doPost(...) in HttpServlet; overridden method does not throw Exception`

**原因排查：**  
父类 HttpServlet，只允许抛出 ServletException 和 IOException。在子类中声明抛出更大的 Exception 会导致编译不通过。

**解决方案：**
- 将方法签名的 `throws Exception` 修改为 `throws ServletException, IOException`。
- 将具体的业务逻辑（如数据库操作）包裹在 `try-catch` 块内处理。

### 2. 编译失败：包名与路径不匹配
**错误现象：**  
ERROR: `file does not contain class com.memosystem.Dao`

**原因排查：**  
Java 编译器的“洁癖”。代码中的 package 声明必须与磁盘上的文件夹路径严格对应。若文件夹名为 `memosystem`，则 package 必须是 `com.memosystem`。

**解决方案：**
- 统一所有 `.java` 文件的 package 声明。
- 确保文件物理路径为 `src/main/java/com/memosystem/`。
- 同一个包下的类调用无需 import，手动删除多余的自引用 import。

### 3. 运行失败：localhost 拒绝连接
**错误现象：**  
浏览器提示 `Connection Refused`。

**原因排查：**  
缺少 Web 容器。Servlet 无法独立运行，需要 Tomcat 等容器监听端口。

**解决方案：**
- 在 `pom.xml` 中引入 `tomcat7-maven-plugin` 插件。
- 使用 `mvn tomcat7:run` 命令启动内置服务器。

### 4. 调试技巧：热部署与清理
**关键点：**  
代码修改后如果不生效，通常是由于 Maven 的 `target` 目录未更新。

**命令：**  
使用 `mvn clean tomcat7:run` 强制清理旧编译产物并重新打包运行。

## 目前识别出的类与项目结构总结
在这次排错过程中，我也顺带把项目里已经能看出来的类和结构梳理了一下。刚开始看项目时很多地方是乱的，但问题理顺之后，结构就慢慢清楚了。

### 项目目录结构

```
memo/
├── pom.xml                          # Maven 项目配置
└── src/main/
    ├── java/com/memosystem/         # 包名：com.memosystem
    │   ├── Login.java               # 登录 Servlet
    │   ├── Register.java            # 注册 Servlet
    │   ├── addMemo.java             # 新增备忘录 Servlet
    │   ├── DeleteMemo.java          # 删除备忘录 Servlet
    │   ├── UpdateMemo.java          # 修改备忘录 Servlet
    │   ├── GetMemos.java            # 获取备忘录列表 Servlet
    │   ├── UploadServlet.java       # 文件上传 Servlet（头像等）
    │   ├── User.java                # 用户实体类
    │   ├── Dao.java                 # 数据库访问类
    │   └── Utils.java               # 工具类（数据库连接等）
    └── webapp/
        ├── login.jsp                # 登录页面
        ├── index.jsp                # 主页面
        └── WEB-INF/
            └── web.xml              # Servlet 配置
```

### 各个类的职责划分

**Servlet 层（处理用户请求）：**
| 类名 | 职责 |
|------|------|
| `Login` | 处理登录请求，校验用户名密码 |
| `Register` | 处理注册请求，创建新用户 |
| `addMemo` | 新增一条备忘录 |
| `DeleteMemo` | 删除指定备忘录 |
| `UpdateMemo` | 修改备忘录内容或状态 |
| `GetMemos` | 获取当前用户的备忘录列表 |
| `UploadServlet` | 处理文件上传（如用户头像） |

**数据 & 工具层：**
| 类名 | 职责 |
|------|------|
| `User` | 用户实体类，封装用户信息 |
| `Dao` | 数据库访问类，负责与 MySQL 交互 |
| `Utils` | 工具类，提供数据库连接等公共方法 |

**框架类（非自己编写）：**
- `HttpServlet`：Servlet 规范提供的父类，上面所有 Servlet 都继承自它。

### 功能模块对应关系

```
用户模块  ←→  users 表
  ├── Login.java      → 登录
  ├── Register.java   → 注册
  └── UploadServlet   → 头像上传

备忘录模块  ←→  memos 表
  ├── addMemo.java    → 新增
  ├── DeleteMemo.java → 删除
  ├── UpdateMemo.java → 修改
  └── GetMemos.java   → 查询

公共支撑
  ├── Dao.java        → 所有数据库操作
  ├── Utils.java      → 连接管理
  └── User.java       → 用户数据封装
```

### 技术栈总结
- **语言**：Java
- **项目类型**：Java Web / Servlet
- **构建工具**：Maven
- **Web 容器**：Tomcat（通过 `tomcat7-maven-plugin` 内嵌启动）
- **数据库**：MySQL
- **前端**：JSP（前端页面主要借助了 AI 生成，坦白说这块我还不太熟，后端逻辑是自己写的）

## 建表语句

```sql
CREATE DATABASE IF NOT EXISTS memo_system CHARACTER SET utf8mb4;
USE memo_system;

-- 1. 用户表：存储基本信息和头像路径 [cite: 4, 6]
CREATE TABLE `users` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(50) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `avatar` VARCHAR(255) DEFAULT 'default.png', -- 记录头像在服务器上的文件名
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 2. 备忘录表：关联用户 ID 实现权限隔离 [cite: 5]
CREATE TABLE `memos` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,                     -- 外键，指向 users.id
    `title` VARCHAR(100) NOT NULL,
    `content` TEXT,
    `is_completed` TINYINT(1) DEFAULT 0,        -- 0:进行中, 1:已完成 [cite: 2]
    `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `fk_user_memo` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;


