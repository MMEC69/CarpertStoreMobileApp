package cinec.edu.carpertstoremmec.data

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,

    val imgPath: String = ""
){
    constructor(): this ("", "", "", "")
}


