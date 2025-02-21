import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kmp)
    alias(libs.plugins.dokka)
    alias(libs.plugins.publisher)
    alias(libs.plugins.kover)
}

kotlin {
    jvm()
    js {
        nodejs()
        browser()
    }

    sourceSets {
        commonMain.dependencies {}
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}


mavenPublishing {
    configure(
        KotlinMultiplatform(
            javadocJar = JavadocJar.Dokka("dokkaHtml"),
            sourcesJar = true,
        )
    )

    coordinates(
        groupId = rootProject.group as String,
        artifactId = rootProject.name,
        version = rootProject.version as String
    )

    pom {
        name.set("Kotlinwind CSS")
        description.set("HTML styling inspired on tailwind.css")
        inceptionYear.set("2024")
        url.set("https://github.com/allangomes/kotlinwind-css/")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("allangomes")
                name.set("Allan Gomes")
                url.set("https://github.com/allangomes/")
            }
        }
        scm {
            url.set("https://github.com/allangomes/kotlinwind-css/")
            connection.set("scm:git:git://github.com/allangomes/kotlinwind-css.git")
            developerConnection.set("scm:git:ssh://git@github.com/allangomes/kotlinwind-css.git")
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    // Enable GPG signing for all publications
    // signAllPublications()
}

tasks.withType(Javadoc::class.java) {
    isFailOnError = false
    options {
        this as StandardJavadocDocletOptions
        addStringOption("Xdoclint:none", "-quiet")
        addStringOption("encoding", "UTF-8")
        addStringOption("charSet", "UTF-8")
    }
}
