
dependencies {
    implementation("software.amazon.awssdk:dynamodb-enhanced:2.18.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("com.amazonaws:aws-lambda-java-core:1.2.1")
    implementation("com.amazonaws:aws-lambda-java-events:3.11.0")
//    implementation("com.amazonaws:aws-xray-recorder-sdk-core:2.12.0")
//    implementation("com.amazonaws:aws-xray-recorder-sdk-aws-sdk:2.12.0")
//    implementation("com.amazonaws:aws-xray-recorder-sdk-aws-sdk-instrumentor:2.12.0")
    implementation("com.amazonaws:aws-xray-recorder-sdk-aws-sdk-v2:2.12.0")
    implementation("org.slf4j:slf4j-api:2.0.3")
    implementation("org.slf4j:slf4j-simple:2.0.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.5.31")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.mockito:mockito-junit-jupiter:4.8.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    testImplementation("org.testcontainers:junit-jupiter:1.17.4")
}

configurations {
    runtimeClasspath {
        exclude("org.apache.httpcomponents")
        exclude("org.jetbrains")
    }
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
