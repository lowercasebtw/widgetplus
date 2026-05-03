plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.loom)
    alias(libs.plugins.blossom)
    alias(libs.plugins.ksp)
    alias(libs.plugins.fletchingtable.fabric)
    id("maven-publish")
}

class ModData {
    val id = property("mod.id") as String
    val name = property("mod.name") as String
    val version = property("mod.version") as String
    val group = property("mod.group") as String
    val description = property("mod.description") as String
    val source = property("mod.source") as String
    val issues = property("mod.issues") as String
    val license = property("mod.license") as String
    val modrinth = property("mod.modrinth") as String
    val curseforge = property("mod.curseforge") as String
    val discord = property("mod.discord") as String
    val minecraftVersion = property("mod.minecraft_version") as String
    val minecraftVersionRange = property("mod.minecraft_version_range") as String
}

class Dependencies {
    val fabricLoaderVersion = property("deps.fabric_loader_version") as String
    val devAuthVersion = property("deps.devauth_version") as String

    // Versioned
    val fabricApiVersion = property("deps.fabric_api_version") as String
    val modmenuVersion = property("deps.modmenu_version") as String
    val yaclVersion = property("deps.yacl_version") as String
}

val mod = ModData()
val deps = Dependencies()

val versionString = "${mod.version}-${mod.minecraftVersion}_fabric"
group = mod.group
base {
    archivesName.set("${mod.id}-${versionString}")
}

loom {
    runConfigs.remove(runConfigs["server"]) // Removes server run configs
    runConfigs.all {
        ideConfigGenerated(stonecutter.current.isActive)
        runDir = "../../run"
    }

    accessWidenerPath = stonecutter.process(
        rootProject.file("src/main/resources/${mod.id}.accesswidener"),
        "build/processed.accesswidener"
    )

    runs {
        afterEvaluate {
            configureEach {
                property("devauth.enabled", "true")
                property("devauth.account", "main")
            }
        }
    }
}

fletchingTable {
    lang.create("main") {
        patterns.add("assets/${mod.id}/lang/**")
    }
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1") // DevAuth
    maven("https://maven.terraformersmc.com/") // Mod Menu
    maven("https://maven.isxander.dev/releases") // YACL
}

dependencies {
    minecraft("com.mojang:minecraft:${mod.minecraftVersion}")
    implementation("net.fabricmc:fabric-loader:${deps.fabricLoaderVersion}")

    implementation("net.fabricmc.fabric-api:fabric-api:${deps.fabricApiVersion}")
    implementation("dev.isxander:yet-another-config-lib:${deps.yaclVersion}-fabric")
    implementation("com.terraformersmc:modmenu:${deps.modmenuVersion}")

    localRuntime("me.djtheredstoner:DevAuth-fabric:${deps.devAuthVersion}")
}

java {
    val requiredJava = JavaVersion.VERSION_25
    sourceCompatibility = requiredJava
    targetCompatibility = requiredJava
    withSourcesJar()
}

tasks {
    processResources {
        val props = buildMap {
            put("id", mod.id)
            put("name", mod.name)
            put("version", mod.version)
            put("description", mod.description)
            put("source", mod.source)
            put("issues", mod.issues)
            put("license", mod.license)
            put("modrinth", mod.modrinth)
            put("curseforge", mod.curseforge)
            put("discord", mod.discord)

            val minecraftVersionRange = if (mod.minecraftVersionRange.contains(' ')) {
                val parts = mod.minecraftVersionRange.trim().split(' ')
                ">=" + parts.first() + ' ' + "<=" + parts.last()
            } else {
                mod.minecraftVersionRange
            }

            put("minecraft_version_range", minecraftVersionRange)
            put("fabric_loader_version", deps.fabricLoaderVersion.trim())
            put("fabric_api_version", deps.fabricApiVersion.trim())
            put("yacl_version", deps.yaclVersion)
        }

        props.forEach(inputs::property)
        filesMatching("**/lang/en_us.json") { // Defaults description to English translation
            expand(props)
            filteringCharset = "UTF-8"
        }

        filesMatching("fabric.mod.json") { expand(props) }
    }

    register<Copy>("buildAndCollect") {
        group = "build"

        val sourcesJar by existing
        from(jar, sourcesJar)

        into(rootProject.layout.buildDirectory.file("libs/${mod.version}"))
        dependsOn("build")
    }
}

val currentCommitHash: String by lazy {
    Runtime.getRuntime()
        .exec(arrayOf("git", "rev-parse", "--verify", "--short", "HEAD"), null, rootDir)
        .inputStream.bufferedReader().readText().trim()
}

blossom {
    replaceToken("@MODID@", mod.id)
    replaceToken("@VERSION@", mod.version)
    replaceToken("@COMMIT_HASH@", currentCommitHash)
}

if (stonecutter.current.isActive) {
    rootProject.tasks.register("buildActive") {
        group = "project"
        dependsOn(tasks.named("build"))
    }
}

fun <T> optionalProp(property: String, block: (String) -> T?): T? =
    findProperty(property)?.toString()?.takeUnless { it.isBlank() }?.let(block)