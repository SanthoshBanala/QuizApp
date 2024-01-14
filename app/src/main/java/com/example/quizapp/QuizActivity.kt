package com.example.quizapp

import android.app.AlertDialog
import android.content.IntentSender.OnFinished
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import com.example.quizapp.databinding.ActivityQuizBinding
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.databinding.ScoreDialogueBinding

class QuizActivity : AppCompatActivity(), View.OnClickListener {
    companion object{
        var questionModelList : List<Questionmodel> = listOf()
        var time:String=""
    }
    lateinit var binding: ActivityQuizBinding
    var currentQuestionIndex =0;
    var selectedAnswer=""
    var score =0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btn0.setOnClickListener(this@QuizActivity)
            btn1.setOnClickListener(this@QuizActivity)
            btn2.setOnClickListener(this@QuizActivity)
            btn3.setOnClickListener(this@QuizActivity)
            nextBtn.setOnClickListener(this@QuizActivity)
        }

        loadQuestion()
        startTimer()
    }

    private fun startTimer() {
        val totalTimeInMS = time.toInt()*60*1000L
        object:CountDownTimer(totalTimeInMS,1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                binding.timerIndicatorTextview.text =
                    String.format("%02d:%02d", minutes, remainingSeconds)
            }

            override fun onFinish() {
                TODO("Not yet implemented")
            }
        }.start()
    }

    private fun loadQuestion(){
        selectedAnswer=""
        if(currentQuestionIndex== questionModelList.size){
            finishQuiz()
            return
        }
        binding.apply {
            questionIndicatorTextview.text = "Question ${currentQuestionIndex+1}/ ${questionModelList.size}"
            questionProgressIndicator.progress = (currentQuestionIndex.toFloat()/ questionModelList.size.toFloat()*100).toInt()
            questionTextView.text = questionModelList[currentQuestionIndex].question
            btn0.text = questionModelList[currentQuestionIndex].options[0]
            btn1.text = questionModelList[currentQuestionIndex].options[1]
            btn2.text = questionModelList[currentQuestionIndex].options[2]
            btn3.text = questionModelList[currentQuestionIndex].options[3]

        }
    }

    private fun finishQuiz() {
    val totalQuestions = questionModelList.size
        val percentage = ((score.toFloat()/totalQuestions.toFloat())*100).toInt()

        val dialogueBinding=ScoreDialogueBinding.inflate(layoutInflater)
        dialogueBinding.apply {
            scoreProgressIndicator.progress=percentage
            scoreProgressText.text="$percentage %"
            if(percentage>60){
                scoreTitle.text="Congrats! You have passed"
                scoreTitle.setTextColor(Color.BLUE)
            }
            else{
                scoreTitle.text="Oops! You have an opportunity to revise"
                scoreTitle.setTextColor(Color.RED)
            }
            scoreSubtitle.text ="$score answers are correct out of $totalQuestions questions"
            finishBtn.setOnClickListener{
                finish()
            }
        }
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setView(dialogueBinding.root)
            .setCancelable(false)
            .show()
    }

    override fun onClick(view: View?) {
        binding.apply {
            btn0.setBackgroundColor(getColor(R.color.grey))
            btn1.setBackgroundColor(getColor(R.color.grey))
            btn2.setBackgroundColor(getColor(R.color.grey))
            btn3.setBackgroundColor(getColor(R.color.grey))
        }
        val clickedBtn = view as Button
        if(clickedBtn.id==R.id.next_btn){
            if(selectedAnswer== questionModelList[currentQuestionIndex].correct){
                score++
            }
            currentQuestionIndex++
            loadQuestion()
        }
        else{
            clickedBtn.setBackgroundColor(getColor(R.color.orange))
            selectedAnswer=clickedBtn.text.toString()
        }
    }


}