package com.zed.kz.doskaz.main.presentation.main.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zed.kz.doskaz.R
import com.zed.kz.doskaz.global.base.BaseFragment
import com.zed.kz.doskaz.global.extension.addFragmentWithBackStack
import com.zed.kz.doskaz.global.extension.replaceFragment
import com.zed.kz.doskaz.global.extension.replaceFragmentWithBackStack
import com.zed.kz.doskaz.global.utils.AppConstants
import com.zed.kz.doskaz.global.utils.MenuObject
import com.zed.kz.doskaz.main.di.MainScope
import com.zed.kz.doskaz.main.presentation.auth.sign_in.SignInFragment
import com.zed.kz.doskaz.main.presentation.main.about.AboutFragment
import com.zed.kz.doskaz.main.presentation.main.blog.list.BlogListFragment
import com.zed.kz.doskaz.main.presentation.main.contacts.ContactsFragment
import com.zed.kz.doskaz.main.presentation.main.instruction.InstructionFragment
import com.zed.kz.doskaz.main.presentation.main.map.MapFragment
import com.zed.kz.doskaz.main.presentation.main.settings.SettingsFragment
import com.zed.kz.doskaz.main.presentation.profile.main.show.ShowProfileFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import timber.log.Timber

class HomeFragment : BaseFragment(), HomeFragmentView{

    companion object{

        val TAG = "HomeFragment"

        fun newInstance(): HomeFragment =
            HomeFragment()
    }

    @InjectPresenter
    lateinit var presenter: HomePresenter

    @ProvidePresenter
    fun providePresenter(): HomePresenter {
        getKoin().getScopeOrNull(MainScope.HOME_SCOPE)?.close()
        return getKoin().getOrCreateScope(MainScope.HOME_SCOPE, named(MainScope.HOME_SCOPE)).get()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_home

    override fun setUp(savedInstanceState: Bundle?) {
        navViewHome?.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.nav_home_map -> {
                    openMapFragment()
                }
                R.id.nav_home_profile -> {
                    presenter.onProfileMenuItemClicked()
                    selectMapFragment()
                }
                R.id.nav_home_blog -> {
                    openBlogListFragment()
                    selectMapFragment()
                }
                R.id.nav_home_instruction -> {
                    openInstructionFragment()
                    selectMapFragment()
                }
                R.id.nav_home_more -> {
                    openOrCloseDrawer()
                    selectMapFragment()
                }
            }

            return@setOnNavigationItemSelectedListener true
        }

        navDrawerViewHome?.setNavigationItemSelectedListener {

            when(it.itemId){
//                R.id.nav_drawer_blog -> openBlogListFragment()
//                R.id.nav_drawer_events -> presenter.onEventDrawerItemClicked()
//                R.id.nav_drawer_task -> presenter.onTaskDrawerItemClicked()
//                R.id.nav_drawer_comment -> presenter.onCommentDrawerItemClicked()
//                R.id.nav_drawer_objects -> presenter.onObjectDrawerItemClicked()
//                R.id.nav_drawer_help -> {}
                R.id.nav_drawer_about -> openAboutFragment()
                R.id.nav_drawer_contact -> openContactsFragment()
                R.id.nav_drawer_settings -> openSettingsFragment()
            }

            openOrCloseDrawer()
            return@setNavigationItemSelectedListener true
        }

        presenter.onFirstInit()

        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(
                    menuBackPressedReceiver,
                    IntentFilter(AppConstants.FILTER_MENU)
                )

        }
    }

    private fun selectMapFragment(){
        Handler().postDelayed({navViewHome?.selectedItemId = R.id.nav_home_map}, 1500)
    }

    override fun onDestroy() {
        getKoin().getScopeOrNull(MainScope.HOME_SCOPE)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .unregisterReceiver(menuBackPressedReceiver)
        }
        super.onDestroy()
    }

    private fun openOrCloseDrawer(){
        if (drawerLayout?.isDrawerOpen(GravityCompat.START) ?: false)
            drawerLayout?.closeDrawer(GravityCompat.START)
        else {
            MenuObject.isMenuOpen = true
            drawerLayout?.openDrawer(GravityCompat.START)
        }
    }

    override fun openMapFragment() {
        childFragmentManager.replaceFragment(
            R.id.container_home,
            MapFragment.newInstance(),
            MapFragment.TAG
        )
    }

    override fun openProfileFragment() {
        activity?.addFragmentWithBackStack(
            R.id.container,
            ShowProfileFragment.newInstance(),
            ShowProfileFragment.TAG
        )
    }

    override fun openAboutFragment() {
        activity?.addFragmentWithBackStack(
            R.id.container,
            AboutFragment.newInstance(),
            AboutFragment.TAG
        )
    }

    override fun openInstructionFragment() {
        activity?.addFragmentWithBackStack(
            R.id.container,
            InstructionFragment.newInstance(),
            InstructionFragment.TAG
        )
    }

    override fun openContactsFragment() {
        activity?.addFragmentWithBackStack(
            R.id.container,
            ContactsFragment.newInstance(),
            ContactsFragment.TAG
        )
    }

    override fun openBlogListFragment() {
        activity?.addFragmentWithBackStack(
            R.id.container,
            BlogListFragment.newInstance(),
            BlogListFragment.TAG
        )
    }

    override fun openShowProfileFragment(type: String) {
        activity?.addFragmentWithBackStack(
            R.id.container,
            ShowProfileFragment.newInstance(type),
            ShowProfileFragment.TAG
        )
    }

    override fun openSignInFragment() {
        activity?.addFragmentWithBackStack(
            R.id.container,
            SignInFragment.newInstance(),
            SignInFragment.TAG
        )
    }

    override fun openSettingsFragment() {
        activity?.replaceFragmentWithBackStack(
            R.id.container,
            SettingsFragment.newInstance(),
            SettingsFragment.TAG
        )
    }

    override fun showCurrentCity(name: String) {
        navDrawerViewHome?.menu?.findItem(R.id.nav_drawer_city)?.title = name
    }

    private val menuBackPressedReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            openOrCloseDrawer()
            MenuObject.isMenuOpen = false
            Timber.i("Sdaskajsfbjadfjk")
        }
    }
}