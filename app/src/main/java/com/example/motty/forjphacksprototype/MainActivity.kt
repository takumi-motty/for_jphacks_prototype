package com.example.motty.forjphacksprototype

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() , SensorEventListener {
    private var mManager: SensorManager by Delegates.notNull<SensorManager>()
    private var mSensor: Sensor by Delegates.notNull<Sensor>()
    private var gManager: SensorManager by Delegates.notNull<SensorManager>()
    private var gSensor: Sensor by Delegates.notNull<Sensor>()

    fun getToday(): String {
        val date = Date()
        val format = SimpleDateFormat("yyyy/MM/dd/HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }

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

        Log.d("path:", externalStoragePath())
        Log.d("Multi", " getFilesDir(): " + getFilesDir())

        val path = getFilesDir()

        val fileName = "test.csv"
        val file = File("/data/user/0/com.example.motty.getlabeltimeapplication/files", fileName)

        //センサーマネージャーを取得する
        mManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //加速度計のセンサーを取得する
        //その他のセンサーを取得する場合には引数を違うものに変更する
        mSensor = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        //ジャイロセンサ
        gManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        gSensor = gManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)


        boardButton.setOnClickListener {
            timeText.setText(getToday())
            labelsText.setText(getString(R.string.board))
            var text = getString(R.string.board)+ ","+getToday()
            file.writeText(text)
        }
        sitdownButton.setOnClickListener {
            timeText.setText(getToday())
            labelsText.setText(getString(R.string.sitdown))
            var text = getString(R.string.sitdown)+ ","+getToday()
            file.writeText(text)
        }

        getoffButton.setOnClickListener {
            timeText.setText(getToday())
            labelsText.setText(getString(R.string.getoff))
            var text = getString(R.string.getoff)+ ","+getToday()
            file.writeText(text)
        }

        readButton.setOnClickListener {
            if (file.exists()) {
                val contents = file.bufferedReader().use(BufferedReader::readText)
                Log.d("data", contents)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
    //アクティビティが閉じられたときにリスナーを解除する
    override fun onPause() {
        super.onPause()
        //リスナーを解除しないとバックグラウンドにいるとき常にコールバックされ続ける
        mManager.unregisterListener(this)
        gManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        //リスナーとセンサーオブジェクトを渡す
        //第一引数はインターフェースを継承したクラス、今回はthis
        //第二引数は取得したセンサーオブジェクト
        //第三引数は更新頻度 UIはUI表示向き、FASTはできるだけ早く、GAMEはゲーム向き
        mManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI)
        gManager.registerListener(this, gSensor, SensorManager.SENSOR_DELAY_UI)
    }
}