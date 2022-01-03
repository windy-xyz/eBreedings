package com.wcs.ebreedings.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.wcs.ebreedings.R
import com.wcs.ebreedings.data.storage.Pref
import com.wcs.ebreedings.databinding.ActivityMainBinding
import com.wcs.ebreedings.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        if (!getIsLogin(this)) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.logout_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Log Out")
                    .setMessage("Are you sure to log out?")
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.cancel()
                    }
                    .setPositiveButton("Yes") { dialog, _ ->
                        doLogOut(this)
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        setResult(Activity.RESULT_OK)
                        finish()
                        dialog.dismiss()
                    }
                    .show()

                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getIsLogin(context: Context): Boolean {
        val pref = Pref(context)
        return pref.getIsLogin()
    }

    private fun doLogOut(context: Context) {
        val pref = Pref(context)
        pref.doLogout()
    }

}
