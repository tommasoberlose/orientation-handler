package com.tommasoberlose.orientationhandler

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import android.support.v4.view.ViewCompat.setRotation
import android.view.Surface.ROTATION_270
import android.view.Surface.ROTATION_180
import android.view.Surface.ROTATION_90
import android.view.Surface.ROTATION_0
import android.hardware.SensorManager
import android.hardware.Sensor.TYPE_ACCELEROMETER
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.os.Bundle
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.view.Surface


class OrientationEventListenerService : Service() {

  override fun onBind(intent: Intent): IBinder {
    TODO("Return the communication channel to the service.")
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    Log.i("ORI_H", "START COMMAND")
    return super.onStartCommand(intent, flags, startId)
  }

  override fun onCreate() {
    Log.i("ORI_H", "ON CREATE")
    super.onCreate()

    // Get sensor manager
    mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    // Get the default sensor of specified type
    mOrientation = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


    if (mOrientation != null) {
      mSensorManager!!.registerListener(mOrientationSensorListener, mOrientation, SensorManager.SENSOR_DELAY_GAME)
    }
  }

  override fun onDestroy() {
    Log.i("ORI_H", "ON DESTROY")
    if (mOrientation != null) {
      mSensorManager!!.unregisterListener(mOrientationSensorListener)
    }
    super.onDestroy()
  }

  override fun onConfigurationChanged(newConfig: Configuration?) {
    super.onConfigurationChanged(newConfig)
  }

  private var mSensorManager: SensorManager? = null
  private var mOrientation: Sensor? = null

  var value_0 = -10000f
  var value_1 = -10000f

  private val mOrientationSensorListener = object : SensorEventListener {
    internal var orientation = -1

    override fun onSensorChanged(event: SensorEvent) {
      var value: Int
      if (value_0 == event.values[0] && value_1 == event.values[1]) {
        return
      }
      Log.d("ORI_H", "values:" + event.values[0]+", "+event.values[1])
      if (event.values[1] > 0 && event.values[0] == 0f) {
        value = Surface.ROTATION_0//portrait
        if (orientation != value) {
          Log.d("ORI_H", "portrait  + update")
        }
        orientation = value
        Log.d("ORI_H", "portrait ")
      }


      if (event.values[1] < 0 && event.values[0] == 0f) {
        value = Surface.ROTATION_180//portrait reverse
        if (orientation != value) {
          Log.d("ORI_H", "portrait reverse + update")
        }
        orientation = value
        Log.d("ORI_H", "portrait reverse")
      }

      if (event.values[0] > 0 && event.values[1] == 0f) {
        value = Surface.ROTATION_90//portrait reverse
        if (orientation != value) {
          Log.d("ORI_H", "landscape  + update")
        }
        orientation = value
        Log.d("ORI_H", "landscape")
      }

      if (event.values[0] < 0 && event.values[1] == 0f) {
        value = Surface.ROTATION_270//portrait reverse
        if (orientation != value) {
          Log.d("ORI_H", "landscape  + update")
        }
        orientation = value
        Log.d("ORI_H", "landscape reverse")
      }

      value_0 = event.values[0]
      value_1 = event.values[1]
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }
  }
}
