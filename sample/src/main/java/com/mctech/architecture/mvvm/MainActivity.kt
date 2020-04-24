package com.mctech.architecture.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mctech.architecture.mvvm.presentation.view.ImageListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.containerFragment, ImageListFragment())
            .commit()
    }
}
