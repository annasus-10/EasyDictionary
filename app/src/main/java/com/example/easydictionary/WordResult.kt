package com.example.easydictionary

data class WordResult(
    val word: String,
    val pronunciation: Pronunciation,
    val results: List<Result>
)

data class Result(
    val definition: String,
    val partOfSpeech: String,
    val synonyms: List<String>?,
    val examples: List<String>?
)

data class Pronunciation(
    val all: String
)
