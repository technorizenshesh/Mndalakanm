package com.app.mndalakanm.ui.setupParent.Menu.About

import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.app.mndalakanm.retrofit.ApiClient
import com.app.mndalakanm.utils.DataManager
import com.cityoneprovider.retrofit.ProviderInterface
import com.techno.mndalakanm.R
import com.techno.mndalakanm.databinding.FragmentPrivacyBinding
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class privacyFragment : Fragment() {

    private lateinit var apiInterface: ProviderInterface
    private lateinit var binding: FragmentPrivacyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= DataBindingUtil.inflate(inflater,R.layout.fragment_privacy, container, false)
        apiInterface = ApiClient.getClient(requireContext())!!.create(ProviderInterface::class.java)
        addSub()
return binding.root
    }
    private fun addSub() {
        DataManager.instance
            .showProgressMessage(requireActivity(), getString(R.string.please_wait))
        val map = HashMap<String, String>()
        Timber.tag(ContentValues.TAG).e("Login user Request = %s", map)
        apiInterface.privacy_policy(map).enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                DataManager.instance.hideProgressMessage()
                try {
                    val responseString = response.body()!!.string()
                    val jsonObject = JSONObject(responseString)
                    val result = jsonObject.getJSONObject("result")
                    val message  = jsonObject.getString("message")
                   setWebView(result.getString("description"))
                } catch (e: Exception) {
                    DataManager.instance.hideProgressMessage()
                    Toast.makeText(context, "Exception = " + e.message, Toast.LENGTH_SHORT).show()
                    Timber.tag("Exception").e("Exception = %s", e.message)
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                DataManager.instance.hideProgressMessage()
                Timber.tag(ContentValues.TAG).e("onFailure: %s", t.localizedMessage)
                Timber.tag(ContentValues.TAG).e("onFailure: %s", t.cause.toString())
                Timber.tag(ContentValues.TAG).e("onFailure: %s", t.message.toString())
            }
        })
    }
    private fun setWebView(description: String) {
        binding.webView.getSettings().setJavaScriptEnabled(true)
        binding.webView.loadData(description, "text/html; charset=utf-8", "UTF-8")
    }

}