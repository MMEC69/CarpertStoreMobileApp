package cinec.edu.carpertstoremmec.fragments.loginRegister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cinec.edu.carpertstoremmec.R
import cinec.edu.carpertstoremmec.activities.RequiredFutureChanges
import cinec.edu.carpertstoremmec.activities.ShoppingActivity
import cinec.edu.carpertstoremmec.databinding.FragmentLoginBinding
import cinec.edu.carpertstoremmec.dialog.setupBottomSheetDialog
import cinec.edu.carpertstoremmec.util.Resource
import cinec.edu.carpertstoremmec.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.noAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.apply {
            buttonLogin.setOnClickListener {
                val email = edEmailLogin.text.toString().trim()
                val password = password.text.toString()


                viewModel.login(email, password)
            }
        }

        binding.forgotPassword.setOnClickListener {
            setupBottomSheetDialog { email ->
                viewModel.resetPassword(email)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.resetPassword.collect{
                when(it){
                    is Resource.Loading -> {
                        //binding.buttonLogin.startAnimation()
                    }
                    is Resource.Success -> {
                        //binding.buttonLogin.revertAnimation()
                        Snackbar.make(requireView(), "Reset link was sent to your mail", Snackbar.LENGTH_LONG).show()
                    }
                    is Resource.Error -> {
                        Snackbar.make(requireView(), "Error: ${it.message}", Snackbar.LENGTH_LONG).show()
                        //binding.buttonLogin.revertAnimation()
                    }
                    else -> Unit
                }

            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.login.collect{

                when(it){
                    is Resource.Loading -> {
                        //binding.buttonLogin.startAnimation()

                    }
                    is Resource.Success -> {
                        //binding.buttonLogin.revertAnimation()
                        Intent(requireActivity(), ShoppingActivity::class.java).also { intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }

                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        //binding.buttonLogin.revertAnimation()

                    }
                    else -> Unit

                }
            }
        }


    }
}