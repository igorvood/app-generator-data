import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

val koin_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm")
    kotlin("plugin.spring") version "2.1.10"
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"
    id("org.openapi.generator") version "7.10.0"
}

group = "ru.vood.data.generator"
version = "0.0.1"

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
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json")
//    implementation("org.openapitools:openapi-generator:7:10:0")
    implementation("org.openapitools:openapi-generator:7.10.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

val openApiConfigOptions = mapOf(
    "serializationLibrary" to "kotlinx_serialization",
//    "serializationLibrary" to "jackson",

    "interfaceOnly" to "true",
//    "library" to "jvm-spring-restclient",
//    "library" to "jvm-spring-webclient",

//    "library" to "jvm-ktor",

//    "useSpringBoot3" to "true",


//    "dateLibrary" to "kotlinx-datetime",
//    "explicitApi" to "true",
)


//tasks.withType<Jar> {
//    dependsOn(
//        "t1",
//    )
//}

// Конфигурация OpenAPI Generator
//tasks.register<GenerateTask>("t1") {
openApiGenerate {
    id.set("kotlin-spring")
    generatorName.set("kotlin")  // Генератор для Ktor
    inputSpec.set("${projectDir}/src/main/resources/openapi/openapi.yml")  // Путь к OpenAPI-спецификации
    outputDir.set("${layout.buildDirectory.get()}/generated")  // Выходная директория
    apiPackage.set("ru.vood.data.geration.api")  // Пакет для API-роутов
    modelPackage.set("ru.vood.data.geration.model")  // Пакет для DTO-моделей
    configOptions.set(openApiConfigOptions)
    globalProperties.set(mapOf("models" to ""))
    generateModelTests = false
}


sourceSets {
    main {
        kotlin {
            srcDir("${buildDir}/generated/src/main/kotlin")
        }
    }
}

// Зависимость компиляции от генерации OpenAPI
//tasks.withType<KotlinCompile> {
//    dependsOn("openApiGenerate")
//}

// Конфигурация Ktor
//ktor {
//    fatJar {
//        archiveFileName.set("task-api.jar")
//    }
//}

// Добавляем сгенерированный код в исходники
//kotlin.sourceSets["main"].kotlin.srcDirs("${layout.buildDirectory.get()}/generated/src/main/kotlin")

tasks.withType<Test> {
    useJUnitPlatform()
}

//tasks.withType<KotlinCompile>{
//    dependsOn("openApiGenerator")
//}