package com.bubblebubble.hr.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// 엔티티와 JPA Repository Interface를 Spring Boot 애플리케이션에서 인식하도록 도와줌(JPA 기능 사용하기 위해)
@Configuration
@EntityScan(basePackages = "com.bubblebubble.hr") // 엔티티 클래스들을 스캔하여 Spring에서 인식하도록 설정(인지할 엔티티 범위로서 패키지 내의 엔티티 클래스 인식)
@EnableJpaRepositories(basePackages = "com.bubblebubble.hr") // JPA Repository Interface들을 스캔하여 Spring에서 인식하도록 설정
public class JpaConfiguration {
}
