package com.yalantis.contextmenu.sample

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.yalantis.contextmenu.R
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.MenuParams
import kotlinx.android.synthetic.main.toolbar.*

class SampleActivity : AppCompatActivity() {

    private lateinit var contextMenuDialogFragment: ContextMenuDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        initToolbar()
        initMenuFragment()
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

    private fun initToolbar() {
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { onBackPressed() }
        }

        tvToolbarTitle.text = "Samantha"
    }

    /**
     * If you want to change the side you need to add 'gravity' parameter,
     * by default it is MenuGravity.END.
     *
     * For example:
     *
     * MenuParams(
     *     actionBarSize = resources.getDimension(R.dimen.tool_bar_height).toInt(),
     *     menuObjects = getMenuObjects(),
     *     isClosableOutside = false,
     *     gravity = MenuGravity.START
     * )
     */
    private fun initMenuFragment() {
        val menuParams = MenuParams(
                actionBarSize = resources.getDimension(R.dimen.tool_bar_height).toInt(),
                menuObjects = getMenuObjects(),
                isClosableOutside = false
        )

        contextMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams).apply {
            menuItemClickListener = { view, position ->
                Toast.makeText(
                        this@SampleActivity,
                        "Clicked on position: $position",
                        Toast.LENGTH_SHORT
                ).show()
            }
            menuItemLongClickListener = { view, position ->
                Toast.makeText(
                        this@SampleActivity,
                        "Long clicked on position: $position",
                        Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    /**
     * You can use any (drawable, resource, bitmap, color) as image:
     * menuObject.drawable = ...
     * menuObject.setResourceValue(...)
     * menuObject.setBitmapValue(...)
     * menuObject.setColorValue(...)
     *
     * You can set image ScaleType:
     * menuObject.scaleType = ScaleType.FIT_XY
     *
     * You can use any [resource, drawable, color] as background:
     * menuObject.setBgResourceValue(...)
     * menuObject.setBgDrawable(...)
     * menuObject.setBgColorValue(...)
     *
     * You can use any (color) as text color:
     * menuObject.textColor = ...
     *
     * You can set any (color) as divider color:
     * menuObject.dividerColor = ...
     */
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
