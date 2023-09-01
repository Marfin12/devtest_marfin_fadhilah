package com.marfin_fadhilah.devtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.marfin_fadhilah.devtest.core.utils.Utils.isContainFile
import com.marfin_fadhilah.devtest.databinding.ActivityMainBinding
import com.marfin_fadhilah.devtest.home.HomeFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkRootedDevice()

        setSupportActionBar(binding.appBarMain.toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, HomeFragment())
                .commit()
            supportActionBar?.title = getString(R.string.app_name)
        }
    }

    private fun checkRootedDevice() {
        if (isContainFile(SUPER_USER_FILE, SU_BINARY_FILE)) {
            AlertDialog.Builder(this)
                .setTitle(R.string.error)
                .setMessage(R.string.rooted_device_detected)
                .setPositiveButton(R.string.ok) { _, _ ->
                    finish()
                }
                .create()
                .show()
        }
    }
}