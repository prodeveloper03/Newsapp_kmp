Release Note & Run details : https://www.notion.so/News-App-2d9ce7f6c7288023aea9f25b693cec8d?source=copy_link

🛠 Tech Stack

-> Kotlin Multiplatform (KMP) – Shared business logic

-> Compose Multiplatform – Single UI for Android & iOS

-> Clean Architecture + MVVM

-> Ktor Client – Networking

-> kotlinx.serialization – JSON parsing

-> Kotlin Coroutines & Flow – Async & state handling

-> Multiplatform Settings – TTL-based offline caching

-> Koin – Dependency Injection

🏗 Architecture

-> Presentation Layer
   Compose UI + ViewModels

-> Domain Layer
   Business models & UI state

-> Data Layer
   Repository + Network (Ktor) + Cache (Key-Value, TTL)

-> Dependency Injection
   Centralized dependency graph
