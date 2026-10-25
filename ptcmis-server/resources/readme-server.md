组培信息系统服务器地址： 192.168.100.218

服务器windows密码： asd1234567@
数据库密码： root@[%,localhost]/ptcmis@[%,localhost]: ptcmis
数据库名: ptcmis

部署位置： D:\ptcmis

端口:
http web: 9006
mysql-db: 9007
server: 9005

目录：
D:\ptcmis\ptcmis-db: mysql数据库主目录
D:\ptcmis\ptcmis-nginx: web服务主目录
D:\ptcmis\ptcmis-server: java后台主目录
D:\ptcmis\ptcmis-web: web资源目录

windows 系统服务:
PTCMIS-DB-MYSQL
PTCMIS-SERVER
PTCMIS-WEB-SERVER

windows 定时任务：
PTCMIS-AUTO-DB-BACKUP: 组培信息系统数据备份任务，每日2:00自动备份，保留近7天备份。
