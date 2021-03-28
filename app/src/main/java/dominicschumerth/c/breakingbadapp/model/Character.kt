package dominicschumerth.c.breakingbadapp.model

import java.io.Serializable
import com.google.gson.annotations.SerializedName

data class Character (
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("occupations")
    val occupations: List<String>,
    @SerializedName("status")
    val status: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("seasons")
    val seasons: List<String>
): Serializable