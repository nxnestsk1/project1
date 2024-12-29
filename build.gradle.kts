plugins {
    application
    kotlin("jvm") version "1.7.10"
}

repositories {
    mavenCentral()  // Adiciona o repositório Maven Central
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") } // Repositório Ktor EAP (caso seja necessário)
}

dependencies {
    // Ktor Server dependencies
    implementation("io.ktor:ktor-server-core:2.3.1")        // Núcleo do servidor Ktor
    implementation("io.ktor:ktor-server-netty:2.3.1")       // Engine Netty para Ktor
    implementation("io.ktor:ktor-server-html-builder:2.3.1") // Suporte para construção de HTML
    implementation("io.ktor:ktor-server-call-logging:2.3.1") // Para registro de chamadas
    implementation("io.ktor:ktor-server-status-pages:2.3.1") // Para gerenciar páginas de status
    implementation("ch.qos.logback:logback-classic:1.4.11") // Logger para Ktor
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.9.1") // Para construção de HTML
    implementation("org.slf4j:slf4j-simple:2.0.9") // Logger simples SLF4J
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.1")
}
