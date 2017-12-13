package com.pointy.assignment.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * The root configuration class
 */
@Configuration
@ImportResource({"classpath:/META-INF/beans.xml"})
@ComponentScan(basePackages = "com.pointy.assignment")
public class AppConfig {

}
