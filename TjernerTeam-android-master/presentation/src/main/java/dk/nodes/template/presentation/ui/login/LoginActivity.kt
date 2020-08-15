package dk.nodes.template.presentation.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import dk.nodes.template.presentation.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
      disconnectFromFacebook()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


    login_btn.setOnClickListener(this)



    }

    fun disconnectFromFacebook() {
        GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, GraphRequest.Callback { LoginManager.getInstance().logOut() }).executeAsync()

        if (AccessToken.getCurrentAccessToken() == null) {

            finishAffinity()
            return  // already logged out
        }

    }

}
