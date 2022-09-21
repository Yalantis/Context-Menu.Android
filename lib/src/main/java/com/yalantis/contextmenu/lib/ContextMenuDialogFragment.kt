package com.yalantis.contextmenu.lib

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.yalantis.contextmenu.lib.databinding.FragmentMenuBinding
import com.yalantis.contextmenu.lib.extensions.backgroundColorAppear
import com.yalantis.contextmenu.lib.extensions.backgroundColorDisappear

open class ContextMenuDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentMenuBinding
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
    ): View? {
        binding = FragmentMenuBinding.inflate(layoutInflater, container, false)
        binding.root.apply {
            fitsSystemWindows = menuParams.isFitsSystemWindow
            (this as ViewGroup).clipToPadding = menuParams.isClipToPadding
            dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDropDownMenuAdapter()

        Handler().postDelayed({
            dropDownMenuAdapter.menuToggle()
        }, menuParams.animationDelay)

        binding.wrapperView.apply {
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
                binding.wrapperView.wrapperButtons,
                binding.wrapperView.wrapperText,
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
        binding.wrapperView.backgroundColorDisappear(menuParams.backgroundColorAnimationDuration) {
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