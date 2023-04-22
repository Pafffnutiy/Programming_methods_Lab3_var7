import com.google.gson.Gson
import java.io.FileReader


/**
 * object-reader from JSON
 * @author Pavel Zilbershteyn
 * @constructor Empty
 */
object OlympicTeamReader {
    /**
     * Team will be read from JSON with [PATH]+filename
     * @param filename name of JSON file with Team
     * @return OlympicTeam object
     */
    fun readTeamFromJSON(filename: String): OlympicTeam {
        return Gson().fromJson(FileReader(PATH+filename), OlympicTeam::class.java)
    }

    private const val PATH = "src/main/resources/testData/"
}