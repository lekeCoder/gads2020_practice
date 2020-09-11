package com.gads2020.practice.ui.fragments.ratings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gads2020.practice.adapters.RateAdapter
import com.gads2020.practice.databinding.FragmentRecordsBinding

/**
 * A simple [Fragment] subclass.
 * Use the [RatingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RatingFragment : Fragment() {

    private lateinit var mBinding: FragmentRecordsBinding
    private  var type: Int = 0
    private val viewModel: RatingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       type = arguments?.getInt(ARG_SECTION_NUMBER) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentRecordsBinding.inflate(inflater)

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding.lifecycleOwner = this
        mBinding.model = viewModel
        mBinding.recyclerView.adapter = RateAdapter()

       if(type == 0) viewModel.fetchTopByHours() else viewModel.fetchTopSkill()

        viewModel.learners().observe(viewLifecycleOwner, {
            it?.let { records ->
                (mBinding.recyclerView.adapter as RateAdapter).addData(records)
            }
        })
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment RatingFragment.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int) = RatingFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_SECTION_NUMBER, sectionNumber)
            }
        }
    }
}