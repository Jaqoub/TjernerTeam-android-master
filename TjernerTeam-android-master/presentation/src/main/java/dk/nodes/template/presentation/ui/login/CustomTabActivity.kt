package dk.nodes.template.presentation.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dk.nodes.template.presentation.R
import timber.log.Timber

class CustomTabActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_tab)


        val getinent = intent.extras


        if(getinent != null){

            val id = getinent.getString("id")
            Timber.e(id)

        }
    }
}
