package com.example.dronesexample.presentation.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.dronesexample.R
import com.example.dronesexample.presentation.drones.EditDronesFragment
import com.example.dronesexample.presentation.edit_plans.EditPlansFragment
import com.example.dronesexample.presentation.home.HomeFragment
import com.example.dronesexample.presentation.map.MapFragment
import com.example.dronesexample.presentation.plans.PlansFragment
import com.example.dronesexample.presentation.profile.EditProfileFragment
import com.example.dronesexample.presentation.request.RequestFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NavigationManager(private val fm: FragmentManager) {

    fun navigate(to: Destination) {

        when (to) {
            Destination.HOME -> toFragment( HomeFragment() )

            Destination.PROFILE -> toFragment( EditProfileFragment() )

            Destination.DRONES -> toDialog( EditDronesFragment() )

            Destination.PLANS -> toDialog( PlansFragment() )

            Destination.EDIT_PLANS -> toDialog( EditPlansFragment() )

            Destination.REQUEST -> toDialog( RequestFragment() )

            Destination.MAP -> toDialog( MapFragment() )
        }
    }

    private fun toFragment(fragment: Fragment) {
        fm.commit {
            replace(R.id.main_fragment_container, fragment, fragment::class.java.simpleName)
        }
    }

    private fun toDialog(dialog: BottomSheetDialogFragment) = dialog.show(fm, dialog::class.java.simpleName)
}

enum class Destination {
    HOME,
    PROFILE,
    DRONES,
    PLANS,
    EDIT_PLANS,
    REQUEST,
    MAP
}