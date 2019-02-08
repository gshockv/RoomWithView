package com.gshockv.rws.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.gshockv.rws.R
import com.gshockv.rws.data.Word
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var wordViewModel : WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)

        val recyclerViewWords = findViewById<RecyclerView>(R.id.recyclerViewWords)
        val adapter = WordsListAdapter(this)
        recyclerViewWords.adapter = adapter
        recyclerViewWords.layoutManager = LinearLayoutManager(this)
        recyclerViewWords.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        wordViewModel.allWords.observe(this, Observer { words ->
            words?.let {
                adapter.setWords(it)
            }
        })

        fabAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, EditWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let {
                val word = Word(it.getStringExtra(EditWordActivity.EXTRA_REPLY))
                wordViewModel.insert(word)
            }
        }
    }

    companion object {
        const val newWordActivityRequestCode = 1
    }
}
