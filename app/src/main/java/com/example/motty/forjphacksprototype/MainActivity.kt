package com.example.motty.forjphacksprototype

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Environment
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() , SensorEventListener {
    private var mManager: SensorManager by Delegates.notNull<SensorManager>()
    private var mSensor: Sensor by Delegates.notNull<Sensor>()
    private var gManager: SensorManager by Delegates.notNull<SensorManager>()
    private var gSensor: Sensor by Delegates.notNull<Sensor>()

    private fun externalStoragePath(): String {
        return Environment.getExternalStorageDirectory().absolutePath
    }

    override fun onSensorChanged(event: SensorEvent) {
        var strb: StringBuffer = StringBuffer()
        var strb2: StringBuffer = StringBuffer()

        var sensor: Sensor = event.sensor

        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            strb.append("x軸")
            strb.append(event.values[0])
            strb.append("　y軸")
            strb.append(event.values[1])
            strb.append("　z軸")
            strb.append(event.values[2])
            accelText.setText(strb.toString())
            if (event.values[0] >= 9) gravityText.setText("←")
            if (event.values[0] <= -9) gravityText.setText("→")
            if (event.values[1] >= 9) gravityText.setText("↓")
            if (event.values[1] <= -9) gravityText.setText("↑")
            if (event.values[2] >= 9) gravityText.setText("下")
            if (event.values[2] <= -9) gravityText.setText("上")
        } else if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
            strb2.append("x軸")
            strb2.append(event.values[0])
            strb2.append("　y軸")
            strb2.append(event.values[1])
            strb2.append("　z軸")
            strb2.append(event.values[2])
            gyroText.setText(strb2.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //センサーマネージャーを取得する
        mManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //加速度計のセンサーを取得する
        mSensor = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        //ジャイロセンサ
        gManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gSensor = gManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)


        action1Button.setOnTouchListener { _, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // ボタンが押し込まれたとき
                    mManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI)
                    gManager.registerListener(this, gSensor, SensorManager.SENSOR_DELAY_UI)
                    labelsText.setText("押しているよ")
                }
                MotionEvent.ACTION_UP -> {
                    // ボタンが離されたとき
                    mManager.unregisterListener(this)
                    gManager.unregisterListener(this)
                    labelsText.setText("離したよ")
                    gravityText.setText("重力リセット")
                }
            }
            false
        }
        action2Button.setOnTouchListener { _, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // ボタンが押し込まれたとき
                    mManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI)
                    gManager.registerListener(this, gSensor, SensorManager.SENSOR_DELAY_UI)
                    labelsText.setText("押しているよ")
                }
                MotionEvent.ACTION_UP -> {
                    // ボタンが離されたとき
                    mManager.unregisterListener(this)
                    gManager.unregisterListener(this)
                    labelsText.setText("離したよ")
                    gravityText.setText("重力リセット")
                }
            }
            false
        }
        action3Button.setOnTouchListener { _, event ->
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // ボタンが押し込まれたとき
                    mManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI)
                    gManager.registerListener(this, gSensor, SensorManager.SENSOR_DELAY_UI)
                    labelsText.setText("押しているよ")
                }
                MotionEvent.ACTION_UP -> {
                    // ボタンが離されたとき
                    mManager.unregisterListener(this)
                    gManager.unregisterListener(this)
                    labelsText.setText("離したよ")
                    gravityText.setText("重力リセット")
                }
            }
            false
        }
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}