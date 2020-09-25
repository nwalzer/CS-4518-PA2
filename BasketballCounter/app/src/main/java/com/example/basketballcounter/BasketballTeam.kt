package com.example.basketballcounter

//A simple data class for storing and updating points
data class BasketballTeam (var points: Int){

    fun increasePoints(amount: Int){
        points += amount;
    }

    fun reset(){
        points = 0;
    }
}