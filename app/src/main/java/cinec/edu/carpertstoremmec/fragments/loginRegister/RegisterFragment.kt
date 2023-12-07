package cinec.edu.carpertstoremmec.fragments.loginRegister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cinec.edu.carpertstoremmec.R
import cinec.edu.carpertstoremmec.databinding.FragmentRegisterBinding
import cinec.edu.carpertstoremmec.viewmodel.RegisterViewModel
import cinec.edu.carpertstoremmec.data.User
import cinec.edu.carpertstoremmec.util.RegisterValidation
import cinec.edu.carpertstoremmec.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val TAG = "RegisterFragment"

@AndroidEntryPoint
class RegisterFragment: Fragment(R.layout.fragment_register) {

    private lateinit var bindings: FragmentRegisterBinding

    //to get view model
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindings = FragmentRegisterBinding.inflate(inflater)
        return bindings.root
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindings.regTxt.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment2_to_loginFragment2)
        }

        bindings.apply {
            buttonLogin.setOnClickListener {
                val user = User(
                    //trim will remove spaces at the end at the first of the string
                    firstName.text.toString().trim(),
                    lastName.text.toString().trim(),
                    edEmailLogin.text.toString().trim()

                )
                val password = password.text.toString()
                viewModel.createUserWithEmailAndPassword(user, password)
            }
        }
        //observe the register operation
        lifecycleScope.launchWhenStarted {
            //we collect our flow in here
            viewModel.register.collect{
                when(it){
                    is Resource.Loading -> {
                        //bindings.buttonLogin.startAnimation( )

                    }
                    is Resource.Success -> {
                        Log.d("test", it.message.toString())
                        //to stop animation after success
                        //bindings.buttonLogin.revertAnimation()

                    }
                    is Resource.Error -> {
                        Log.e(TAG,it.message.toString())
                        //to stop animation after error
                        //bindings.buttonLogin.revertAnimation()
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.validation.collect(){ validation ->
                if(validation.email is RegisterValidation.Failed){
                    withContext(Dispatchers.Main){
                        bindings.edEmailLogin.apply {
                            requestFocus()
                            error = validation.email.message
                        }
                    }
                }
                if (validation.password is RegisterValidation.Failed){
                    withContext(Dispatchers.Main){
                        bindings.password.apply {
                            requestFocus()
                            error = validation.password.message
                        }
                    }
                }
            }
        }

    }

}