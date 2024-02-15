package org.gdsc_android.picky_panda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import org.gdsc_android.picky_panda.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val RequestCode_SIGN_IN = 123 //임의의 값
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //googleSignInOptions 설정
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("444216138764-3ai31eo0j37jpbs8if4srvklulnr2bq1.apps.googleusercontent.com")
            .build()
        
        //googleSignInClient. 로그인 창 표시
        val mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
         
        binding.googleSignInButton.setOnClickListener{
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RequestCode_SIGN_IN)
         }
    }
    
    override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        
        //google 로그인 결과 처리
        if (requestCode == RequestCode_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: com.google.android.gms.tasks.Task<GoogleSignInAccount>) {
        try{
            //google 로그인 성공 시
            val account = completedTask.getResult(ApiException::class.java)

            //account를 사용해 로그인 또는 회원가입 로직 수행
            //여기에 로그인 또는 회원가입 성공 시의 작업을 추가

            //로그인 성공 후 메인 화면으로 이동
            startActivity(Intent(this, HomeFragment::class.java))
            finish()
        } catch (e: ApiException){
            //google 로그인 실패했을 때 작업 추가
        }
    }


}