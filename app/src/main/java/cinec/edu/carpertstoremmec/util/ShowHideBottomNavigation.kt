package cinec.edu.carpertstoremmec.util

import android.view.View
import androidx.fragment.app.Fragment
import cinec.edu.carpertstoremmec.R
import cinec.edu.carpertstoremmec.activities.ShoppingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Fragment.hideBottomNavigationView(){
    val bottomNavigationView =
        (activity as ShoppingActivity).findViewById<BottomNavigationView>(
        R.id.bottomNavigation
    )

    bottomNavigationView.visibility = View.GONE
}

fun Fragment.showBottomNavigationView(){
    val bottomNavigationView =
        (activity as ShoppingActivity).findViewById<BottomNavigationView>(
            R.id.bottomNavigation
        )

    bottomNavigationView.visibility = View.VISIBLE
}