# AnimeAZ
## KMP project with SharedUI and Shared Logic

## App Preview
<table align="center">
 <tr>
  <td><img src="https://github.com/omarzer0/ShowTime/blob/main/assets/1.gif" alt="Home Screen"</td>
  <td><img src="https://github.com/omarzer0/ShowTime/blob/main/assets/2.gif" alt="Drawer Tabs"></td>
 </tr>
</table>

## Featuring
- 🎉🎉 Single codebase for both Android and IOS, and YES even Shared UI 🎉🎉 and should also work across all platforms.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) for writing only single code for Android and IOS.
- [Ktor client](https://ktor.io/docs/create-client.html) for handling network requests.
- [SqlDelight database](https://github.com/cashapp/sqldelight) for saving favorite anime locally.
- [Koin](https://insert-koin.io/docs/reference/koin-mp/kmp/) for dependency manegment across both Android and IOS.
- [Compose ImageLoader](https://github.com/qdsfdhvh/compose-imageloader) for loading images.
- [Decompose + Router](https://github.com/xxfast/Decompose-Router) for easily handling navigation on all platforms.
- [Kotlin date-time](https://github.com/Kotlin/kotlinx-datetime) for working with data and time.
- [Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings) for saving key value pairs like datastore.
- [AZ-Paging](https://github.com/omarzer0/AnimeAZ/tree/main/paging) for handle pagingating data from data source like network or database. I still need to publish it until we have a paging support.
- [MOKO Mvvm](https://github.com/icerockdev/moko-mvvm) provides architecture components of Model-View-ViewModel for UI applications.
- [MOKO resources](https://github.com/icerockdev/moko-resources) A great library for providing access to shared resources (like images, fonts and strings) on all platforms.🔥🔥
- [MOKO Biometry](https://github.com/icerockdev/moko-biometry) for handling biometric authentication (Face ID, Touch ID, and also using PIN code).



## Tech stack & Open-source libraries
- Minimum SDK level 21
- 100% [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) together with [Flow](https://developer.android.com/kotlin/flow) for asynchronous streams 
and one side viewModel to fragment communication.
- Dagger Hilt for dependency injection.
- [Retrofit](https://square.github.io/retrofit/) A type-safe HTTP client for Android and Java
- [Glide](https://github.com/bumptech/glide) for loading images.
- JetPack
  - Lifecycle - Dispose of observing data when the lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - ViewBinding - Interact with XML views in safeway and avoid findViewById() 
  - Navigation Component - Make it easy to navigate between different screens and pass data in type-safe way
- Architecture
  - MVVM Architecture (View - ViewModel - Model) together with Events that decide what Fragment or Activity should do
  - Repository pattern
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like cardView
- SaveStateHandler to handle process death
