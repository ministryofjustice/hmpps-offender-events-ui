plugins {
  id("uk.gov.justice.hmpps.gradle-spring-boot") version "9.1.3"
  kotlin("plugin.spring") version "2.2.20"
  kotlin("plugin.jpa") version "2.2.20"
}

configurations {
  implementation {
    exclude(module = "spring-boot-starter-security")
    exclude(module = "spring-boot-starter-oauth2-client")
    exclude(module = "spring-boot-starter-oauth2-resource-server")
    exclude(module = "commons-logging")
  }
}

dependencies {
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

  implementation("uk.gov.justice.service.hmpps:hmpps-kotlin-spring-boot-starter:1.7.0")
  implementation("org.springframework.boot:spring-boot-starter-data-redis")
  implementation("uk.gov.justice.service.hmpps:hmpps-sqs-spring-boot-starter:5.6.0")

  implementation("com.google.code.gson:gson:2.13.2")
  implementation("com.google.guava:guava:33.5.0-jre")

  implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
  implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:3.4.0")
  implementation("org.apache.groovy:groovy:5.0.1")
  // Needs to match this version https://github.com/microsoft/ApplicationInsights-Java/blob/3.6.2/dependencyManagement/build.gradle.kts#L16
  implementation("io.opentelemetry.instrumentation:opentelemetry-instrumentation-annotations:2.18.1")

  testImplementation("net.javacrumbs.json-unit:json-unit-assertj:5.0.0")
  testImplementation("org.testcontainers:localstack:1.21.3")
  testImplementation("com.amazonaws:aws-java-sdk-core:1.12.792")
  testImplementation("org.awaitility:awaitility-kotlin:4.3.0")
  testImplementation("com.github.codemonstur:embedded-redis:1.4.3") { exclude("org.slf4j", "slf4j-simple") }
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjvm-default=all", "-Xwhen-guards", "-Xannotation-default-target=param-property")
  }
}

java {
  sourceCompatibility = JavaVersion.VERSION_24
  targetCompatibility = JavaVersion.VERSION_24
}

tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions.jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24
  }
}
