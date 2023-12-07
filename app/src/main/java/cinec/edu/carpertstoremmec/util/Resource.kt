package cinec.edu.carpertstoremmec.util

sealed class Resource<T>(
    //to pass data
    val data:T?=null, //data can be null
    val message: String? = null //for error message

){
    //This class is generic, why ? because of <T>
    class Success<T>(data: T): Resource<T>(data)

    class Error<T>(message: String): Resource<T>(message = message)

    class Loading<T>: Resource<T>()

    class Unspecified<T> : Resource<T>()

}
