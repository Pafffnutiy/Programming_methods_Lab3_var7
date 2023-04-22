import com.google.gson.GsonBuilder
import java.io.FileWriter

/**
 * Class of OlympicTeam
 * @constructor create object by List of Sportsman
 * @author Pavel Zilbershteyn
 */
class OlympicTeam(var team: List<Sportsman> = listOf()) {
    /**
     * Overriding of operator += for case
     * OlympicTeam += Sportsman
     * @param sportsman
     * @return Unit
     */
    operator fun plusAssign(sportsman: Sportsman) {
        team += sportsman
    }

    /**
     * Overriding of operator + for case
     * OlympicTeam + Sportsman
     * @param sportsman
     * @return OlympicTeam
     */
    operator fun plus(sportsman: Sportsman): OlympicTeam {
        return OlympicTeam(team + sportsman)
    }

    /**
     * Overriding of operator + for case
     * OlympicTeam + OlympicTeam
     * @param otherTeam
     * @return OlympicTeam
     */
    operator fun plus(otherTeam: OlympicTeam): OlympicTeam {
        return OlympicTeam(team + otherTeam.team)
    }

    /**
     * Overriding of indexing operator []
     * @param index
     * @return Sportsman
     */
    operator fun get(index: Int): Sportsman = team[index]

    /**
     * Overriding of equals (==) between two OlympicTeam objects
     * If b isn't OlympicTeam returns false
     * @param other Any object
     * @return true if this.team==other.team
     * and false otherwise
     */
    override fun equals(other: Any?): Boolean {
        if (other !is OlympicTeam) return false
        return  team == other.team
    }

    /**
     * Kotlin sorting
     * @param Empty
     * @return sorted OlympicTeam
     */
    fun sort(): OlympicTeam {
        return OlympicTeam(team.sorted())
    }

    /**
     * Sorting by simple inserts
     * @param Empty
     * @return sorted OlympicTeam
     */
    fun sortSimpleInserts(): OlympicTeam {
        val sortedTeam = team.toMutableList()
        for (index in 1 until sortedTeam.size) {
            val value = sortedTeam[index]
            var subIndex = index - 1
            while (subIndex >= 0 && sortedTeam[subIndex] > value) {
                sortedTeam[subIndex + 1] = sortedTeam[subIndex]
                subIndex--
            }
            sortedTeam[subIndex + 1] = value
        }

        return OlympicTeam(sortedTeam)
    }

    /**
     * Quick sorting
     * @param Empty
     * @return sorted OlympicTeam
     */
    fun sortQuick(): OlympicTeam {
        val sortedTeam = team

        if (sortedTeam.size < 2) return this

        val pivot = sortedTeam[sortedTeam.size / 2]
        val equalToPivot = sortedTeam.filter { it == pivot }
        val lessThanPivot = sortedTeam.filter { it < pivot }
        val greaterThanPivot = sortedTeam.filter { it > pivot }

        return  OlympicTeam(lessThanPivot).sortQuick() +
                OlympicTeam(equalToPivot) +
                OlympicTeam(greaterThanPivot).sortQuick()
    }

    /**
     * Writing team to JSON file "src/main/resources/[filename]"
     * @param filename name of JSON file
     * @return Unit
     */
    fun writeTeamToJSON(filename: String) {
        FileWriter("src/main/resources/$filename").use { writer ->
            val gsonPretty = GsonBuilder().setPrettyPrinting().create()
            gsonPretty.toJson(this, writer)
        }
    }

    /**
     * Linear search of sportsman in team
     * @param target required element
     * @return index of target in array, if found and -1 otherwise
     */
    fun linearSearch(target: String): List<Int> {
        var positions = listOf<Int>()
        for ((index, elem) in team.withIndex()) {
            if (elem.sportsType == target) {
                positions += index
            }
        }

        return positions
    }

