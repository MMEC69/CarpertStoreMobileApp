package cinec.edu.carpertstoremmec.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Address(
    val addressTitle: String,
    val fullName: String,
    val street: String,
    val phone: String,
    val city: String,
    val district: String
): Parcelable{

    constructor():this("", "", "", "","", "")

}
