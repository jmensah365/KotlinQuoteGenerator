package com.example.quotegenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.quotegenerator.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getQuote()

        binding.nextBtn.setOnClickListener {
            getQuote()
        }
    }
    private fun getQuote() {
        setInProgress(true)

        GlobalScope.launch {
            try {
                val response = RetroFitInstance.quoteApi.getRandomQuote()
                runOnUiThread{
                    setInProgress(false)
                    response.body()?.first()?.let{
                        setUI(it)
                    }
                }
            } catch (e : Exception) {
                runOnUiThread {
                    setInProgress(false)
                    Toast.makeText(applicationContext, "Check Internet Connection", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun setUI(quoteModel: QuoteModel){
        binding.quoteTv.text = quoteModel.q
        binding.authorTv.text = quoteModel.a
    }
    private fun setInProgress(inProgress: Boolean) {
        if(inProgress) {
            binding.progressBar.visibility = View.VISIBLE
            binding.nextBtn.visibility = View.GONE
        } else{
            binding.progressBar.visibility = View.GONE
            binding.nextBtn.visibility = View.VISIBLE
        }
    }
}