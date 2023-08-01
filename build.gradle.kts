import xyz.srnyx.gradlegalaxy.enums.Repository
import xyz.srnyx.gradlegalaxy.enums.repository
import xyz.srnyx.gradlegalaxy.utility.setupAnnoyingAPI
import xyz.srnyx.gradlegalaxy.utility.spigotAPI


plugins {
    java
    id("xyz.srnyx.gradle-galaxy") version "1.1.2"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

setupAnnoyingAPI("4.1.0", "xyz.srnyx", "2.0.0", "A fun little game... when one person dies, everyone dies")
spigotAPI("1.8.8")
repository(Repository.PLACEHOLDER_API)
dependencies.compileOnly("me.clip", "placeholderapi", "2.11.3")
