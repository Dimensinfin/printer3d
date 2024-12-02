plugins {
    java
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.openapi.generator") version "5.4.0" // Add this line
}

group = "org.dimensinfin.poc"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
//    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
}
dependencies {
    implementation("org.springdoc:springdoc-openapi-ui:1.6.14") // OpenAPI UI
    implementation("org.springdoc:springdoc-openapi-webflux-core:1.6.14") // For WebFlux support
    implementation("org.springdoc:springdoc-openapi-data-rest:1.6.14") // For Data REST support
    implementation("javax.annotation:javax.annotation-api:1.3.2") // Java Annotation API
//    implementation("javax.validation:validation-api:2.0.1.Final") // Java Validation API
}
dependencies {
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
//    runtimeOnly("com.h2database:h2")
}
dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
sourceSets {
    main {
        java {
            // Add the java-client source directory
            srcDirs("src/main/java", "src/main/java-client")
        }
    }
}
// - O P E N A P I
// OpenAPI Code Generation Configuration
//@DisableCachingByDefault
openApiGenerate {
//    generatorName.set("spring") // Specify the generator name
//    println("OpenApiInput->")
//    println("$rootDir/src/main/resources/openapi/pocwebsockets.v0.0.1.yaml")
    inputSpec.set("$rootDir/src/main/resources/openapi/pocwebsockets.v0.0.1.yaml") // Path to your OpenAPI YAML file
//    templateDir.set("$rootDir/src/main/resources/templates") // Templates directory for code generation
    outputDir.set("$rootDir/src/main/generated") // Output directory for generated code
    generatorName.set("spring")
    packageName.set("org.dimensinfin.poc.generated.infrastructure.ports.inbound")
    apiPackage.set("org.dimensinfin.poc.generated.infrastructure.ports.inbound.api")
    modelPackage.set("org.dimensinfin.poc.generated.infrastructure.ports.inbound.domain") // Package for generated model classes
    modelNameSuffix.set("Dto")
    library.set("spring-cloud")
    configOptions.set(
        mapOf(
            "annotationLibrary" to "swagger2",
            "additionalTypeAnnotations" to "@lombok.Builder @lombok.Data @lombok.AllArgsConstructor @lombok.NoArgsConstructor",
            "hideGenerationTimeStamp" to "true",
            "interfaceOnly" to "true", // Generate only interfaces
            "dateLibrary" to "java8",
            "serializationLibrary" to "jackson",
            "useOptional" to "true",
            "useResponseEntity" to "true",
            "useSpringBoot3" to "true",
            "useSuaggerUI" to "true",
            "documentationProvider" to "springdoc",
            "useSpringBoot3" to "true" // Use Spring Boot features
        )
    )
}
// Task to generate OpenAPI code
tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("generateOpenApi") {
    group = "openapi"
    description = "Generates code from OpenAPI definition"
    dependsOn("openApiGenerate")
}
