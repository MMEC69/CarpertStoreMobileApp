package cinec.edu.carpertstoremmec.util

import android.util.Patterns

//This contains the functions the that is going to validate the email and password
fun validateEmail(email : String): RegisterValidation{
    if(email.isEmpty())
        return RegisterValidation.Failed("Email field cannot be empty")

    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return RegisterValidation.Failed("Email is in wrong format")

    return RegisterValidation.Success
}

fun validatePassword(password: String): RegisterValidation{
    if(password.isEmpty())
        return RegisterValidation.Failed("Password field cannot be empty")

    if(password.length<6)
        return RegisterValidation.Failed("Password should contain at least 6 characters")

    return RegisterValidation.Success
}