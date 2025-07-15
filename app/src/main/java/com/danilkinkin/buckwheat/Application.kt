package com.danilkinkin.buckwheat

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.danilkinkin.buckwheat.BuildConfig
import com.danilkinkin.buckwheat.widget.extend.ExtendWidgetReceiver
import com.danilkinkin.buckwheat.widget.minimal.MinimalWidgetReceiver
import com.instabug.library.Instabug
import com.instabug.library.LogLevel
import com.instabug.library.invocation.InstabugInvocationEvent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        configurInstabug()

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {
                ExtendWidgetReceiver.requestUpdateData(activity.applicationContext)
                MinimalWidgetReceiver.requestUpdateData(activity.applicationContext)
            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })
    }
    private fun configurInstabug() {
        try {
            Instabug.Builder(this, "Your_App_Token")
                .setInvocationEvents(
                    InstabugInvocationEvent.SHAKE,
                    InstabugInvocationEvent.SCREENSHOT,
                    InstabugInvocationEvent.FLOATING_BUTTON
                ).setSdkDebugLogsLevel(LogLevel.VERBOSE)
                .build()

            Instabug.addTags("android", "debug", "buckwheat")
            Instabug.setUserAttribute("app_version", BuildConfig.VERSION_NAME)

        } catch (e: Exception) {
            android.util.Log.e("INSTABUG_ERROR", "Failed to initialize Instabug", e)
        }
    }
}
