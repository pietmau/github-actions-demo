plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.pietrantuono.library"
    compileSdk = 33

    defaultConfig {
        minSdk = 27

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    flavorDimensions.add("color")

    productFlavors {
        create("flavourA") {
            namespace = "com.pietrantuono.libraryA"
            dimension = "color"
        }
        create("flavourB") {
            namespace = "com.pietrantuono.libraryB"
            dimension = "color"
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    publishing {
        multipleVariants {
            allVariants()
            withJavadocJar()
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

publishing {
    publications {
        register<MavenPublication>("flavourA") {
            groupId = "com.pietrantuono.library"
            artifactId = "my-library.flavourA"
            version = "1.0"

            afterEvaluate {
                from(components["flavourARelease"])
            }
        }

        register<MavenPublication>("flavourB") {
            groupId = "com.pietrantuono.library"
            artifactId = "my-library.flavourB"
            version = "1.1"

            afterEvaluate {
                from(components["flavourBRelease"])
            }
        }
        repositories {
            maven {
                url = uri("https://nexus.my-company.com/repository/maven-releases/")
                credentials {
                    username = "NEXUS_USER_NAME"
                    password = "NEXUS_USER_PASSWORD"
                }
            }
        }
    }
}


