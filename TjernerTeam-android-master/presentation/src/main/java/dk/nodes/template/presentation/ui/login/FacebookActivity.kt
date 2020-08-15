package dk.nodes.template.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import dk.nodes.template.models.FacebookUser
import dk.nodes.template.presentation.R
import kotlinx.android.synthetic.main.activity_facebook.*
import org.json.JSONException
import java.util.*
import com.facebook.AccessToken
import dk.nodes.template.models.Shift
import dk.nodes.template.presentation.ui.main.MainActivity


class FacebookActivity : AppCompatActivity() {

    val callbackManager = CallbackManager.Factory.create()
    lateinit var mainActivityIntent: Intent
     var fcmToken : String ? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook)
        updateWithToken(AccessToken.getCurrentAccessToken())

        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }
                    val token = task.result?.token
                    fcmToken =token.toString()
                })

        mainActivityIntent = Intent(this, MainActivity::class.java)

        val loginButton = login_btn
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"))
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val accessToken = loginResult.accessToken
                useLoginInformation(accessToken)
            }

            override fun onCancel() {}
            override fun onError(error: FacebookException) {}


        })


    }

    private fun updateWithToken(currentAccessToken: AccessToken?) {

        if (currentAccessToken != null) {
            useLoginInformation(currentAccessToken)

        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)


    }

    private fun useLoginInformation(accessToken: AccessToken) {
        login_status_progress.visibility = View.VISIBLE
        welcome_txt.text = "Checker login status"
        val request = GraphRequest.newMeRequest(accessToken) { `object`, response ->
            try {
                val name = `object`.getString("name")
                val emails = `object`.getString("email")
                val id = `object`.getString("id")

                mainActivityIntent.putExtra("shift", intent.getParcelableExtra<Shift>("shift"))
                mainActivityIntent.putExtra("user", FacebookUser(id.toLong(), name, emails, null,null,null,null, fcmToken,null,null, null,null, null))

                startActivity(mainActivityIntent)
                finish()

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        val parameters = Bundle()
        parameters.putString("fields", "id,name,email,picture.width(200)")
        request.parameters = parameters

        request.executeAsync()

        login_btn.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                val accessToken = loginResult.accessToken

                useLoginInformation(accessToken)
                startActivity(mainActivityIntent)
            }

            override fun onCancel() {

            }
            override fun onError(error: FacebookException) {


            }
        })
    }
}