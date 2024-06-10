package com.example.easydictionary

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easydictionary.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var adapter: MeaningAdapter

    private val searchHistory = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadSearchHistory()
        updateSearchHistory()

        binding.searchBtn.setOnClickListener {
            val word = binding.searchInput.text.toString()
            if (word.isNotEmpty()) {
                if (NetworkUtils.isNetworkAvailable(this)) {
                    getMeaning(word)
                    addToSearchHistory(word)
                    binding.searchHistoryTextview.visibility = View.GONE
                } else {
                    showAlert("No Internet Connection", "Please check your internet connection and try again.")
                }
            }
        }

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Clear previous results when user starts typing
                clearPreviousResults()
                updateSearchHistory()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        adapter = MeaningAdapter(emptyList())
        binding.meaningRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.meaningRecyclerView.adapter = adapter

        updateSearchHistory()
    }

    private fun getMeaning(word: String) {
        setInProgress(true)
        lifecycleScope.launch {
            try {
                val response = WordsApi.service.getMeaning(word)
                if (response.isSuccessful) {
                    val wordResult = response.body()
                    if (wordResult != null) {
                        Log.i("Response from API", wordResult.toString())
                        Log.i("Word", wordResult.word)
                        Log.i("Pronunciation", wordResult.pronunciation.all)
                        val sortedResults = wordResult.results.sortedBy { it.partOfSpeech }
                        setUI(wordResult, sortedResults)
                    } else {
                        setErrorMessage("Word not found in the dictionary.")
                    }
                } else {
                    Log.e("API Error", "Error: ${response.code()} ${response.message()}")
                    setErrorMessage("Failed to retrieve word meaning.")
                }
            } catch (e: Exception) {
                Log.e("API Error", "Exception: $e")
                setErrorMessage("An error occurred: $e")
            } finally {
                setInProgress(false)
            }
        }
    }

    private fun setErrorMessage(message: String) {
        showAlert("Error", message)
    }

    private fun showAlert(title: String, message: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun setUI(response: WordResult, sortedResults: List<Result>) {
        binding.wordTextview.text = response.word
        binding.pronunciationTextview.text = response.pronunciation.all
        val adapter = MeaningAdapter(sortedResults)
        binding.meaningRecyclerView.adapter = adapter
    }

    private fun clearPreviousResults() {
        binding.wordTextview.text = ""
        binding.pronunciationTextview.text = ""
        val adapter = MeaningAdapter(emptyList())
        binding.meaningRecyclerView.adapter = adapter
    }

    private fun setInProgress(inProgress: Boolean) {
        if (inProgress) {
            binding.searchBtn.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.searchBtn.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun updateSearchHistory() {
        if (searchHistory.isEmpty()) {
            binding.searchHistoryTextview.visibility = View.GONE
        } else {
            binding.searchHistoryTextview.visibility = View.VISIBLE
            binding.searchHistoryTextview.text = "Search History:\n" + searchHistory.joinToString("\n")
        }
    }

    private fun addToSearchHistory(word: String) {
        if (!searchHistory.contains(word)) {
            searchHistory.add(word)
            saveSearchHistory()
            updateSearchHistory()
        }
    }

    private fun saveSearchHistory() {
        val sharedPreferences = getSharedPreferences("search_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putStringSet("search_history", searchHistory.toSet())
        editor.apply()
    }

    private fun loadSearchHistory() {
        val sharedPreferences = getSharedPreferences("search_prefs", MODE_PRIVATE)
        val savedHistory = sharedPreferences.getStringSet("search_history", setOf())
        searchHistory.clear()
        searchHistory.addAll(savedHistory ?: setOf())
    }
}
