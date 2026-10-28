# Smart Pantry Manager

## App Description
Smart Pantry Manager is an Android application designed to help users reduce household food waste. By tracking the ingredients a user actually has at home, the app suggests recipes they can cook right now. The core feature is a **Strict-Matching Algorithm** that only suggests a recipe if *every single required ingredient* is currently in the user's pantry in sufficient quantities.

## Features
- **Pantry Management:** Full CRUD (Create, Read, Update, Delete) operations for ingredients.
- **Strict Recipe Matching:** Only displays recipes where 100% of the ingredients are available.
- **Smart Logic:** Handles basic pluralization (e.g., "tomatoes" vs "tomato") and unit conversions (e.g., kg to g).
- **Recipe Details:** Step-by-step preparation instructions and required ingredients.
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

---

##  How to Test the Strict-Matching Algorithm
When you first open the app, your pantry will be empty, and the "Recipes" tab will show 0 matches. To verify the core logic of the app, add the following ingredients into the Pantry to see specific recipes appear:

### Test 1: The Exact Match
*Add these ingredients to your pantry:*
*   `eggs` | `2` | `pcs`
*   `butter` | `10` | `g`
*   `salt` | `1` | `tsp`
    *(Navigate to the "Recipes" tab: **Scrambled Eggs** will now appear. If you delete the salt, it will disappear!)*

### Test 2: Plural Handling ("tomatoes" vs "tomato")
*Add these ingredients to your pantry:*
*   `pasta` | `200` | `g`
*   `tomatoes` | `3` | `pcs` *(Notice the plural 'es'—the recipe asks for "tomato", but the matcher is smart enough to handle it)*
*   `olive oil` | `2` | `tbsp`
    *(Navigate to the "Recipes" tab: **Tomato Pasta** will appear.)*

### Test 3: Unit Conversion (kg to g)
*Add these ingredients to your pantry:*
*   `bread` | `2` | `pcs`
*   `cheese` | `0.1` | `kg` *(The recipe requires 50g. Because 0.1kg = 100g, the app recognizes you have enough)*
*   `butter` | `10` | `g`
    *(Navigate to the "Recipes" tab: **Cheese Toastie** will appear.)*

---

## Author
**[Quade Osborne]**
[402308254]
Mobile App Development 700
