package org.gdsc_android.picky_panda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import org.gdsc_android.picky_panda.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //타이머가 끝나면 내부 실행
        Handler().postDelayed(Runnable {
            //앱의 MainActivity로 넘어가기
            val i = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(i)
            //현재 액티비티 닫기
            finish()
        }, 3000 ) //3초
    }
}