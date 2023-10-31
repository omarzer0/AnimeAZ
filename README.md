# AnimeAZ
Kotlin Multiplatform project with SharedUI and Shared Logic.

## Note: The Desktop version is almost ready! WO HOOO 🎉🎉🎉🎉

## Showcase

<table align="center">
 <tr>
    <th>
    Android
   </th>
  
  <th>
       IOS (I'm limited only to 10mb video)
   </th>
  
 </tr>
  
  <tr>
    <td> 
      <video src ="https://github.com/omarzer0/AnimeAZ/assets/55766997/441e1dc5-10d8-40ab-9c6b-a7b8a34d6302"/>
    </td>
    <td> 
      <video src ="https://github.com/omarzer0/AnimeAZ/assets/55766997/76438784-8d69-4bc0-b99f-61aa14fea93a" />
    </td>
    
  </tr>
</table>

## Stack & Open-source libraries
- 🎉🎉 Single codebase for both Android and IOS, and YES even Shared UI 🎉🎉 and should also work across all platforms.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) for writing only single code for Android and IOS.
- [Ktor client](https://ktor.io/docs/create-client.html) for handling network requests.
- [SqlDelight database](https://github.com/cashapp/sqldelight) for saving favorite anime locally.
- [Koin](https://insert-koin.io/docs/reference/koin-mp/kmp/) for dependency management across both Android and IOS.
- [Compose ImageLoader](https://github.com/qdsfdhvh/compose-imageloader) for loading images.
- [Decompose + Router](https://github.com/xxfast/Decompose-Router) for easily handling navigation on all platforms.
- [Kotlin date-time](https://github.com/Kotlin/kotlinx-datetime) for working with data and time.
- [Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings) for saving key-value pairs like datastore.
- [AZ-Paging](https://github.com/omarzer0/AnimeAZ/tree/main/paging) for handling paginating data from data sources like network or database. I still need to publish it until we have paging support.
- [MOKO Mvvm](https://github.com/icerockdev/moko-mvvm) provides architecture components of Model-View-ViewModel for UI applications.
- [MOKO resources](https://github.com/icerockdev/moko-resources) A great library for providing access to shared resources (like images, fonts, and strings) on all platforms.🔥🔥
- [MOKO Biometry](https://github.com/icerockdev/moko-biometry) for handling biometric authentication (Face ID, Touch ID, and also using PIN code).
- [Pull Refresh](https://github.com/MateriiApps/pullrefresh) Standalone pull to refresh library for Jetpack Compose multiplatform without the reliance on Material (As the dev says).
- [Material 3](https://m3.material.io/) for theming and using dynamic color on Android.
- [Saving favorite Anime images to local file](https://youtu.be/XWSzbMnpAgI?t=8838) Shoutout to Philipp Lackner.

## Featuring
- Shared UI for both Android and IOS.
- Single code for writing the logic of the app using Kotlin.
- Handling paging data from network (loading initial, next, and refresh) and using it together with search functionality.
- Add favorite Anime to a local database but save the image to a local file.
- The App supports 3 languages (English, Arabic, and German).
- Supports Dark and Light mode with Dynamic coloring on Android only.
- Saving key-value pair (saving either to use Lock or not for the app).
- Locking the app using Face ID, Fingerprint, and/or PIN code.
- Handling navigation and passing objects between screens.
- Injecting dependencies for the shared main and injecting platform-specific dependencies from each platform.
- Use default images for error and loading state.
- Swipe to refresh data.
- Search with paging for your favorite anime.
- Show all Top-rated anime.


## Note
- The idea of this app is to use as many features as possible to explore the power of KMP (The app doesn't have to make sense 😊) so feel free to add or request any feature you like.
- The IOS still alpha currently (11/10/2023) so it is a bit laggy even with release.
- Can't record the Biometric Auth screen.





