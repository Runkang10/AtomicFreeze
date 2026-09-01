import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml
import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.shadow)
    alias(libs.plugins.resourceFactoryPaper)
    alias(libs.plugins.runPaper)
    alias(libs.plugins.minotaur)
}

val modrinthToken: String? = System.getenv("MODRINTH_TOKEN")
val projectTitle = System.getenv("TITLE") ?: "0.0.0-DEV"
val projectVersion = System.getenv("VERSION")?.removePrefix("v") ?: "0.0.0-DEV"
val changeLogs = rootProject.file("CHANGELOGS.md").readText().ifBlank { "_No changelog was specified._" }

repositories {
    gradlePluginPortal()
    mavenCentral()

    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-releases/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.kotlinx.coroutines)
    implementation(libs.compactMono)
    implementation(libs.configurate.hocon)
    implementation(libs.configurate.extra.kotlin)

    compileOnly("io.papermc.paper:paper-api:26.2.build.+")
    compileOnly("com.github.retrooper:packetevents-spigot:2.13.0")
}

paperPluginYaml {
    name.set(rootProject.name)
    description.set("Ignore players packets to make them look like they're lagging!")
    version.set(projectVersion)
    author.set("Runkang10")
    website.set("https://github.com/Runkang10/AtomicFreeze")

    apiVersion.set("26.2")
    bootstrapper.set("io.github.runkang10.atomicfreeze.AtomicFreezeBootstrap")
    main.set("io.github.runkang10.atomicfreeze.AtomicFreeze")
    foliaSupported.set(true)
    load.set(BukkitPluginYaml.PluginLoadOrder.STARTUP)
    dependencies {
        server("packetevents", PaperPluginYaml.Load.BEFORE, required = true, joinClasspath = true)
    }
}

tasks {
    shadowJar {
        archiveBaseName.set(rootProject.name)
        archiveVersion.set("")
        archiveClassifier.set("")

        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    runServer {
        minecraftVersion("26.2")
        jvmArgs("-Xms2G", "-Xmx2G", "-Dcom.mojang.eula.agree=true")
        downloadPlugins {
            modrinth("packetevents", "2.13.0+spigot")
        }
    }

    if (modrinthToken != null) modrinth {
        token.set(modrinthToken)
        projectId.set("smiyoJmc")

        versionName.set(projectTitle)
        versionNumber.set(projectVersion)
        versionType.set("release")
        changelog.set(changeLogs)
        uploadFile.set(shadowJar)
        gameVersions.addAll(
            "1.21.4",
            "1.21.5",
            "1.21.6",
            "1.21.7",
            "1.21.8",
            "1.21.9",
            "1.21.10",
            "1.21.11",
            "26.1",
            "26.1.1",
            "26.1.2",
            "26.2"
        )
        loaders.addAll("paper", "purpur", "folia")
        dependencies {
            required.project("packetevents")
        }

        syncBodyFrom.set(rootProject.file("README.md").readText())
    }
}