    /**
     * Binary search of sportsman in team
     * @param target required element
     * @param start start position for searching
     * @param end end position for searching
     * @return index of target in array, if found and -1 otherwise
     */
    fun binarySearch(target: String, start: Int = 0, end: Int = team.lastIndex): List<Int> {
        if (start > end)
            return listOf()

        val middle: Int = start + (end - start) / 2
        val midPos: List<Int> =
            if (team[middle].sportsType == target) {
                listOf(middle)
            } else if (team[middle].sportsType > target) {
                binarySearch(target, start, middle - 1)
            } else {
                binarySearch(target, middle + 1, end)
            }

        if (midPos.isEmpty())
            return midPos

        var leftPos = midPos[0]
        var rightPos = midPos[0]

        while (leftPos > 0 && rightPos < team.size - 1) {
            if (team[leftPos - 1].sportsType != target && team[rightPos + 1].sportsType != target)
                break
            else {
                if (team[leftPos - 1].sportsType == target)
                    leftPos--
                if (team[rightPos + 1].sportsType == target)
                    rightPos++
            }
        }

        return (leftPos..rightPos).toList()
    }

    /**
     * Getting random Sportsman from team
     * @param Empty
     * @return random Sportsman
     */
    fun random(): Sportsman {
        return team.random()
    }

    /**
     * Overriding hashCode fun
     * @param Empty
     * @return hashCode as Int
     */
    override fun hashCode(): Int {
        return team.hashCode()
    }

    /**
     * Helper for Pyramid sorting. Make pyramid from array
     * @param arr Array of Sportsmen
     * @param root root of heap
     * @param n end index
     * @return Unit
     */
    private fun heapify(arr: Array<Sportsman>, size: Int, root: Int) {
        var largest = root // Initialize maximum element as root
        val left = 2 * root + 1
        val right = 2 * root + 2
        // if left son bigger than root
        if (left < size && arr[root] < arr[left]) {
            largest = left
        }
        // if right son bigger than the biggest element by now
        if (right < size && arr[largest] < arr[right]) {
            largest = right
        }
        // if the biggest element is not root
        if (largest != root) {
            arr[root] = arr[largest].also {
                arr[largest] = arr[root]
            }
            // Recursively change affected subheap into heap
            heapify(arr, size, largest)
        }
    }

    /**
     * Pyramid sorting
     * @param Empty
     * @return sorted OlympicTeam
     */
    fun sortPyramid(): OlympicTeam {
        val sortedTeam = team.toTypedArray()
        // Building heap from array
        for (i in sortedTeam.size/2-1 downTo  0)
            heapify(sortedTeam, sortedTeam.size - 1, i)

        // Extract the elements from the heap one by one
        for (i in sortedTeam.size-1 downTo 1) {
            // Move current root into end
            sortedTeam[i] = sortedTeam[0].also {
                sortedTeam[0] = sortedTeam[i]
            }

            heapify(sortedTeam, i, 0)
        }

        return OlympicTeam(sortedTeam.toList())
    }

    /**
     * LY hash map builder
     * @return HashMap built with LY algo
     */
    fun buildHashMapLY(): HashMap<Int, MutableList<Sportsman>> {
        val hashMap = HashMap<Int, MutableList<Sportsman>>()
        team.forEach { sportsman ->
            val hashCode = sportsman.hashCode()
            if (hashCode in hashMap.keys)
                hashMap[hashCode]?.add(sportsman)
            else
                hashMap[hashCode] = mutableListOf(sportsman)
        }

        return hashMap
    }

    /**
     * JS hash map builder
     * @return HashMap built with JS algo
     */
    fun buildHashMapJS(): HashMap<Int, MutableList<Sportsman>> {
        val hashMap = HashMap<Int, MutableList<Sportsman>>()
        team.forEach { sportsman ->
            val hashCode = sportsman.hashCodeJS()
            if (hashCode in hashMap.keys)
                hashMap[hashCode]?.add(sportsman)
            else
                hashMap[hashCode] = mutableListOf(sportsman)
        }

        return hashMap
    }
}