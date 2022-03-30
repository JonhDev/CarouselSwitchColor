package com.jonhbravo.testcarousel

import android.view.View
import com.jonhbravo.testcarousel.databinding.ViewItemBinding
import com.xwray.groupie.viewbinding.BindableItem

class ItemView : BindableItem<ViewItemBinding>() {

    override fun bind(viewBinding: ViewItemBinding, position: Int) = Unit

    override fun getLayout() = R.layout.view_item

    override fun initializeViewBinding(view: View) = ViewItemBinding.bind(view)
}