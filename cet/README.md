## 一个简易的查询四六级成绩的小工具

- 使用前需要安装 lombok 插件(IDEA: Settings -> plugins -> Lombok Plugin)
- 采用最简单的 HttpClient 模仿 get 请求
- 增加了代理IP列表，模拟多线程访问，锻炼多线程操作
- 使用见 Main.java (不用理睬SpringBoot，只不过顺手构建了而已)