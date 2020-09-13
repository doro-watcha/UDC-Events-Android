package com.goddoro.udc

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.*
import com.goddoro.common.common.navigation.MainMenu
import com.goddoro.map.EventMapFragment
import com.goddoro.udc.databinding.ActivityMainBinding
import com.goddoro.udc.di.ViewModelFactory
import com.goddoro.udc.views.home.HomeFragment
import com.goddoro.udc.views.profile.ProfileFragment
import com.goddoro.udc.views.udc.UdcFragment
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity :  DaggerAppCompatActivity(), HasDefaultViewModelProviderFactory {

    private lateinit var mBinding : ActivityMainBinding

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    override fun getDefaultViewModelProviderFactory() = viewModelFactory

    private val mViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

    private lateinit var fragment1 : HomeFragment
    private lateinit var fragment2 : EventMapFragment
    private lateinit var fragment3 : UdcFragment
    private lateinit var fragment4 : ProfileFragment
    private lateinit var curFragment: Fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel


        setContentView(mBinding.root)

        initView()

        initFragments(savedInstanceState == null)
        observeViewModel()
    }

    private fun initView() {

        mBinding.bottomNavigation.setOnNavigationItemSelectedListener {
            _menu.value = MainMenu.parseIdToMainMenu(it.itemId)
            true
        }


    }

    private fun initFragments(isFirstCreation : Boolean) {

        fragment1 = supportFragmentManager.findFragmentByTag("0") as? HomeFragment ?: HomeFragment.newInstance()
        fragment2 = supportFragmentManager.findFragmentByTag("1") as? EventMapFragment ?: EventMapFragment.newInstance()
        fragment3 = supportFragmentManager.findFragmentByTag("2") as? UdcFragment ?: UdcFragment.newInstance()
        fragment4 = supportFragmentManager.findFragmentByTag("3") as? ProfileFragment ?: ProfileFragment.newInstance()
        curFragment = when(menu.value) {
            MainMenu.HOME->fragment1
            MainMenu.EVENT->fragment2
            MainMenu.UDC->fragment3
            MainMenu.VIDEO->fragment4
            else -> throw IllegalStateException()
        }

        if(isFirstCreation) {
            val fm = supportFragmentManager
            fm.beginTransaction().add(R.id.fragmentContainer, fragment4, "0").hide(fragment4).commit()
            fm.beginTransaction().add(R.id.fragmentContainer, fragment3, "1").hide(fragment3).commit()
            fm.beginTransaction().add(R.id.fragmentContainer, fragment2, "2").hide(fragment2).commit()
            fm.beginTransaction().add(R.id.fragmentContainer, fragment1, "3").commit()
        }
    }


    private fun changeFragment(menu : MainMenu) {

        val willShow = when (menu) {
            MainMenu.HOME -> fragment1
            MainMenu.EVENT -> fragment2
            MainMenu.UDC -> fragment3
            MainMenu.VIDEO -> fragment4
        }
        supportFragmentManager.beginTransaction().hide(curFragment).show(willShow).commit()
        curFragment = willShow
    }


    private fun observeViewModel() {

        menu.observe(this@MainActivity) { menu->
            changeFragment(menu)

            if(mBinding.bottomNavigation.selectedItemId != menu.menuId)
                mBinding.bottomNavigation.selectedItemId = menu.menuId
        }


        mViewModel.apply {
        }
    }

    companion object {
        private val _menu : MutableLiveData<MainMenu> = MutableLiveData(MainMenu.HOME)
        val menu : LiveData<MainMenu> = _menu
    }

}