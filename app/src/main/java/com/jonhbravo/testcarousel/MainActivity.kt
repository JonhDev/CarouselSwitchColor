package com.jonhbravo.testcarousel

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.jonhbravo.testcarousel.databinding.ActivityMainBinding
import com.xwray.groupie.GroupieAdapter

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val groupieAdapter = GroupieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpRecyclerView()
        setUpContent()
        setUpListeners()
    }

    private val colors = listOf(
        Color.parseColor("#e2e2ff"),
        Color.parseColor("#c1fdd2"),
        Color.parseColor("#ffdfdf")
    )

    private fun setUpContent() {
        groupieAdapter.update(listOf(ItemView(), ItemView(), ItemView()))
        binding.cardViewBackground.setCardBackgroundColor(colors.first())
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewContent.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = groupieAdapter
            PagerSnapHelper().attachToRecyclerView(this)
        }
    }

    private fun setUpListeners() {
        binding.recyclerViewContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.i("SCROLL STATE", newState.toString())
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val offset = recyclerView.computeHorizontalScrollOffset()
                val extent = recyclerView.computeHorizontalScrollExtent()
                val currentItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                val visibleOffset = currentItemPosition * extent

                Log.i(
                    "SCROLL VALUES",
                    "offset: $offset, extent: $extent, visibleOffset: $visibleOffset, item: $currentItemPosition"
                )
                val percentage = (1f * (offset - visibleOffset) / extent)
                val color = when {
                    offset == visibleOffset -> colors[currentItemPosition]
                    offset > visibleOffset -> ColorUtils.blendARGB(
                        colors[currentItemPosition],
                        colors[currentItemPosition + 1],
                        percentage
                    )
                    offset < visibleOffset -> ColorUtils.blendARGB(
                        colors[currentItemPosition - 1],
                        colors[currentItemPosition],
                        percentage
                    )
                    else -> null
                }
                color?.let {
                    binding.cardViewBackground.setCardBackgroundColor(it)
                }
                Log.i("PERCENTAGE", "$percentage")
            }
        })
    }
}