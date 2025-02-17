package cn.atsuc.screw.service;

import cn.atsuc.screw.config.DocumentSqlConfig;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DocumentSqlService {

    @Resource
    private DocumentSqlConfig documentSqlConfig;

    public void generatorDocument() {
        try {
            documentSqlConfig.documentGeneration();
        } catch (Exception e) {
            log.info("generate document error: {}", e.getMessage());
        }
    }

}
