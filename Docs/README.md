# Smart Adaptive Learning Platform

A production-quality Android app built with Java, implementing an adaptive learning system with AI engagement analysis and smart food recommendations.

## 📱 Project Overview

This is a 3-month semester project for a team of 5 students. The app demonstrates:
- **MVVM Architecture** (beginner-friendly)
- **Firebase Integration** (Auth + Firestore)
- **On-device ML** (ML Kit Face Detection)
- **Local Database** (Room)
- **Modern Android Development** (Java-first approach)

## 🏗️ Architecture

### MVVM Pattern
- **Model**: Data classes (User, Course, Lesson, etc.)
- **View**: Activities and Fragments (UI)
- **ViewModel**: Business logic and data management
- **Repository**: Data source abstraction

### Project Structure
```
com.smartlearn.app
├── activities/          # SplashActivity, LoginActivity, MainActivity
├── ui/                  # Fragments (Dashboard, Courses, Timer, Profile, Engagement, Food)
├── adapters/            # RecyclerView adapters
├── models/              # Data model classes
├── viewmodel/           # ViewModels for MVVM
├── repository/          # Data repositories
├── ml/                  # ML Kit engagement analyzer
├── food/                # Food ordering module
├── firebase/            # Firebase helpers
├── database/            # Room database (DAO, Database)
└── utils/               # Constants and utilities
```

## 🚀 Setup Instructions

### Prerequisites
1. **Android Studio** (latest version)
2. **JDK 8 or higher**
3. **Firebase Account** (free tier is sufficient)

### Step 1: Clone/Download Project
```bash
# If using git
git clone <repository-url>
cd "Project- Sem4"
```

### Step 2: Firebase Setup

1. Go to [Firebase Console](https://console.firebase.google.com)
2. Create a new project (or use existing)
3. Add Android app:
   - Package name: `com.smartlearn.app`
   - Download `google-services.json`
4. Place `google-services.json` in `app/` directory (replace the placeholder file)
5. Enable Firebase Authentication:
   - Go to Authentication → Sign-in method
   - Enable "Email/Password"
6. Enable Firestore:
   - Go to Firestore Database
   - Create database (start in test mode for development)

### Step 3: Firestore Data Structure

Create these collections in Firestore:

#### Collection: `users`
Document ID: `{userId}`
```json
{
  "userId": "string",
  "name": "string",
  "email": "string",
  "totalLessonsCompleted": 0,
  "totalFocusSessions": 0,
  "totalStudyTime": 0
}
```

#### Collection: `courses`
Document ID: `{courseId}`
```json
{
  "title": "Java Basics",
  "description": "Learn Java programming",
  "instructor": "Dr. Smith",
  "totalLessons": 10,
  "completedLessons": 0,
  "lessonIds": ["lesson1", "lesson2"]
}
```

#### Collection: `lessons`
Document ID: `{lessonId}`
```json
{
  "courseId": "course1",
  "title": "Introduction to Java",
  "content": "Java is a programming language...",
  "videoUrl": "optional",
  "duration": 30,
  "order": 1
}
```

#### Collection: `progress`
Document ID: `{userId}_{lessonId}`
```json
{
  "userId": "user1",
  "lessonId": "lesson1",
  "completed": true,
  "timestamp": 1234567890
}
```

### Step 4: Build and Run

1. Open project in Android Studio
2. Sync Gradle files (File → Sync Project with Gradle Files)
3. Connect Android device or start emulator (API 24+)
4. Click Run (▶️) or press `Shift+F10`

## 📦 Modules

### Module 1: Adaptive Learning Core
- ✅ User Authentication (Email/Password)
- ✅ Course Management
- ✅ Lesson Viewer
- ✅ Progress Tracking
- ✅ Pomodoro Focus Timer (25 min focus, 5 min break)
- ✅ Dashboard with Statistics

### Module 2: AI Engagement Analyzer
- ✅ CameraX Integration
- ✅ ML Kit Face Detection
- ✅ Real-time Engagement Analysis
- ✅ Privacy-first (camera off by default, opt-in)
- ✅ Friendly Suggestions

### Module 3: Smart Food Ordering
- ✅ Time-based Recommendations
- ✅ Progress-based Discounts
- ✅ Mock Ordering System

## 🔐 Privacy & Permissions

### Camera Permission
- **OFF by default** - User must explicitly enable
- **No images stored** - All processing on-device
- **Opt-in only** - No forced camera access

### Required Permissions
- `INTERNET` - For Firebase and API calls
- `CAMERA` - Optional, only for engagement analyzer

## 🧪 Testing

### Test User Flow
1. **Splash Screen** → Shows for 2 seconds
2. **Login/Register** → Create account or login
3. **Dashboard** → View statistics
4. **Courses** → Browse available courses
5. **Focus Timer** → Start Pomodoro session
6. **Engagement Analyzer** → Enable camera (optional)
7. **Food Recommendations** → View time-based recommendations

### Test Data
You can add test courses and lessons directly in Firestore Console.

## 📚 Key Concepts Explained

### MVVM Architecture
- **Separation of Concerns**: UI (View) is separate from business logic (ViewModel)
- **LiveData**: Automatically updates UI when data changes
- **Survives Configuration Changes**: ViewModel persists during screen rotation

### Firebase
- **Firebase Auth**: Handles user authentication (no need to manage sessions manually)
- **Firestore**: NoSQL database (like MongoDB) - stores data in collections/documents

### Room Database
- **Local Storage**: Works offline
- **Type-safe Queries**: Compiler checks for errors
- **Automatic SQL Generation**: No need to write SQL manually

### ML Kit
- **On-device Processing**: Privacy-first, no data sent to servers
- **Face Detection**: Detects faces and facial landmarks
- **Rule-based Analysis**: Simple logic (not deep learning)

## 🐛 Troubleshooting

### Build Errors
- **Gradle Sync Failed**: Check internet connection, try "Invalidate Caches / Restart"
- **Firebase Error**: Ensure `google-services.json` is in `app/` directory
- **Missing Dependencies**: Run "Sync Project with Gradle Files"

### Runtime Errors
- **Login Failed**: Check Firebase Authentication is enabled
- **No Courses Showing**: Add courses in Firestore Console
- **Camera Not Working**: Check device has camera, grant permission

## 📝 Project Report Sections

1. **Problem Statement**: Learning platform with adaptive features
2. **Architecture**: MVVM, Firebase, Room, ML Kit
3. **Modules**: Detailed explanation of each module
4. **Ethics & Privacy**: Camera opt-in, on-device processing
5. **Demo Flow**: Step-by-step user journey

## 👥 Team Development

### Git Workflow (if using version control)
```bash
# Create feature branch
git checkout -b feature/module-name

# Commit changes
git add .
git commit -m "Add feature description"

# Push to remote
git push origin feature/module-name
```

## 📄 License

This is an academic project. Use for learning purposes only.

## 🙏 Acknowledgments

- Firebase for backend services
- Google ML Kit for on-device ML
- Android Jetpack for modern Android development

---

**Built with ❤️ using Java and Android SDK**
