group = "com.github.ochuerta.grpc"
version = "1.0-SNAPSHOT"

plugins {
    java
    id("com.google.protobuf") version "0.9.4"
    idea
}

repositories {
    mavenCentral()
}

dependencies {
    // gRPC
    runtimeOnly("io.grpc:grpc-netty-shaded:1.64.0")
    implementation("io.grpc:grpc-protobuf:1.64.0")
    implementation("io.grpc:grpc-stub:1.64.0")
    implementation ("io.grpc:grpc-protobuf-lite:1.64.0")
    compileOnly("org.apache.tomcat:annotations-api:6.0.53") // necessary for Java 9+
    implementation ("org.slf4j:slf4j-api:1.7.36")
    implementation ("ch.qos.logback:logback-classic:1.2.11")

    testImplementation("junit:junit:4.13.2")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.1"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.64.0"
        }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                // Ensure 'java' builtin is not added if it already exists
                if (!task.builtins.names.contains("java")) {
                    create("java") {
                        option("lite")
                    }
                }
            }
            task.plugins {
                create("grpc") {
                    option("lite")
                }
            }
        }
    }
}