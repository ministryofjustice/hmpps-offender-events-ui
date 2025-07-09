plugins {
  id("uk.gov.justice.hmpps.gradle-spring-boot") version "8.3.1"
  kotlin("plugin.spring") version "2.2.0"
  kotlin("plugin.jpa") version "2.2.0"
}

configurations {
  implementation {
    exclude(module = "spring-boot-starter-security")
    exclude(module = "spring-boot-starter-oauth2-client")
    exclude(module = "spring-boot-starter-oauth2-resource-server")
  }
}

dependencies {
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

  implementation("uk.gov.justice.service.hmpps:hmpps-kotlin-spring-boot-starter:1.4.7")
  implementation("org.springframework.boot:spring-boot-starter-data-redis")
  implementation("uk.gov.justice.service.hmpps:hmpps-sqs-spring-boot-starter:5.4.6")

  implementation("com.google.code.gson:gson:2.13.1")
  implementation("com.google.guava:guava:33.4.8-jre")

  implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
  implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:3.4.0")
  implementation("org.apache.groovy:groovy:4.0.27")
  // Needs to match this version https://github.com/microsoft/ApplicationInsights-Java/blob/3.6.2/dependencyManagement/build.gradle.kts#L16
  implementation("io.opentelemetry.instrumentation:opentelemetry-instrumentation-annotations:2.16.0")

  testImplementation("net.javacrumbs.json-unit:json-unit-assertj:4.1.1")
  testImplementation("org.testcontainers:localstack:1.21.3")
  testImplementation("com.amazonaws:aws-java-sdk-core:1.12.787")
  testImplementation("org.awaitility:awaitility-kotlin:4.3.0")
  testImplementation("com.github.codemonstur:embedded-redis:1.4.3") { exclude("org.slf4j", "slf4j-simple") }
}

kotlin {
  jvmToolchain(21)
  compilerOptions {
    freeCompilerArgs.addAll("-Xjvm-default=all", "-Xwhen-guards")
  }
}

tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions.jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
  }
}
