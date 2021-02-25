package com.example.myapplication

class Recipe {

    var ingredients: String? = null
    var id: Int = 0
    var recipeName: String? = null
    var instructions: String? = null


    constructor(id: Int, recipeName: String, ingredients: String, instructions: String) {
        this.id = id
        this.recipeName = recipeName
        this.ingredients = ingredients
        this.instructions = instructions
    }

    constructor(recipeName: String) {
        this.recipeName = recipeName
    }
}