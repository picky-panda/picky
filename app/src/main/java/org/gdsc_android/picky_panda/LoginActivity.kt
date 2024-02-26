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
    private val RequestCode_SIGN_IN = 3253 //any num
    private val api = ServiceCreator.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //googleSignInOptions setting
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("444216138764-uhhqtqb9fjtn7i02mmrdpsjkg7g9q998.apps.googleusercontent.com")
            .requestServerAuthCode("444216138764-uhhqtqb9fjtn7i02mmrdpsjkg7g9q998.apps.googleusercontent.com")
            .build()
        
        //googleSignInClient. make login popup
        val mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
         
        binding.googleSignInButton.setOnClickListener{
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RequestCode_SIGN_IN)
         }
    }
    
    override  fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        
        // processing google login result
        if (requestCode == RequestCode_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: com.google.android.gms.tasks.Task<GoogleSignInAccount>) {
        try{
            //if login success
            val account = completedTask.getResult(ApiException::class.java)!!
            val idToken = account.idToken

            Log.d("IDToken", "ID Token: $idToken")

            // request login to the server using Retrofit
            val requestLoginData = idToken?.let { RequestLoginData(it) }
            val call: Call<ResponseLoginData> = api.login(requestLoginData!!)

            //Run asynchronously
            call.enqueue(object : retrofit2.Callback<ResponseLoginData> {
                override fun onResponse(
                    call: Call<ResponseLoginData>,
                    response: retrofit2.Response<ResponseLoginData>
                ) {
                    //process responses
                    if (response.isSuccessful){
                        val responseLoginData = response.body()

                        // verifying Server Response Code
                        val code = responseLoginData?.code ?: -1

                        when (code) {
                            201 -> {
                                //if login success
                                val data = responseLoginData?.data

                                val socialId = data?.socialId
                                val accessToken = data?.accessToken
                                val refreshToken = data?.refreshToken

                                // save user Information
                                val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putString("socialId", socialId)
                                editor.putString("accessToken", accessToken)
                                editor.putString("refreshToken", refreshToken)
                                editor.apply()

                                // move to main activity
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            400 -> {
                                val message = responseLoginData?.message ?: "Invalid data type"
                                Toast.makeText(this@LoginActivity, "Login failed: $message", Toast.LENGTH_SHORT).show()
                            }
                            401 -> {
                                val message = responseLoginData?.message ?: "Invalid data passed to external API request"
                                Toast.makeText(this@LoginActivity, "Login failed: $message", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                val message = responseLoginData?.message ?: "Unknown error"
                                Toast.makeText(this@LoginActivity, "Login failed: $message", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Server error: ${response.code()}", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<ResponseLoginData>, t: Throwable) {

                    Log.e("ApiError", "Login failed")
                }
            })

        } catch (e: ApiException){

            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
            Log.e("GoogleSignIn", "Login failed: ${e.statusCode}")
        }
    }
}