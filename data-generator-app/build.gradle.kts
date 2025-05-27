import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val koin_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm")
    id("io.ktor.plugin") version "3.1.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"
    id("org.openapi.generator") version "7.13.0"
}

group = "ru.vood.data.generator"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.openfolder:kotlin-asyncapi-ktor:3.1.1")

    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-resources")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-server-netty")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-auth-jvm") // это не сильно надо
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml")

    // OpenAPI & Swagger (для документации)
    implementation("io.ktor:ktor-server-openapi")
    implementation("io.ktor:ktor-server-swagger")

    // Dropwizard Metrics (для мониторинга)
    implementation("io.ktor:ktor-server-metrics-micrometer-jvm") // Основа для метрик
//    implementation("io.ktor:ktor-server-metrics-dropwizard-jvm:1.6.8") // Интеграция с Dropwizard
    implementation("io.dropwizard.metrics:metrics-core:4.2.25")  // Ядро Dropwizard Metrics

    // Micrometer (опционально, если нужны другие экспортеры - Prometheus, JMX и т. д.)
    implementation("io.micrometer:micrometer-registry-prometheus:1.12.0")

    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

// Конфигурация OpenAPI Generator
openApiGenerate {
    generatorName.set("kotlin-server")  // Генератор для Ktor
    inputSpec.set("${projectDir}/src/main/resources/openapi/openapi.yml")  // Путь к OpenAPI-спецификации
    outputDir.set("${buildDir}/generated")  // Выходная директория
    apiPackage.set("ru.vood.data.geration.api")  // Пакет для API-роутов
    modelPackage.set("ru.vood.data.geration.model")  // Пакет для DTO-моделей
//    globalProperties.set(
//
//        mapOf(
////            ЭskipDefaultInterf
//            "models" to "",  // Генерируем модели
//            "apis" to "true"  // Отключаем генерацию API
//        )
//    )
    configOptions.set(
        mapOf(
            "library" to "ktor",  // Используем Ktor
            "serializationLibrary" to "kotlinx_serialization",  // Сериализация
            "sourceFolder" to "src/main/kotlin",  // Куда складывать сгенерированный код
            "excludeAuth" to "true",  // отключение аутентификации
            "excludeMetrics" to "true",
            "excludeCompression" to "true"  // Отключает генерацию Compression
        )
    )
}

// Добавляем сгенерированный код в исходники
sourceSets {
    main {
        kotlin {
            srcDir("${buildDir}/generated/src/main/kotlin")
        }
    }
}

// Зависимость компиляции от генерации OpenAPI
tasks.withType<KotlinCompile> {
    dependsOn("openApiGenerate")
}

// Конфигурация Ktor
//ktor {
//    fatJar {
//        archiveFileName.set("task-api.jar")
//    }
//}

tasks.withType<Test> {
    useJUnitPlatform()
}