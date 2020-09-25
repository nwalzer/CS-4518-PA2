package com.example.basketballcounter

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

private const val TAG = "BasketballCounter"

class BasketballCounter : AppCompatActivity() {
    private val btViewModel: BasketballTeamViewModel by lazy {
        ViewModelProviders.of(this).get(BasketballTeamViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "BasketballCounter instance created")


        //if we have a saved Bundle
        if (savedInstanceState != null) {
            /*

             */

            if(btViewModel.getSize() == 0){ //if there are no games (i.e. loading from destroyed state)
                btViewModel.addNewGame() //add a new game
            }

            btViewModel.setIndex(savedInstanceState.getInt("TEAM_IDX")) //set index
            btViewModel.setPoints(0, savedInstanceState.getInt("TEAM_A_PTS")) //set first team points
            btViewModel.setPoints(1, savedInstanceState.getInt("TEAM_B_PTS")) //set second team points
            //update UI
            updateBothTeams()
        } else {
            //Create a new game
            addNewGame()
        }

        //Assign click listeners to all of the buttons
        findViewById<Button>(R.id.three_point_a)?.setOnClickListener {
            updatePoints( true, 3)
        }

        findViewById<Button>(R.id.three_point_b)?.setOnClickListener {
            updatePoints( false, 3)
        }

        findViewById<Button>(R.id.two_point_a)?.setOnClickListener {
            updatePoints( true, 2)
        }

        findViewById<Button>(R.id.two_point_b)?.setOnClickListener {
            updatePoints( false, 2)
        }

        findViewById<Button>(R.id.free_throw_a)?.setOnClickListener {
            updatePoints( true, 1)
        }

        findViewById<Button>(R.id.free_throw_b)?.setOnClickListener {
            updatePoints( false, 1)
        }

        findViewById<Button>(R.id.reset)?.setOnClickListener {
            resetPoints()
        }

        findViewById<Button>(R.id.add_game)?.setOnClickListener {
            addNewGame()
        }

        findViewById<Button>(R.id.prev_btn)?.setOnClickListener {
            changeGame(-2)
        }

        findViewById<Button>(R.id.next_btn)?.setOnClickListener {
            changeGame(2)
        }
    }

    //Very redundant given the ViewModel, but this will store the current index and currently displayed game's points
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(
            "TEAM_IDX",
            btViewModel.getIndex()
        )

        savedInstanceState.putInt(
            "TEAM_A_PTS",
            btViewModel.getPoints(true, 0)
        )

        savedInstanceState.putInt(
            "TEAM_B_PTS",
            btViewModel.getPoints(false, 0)
        )
        Log.d(TAG, "BasketballCounter index & Team A and B points, saved to Bundle")
    }

    //increments the given team's points by the supplied amount, updates UI
    private fun updatePoints(isA: Boolean, points: Int){
        btViewModel.updatePoints( isA, points)
        updateTeamStanding(isA)
    }

    //sets both team's points to 0, updates UI
    private fun resetPoints(){
        btViewModel.resetPoints()
        updateBothTeams()
    }

    //Adds a new game, changes current game to the new one and updates UI
    private fun addNewGame(){
        btViewModel.addNewGame()
        updateBothTeams()
    }

    //Updates the UI to show the Prev/Next game
    private fun changeGame(amount: Int){
        btViewModel.changeGame(amount)
        updateBothTeams()
    }

    //Changes one team's displayed points
    private fun updateTeamStanding(isA: Boolean){
        if(isA){
            val textView = findViewById<TextView>(R.id.point_label_a)
            textView.text = btViewModel.getPoints(isA).toString()
        } else {
            val textView = findViewById<TextView>(R.id.point_label_b)
            textView.text = btViewModel.getPoints(isA).toString()
        }
    }

    //Changes both team's displayed points
    private fun updateBothTeams(){
        updateTeamStanding(true)
        updateTeamStanding(false)
        findViewById<TextView>(R.id.game_label)?.text = "Game #${(btViewModel.getIndex() / 2) + 1}"
    }
}