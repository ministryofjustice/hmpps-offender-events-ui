plugins {
  id("uk.gov.justice.hmpps.gradle-spring-boot") version "10.0.2"
  kotlin("plugin.spring") version "2.3.0"
  kotlin("plugin.jpa") version "2.3.0"
}

configurations {
  implementation {
    exclude(module = "spring-boot-starter-security")
    exclude(module = "spring-boot-starter-security-oauth2-client")
    exclude(module = "spring-boot-starter-security-oauth2-resource-server")
    exclude(group = "org.springdoc", module = "springdoc-openapi-starter-common")
    exclude(group = "com.fasterxml.jackson.module", module = "jackson-module-kotlin")
  }
}

dependencies {
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

  implementation("uk.gov.justice.service.hmpps:hmpps-kotlin-spring-boot-starter:2.0.0")
  implementation("org.springframework.boot:spring-boot-starter-data-redis")
  implementation("org.springframework.boot:spring-boot-starter-gson")
  implementation("uk.gov.justice.service.hmpps:hmpps-sqs-spring-boot-starter:6.0.2-beta")

  implementation("com.google.guava:guava:33.5.0-jre")

  implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
  implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:3.4.0")
  implementation("org.apache.groovy:groovy:5.0.3")
  // Needs to match this version https://github.com/microsoft/ApplicationInsights-Java/blob/<version>/dependencyManagement/build.gradle.kts#L16
  // where <version> is the version of application insights pulled in by hmpps-gradle-spring-boot
  // at https://github.com/ministryofjustice/hmpps-gradle-spring-boot/blob/main/src/main/kotlin/uk/gov/justice/digital/hmpps/gradle/configmanagers/AppInsightsConfigManager.kt#L7
  implementation("io.opentelemetry.instrumentation:opentelemetry-instrumentation-annotations:2.21.0")

  testImplementation("net.javacrumbs.json-unit:json-unit-assertj:5.1.0")
  testImplementation("org.testcontainers:testcontainers-localstack:2.0.3")
  testImplementation("com.amazonaws:aws-java-sdk-core:1.12.796")
  testImplementation("org.awaitility:awaitility-kotlin:4.3.0")
  testImplementation("com.github.codemonstur:embedded-redis:1.4.3") { exclude("org.slf4j", "slf4j-simple") }
}

kotlin {
  jvmToolchain(25)
  compilerOptions {
    freeCompilerArgs.addAll("-Xwhen-guards", "-Xannotation-default-target=param-property")
  }
}

tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions.jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_25
  }
}
