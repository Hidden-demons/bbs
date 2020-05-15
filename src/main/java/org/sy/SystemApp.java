package org.sy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: ？？？
 * @Date:2019/09/17 15:22
 * @Description: (qwq..)
 */
@SpringBootApplication
@MapperScan("org.sy.mapper")
public class SystemApp {
    public static void main(String[] args) {
        SpringApplication.run(SystemApp.class,args);
    }
}
