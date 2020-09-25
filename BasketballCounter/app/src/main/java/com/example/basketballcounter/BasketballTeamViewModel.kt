package com.example.basketballcounter

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "BasketballCounter"

class BasketballTeamViewModel : ViewModel() {
    private var teams: MutableList<BasketballTeam> = ArrayList()
    private var index: Int = 0

    init {
        Log.d(TAG, "BasketballTeamViewModel instance created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "BasketballTeamViewModel instance about to be destroyed")
    }

    //sets a valid index
    fun setIndex(value: Int){
        index = when {
            value > teams.size-2 -> {
                teams.size-2
            }
            value % 2 != 0 -> {
                0;
            }
            else -> {
                value
            }
        }
    }

    //retrieves the current index
    fun getIndex(): Int{
        return index
    }

    //retrieves the current number of team sbeing tracked
    fun getSize(): Int{
        return teams.size
    }

    //sets the points at the given index to the supplied amount
    fun setPoints(idx: Int, points: Int){
        teams[idx].reset()
        teams[idx].increasePoints(points)
    }

    //retrieves the points of the current A or B team
    fun getPoints(isA: Boolean, idx: Int = index): Int{
        return if(isA){
            teams[idx].points
        } else {
            teams[idx+1].points
        }
    }

    //increases the number of points for either the A or B team by the supplied amount
    fun updatePoints(isA: Boolean, numPoints: Int){
        if(isA){
            teams[index].increasePoints(numPoints)
        } else {
            teams[index+1].increasePoints(numPoints)
        }
    }

    //resets both team's points to 0
    fun resetPoints(){
        teams[index].reset()
        teams[index+1].reset()
    }

    //changes the index to the Prev/Next game's teams
    fun changeGame(amount: Int){
        index = if(index == 0 && amount == -2){
            teams.size-2
        } else {
            (index+amount)%teams.size
        }
    }

    //Adds a new game, sets index to the new game
    fun addNewGame(){
        teams.add(BasketballTeam(0))
        teams.add(BasketballTeam(0))
        index = teams.size-2
    }
}