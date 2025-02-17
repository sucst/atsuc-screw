package cn.atsuc.screw.config;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;

import javax.sql.DataSource;
import java.util.ArrayList;

@org.springframework.context.annotation.Configuration
public class DocumentSqlConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${FILE_OUTPUT_DIR}")
    private String outputDir;
    @Value("${DOC_FILE_NAME}")
    private String docFileName;
    @Value("${DOC_DESCRIPTION}")
    private String docDescription;
    @Value("${DOC_VERSION}")
    private String docVersion;
    @Value("${IGNORE_TABLE_NAME}")
    private String ignoreTableName;
    @Value("${IGNORE_PREFIX}")
    private String ignorePrefix;
    @Value("${IGNORE_SUFFIX}")
    private String ignoreSuffix;
    /**
     * 文档生成
     */
    public void documentGeneration() {
        //数据源
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        //设置可以获取tables remarks信息
        hikariConfig.addDataSourceProperty("useInformationSchema", "true");
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(5);
        DataSource dataSource = new HikariDataSource(hikariConfig);
        //生成配置
        EngineConfig engineConfig = EngineConfig.builder()
                //生成文件路径
                .fileOutputDir(outputDir)
                //打开目录
                .openOutputDir(true)
                //文件类型  HTML->HTML文件  WORD->WORD文件  MD->Markdown文件
                .fileType(EngineFileType.HTML)
                //生成模板实现
                .produceType(EngineTemplateType.freemarker)
                //自定义文件名称
                .fileName(docFileName)
                .build();

        //忽略表
        ArrayList<String> ignoreTableNameList = new ArrayList<>();
        if (ignoreTableName != null) {
            for (String tableName : ignoreTableName.split(",")) {
                ignoreTableNameList.add(tableName.trim());
            }
        }
        // 忽略表前缀（修复逻辑）
        ArrayList<String> ignorePrefixList = new ArrayList<>();
        if (ignorePrefix != null && !ignorePrefix.isEmpty()) { // 改为检查传入的 ignorePrefix 参数
            for (String prefix : ignorePrefix.split(",")) {
                ignorePrefixList.add(prefix.trim());
            }
        }

        // 忽略表后缀（修复逻辑）
        ArrayList<String> ignoreSuffixList = new ArrayList<>();
        if (ignoreSuffix != null && !ignoreSuffix.isEmpty()) { // 改为检查传入的 ignoreSuffix 参数
            for (String suffix : ignoreSuffix.split(",")) {
                ignoreSuffixList.add(suffix.trim());
            }
        }
        ProcessConfig processConfig = ProcessConfig.builder()
                //指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
                //根据名称指定表生成
                .designatedTableName(new ArrayList<>())
                //根据表前缀生成
                .designatedTablePrefix(new ArrayList<>())
                //根据表后缀生成
                .designatedTableSuffix(new ArrayList<>())
                //忽略表名
                .ignoreTableName(ignoreTableNameList)
                //忽略表前缀
                .ignoreTablePrefix(ignorePrefixList)
                //忽略表后缀
                .ignoreTableSuffix(ignoreSuffixList)
                .build();
        //配置
        Configuration config = Configuration.builder()
                //版本
                .version(docVersion)
                //描述
                .description(docDescription)
                //数据源
                .dataSource(dataSource)
                //生成配置
                .engineConfig(engineConfig)
                //生成配置
                .produceConfig(processConfig)
                .build();
        //执行生成
        new DocumentationExecute(config).execute();

    }

}
