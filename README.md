# Github User Finder

Testing apps for find github user
[**Download APK**](https://drive.google.com/open?id=1AxpPZ3N42F9_sCBTFBIEl5b_t-T4Ly88)

**Specification**
- Search by typing on search box
- error state when hit limit reached
- empty state when data not found
- pull to refresh
- endless scrolling

**Please use master branch**

To create development build just run:
`./gradlew assembleDebug`
it will produce unsigned APK in `build/output/apk/debug` folder

Architecture Pattern: **MVVM**

### Library used:
* [ViewModelComponent](https://developer.android.com/topic/libraries/architecture/viewmodel) (Lifecycle aware view model)
* [OKHttp](https://github.com/square/okhttp) (HTTP Client)
* [Retrofit](https://github.com/square/retrofit) (Type safe HTTP API)
* [Picasso](https://github.com/square/picasso) (Image Loader)
* [Coroutine](https://developer.android.com/kotlin/coroutines) (Threading like a boss)
* [Koin](https://insert-koin.io/) (Dependency Injection)

### Testing Library Used
* [Jacoco](https://www.jacoco.org/) (Code coverage library)
* [JUnit4](https://github.com/junit-team/junit4) (Testing Framework)
* [Mockk](https://github.com/mockk/mockk) (Mocking library for kotlin)
* [Kluent](https://github.com/MarkusAmshove/Kluent) ( Assertion library )


## Generate Test coverage
Open your terminal, go to your root project directory, then run this command:
`./gradlew clean assembleDebug testDebugUnitTest jacocoTestReport`

You can see test coverage report in:
`$rootProject/$yourModule/build/reports/jacoco/jacocoTestReport/html/`

