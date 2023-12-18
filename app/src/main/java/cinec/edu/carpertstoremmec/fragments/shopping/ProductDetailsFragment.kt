package cinec.edu.carpertstoremmec.fragments.shopping

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import cinec.edu.carpertstoremmec.R
import cinec.edu.carpertstoremmec.activities.ShoppingActivity
import cinec.edu.carpertstoremmec.adapters.ColorsAdapter
import cinec.edu.carpertstoremmec.adapters.SizesAdapter
import cinec.edu.carpertstoremmec.adapters.ViewPager2Images
import cinec.edu.carpertstoremmec.data.CartProduct
import cinec.edu.carpertstoremmec.databinding.FragmentProductDetailsBinding
import cinec.edu.carpertstoremmec.util.Resource
import cinec.edu.carpertstoremmec.util.hideBottomNavigationView
import cinec.edu.carpertstoremmec.viewmodel.DetailsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProductDetailsFragment: Fragment() {

    private val args by navArgs<ProductDetailsFragmentArgs>()
    private lateinit var binding: FragmentProductDetailsBinding
    private val viewPagerAdapter by lazy { ViewPager2Images() }
    private val sizesAdapter by lazy { SizesAdapter() }
    private val colorsAdapter by lazy { ColorsAdapter() }

    private var selectedColor: Int? = null
    private var selectedSize: String? = null
    private val viewModel by viewModels<DetailsViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        hideBottomNavigationView()

        binding = FragmentProductDetailsBinding.inflate((inflater))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = args.product

        setupSizesRv()
        setupColorRv()
        setupViewpager()

        binding.imageClose.setOnClickListener{
            findNavController().navigateUp()
        }

        sizesAdapter.onItemClick = {
            selectedSize = it

        }

        colorsAdapter.onItemClick = {
            selectedColor = it
        }

        binding.buttonAddToCart.setOnClickListener {
            viewModel.addUpdateProductInCart(CartProduct(product, 1, selectedColor, selectedSize))
        }

        lifecycleScope.launchWhenStarted {
            viewModel.addToCart.collectLatest {
                when(it){
                    is Resource.Loading ->{
                        //start animation if have
                    }
                    is Resource.Success ->{
                        //stop animation if have
                        //binding.buttonAddToCart.setBackgroundColor(Color.GREEN)
                        Toast.makeText(requireContext(), "Product added to cart", Toast.LENGTH_SHORT).show()

                    }
                    is Resource.Error ->{
                        //stop animation if have
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

        binding.apply {
            tvProductName.text = product.name
            tvProductPrice.text = "$ ${product.price}"
            tvProductDescription.text = product.description

            if (product.colors.isNullOrEmpty())
                tvProductColors.visibility = View.INVISIBLE

            if (product.sizes.isNullOrEmpty())
                tvProductSize.visibility = View.INVISIBLE


        }
        viewPagerAdapter.differ.submitList(product.images)
        product.colors?.let { colorsAdapter.differ.submitList(it) }
        product.sizes?.let { sizesAdapter.differ.submitList(it) }


    }

    private fun setupViewpager() {
        binding.apply {
                viewPagerProductImages.adapter = viewPagerAdapter
            }
    }

    private fun setupColorRv() {
        binding.rvColors.apply {
            adapter = colorsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupSizesRv() {
        binding.rvSizes.apply {
            adapter = sizesAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
}