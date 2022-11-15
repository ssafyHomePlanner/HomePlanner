package com.ssafy.homepjt.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(
		basePackages = "com.ssafy.homepjt.model.mapper"
)
public class DatabaseConfiguration {

}
