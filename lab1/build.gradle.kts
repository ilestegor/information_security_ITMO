plugins {
    java
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.github.spotbugs") version "6.3.0"
    id("io.snyk.gradle.plugin.snykplugin") version "0.7.0"


}

group = "org.ilestegor"
group = "org.ilestegor"
version = "0.0.1-SNAPSHOT"
description = "lab1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    runtimeOnly("org.postgresql:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.apache.commons:commons-lang3:3.18.0")
    spotbugs("com.github.spotbugs:spotbugs:4.9.4")
    spotbugs("org.apache.commons:commons-lang3:3.18.0")

    implementation("io.jsonwebtoken:jjwt-api:0.13.0")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.13.0")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.13.0")

    implementation("org.projectlombok:lombok:1.18.38")
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
    implementation("org.jsoup:jsoup:1.21.1")




}

tasks.withType<Test> {
    useJUnitPlatform()
}
spotbugs {
    ignoreFailures.set(false)
    showProgress.set(true)
    effort.set(com.github.spotbugs.snom.Effort.DEFAULT)
    reportLevel.set(com.github.spotbugs.snom.Confidence.HIGH)
}

tasks.withType<com.github.spotbugs.snom.SpotBugsTask> {
    reports.create("html") {
        required.set(true)
        outputLocation.set(layout.buildDirectory.file("reports/spotbugs/sast_report.html"))
        setStylesheet("fancy-hist.xsl")
    }
}

snyk {
    setArguments("--all-sub-projects")
    setSeverity("low")
    setApi("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx")
    setAutoDownload(true)
    setAutoUpdate(true)
}



