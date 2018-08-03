package com.tommasoberlose.orientationhandler

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    action_start.setOnClickListener {
      startService(Intent(this, OrientationEventListenerService::class.java))
    }

    action_stop.setOnClickListener {
      stopService(Intent(this, OrientationEventListenerService::class.java))
    }
  }
}
