screw: 生成数据库文档 

生成文档对应说明:
```yaml
spring:
  datasource:
    name: 库名
    url: jdbc:mysql://ip:3306/库名?&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSl=false
    username: 数据库名称
    password: 数据库密码
    driver-class-name: com.mysql.cj.jdbc.Driver

# 生成数据库文档文件路径
FILE_OUTPUT_DIR: xxx
# 版本
DOC_VERSION: x.x.x
# screw配置的文件名称，即数据库文档名称
DOC_FILE_NAME: xxx据库设计文档
# 描述
DOC_DESCRIPTION: xxx设计文档生成
# 忽略表
IGNORE_TABLE_NAME: # a_table, b_table
# 忽略表前缀
IGNORE_PREFIX: # auth_, trade_
# 忽略表后缀
IGNORE_SUFFIX: # _detail, _issue

```