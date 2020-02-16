package sample.my.force_version_up.sample

import android.app.Application
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import org.greenrobot.eventbus.EventBus

class App : Application(), LifecycleObserver {

    companion object {
        const val TAG = "App"
    }

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreateLifecycle() {
        // 最初の1回のみ
        Log.d(TAG, "ON_CREATE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStartLifecycle() {
        // アプリ開始時 or バックグラウンドからの復帰時
        Log.d(TAG, "ON_START")

        EventBus.getDefault().post(AppForegroundMessage())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResumeLifecycle() {
        // アプリ開始時 or バックグラウンドからの復帰時
        Log.d(TAG, "ON_RESUME")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPauseLifecycle() {
        // アプリ終了時 or バックグラウンド移行時
        Log.d(TAG, "ON_PAUSE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStopLifecycle() {
        // アプリ終了時 or バックグラウンド移行時
        Log.d(TAG, "ON_STOP")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyLifecycle() {
        // 呼ばれない
        Log.d(TAG, "ON_DESTROY")
    }

}
// Event Bus Message class
class AppForegroundMessage