package sample.my.force_version_up.sample

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val forceVersionUpLogic = ForceVersionUpLogic()

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        ApiManager.service.getVersionJson().enqueue(object : Callback<VersionModel> {
            override fun onFailure(call: Call<VersionModel>, t: Throwable) {
                Log.d(TAG, "ApiManager.service.getVersionJson() onFailure", t)
            }

            override fun onResponse(call: Call<VersionModel>, response: Response<VersionModel>) {

                response.body()?.let {
                    if (forceVersionUpLogic.checkForceVersionUp(it.version, BuildConfig.VERSION_NAME)) {
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle("アップデート")
                            .setMessage("アプリストアで最新のアプリをインストールしてください")
                            .setPositiveButton("ストアへ") { _, _ ->
                                // https://developer.android.com/distribute/marketing-tools/linking-to-google-play?hl=ja
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    data = Uri.parse("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
                                }
                                startActivity(intent)
                            }
                            .show()
                    }
                }
            }
        })


    }
}
