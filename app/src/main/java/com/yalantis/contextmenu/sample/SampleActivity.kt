package com.yalantis.contextmenu.sample

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.yalantis.contextmenu.R
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.MenuParams
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener
import kotlinx.android.synthetic.main.toolbar.*

class SampleActivity : AppCompatActivity() {

    private lateinit var contextMenuDialogFragment: ContextMenuDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        initToolbar()
        initMenuFragment()
        addFragment(SampleFragment(), true, R.id.container)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            when (it.itemId) {
                R.id.context_menu -> {
                    showContextMenuDialogFragment()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (::contextMenuDialogFragment.isInitialized && contextMenuDialogFragment.isAdded) {
            contextMenuDialogFragment.dismiss()
        } else {
            finish()
        }
    }

    protected fun addFragment(
            fragment: Fragment,
            addToBackStack: Boolean,
            @IdRes containerResId: Int
    ) {
        invalidateOptionsMenu()
        val backStackName = fragment.javaClass.name
        val fragmentPopped = supportFragmentManager.popBackStackImmediate(backStackName, 0)
        if (!fragmentPopped) {
            supportFragmentManager.beginTransaction().apply {
                add(containerResId, fragment, backStackName)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                if (addToBackStack) {
                    addToBackStack(backStackName)
                }
                commit()
            }
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        toolbar.apply {
            setNavigationIcon(R.drawable.btn_back)
            setNavigationOnClickListener { onBackPressed() }
        }

        tvToolbarTitle.text = "Samantha"
    }

    private fun initMenuFragment() {
        val menuParams = MenuParams(
                actionBarSize = resources.getDimension(R.dimen.tool_bar_height).toInt(),
                menuObjects = getMenuObjects(),
                isClosableOutside = false
        )

        contextMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams).apply {
            setItemClickListener(object : OnMenuItemClickListener {
                override fun onMenuItemClick(clickedView: View, position: Int) {
                    Toast.makeText(
                            this@SampleActivity,
                            "Clicked on position: $position",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            })
            setItemLongClickListener(object : OnMenuItemLongClickListener {
                override fun onMenuItemLongClick(clickedView: View, position: Int) {
                    Toast.makeText(
                            this@SampleActivity,
                            "Long clicked on position: $position",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    private fun getMenuObjects() = mutableListOf<MenuObject>().apply {
        val close = MenuObject().apply { setResourceValue(R.drawable.icn_close) }
        val send = MenuObject("Send message").apply { setResourceValue(R.drawable.icn_1) }
        val like = MenuObject("Like profile").apply {
            setBitmapValue(BitmapFactory.decodeResource(resources, R.drawable.icn_2))
        }
        val addFriend = MenuObject("Add to friends").apply {
            drawable = BitmapDrawable(
                    resources,
                    BitmapFactory.decodeResource(resources, R.drawable.icn_3)
            )
        }
        val addFavorite = MenuObject("Add to favorites").apply {
            setResourceValue(R.drawable.icn_4)
        }
        val block = MenuObject("Block user").apply { setResourceValue(R.drawable.icn_5) }

        add(close)
        add(send)
        add(like)
        add(addFriend)
        add(addFavorite)
        add(block)
    }

    private fun showContextMenuDialogFragment() {
        if (supportFragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
            contextMenuDialogFragment.show(supportFragmentManager, ContextMenuDialogFragment.TAG)
        }
    }
}
