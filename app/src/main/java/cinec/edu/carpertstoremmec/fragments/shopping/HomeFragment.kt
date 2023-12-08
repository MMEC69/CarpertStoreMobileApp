package cinec.edu.carpertstoremmec.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cinec.edu.carpertstoremmec.R
import cinec.edu.carpertstoremmec.databinding.FragmentHomeBinding
import cinec.edu.carpertstoremmec.fragments.categories.MainCategoryFragment
import cinec.edu.carpertstoremmec.fragments.categories.NylonFragment
import cinec.edu.carpertstoremmec.fragments.categories.PolyesterFragment
import cinec.edu.carpertstoremmec.fragments.categories.PolypropyleneFragment
import cinec.edu.carpertstoremmec.fragments.categories.TriextaFragment
import cinec.edu.carpertstoremmec.fragments.categories.WoolFragment
import cinec.edu.furniturestoremmec.adapters.HomeViewpagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment: Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesFragment = arrayListOf<Fragment>(
            MainCategoryFragment(),
            NylonFragment(),
            PolyesterFragment(),
            PolypropyleneFragment(),
            TriextaFragment(),
            WoolFragment()
        )

        binding.viewpagerHome.isUserInputEnabled = false

        val viewpager2Adapter =
            HomeViewpagerAdapter(categoriesFragment, childFragmentManager, lifecycle)
        binding.viewpagerHome.adapter = viewpager2Adapter

        TabLayoutMediator(binding.tabLayout, binding.viewpagerHome) { tab, position ->
            when (position) {
                0 -> tab.text = "Main"
                1 -> tab.text = "Nylon"
                2 -> tab.text = "Polyester"
                3 -> tab.text = "Polypropylene"
                4 -> tab.text = "Triexta"
                5 -> tab.text = "Wool"
            }
        }.attach()
    }
}