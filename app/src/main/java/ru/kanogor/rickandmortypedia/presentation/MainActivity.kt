package ru.kanogor.rickandmortypedia.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.kanogor.rickandmortypedia.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}