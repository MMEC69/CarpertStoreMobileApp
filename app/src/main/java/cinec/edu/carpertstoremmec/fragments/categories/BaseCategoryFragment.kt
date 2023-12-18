package cinec.edu.carpertstoremmec.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cinec.edu.carpertstoremmec.R
import cinec.edu.carpertstoremmec.adapters.BestProductAdapter
import cinec.edu.carpertstoremmec.databinding.FragmentBaseCategoryBinding
import cinec.edu.carpertstoremmec.util.showBottomNavigationView

open class BaseCategoryFragment: Fragment (R.layout.fragment_base_category) {

    private lateinit var binding: FragmentBaseCategoryBinding
    protected val offerAdapter: BestProductAdapter by lazy {
        BestProductAdapter()
    }
    protected val bestProductsAdapter: BestProductAdapter by lazy {
        BestProductAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOfferRv()
        setupProductRv()

        offerAdapter.onClick = {
            val b = Bundle().apply { putParcelable("product", it) }
            findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment, b)
        }

        bestProductsAdapter.onClick = {
            val b = Bundle().apply { putParcelable("product", it) }
            findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment, b)
        }

        binding.rvBestProducts.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1) && dx != 0) {
                    onOfferPagingRequest()
                }
            }
        })



    }

    fun showOfferLoading(){
        binding.offerProductsProgressbar.visibility = View.VISIBLE
    }

    fun hideOfferLoading(){
        binding.offerProductsProgressbar.visibility = View.GONE

    }

    fun showBestProductLoading(){
        binding.bestProductsProgressbar.visibility = View.VISIBLE
    }

    fun hideBestProductLoading(){
        binding.bestProductsProgressbar.visibility = View.GONE

    }

    open fun onOfferPagingRequest(){

    }

    open fun onBestProductsPagingRequest(){

    }

    private fun setupProductRv() {
        //bestProductsAdapter = BestProductAdapter()

        binding.rvBestProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = bestProductsAdapter
        }
    }

    private fun setupOfferRv() {
        //offerAdapter = BestProductAdapter()
        binding.rvOffer.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = offerAdapter
        }
    }

    override fun onResume() {
        super.onResume()

        showBottomNavigationView()
    }




}