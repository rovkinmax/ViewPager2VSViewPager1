package com.osome.viewpager2wrapper

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager2.adapter = Adapterv2(this)
        (viewPager2.getChildAt(0) as RecyclerView).apply {
            setPadding(40, 0, 40, 0)
            clipToPadding = false
        }
        viewPager.adapter = Adapterv1(this)
        showV1.setOnClickListener {
            viewPager.visibility = View.VISIBLE
            viewPager2.visibility = View.GONE
        }

        showV2.setOnClickListener {
            viewPager2.visibility = View.VISIBLE
            viewPager.visibility = View.GONE
        }
    }
}

class Adapterv2(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val colors = arrayOf(Color.BLUE, Color.CYAN, Color.GREEN)
    override fun getItemCount(): Int {
        return colors.size
    }

    override fun createFragment(position: Int): Fragment {
        return MyFragment.newInstance(colors[position])
    }
}

@Suppress("DEPRECATION")
class Adapterv1(fragmentActivity: FragmentActivity) :
    FragmentStatePagerAdapter(fragmentActivity.supportFragmentManager) {
    private val colors = arrayOf(Color.BLUE, Color.CYAN, Color.GREEN)

    override fun getItem(position: Int): Fragment {
        return MyFragment.newInstance(colors[position])
    }

    override fun getCount(): Int = colors.size

    override fun getPageWidth(position: Int): Float {
        return if (position != 1) 0.95F
        else super.getPageWidth(position)
    }
}

class MyFragment : Fragment() {
    companion object {
        private const val KEY_COLOR = "COLOR"
        fun newInstance(color: Int): MyFragment {
            return MyFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_COLOR, color)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return View(context).apply {
            setBackgroundColor(arguments!!.getInt(KEY_COLOR))
        }
    }
}
