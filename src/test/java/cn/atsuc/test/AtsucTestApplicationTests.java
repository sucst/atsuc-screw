package cn.atsuc.test;

import cn.atsuc.test.entity.ValueEntity;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AtsucTestApplicationTests {

    @Resource
    private ValueEntity valueEntity;

    @Test
    void contextLoads() {

        System.out.println(valueEntity.getTomcatMe());
    }

}
