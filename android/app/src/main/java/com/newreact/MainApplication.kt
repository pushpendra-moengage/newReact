package com.newreact

import android.R
import android.app.Application
import com.facebook.react.PackageList
import com.facebook.react.ReactApplication
import com.facebook.react.ReactHost
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactPackage
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.load
import com.facebook.react.defaults.DefaultReactHost.getDefaultReactHost
import com.facebook.react.defaults.DefaultReactNativeHost
import com.facebook.react.flipper.ReactNativeFlipper
import com.facebook.soloader.SoLoader
import com.moengage.core.DataCenter
import com.moengage.core.LogLevel
import com.moengage.core.MoEngage
import com.moengage.core.config.LogConfig
import com.moengage.core.config.NotificationConfig
import com.moengage.push.amp.plus.MiPushHelper
import com.moengage.react.MoEInitializer
import com.xiaomi.channel.commonutils.android.Region


class MainApplication : Application(), ReactApplication {

  val appKey = "5432117747547"
  val appId = "2882303761521177547"

  override val reactNativeHost: ReactNativeHost =
      object : DefaultReactNativeHost(this) {
        override fun getPackages(): List<ReactPackage> {
          // Packages that cannot be autolinked yet can be added manually here, for example:
          // packages.add(new MyReactNativePackage());
          return PackageList(this).packages
        }

        override fun getJSMainModuleName(): String = "index"

        override fun getUseDeveloperSupport(): Boolean = BuildConfig.DEBUG

        override val isNewArchEnabled: Boolean = BuildConfig.IS_NEW_ARCHITECTURE_ENABLED
        override val isHermesEnabled: Boolean = BuildConfig.IS_HERMES_ENABLED
      }

  override val reactHost: ReactHost
    get() = getDefaultReactHost(this.applicationContext, reactNativeHost)

  override fun onCreate() {
    super.onCreate()
    SoLoader.init(this, false)
    if (BuildConfig.IS_NEW_ARCHITECTURE_ENABLED) {
      // If you opted-in for the New Architecture, we load the native entry point for this app.
      load()
    }
    ReactNativeFlipper.initializeFlipper(this, reactNativeHost.reactInstanceManager)

    val moEngage = MoEngage.Builder(this, "8SIW681S80Z08KSHQFSTIZ8T", DataCenter.DATA_CENTER_1)
    moEngage.configureNotificationMetaData(
      NotificationConfig(
        R.mipmap.sym_def_app_icon,
        R.mipmap.sym_def_app_icon,
        -1,
        true,
        true,
        true
      )
    )
    moEngage.configureLogs(LogConfig(LogLevel.VERBOSE, true))
    MoEInitializer.initializeDefaultInstance(getApplicationContext(), moEngage);

    MiPushHelper.initialiseMiPush(this, appKey, appId, Region.India);
//    MiPushHelper.initialiseMiPush(App.application!!, appKey, appId, Region.India)
    ;



  }
}
