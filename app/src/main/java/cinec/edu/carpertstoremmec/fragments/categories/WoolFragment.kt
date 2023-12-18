package cinec.edu.carpertstoremmec.fragments.categories

import android.os.Bundle
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import cinec.edu.carpertstoremmec.data.Category
import cinec.edu.carpertstoremmec.databinding.FragmentBaseCategoryBinding
import cinec.edu.carpertstoremmec.databinding.FragmentMainCategoryBinding
import cinec.edu.carpertstoremmec.util.Resource
import cinec.edu.carpertstoremmec.viewmodel.CategoryViewModel
import cinec.edu.carpertstoremmec.viewmodel.factory.BaseCategoryViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class WoolFragment: BaseCategoryFragment() {
    private lateinit var binding: FragmentBaseCategoryBinding

    @Inject
    lateinit var firestore: FirebaseFirestore


    val viewModel by viewModels<CategoryViewModel> {
        BaseCategoryViewModelFactory(firestore, Category.Wool)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.offerProducts.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        showOfferLoading()
                    }

                    is Resource.Success -> {
                        offerAdapter.differ.submitList(it.data)
                        hideOfferLoading()
                    }

                    is Resource.Error -> {
                        Snackbar.make(requireView(), it.message.toString(), Snackbar.LENGTH_LONG)
                            .show()
                        hideOfferLoading()
                    }

                    else -> Unit
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.bestProducts.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        showBestProductLoading()
                    }

                    is Resource.Success -> {
                        bestProductsAdapter.differ.submitList(it.data)
                        hideBestProductLoading()
                    }

                    is Resource.Error -> {
                        Snackbar.make(requireView(), it.message.toString(), Snackbar.LENGTH_LONG)
                            .show()
                        hideBestProductLoading()
                    }

                    else -> Unit
                }
            }

        }

    }

    override fun onBestProductsPagingRequest() {

    }

    override fun onOfferPagingRequest() {

    }

}
