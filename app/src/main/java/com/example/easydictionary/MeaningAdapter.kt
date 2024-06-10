package com.example.easydictionary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.easydictionary.databinding.MeaningRecyclerRowBinding

class MeaningAdapter(private var meaningList: List<Result>) : RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder>() {

    class MeaningViewHolder(private val binding: MeaningRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.partOfSpeechTextview.text = result.partOfSpeech
            binding.definitionsTextview.text = result.definition

            binding.exampleTitleTextview.visibility = if (result.examples.isNullOrEmpty()) View.GONE else View.VISIBLE
            binding.exampleTextview.visibility = if (result.examples.isNullOrEmpty()) View.GONE else View.VISIBLE
            binding.exampleTextview.text = result.examples?.joinToString("\n") ?: ""


            if (result.synonyms.isNullOrEmpty()) {
                binding.synonymsTitleTextview.visibility = View.GONE
                binding.synonymsTextview.visibility = View.GONE
            } else {
                binding.synonymsTitleTextview.visibility = View.VISIBLE
                binding.synonymsTextview.visibility = View.VISIBLE
                binding.synonymsTextview.text = result.synonyms.joinToString(", ")
            }
        }
    }

    fun updateNewData(newMeaningList: List<Result>) {
        meaningList = newMeaningList.sortedBy { it.partOfSpeech }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val binding = MeaningRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeaningViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return meaningList.size
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        holder.bind(meaningList[position])
    }
}
