package com.android.segunfrancis.biometrics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executors
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val executor = Executors.newSingleThreadExecutor()
        val activity: FragmentActivity = this

        val biometricPrompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    // user clicked negative button
                } else {
                    // Called when an unrecoverable error has been encountered and operation is complete
                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                // called when a biometric is recognized
                Log.d(TAG, "Success:  $result")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                // called when biometric is valid but not recognized
                Log.e(TAG, "Failed")
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Prompt Title")
            .setSubtitle("Biometric Prompt Subtitle")
            .setDescription("Biometric Prompt Description")
            .setNegativeButtonText("Cancel")
            .build()

        findViewById<Button>(R.id.authentication_button).setOnClickListener{
            biometricPrompt.authenticate(promptInfo)
        }
    }
}
