package com.zkteco.dbs.startup;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Application
 * 
 * @author able.lee
 * @date 2020/11/30 14:56
 * @since v1.0.0
 */
@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.zkteco")
@ComponentScan({"com.gitee.sunchenbin.mybatis.actable.manager.*","com.zkteco.dbs.*"})
@MapperScan({"com.zkteco.dbs.*.dao","com.gitee.sunchenbin.mybatis.actable.dao.*"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
