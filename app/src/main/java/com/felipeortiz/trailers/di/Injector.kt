package com.felipeortiz.trailers.di

import com.felipeortiz.trailers.MovieApplication

class Injector private constructor() {
    companion object {
        fun get(): AppComponent = MovieApplication.get().component
    }
}