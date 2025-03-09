plugins {
	java
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
	id("com.github.johnrengelman.shadow") version "8.1.1" // Replace with the latest version
}
group = "com"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation ("org.jsoup:jsoup:1.17.2")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation ("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect")
	implementation("org.springframework.boot:spring-boot-starter-web")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.jar {
	manifest.attributes["Main-Class"] = "Scrapers.PopulateAndUpdateDB"
}

tasks.shadowJar {
	archiveBaseName = "PopulateAndUpdateDB"
	archiveClassifier = ""
	archiveVersion = ""
}

task("fullBuild") {
	dependsOn("clean")
	dependsOn("build")
	dependsOn("shadowJar")
	tasks.findByName("build")?.mustRunAfter("clean")
	tasks.findByName("shadowJar")?.mustRunAfter("build")
}

