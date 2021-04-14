import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.31"
	kotlin("plugin.spring") version "1.4.31"
	kotlin("plugin.jpa") version "1.4.31"
}

group = "com.flash21"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	// 이거 없으면 시작하자마자 꺼짐
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.hibernate:hibernate-validator:6.1.5.Final")

	implementation("io.springfox:springfox-swagger2:2.9.2")
	implementation("io.springfox:springfox-bean-validators:2.9.2")
	implementation("io.springfox:springfox-swagger-ui:2.9.2")
	implementation("org.bitbucket.tek-nik:spring-swagger-simplified:1.0.8")

	implementation("io.jsonwebtoken:jjwt-api:0.11.2")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	implementation("org.modelmapper:modelmapper:2.1.1")

	implementation("org.springframework.boot:spring-boot-configuration-processor")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
