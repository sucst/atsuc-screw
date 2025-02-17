package cn.atsuc.screw.controller;

import cn.atsuc.screw.service.DocumentSqlService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/generator")
public class GeneratorController {

    @Resource
    private final DocumentSqlService documentSqlService;

    public GeneratorController(DocumentSqlService documentSqlService) {
        this.documentSqlService = documentSqlService;
    }

    /**
     * 生成数据库表结构文档
     */
    @RequestMapping("/generatorDocument")
    public String generatorDocument(){
        try {
            documentSqlService.generatorDocument();
        } catch (Exception e) {
            log.info("generate controller error: {}", e.getMessage());
            return "error" + e.getMessage();
        }
        return "success";
    }

}
