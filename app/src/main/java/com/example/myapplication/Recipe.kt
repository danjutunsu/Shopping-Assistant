package com.example.myapplication

class Recipe {

    var ingredients: String? = null
    var id: Int = 0
    var recipeName: String? = null

    constructor(id: Int, recipeName: String, ingredients: String) {
        this.id = id
        this.recipeName = recipeName
        this.ingredients = ingredients
    }

    constructor(recipeName: String) {
        this.recipeName = recipeName
    }
}