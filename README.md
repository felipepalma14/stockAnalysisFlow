
# Stocks Info - MB

An Android application to showcase Stocks info. The application allows user to search
for stocks and view the details of each stock. The application consumes
data from the [MB API](https://api.montebravo.lionx.ai/api/estimates?origin=XP)

Minimum Api Level : 21
compileSdkVersion : 31

Build System : [Gradle](https://gradle.org/)

## Table of Contents

- [Architecture](#architecture)
- [Libraries](#libraries)
- [MAD SCOREBOARD](#Mad Scoreboard)
- [Demo](#demo)


## Architecture

Since the application is expected to scale with multiple developers working
on it, I used the Clean architectural principles to build the application.
I choose this architecture because it fosters better separation of concerns
and testability.

The Application is split into a three layer architecture:

- Data
- Domain
- Presentation


#### Data

The data layer handles the business logic and provides data from the
star wars API and a local database leveraging Room. This layer uses the
Repository pattern to fetch data from various data sources which in
this case is the Star Wars API and a local database.


#### Domain

The domain layer contains the application specifics logic. It contains
interactors/use cases that expose the actions that can be performed in the application.

The UseCases use a ```BaseUseCase``` interface that defines the parameters its taking in and
output and also handles running the UseCases in a background thread leveraging Kotlin Coroutines.


#### Presentation

I used the MVVM pattern for the presentation layer. The Model essentially exposes
the various states the view can be in. The ViewModel handles the UI logic and provides
data via Android architectural component LiveData to the view. The ViewModel talks to
the domain layer with the individual use cases. Jetpack's Data binding is used to bind
the data declaratively to the views.


## Libraries

Libraries used in the application are:

- [Jetpack](https://developer.android.com/jetpack)
  - [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manage UI related data in a lifecycle conscious way
  and act as a channel between use cases and UI.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Provides an observable data holder class.
- [Retrofit](https://square.github.io/retrofit/) - type safe http client and supports coroutines out of the box.
- [Shimmer](https://facebook.github.io/shimmer-android/) - Shimmer provides an easy way to add a shimmer effect to views in the application.
- [Moshi](https://github.com/square/moshi) - JSON Parser, used to parse requests on the data layer for Entities and understands Kotlin non-nullable
and default parameters.
- [okhttp-logging-interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - logs HTTP request and response data.
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines. I used this for asynchronous programming in order
to obtain data from the network as well as the database.
- [JUnit](https://junit.org/junit4/) - This was used for unit testing the repository, the use cases and the ViewModels.
- [Mockk](https://mockk.io/) This is a mocking library for Kotlin. I used it to provide test doubles during testing.
- [Truth](https://truth.dev/) - Assertions Library, provides readability as far as assertions are concerned.
- [Hilt](https://github.com/InsertKoinIO/koin) - Dependency injection plays a central role in the architectural pattern used.
For this reason I have chosen Hilt which is built on top of the battle tested DI framework - Dagger 2.

## Mad Scoreboard
<p align="center">
    <img src="assets/summary.png"
        style="margin-right: 20px;"
    />
</p>

<p align="center">
    <img src="assets/kotlin.png"
        style="margin-right: 20px;"
    />
</p>


## Demo


