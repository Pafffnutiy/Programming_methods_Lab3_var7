import java.io.File
import kotlin.system.measureNanoTime

val sizes = listOf(1, 5, 100, 500, 1000, 5000, 10000, 50000, 100000)

fun main() {
    File("src/main/resources/time.txt").printWriter().use { timeWriter ->
        File("src/main/resources/collision.txt").printWriter().use { collisionWriter ->
            timeWriter.println("\t\tLY\t\tJS")
            collisionWriter.println("\t\tLY\t\tJS")

            sizes.forEach { size ->
                val olympicTeam = OlympicTeamReader.readTeamFromJSON("$size.json")
                val targetSportsType = olympicTeam.random().sportsType
                val hashMapJS = olympicTeam.buildHashMapJS()
                val hashMapLY = olympicTeam.buildHashMapLY()

                val collisionLYCnt = getCollisionCount(hashMapLY)
                val collisionJSCnt = getCollisionCount(hashMapJS)
                val collisionCntLYFormatted = if (size < 1000) "\t$collisionLYCnt" else "$collisionLYCnt"
                collisionWriter.println("$size\t$collisionCntLYFormatted\t\t$collisionJSCnt")

                val timeToFindLY = measureNanoTime {
                    hashMapLY.findLY(targetSportsType)
                }
                val timeToFindJS = measureNanoTime {
                    hashMapJS.findJS(targetSportsType)
                }
                val timeToFindLYFormatted = if (size < 1000) "\t$timeToFindLY" else "$timeToFindLY"
                timeWriter.println("$size\t$timeToFindLYFormatted\t$timeToFindJS")
            }
        }
    }

//    val olympicTeam = OlympicTeamReader.readTeamFromJSON("${sizes[7]}.json")
//    val hashMapJS = olympicTeam.buildHashMapJS()
//    val hashMapLY = olympicTeam.buildHashMapLY()
//    var cnt = 0
//    var i = 1
//    var j = 1
//    hashMapLY.keys.forEach {
//        if (hashMapLY[it]!!.size > 1) {
//            println("$i: $it - ${hashMapLY[it]}")
//            i++
//        }
//    }
//println()
//println()
//    hashMapJS.keys.forEach {
//        if (hashMapJS[it]!!.size > 1) {
//            println("$j: $it - ${hashMapJS[it]}")
//            j++
//        }
//    }
//    println(cnt)
}

fun HashMap<Int, MutableList<Sportsman>>.findLY(targetSportsType: String): MutableList<Sportsman>? {
    val targetHashCode = hashCodeLY(targetSportsType)
    return this[targetHashCode]
}

fun HashMap<Int, MutableList<Sportsman>>.findJS(targetSportsType: String): MutableList<Sportsman>? {
    val targetHashCode = hashCodeJS(targetSportsType)
    return this[targetHashCode]
}

fun getCollisionCount(hashMap: HashMap<Int, MutableList<Sportsman>>): Int {
    val copyHashMap = hashMap
    return copyHashMap.filter { it.toPair().second.size > 1 }.size
}