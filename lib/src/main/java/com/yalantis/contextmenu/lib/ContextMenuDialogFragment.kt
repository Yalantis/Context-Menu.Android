package com.yalantis.contextmenu.lib

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.yalantis.contextmenu.lib.extensions.backgroundColorAppear
import com.yalantis.contextmenu.lib.extensions.backgroundColorDisappear
import kotlinx.android.synthetic.main.fragment_menu.*

open class ContextMenuDialogFragment : DialogFragment() {

    var menuItemClickListener: (view: View, position: Int) -> Unit = { _, _ -> }
    var menuItemLongClickListener: (view: View, position: Int) -> Unit = { _, _ -> }

    private lateinit var menuParams: MenuParams
    private lateinit var dropDownMenuAdapter: MenuAdapter

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
        }, menuParams.animationDelay)

        wrapperView.apply {
            backgroundColorAppear(menuParams.backgroundColorAnimationDuration)
            show(menuParams.gravity)

            if (menuParams.isClosableOutside) {
                rootRelativeLayout.setOnClickListener {
                    if (isAdded) {
                        dropDownMenuAdapter.closeOutside()
                    }
                }
            }
        }
    }

    private fun initDropDownMenuAdapter() {
        activity?.let {
            dropDownMenuAdapter = MenuAdapter(
                    it,
                    wrapperView.wrapperButtons,
                    wrapperView.wrapperText,
                    menuParams.menuObjects,
                    menuParams.actionBarSize,
                    menuParams.gravity
            ).apply {
                setAnimationDuration(menuParams.animationDuration)

                onCloseOutsideClickListener = { _ ->
                    close()
                }

                onItemClickListener = { view ->
                    val position = (view.parent as ViewGroup).indexOfChild(view)
                    menuItemClickListener(view, position)
                    close()
                }

                onItemLongClickListener = { view ->
                    val position = (view.parent as ViewGroup).indexOfChild(view)
                    menuItemLongClickListener(view, position)
                    close()
                }
            }
        }
    }

    private fun close() {
        wrapperView.backgroundColorDisappear(menuParams.backgroundColorAnimationDuration) {
            Handler().postDelayed({
                dismissAllowingStateLoss()
            }, menuParams.animationDelay)
        }
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