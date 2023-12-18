package cinec.edu.carpertstoremmec.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cinec.edu.carpertstoremmec.adapters.AllOrdersAdapter
import cinec.edu.carpertstoremmec.databinding.FragmentOrdersBinding
import cinec.edu.carpertstoremmec.util.Resource
import cinec.edu.carpertstoremmec.viewmodel.AllOrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AllOrdersFragment: Fragment() {
    private lateinit var binding: FragmentOrdersBinding
    val viewModel by viewModels<AllOrdersViewModel>()
    val allOrdersAdapter by lazy { AllOrdersAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrdersBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOrderRv()

        lifecycleScope.launchWhenStarted {
            viewModel.allOrders.collectLatest {
                when(it){
                    is Resource.Loading ->{
                        binding.progressbarAllOrders.visibility = View.VISIBLE
                    }
                    is Resource.Success ->{
                        binding.progressbarAllOrders.visibility = View.GONE
                        allOrdersAdapter.differ.submitList(it.data)
                        if (it.data.isNullOrEmpty()){
                            binding.tvEmptyOrders.visibility = View.VISIBLE
                        }
                    }
                    is Resource.Error ->{
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        binding.tvEmptyOrders.visibility = View.GONE

                    }
                    else -> Unit
                }
            }
        }
        allOrdersAdapter.onClick = {
            val action = AllOrdersFragmentDirections.actionOrdersFragmentToOrderDetailFragment(it)
            findNavController().navigate(action)
        }

    }

    private fun setupOrderRv() {
        binding.rvAllOrders.apply {
            adapter = allOrdersAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

}