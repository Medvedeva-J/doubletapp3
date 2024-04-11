package com.example.doubletapp3

class Model {
    private var title: String? = null
    private var description: String? = null

    fun loadDataAsync(id: Int) {
        println("async loading")
    }

    fun getTitle(): String? {
        return title
    }

    fun getDescription(): String? {
        return description
    }

    fun setTitle(value: String?) {
        title = value
    }

    fun setDescription(value: String?) {
        description = value
    }
}