plugins {
	java
    id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.bettertfrrs"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	//implementation ("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")
    implementation("com.h2database:h2:2.3.232")
	implementation ("org.jsoup:jsoup:1.17.2")

	testImplementation("org.springframework.boot:spring-boot-starter-test")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
