package dominicschumerth.c.breakingbadapp.network.interactor

import android.util.Log
import dominicschumerth.c.breakingbadapp.injection.Injector
import dominicschumerth.c.breakingbadapp.model.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL

class BreakingBadInteractor {

    init {
        Injector.appInstance.inject(this)
    }

    private val url = "https://breakingbadapi.com/api/characters"

    suspend fun fetchCharacters(): ArrayList<Character> {
        val characters = ArrayList<Character>()

        val url:URL? = try {
            URL(url)
        }catch (e: MalformedURLException){
            Log.d("Exception", e.toString())
            null
        }

        url?.getString()?.apply {
            withContext(Dispatchers.Default) {
                val list = parseJson(this@apply)
                withContext(Dispatchers.Main) {
                    list?.let {
                        characters.addAll(it)
                    }
                }
            }
        }
        return characters
    }

    private fun URL.getString(): String? {
        val stream = openStream()
        return try {
            val r = BufferedReader(InputStreamReader(stream))
            val result = StringBuilder()
            var line: String?
            while (r.readLine().also { line = it } != null) {
                result.append(line).appendLine()
            }
            result.toString()
        }catch (e: IOException){
            e.toString()
        }
    }

    private fun parseJson(data:String):List<Character>?{
        val list = mutableListOf<Character>()

        try {
            val array = JSONArray(data)
            for(i in 0 until array.length()){
                val obj = JSONObject(array[i].toString())
                val name = obj.getString("name")
                val image = obj.getString("img")
                val occupations = ArrayList<String>()
                val occupationArray = obj.getJSONArray("occupation")
                for (j in 0 until occupationArray.length()) {
                    occupations.add(occupationArray[j].toString())
                }
                val status = obj.getString("status")
                val nickname = obj.getString("nickname")
                val seasons = ArrayList<String>()
                val seasonArray = obj.getJSONArray("appearance")
                for (j in 0 until seasonArray.length()) {
                    seasons.add(seasonArray[j].toString())
                }
                list.add(Character(name, image, occupations, status, nickname, seasons))
            }
        }catch (e: JSONException){
            Log.d("Exception", e.toString())
        }

        return list
    }
}