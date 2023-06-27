package ph.projects.navor_jamesdave.apis.versions.with_gson.pokeapi_entities


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String = "",
    @SerializedName("front_shiny")
    val frontShiny: String = "",
): Parcelable