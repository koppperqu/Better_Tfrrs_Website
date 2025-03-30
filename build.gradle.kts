plugins {
	id("java")
    id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.4"
    id("application")
}
//group = "com"

java {
	sourceCompatibility = JavaVersion.VERSION_24
}

repositories {
	mavenCentral()
}

dependencies {
    implementation("com.h2database:h2:2.3.232")
	implementation ("org.jsoup:jsoup:1.17.2")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation ("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")
	implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

application {
    mainClass = "com.bettertfrrswebsite.BetterTfrrsWebsiteApplication"
}
