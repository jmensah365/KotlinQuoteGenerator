package com.example.quotegenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.quotegenerator.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    //creating binding for variables and views
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //assigning views with binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getQuote()
        //adding next btn functionality
        binding.nextBtn.setOnClickListener {
            getQuote()
        }
    }
    //calling the Quote Api
    private fun getQuote() {
        setInProgress(true)

        GlobalScope.launch {
            try {
                //grabbing quote and author from response and setting the UI with those values
                val response = RetroFitInstance.quoteApi.getRandomQuote()
                runOnUiThread{
                    setInProgress(false)
                    response.body()?.first()?.let{
                        setUI(it)
                    }
                }
                //if not internet connection throw error
            } catch (e : Exception) {
                runOnUiThread {
                    setInProgress(false)
                    Toast.makeText(applicationContext, "Check Internet Connection", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    //sets the quote and author values
    private fun setUI(quoteModel: QuoteModel){
        binding.quoteTv.text = quoteModel.q
        binding.authorTv.text = quoteModel.a
    }
    //checking to see if quotes are visible
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
