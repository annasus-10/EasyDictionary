# EasyDictionary

EasyDictionary is an Android application designed to provide word definitions using the WordsAPI. This app offers various features such as search history management and network availability checks, ensuring a seamless experience for users seeking word meanings.

## Features

- **Word Search**: Fetches definitions, pronunciations, and examples for words using the WordsAPI.
- **Search History**: Tracks previously searched words and displays them for easy reference.
- **Network Availability Check**: Notifies users if there is no internet connection, ensuring uninterrupted usage.
- **User-Friendly Interface**: Designed with a clean and intuitive UI for a better user experience.

## Technologies Used

- **Kotlin**: Utilized as the programming language for Android development.
- **Retrofit**: Employed as a type-safe HTTP client for Android to handle network requests effectively.
- **OkHttp**: Used as an HTTP client to add headers to requests for enhanced functionality.
- **Coroutines**: Implemented for asynchronous programming to manage tasks efficiently.
- **SharedPreferences**: Utilized for storing and retrieving search history data locally on the device.
- **RecyclerView**: Incorporated to display search results in a structured and organized manner.

## Installation

1. **Clone the repository**:
   ```sh
   git clone https://github.com/YOUR_GITHUB_USERNAME/YOUR_REPOSITORY_NAME.git
   cd YOUR_REPOSITORY_NAME
   ```

2. **Open the project in Android Studio**:
   - Open Android Studio.
   - Select `Open an existing Android Studio project`.
   - Navigate to the cloned repository and open it.

3. **Add your WordsAPI Key**:
   - Replace `your-api-key` in the `WordsApi` object with your actual WordsAPI key.

### Running the App

1. Connect an Android device or start an emulator.
2. Click on the `Run` button in Android Studio.

## Acknowledgments

- **UI References**: Some UI elements and design ideas were inspired by the YouTube video "How to make Dictionary app | Android Studio | 2024" by [Easy Tuto](https://youtu.be/LM5lWetuCHo?si=YnRE5dC98tkY797V).

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
