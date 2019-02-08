package com.gshockv.rws.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gshockv.rws.R
import com.gshockv.rws.data.Word

class WordsListAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<WordsListAdapter.WordViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private var words = emptyList<Word>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.item_word, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: WordViewHolder, position: Int) {
        viewHolder.bind(words[position])
    }

    override fun getItemCount() = words.size

    internal fun setWords(words: List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(word: Word) {
            val textView = itemView.findViewById<TextView>(R.id.textViewWord)
            textView.setText(word.word)
        }
    }
}
