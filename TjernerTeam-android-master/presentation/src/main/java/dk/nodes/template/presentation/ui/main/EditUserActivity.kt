package dk.nodes.template.presentation.ui.main

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.facebook.AccessToken
import com.facebook.FacebookActivity
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import com.google.android.material.internal.ContextUtils.getActivity
import dk.nodes.template.models.FacebookUser
import dk.nodes.template.models.PostCode
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseActivity
import dk.nodes.template.presentation.ui.login.LoginActivity
import kotlinx.android.synthetic.main.edit_user_activity.*
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class EditUserActivity : BaseActivity(), View.OnClickListener, TextWatcher {


    private val viewModel by viewModel<MainActivityViewModel>()
    private var fcbUcer: FacebookUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_user_activity)
        viewModel.fetchUser(isLoggedIn())
        cpr_edit.addTextChangedListener(this)
        dateofbirth_edittext.setOnClickListener(this)
        viewModel.viewState.observeNonNull(this) { state ->
            handleUser(state)

        }
        update_btn.setOnClickListener(this)
        delete_btn.setOnClickListener(this)


    }


    fun isLoggedIn(): String {
        val accessToken = AccessToken.getCurrentAccessToken()
        if (accessToken != null) {

            return accessToken.userId
        }
        val intent = Intent(this, FacebookActivity::class.java)
        startActivity(intent)
        return ""
    }

    fun getAccesToken(): AccessToken {
        val accessToken = AccessToken.getCurrentAccessToken()

        return accessToken


    }

    private fun handleUser(state: MainActivityViewState) {
        state.facebookUser?.let { user ->

            user.fullName?.let {
                name_edittext.setText(it.toString())
            }

            user.email?.let {
                email_edittext.setText(it)
            }

            user.address?.let {
                adress_edittext.setText(it)
            }

            user.city?.let {
                city_edittext.setText(it)

            }

            user.phoneNumber?.let {

                if(it != 0L){
                    phoneNumb_edittext.setText(it.toString())

                }

            }

            user.postCode?.postCode?.let {
                if(it !=0) {

                    postCode_edittext.setText(it.toString())
                }
            }

            user.cprNumber?.let {

                if( it !="") {
                    //Log.d("notnull", it)
                    cpr_edit.setText(it.toString() + "-XXXX")
                }
            }

            user.regNumber?.let {
                if(it !=0) {

                    regNumber_edittext.setText(it.toString())
                }
            }

            user.accountNumber?.let {

                    accountnr_edittext.setText(it.toString())
                }



            user.dateOfBirth?.let {

                dateofbirth_edittext.setText(it)

            }

            fcbUcer = user

        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {

            update_btn.id -> {
                fcbUcer?.address = adress_edittext.text.toString()
                fcbUcer?.fullName = name_edittext.text.toString()
                fcbUcer?.city = city_edittext.text.toString()

                phoneNumb_edittext?.text.let {
                   if (it.toString() != ""){
                       fcbUcer?.phoneNumber = it.toString().toLong()

                   }
                }

                postCode_edittext?.text.let {
                    if (it.toString() != ""){
                        fcbUcer?.postCode = PostCode(it.toString().toInt())

                    }


                }
                regNumber_edittext?.text.let {
                    if (it.toString() != ""){
                        fcbUcer?.regNumber = it.toString().toInt()
                    }

                }


                accountnr_edittext?.text.let {
                    if (it.toString() != ""){
                        fcbUcer?.accountNumber = it.toString().toLong()
                    }
                }

                dateofbirth_edittext?.text.let {

                    if (it.toString() != ""){
                        fcbUcer?.dateOfBirth =it.toString()
                    }
                }

                fcbUcer?.cprNumber = cpr_edit.text.toString()

                viewModel.updateUser(fcbUcer!!)
                finish()
            }

            delete_btn.id -> {
                val builder = AlertDialog.Builder(this)

                builder.setTitle("Slet din konto")

                builder.setMessage("Er du sikker på at du ville slette din konto?")

                builder.setPositiveButton("JA") { dialog, which ->
                    fcbUcer?.facebookId?.let { viewModel.deleteUser(it) }
                    disconnectFromFacebook()
                    finishAffinity()

                }


                builder.setNegativeButton("ANNULLER") { dialog, which ->
                    dialog.dismiss()
                }

                val dialog: AlertDialog = builder.create()

                dialog.show()


            }
            dateofbirth_edittext.id -> {

                calenderFuntion()
            }

        }
    }

    fun disconnectFromFacebook() {
        GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, GraphRequest.Callback { LoginManager.getInstance().logOut() }).executeAsync()

        if (AccessToken.getCurrentAccessToken() == null) {


            return  // already logged out
        }

    }

    private fun calenderFuntion() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/YYYY")

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            val dateformat = dateFormatter.format(LocalDate.of(year, monthOfYear + 1, dayOfMonth))
            dateofbirth_edittext.setText(dateformat)


        }, year, month, day)
        dpd.show()

    }








    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        Timber.e(count.toString())
        Timber.e("start " + start + " count " + count + " before " + before)

        if (start + count == 6) {
            cpr_edit.setText(s.toString() + "-")
            cpr_edit.setSelection(cpr_edit.getText().toString().length);
        }

    }

    override fun afterTextChanged(s: Editable?) {


    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        Timber.e("start " + start + " count " + count + " after " + after)


    }

}

