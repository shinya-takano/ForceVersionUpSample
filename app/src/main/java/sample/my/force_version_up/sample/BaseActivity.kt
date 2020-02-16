package sample.my.force_version_up.sample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseActivity : AppCompatActivity() {

    private val forceVersionUpLogic =
        ForceVersionUpLogic()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onReceiveMessage(eventMessage: AppForegroundMessage) {
        ApiManager.service.getVersionJson().enqueue(object :
            Callback<VersionModel> {
            override fun onFailure(call: Call<VersionModel>, t: Throwable) {
                Log.d(
                    "AppForegroundMessage",
                    "ApiManager.service.getVersionJson() onFailure",
                    t
                )
            }

            override fun onResponse(call: Call<VersionModel>, response: Response<VersionModel>) {

                response.body()?.let {
                    if (forceVersionUpLogic.checkForceVersionUp(it.version,
                            BuildConfig.VERSION_NAME
                        )) {
                        AlertDialog.Builder(this@BaseActivity)
                            .setTitle("アップデート")
                            .setMessage("アプリストアで最新のアプリをインストールしてください")
                            .setPositiveButton("ストアへ") { _, _ ->
                                // https://developer.android.com/distribute/marketing-tools/linking-to-google-play?hl=ja
                                val intent = Intent(
                                    Intent.ACTION_VIEW
                                ).apply {
                                    data =
                                        Uri.parse("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
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