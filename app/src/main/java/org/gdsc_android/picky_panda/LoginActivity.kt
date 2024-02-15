package org.gdsc_android.picky_panda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import org.gdsc_android.picky_panda.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val RequestCode_SIGN_IN = 3253 //임의의 값
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //googleSignInOptions 설정
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("444216138764-t11tjjmsic0fm29pub8vt81pduvfk8a1.apps.googleusercontent.com")
            .requestServerAuthCode("444216138764-t11tjjmsic0fm29pub8vt81pduvfk8a1.apps.googleusercontent.com")
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
            val account = completedTask.getResult(ApiException::class.java)!!

            // Access Token 가져오기
            //getGoogleAccessToken(account.serverAuthCode!!)

            //로그인 성공 후 메인 화면으로 이동
            val fragment = HomeFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.homeFragment, fragment)
                .commit()
            finish()
        } catch (e: ApiException){
            //google 로그인 실패했을 때 작업 추가
            Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            Log.e("GoogleSignIn", "로그인 실패: ${e.statusCode}")
        }
    }
}