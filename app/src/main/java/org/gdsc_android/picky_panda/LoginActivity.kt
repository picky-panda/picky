package org.gdsc_android.picky_panda

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import org.gdsc_android.picky_panda.data.RequestLoginData
import org.gdsc_android.picky_panda.data.ResponseLoginData
import org.gdsc_android.picky_panda.data.ServiceCreator
import org.gdsc_android.picky_panda.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val RequestCode_SIGN_IN = 3253 //임의의 값
    private val api = ServiceCreator.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //googleSignInOptions 설정
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("444216138764-uhhqtqb9fjtn7i02mmrdpsjkg7g9q998.apps.googleusercontent.com")
            .requestServerAuthCode("444216138764-uhhqtqb9fjtn7i02mmrdpsjkg7g9q998.apps.googleusercontent.com")
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
            val idToken = account.idToken

            // ID Token 출력
            Log.d("IDToken", "ID Token: $idToken")

            // Retrofit을 사용하여 서버에 로그인 요청
            val requestLoginData = idToken?.let { RequestLoginData(it) }
            val call: Call<ResponseLoginData> = api.login(requestLoginData!!)

            //비동기적으로 실행
            call.enqueue(object : retrofit2.Callback<ResponseLoginData> {
                override fun onResponse(
                    call: Call<ResponseLoginData>,
                    response: retrofit2.Response<ResponseLoginData>
                ) {
                    //응답을 처리하는 코드
                    if (response.isSuccessful){
                        val responseLoginData = response.body()

                        //서버 응답 코드 확인
                        val code = responseLoginData?.code ?: -1

                        when (code) {
                            201 -> {
                                //성공적으로 로그인한 경우
                                val data = responseLoginData?.data

                                //응답 데이터 사용 예시
                                val socialId = data?.socialId
                                val accessToken = data?.accessToken
                                val refreshToken = data?.refreshToken

                                // 사용자 정보 저장
                                val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putString("socialId", socialId)
                                editor.putString("accessToken", accessToken)
                                editor.putString("refreshToken", refreshToken)
                                editor.apply()

                                //main activity로 이동
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            400 -> {
                                //request data가 잘못된 경우
                                val message = responseLoginData?.message ?: "잘못된 데이터 형식입니다"
                                Toast.makeText(this@LoginActivity, "Login failed: $message", Toast.LENGTH_SHORT).show()
                            }
                            401 -> {
                                //잘못된 accessToken이 넘어온 경우
                                val message = responseLoginData?.message ?: "외부 API 요청에 잘못된 데이터가 전달됐습니다"
                                Toast.makeText(this@LoginActivity, "Login failed: $message", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                //기타 오류인 경우
                                val message = responseLoginData?.message ?: "Unknown error"
                                Toast.makeText(this@LoginActivity, "Login failed: $message", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        // 서버 응답이 실패한 경우
                        Toast.makeText(this@LoginActivity, "Server error: ${response.code()}", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<ResponseLoginData>, t: Throwable) {
                    //실패 시 처리하는 코드 작성
                    Log.e("ApiError", "로그인 실패")
                }
            })

        } catch (e: ApiException){
            //google 로그인 실패했을 때 작업 추가
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
            Log.e("GoogleSignIn", "로그인 실패: ${e.statusCode}")
        }
    }
}