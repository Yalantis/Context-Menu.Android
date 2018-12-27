package com.yalantis.contextmenu.lib

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.DialogFragment
import android.view.*
import com.yalantis.contextmenu.lib.interfaces.OnItemClickListener
import com.yalantis.contextmenu.lib.interfaces.OnItemLongClickListener
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener
import kotlinx.android.synthetic.main.fragment_menu.*

class ContextMenuDialogFragment : DialogFragment() {

    private lateinit var menuParams: MenuParams
    private lateinit var dropDownMenuAdapter: MenuAdapter

    private var menuItemClickListener: OnMenuItemClickListener? = null
    private var menuItemLongClickListener: OnMenuItemLongClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.MenuFragmentStyle)
        menuParams = arguments?.getParcelable(ARGS_MENU_PARAMS) as? MenuParams ?: MenuParams()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_menu, container, false)?.apply {
        fitsSystemWindows = menuParams.isFitsSystemWindow
        (this as ViewGroup).clipToPadding = menuParams.isClipToPadding
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDropDownMenuAdapter()

        Handler().postDelayed({
            dropDownMenuAdapter.menuToggle()
        }, menuParams.animationDelay.toLong())

        if (menuParams.isClosableOutside) {
            wrapperView.rootRelativeLayout.setOnClickListener {
                if (isAdded) {
                    dismissAllowingStateLoss()
                }
            }
        }

        if (!menuParams.isOnTheEndSide) {
            wrapperView.showOnTheStartSide()
        }
    }

    fun setItemClickListener(itemClickListener: OnMenuItemClickListener) {
        this.menuItemClickListener = itemClickListener
    }

    fun setItemLongClickListener(itemLongClickListener: OnMenuItemLongClickListener) {
        this.menuItemLongClickListener = itemLongClickListener
    }

    private fun initDropDownMenuAdapter() {
        activity?.let {
            dropDownMenuAdapter = MenuAdapter(
                    it,
                    wrapperView.wrapperButtons,
                    wrapperView.wrapperText,
                    menuParams.menuObjects,
                    menuParams.actionBarSize,
                    menuParams.isOnTheEndSide
            ).apply {
                setAnimationDuration(menuParams.animationDuration)

                setOnItemClickListener(object : OnItemClickListener {
                    override fun onClick(view: View) {
                        val position = (view.parent as ViewGroup).indexOfChild(view)
                        menuItemClickListener?.onMenuItemClick(view, position)
                        close()
                    }
                })

                setOnItemLongClickListener(object : OnItemLongClickListener {
                    override fun onLongClick(view: View) {
                        val position = (view.parent as ViewGroup).indexOfChild(view)
                        menuItemLongClickListener?.onMenuItemLongClick(view, position)
                        close()
                    }
                })
            }
        }
    }

    private fun close() {
        Handler().postDelayed({
            dismissAllowingStateLoss()
        }, menuParams.animationDelay.toLong())
    }

    companion object {

        const val TAG = "ContextMenuDialogFragment"
        private const val ARGS_MENU_PARAMS = "menuParams"

        @JvmStatic
        fun newInstance(menuParams: MenuParams): ContextMenuDialogFragment =
                ContextMenuDialogFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARGS_MENU_PARAMS, menuParams)
                    }
                }
    }
}