import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml
import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.shadow)
    alias(libs.plugins.resourceFactoryPaper)
    alias(libs.plugins.runPaper)
}

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
    version.set("0.0.0-DEV")
    author.set("Runkang10")
    website.set("https://github.com/Runkang10/AtomicFreeze")

    apiVersion.set("26.2")
    bootstrapper.set("io.github.runkang10.atomicfreeze.AtomicFreezeBootstrap")
    main.set("io.github.runkang10.atomicfreeze.AtomicFreeze")
    foliaSupported.set(true)
    load.set(BukkitPluginYaml.PluginLoadOrder.STARTUP)
    dependencies {
        server("packetevents", PaperPluginYaml.Load.BEFORE, required = false, joinClasspath = true)
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
}