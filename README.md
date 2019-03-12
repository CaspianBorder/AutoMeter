# AutoMeter
PC端简易网络流量监控器

## 使用方法
执行GUI.java即可运行程序，每个模块也可单独运行（需要自行修改main函数，不推荐）

## 注意事项
1. 使用前请确保安装了
    1. winpcap数据包抓取工具
    2. Microsoft SQL Server 2008 及以上，并配置本地回环端口及管理员账号（具体内容可在DataBaseOperation.java中进行修改）
2. 请在运行前，将/package内的jpcap.dll复制至当前所使用的jdk/bin文件夹中
3. 由于所使用的第三方jar包jpcap已经停止维护，且jpcap包对于64位系统有严重兼容问题，因此若出现由于jpcap包与系统不兼容的情况，可以自行尝试下载网上其他版本的jpcap包/jpcap.dll进行替换使用
4. 程序采用简易实现，所获取的进程数据流量使用量与其他监控软件存在一定偏差
