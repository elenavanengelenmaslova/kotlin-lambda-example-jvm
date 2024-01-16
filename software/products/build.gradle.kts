
dependencies {
    implementation("software.amazon.awssdk:dynamodb-enhanced:2.20.68")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("com.amazonaws:aws-lambda-java-core:1.2.2")
    implementation("com.amazonaws:aws-lambda-java-events:3.11.1")
   // implementation("com.amazonaws:aws-xray-recorder-sdk-aws-sdk-v2:2.12.0")
    implementation("org.slf4j:slf4j-api:2.0.5")
    implementation("org.slf4j:slf4j-simple:2.0.5")
    // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-test-junit5
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.9.22")
    // https://mvnrepository.com/artifact/org.mockito.kotlin/mockito-kotlin
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    // https://mvnrepository.com/artifact/org.mockito/mockito-junit-jupiter
    testImplementation("org.mockito:mockito-junit-jupiter:5.9.0")
    // https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    // https://mvnrepository.com/artifact/org.testcontainers/junit-jupiter
    testImplementation("org.testcontainers:junit-jupiter:1.19.3")

}

configurations {
    runtimeClasspath {
        exclude("org.apache.httpcomponents")
        exclude("org.jetbrains")
    }
}
repositories {
    mavenCentral()
}

tasks.register<Zip>("packageDistribution") {
    from(tasks.compileKotlin)
    from(tasks.compileJava)
    from(tasks.processResources)
    into("lib") {
        from(configurations.runtimeClasspath)
    }
    archiveFileName.set("function.zip")
    destinationDirectory.set(file("${project.rootDir}/build/dist"))
    dependsOn(":products:build")
}
