# MVArchitecture
Implementation of _MVC, MVP, MVVM, MVI_ patterns using Clean Architecture and functional reactive programming principles.

## About
The application that allows to get random image from [api](https://picsum.photos/) and add it to favorites.

<details>
<summary>Demonstration GIF</summary>
  
![App demonstration gif](../main/media/mvarchitecture_demo.gif)
  
</details>

<details>
<summary>Architecture diagram</summary>
  
![Full architecture diagram](../main/media/mvarchitecture_full_scheme.png)
  
**_*Some details have been omitted to provide a better visibility of the direction of the dependencies._**
  
</details>

## Topic
- Clean Architecture
- MVC
- MVP
- MVVM
- MVI

## API
- [Lorem Picsum](https://picsum.photos/) - *Random image API*

## Features

*_Common:_*
- Verify required permissions

*_Random image:_*
- Get random image
- Add image to favorites
- Remove image from favorites

*_Favorite images:_*
- Get favorite images
- Load more images on scroll
- Remove image from favorites
- Undo image removal

## Tech
- Android Compose
- Kotlin
- Coroutines(Flow)
- Arrow-KT
- Koin DI
- Retrofit
- Room
- Coil
- Accompanist
- JUnit
- Mockk
