# Smart Pantry Manager

## App Description
Smart Pantry Manager is an Android application designed to help users reduce household food waste. By tracking the ingredients a user actually has at home, the app suggests recipes they can cook right now. The core feature is a **Strict-Matching Algorithm** that only suggests a recipe if *every single required ingredient* is currently in the user's pantry in sufficient quantities.

## Features
- **Pantry Management:** Full CRUD (Create, Read, Update, Delete) operations for ingredients.
- **Strict Recipe Matching:** Only displays recipes where 100% of the ingredients are available.
- **Smart Logic:** Handles basic pluralization (e.g., "tomatoes" vs "tomato") and unit conversions (e.g., kg to g).
- **Recipe Details:** Step-by-step preparation instructions.
- **Persistent Data:** Data remains intact even after closing the app.
- **Feedback States:** Helpful UI messages when the pantry or recipe list is empty.

## Database Choice & Justification
**Option Chosen: SQLite (via SQLiteOpenHelper)**

**Why:**
I chose SQLite for this application because it is a lightweight, serverless database built directly into the Android operating system. Unlike Firebase or PostgreSQL, SQLite requires no internet connection, ensuring the app is fully functional offline (which is crucial for users checking their pantry in the kitchen). It provides fast, reliable local CRUD operations, ensuring data persists securely on the device after the app is closed.

## Tech Stack & Requirements
- **Language:** Java
- **IDE:** Android Studio
- **Minimum SDK:** API 24 (Android 7.0)
- **Architecture:** Activities, RecyclerView with Custom Adapters, Intents.

## Setup & Run Instructions
1. Clone or download this repository.
2. Open the project in **Android Studio**.
3. Allow Gradle to sync (this may take a few minutes on the first run).
4. Connect an Android device or start an Android Emulator (API 24 or higher is required).
5. Click the green **Run (▶)** button in the top toolbar to build and launch the app.

## The Strict Matching Algorithm
The core logic is handled in `RecipeMatcher.java`. The algorithm loops through every ingredient required for a recipe and checks it against the user's pantry list. If even *one* ingredient is missing or the quantity is insufficient, the recipe is excluded from the suggested list.

## Author
**[Quade Osborne]**
[Student Number: 402308254]
Mobile App Development 700