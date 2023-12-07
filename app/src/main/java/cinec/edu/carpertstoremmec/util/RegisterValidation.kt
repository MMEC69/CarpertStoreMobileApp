package cinec.edu.carpertstoremmec.util

sealed class RegisterValidation(){
    object Success:RegisterValidation()
    data class Failed(val message: String): RegisterValidation()
}

data class RegisterFieldsState(
    //specify fields here to be validated
    val email: RegisterValidation,
    val password: RegisterValidation
    //We will send an object from this calls to fragment to check
)
