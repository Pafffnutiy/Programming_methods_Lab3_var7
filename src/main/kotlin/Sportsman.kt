/**
 * Sportsman class
 * @author Pavel Zilbershteyn
 * @constructor Create object by
 * fullName, age, height, weight and sportsType
 */
data class Sportsman(
    val fullName: String,
    val age: UInt,
    val height: UInt,
    val weight: UInt,
    val sportsType: String
) : Comparable<Sportsman> {
    /**
     * Overrided fun of equals (a==b) between two Sportsman objects
     * If b isn't Sportsman - false
     * @param other Any object
     * @return true if a and b equals by fields
     * sportsType, fullName and age and false otherwise
     */
    override fun equals(other: Any?): Boolean {
        if (other !is Sportsman) return false
        return  this.sportsType == other.sportsType &&
                this.fullName == other.fullName     &&
                this.age == other.age
    }

    /**
     * Overrided compares (>, <, >=, <=) of two Sportsman objects
     * @param other Sportsman object
     * @return difference as Int
     */
    override fun compareTo(other: Sportsman): Int {
        return  compareValuesBy(
            this,
            other,
            Sportsman::sportsType,
            Sportsman::fullName,
            Sportsman::age
        )
    }

    /**
     * Overrided hashCode fun
     *
     * LY
     * @param Empty
     * @return hashCode as Int
     */
    override fun hashCode(): Int {
//        return hashCodeLY(sportsType)
        return hashCodeLY(sportsType)
    }

    fun hashCodeJS(): Int {
//        return hashCodeJS(sportsType)
        return hashCodeJS(sportsType)
    }
}

fun hashCodeLY(str: String): Int {
    var hash = 0
    str.forEach { letter ->
        hash = (hash * 1664525) + letter.code + 1013904223
    }
    hash %= (1 shl 6)
    return hash
}

fun hashCodeJS(str: String): Int {
    var hash = 1315423911
    str.forEach { letter ->
        hash = hash xor ((hash shl 5) + letter.code + (hash shr 2))
    }
    hash %= (1 shl 6)
    return hash
}
