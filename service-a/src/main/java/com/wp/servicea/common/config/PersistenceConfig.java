package com.wp.servicea.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

// SHOULD BE IN WP-LIBS/LIB-WP-DATABASE
//// USED JUST FOR EXAMPLE
//// !!!!!!!
@Configuration
@MapperScan(basePackages = "com.wp.*.**.dao")
public class PersistenceConfig {
}
