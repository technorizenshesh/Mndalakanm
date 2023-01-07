package com.techno.mndalakanm.Ui.Home

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.techno.mndalakanm.R
import com.techno.mndalakanm.databinding.FragmentHomeBinding
import com.techno.mndalakanm.utils.Session
import com.vilborgtower.user.utils.Constant
import com.vilborgtower.user.utils.PrefManager
import java.util.concurrent.TimeUnit


class HomeFragment : Fragment() {
    var myCountDownTimer: MyCountDownTimer? = null
lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        myCountDownTimer = MyCountDownTimer(60000, 1000)
        myCountDownTimer!!.start()
        PrefManager.setString(Constant.IS_LOGIN, "true")
        binding.btn.setOnClickListener{

            Toast.makeText(context,    PrefManager.getString(Constant.IS_LOGIN), Toast.LENGTH_LONG).show()
            PrefManager.setString(Constant.IS_LOGIN, "false")
        }

        return binding.root
    }
    inner class MyCountDownTimer(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {
        override fun onTick(millisUntilFinished: Long) {
            val progress = (millisUntilFinished / 1000).toInt()
            binding.progressBar.setProgress(binding.progressBar.getMax() - progress)
            binding.cloctTime.setText(converter(millisUntilFinished))
        }

        override fun onFinish() {
            //finish()
        }
    }

    fun converter(millis: Long): String =
        String.format(
            " %02d : %02d : %02d",
            TimeUnit.MILLISECONDS.toHours(millis),
            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(millis)
            ),
            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(millis)
            )
        )
}