package com.gads2020.practice.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gads2020.practice.databinding.SkillzIqItemBinding
import com.gads2020.practice.models.Learner



class RateAdapter : RecyclerView.Adapter<RateAdapter.RecordViewHolder>() {

    private var learners = mutableListOf<Learner>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder =
        RecordViewHolder(
            SkillzIqItemBinding.inflate(LayoutInflater.from(parent.context))
        )

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(learners[position])
    }

    override fun getItemCount(): Int = learners.size

    fun addData(learners: List<Learner>) {
        this.learners.addAll(learners)
        notifyDataSetChanged()
    }

    class RecordViewHolder(private val binding: SkillzIqItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(learner: Learner) {
            binding.learner = learner
        }
    }

}