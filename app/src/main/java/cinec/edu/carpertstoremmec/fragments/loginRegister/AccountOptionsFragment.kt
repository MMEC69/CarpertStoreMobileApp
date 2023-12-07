package cinec.edu.carpertstoremmec.fragments.loginRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cinec.edu.carpertstoremmec.R
import cinec.edu.carpertstoremmec.databinding.FragmentAccountBinding

class AccountOptionsFragment: Fragment(R.layout.fragment_account) {
    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRegister.setOnClickListener{
            findNavController().navigate(R.id.action_accountOptionsFragment_to_registerFragment)
        }

        binding.buttonLoginAccountOptions.setOnClickListener{
            findNavController().navigate(R.id.action_accountOptionsFragment_to_loginFragment)
        }
    }
}
