package com.example.android.unscramble.ui.game

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModel

private const val TAG = "GameViewModel"

class GameViewModel : ViewModel() {
    private var _score = 0
    private var _currentWordCount = 0
    private var _currentScrambledWord: String = "test"
    private val _wordList = mutableListOf<String>()
    private var _currentScrambleWordLiveData: MutableLiveData<String> = MutableLiveData()
    private var _currentWord = "test"

    private var _currentWordCountLiveData: MutableLiveData<Int> = MutableLiveData()
    private var _totalWordLiveData: MutableLiveData<Int> = MutableLiveData()

    val score
        get() = _score
    val currentScrambledWord: String
        get() = _currentScrambledWord

    val currentScrambleWordLiveData: LiveData<String>
        get() = _currentScrambleWordLiveData

    val currentWordCountLiveData: LiveData<Int>
        get() = _currentWordCountLiveData

    val totalWordLiveData: LiveData<Int>
        get() = _totalWordLiveData


    init {
        _currentWordCountLiveData.value = 0
        _totalWordLiveData.value = MAX_NO_OF_WORDS
        getNextWord()
    }

    fun getNextWord() {
        val allWords = allWordsList
        _currentWord = allWords.random()

        if (_wordList.contains(_currentWord)) {
            getNextWord()
        }

        val arrayLetters = _currentWord.toCharArray()
        arrayLetters.shuffle()

        _currentScrambledWord = String(arrayLetters)
        _currentScrambleWordLiveData.value = _currentScrambledWord
        _currentWordCount++
        _currentWordCountLiveData.value = _currentWordCount
        _wordList.add(_currentWord)
    }

    fun skipWord(): Boolean {
        if (_currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            return true
        }
        return false
    }

    fun checkPlayerWord(playerWord: String): Int {
        if (_currentWord.equals(playerWord)) {

            if (_currentWordCount < MAX_NO_OF_WORDS) {
                _score += SCORE_INCREASE
                getNextWord()
            } else if (_currentWordCount == MAX_NO_OF_WORDS) {
                _score += SCORE_INCREASE
                // Tăng thêm để từ lần thứ 10 sẽ tăng lên 11 và nhảy xuống false
                _currentWordCount++
            } else {
                return -1
            }
            return 1
        } else return 0
    }

}