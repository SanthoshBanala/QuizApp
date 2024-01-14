package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var quizModelList:MutableList<QuizModel>
    lateinit var adapter: QuizListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizModelList = mutableListOf()
        getDataFromFirebase()
    }
    private fun setupRecyclerView(){
    adapter= QuizListAdapter(quizModelList)
        binding.recyclerView.layoutManager=LinearLayoutManager(this)

        binding.recyclerView.adapter=adapter
    }
    private fun getDataFromFirebase(){
        //val ListQuestionModel = mutableListOf<Questionmodel>()
        //ListQuestionModel.add(Questionmodel("What is Android Language", mutableListOf("Language","OS","Protocol","None"),"Language"))
        //ListQuestionModel.add(Questionmodel("Who wons Android?", mutableListOf("Apple","Google","Microsoft","HP"),"Google"))
        //ListQuestionModel.add(Questionmodel("Which assistant is used in Andoroid?", mutableListOf("Siri","Cortana","Google Assistant","Nani"),"Google Assistant"))
        //quizModelList.add(QuizModel("1","Aptitude","very essential","10", ListQuestionModel))
        //quizModelList.add(QuizModel("2","English","next essential","10", LisQuestionModel))
        //quizModelList.add(QuizModel("3","GS","next essential","10", LisQuestionModel))
        FirebaseDatabase.getInstance().reference
            .get()
            .addOnSuccessListener {dataSnapshot->
                if(dataSnapshot.exists()){
                    for(snapshot in dataSnapshot.children){
                        val quizModel = snapshot.getValue(QuizModel::class.java)
                        if (quizModel != null) {
                            quizModelList.add(quizModel)
                        }
                    }
                }
                setupRecyclerView()
            }

    }
}