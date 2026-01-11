# 📘 Step-by-Step Setup Guide

This guide will help you set up the Smart Adaptive Learning Platform project from scratch.

## Prerequisites Checklist

- [ ] Android Studio (Arctic Fox or later)
- [ ] JDK 8 or higher installed
- [ ] Android SDK (API 24+)
- [ ] Google account (for Firebase)
- [ ] Android device or emulator

## Step 1: Open Project in Android Studio

1. Launch Android Studio
2. Click **File → Open**
3. Navigate to the project folder: `Project- Sem4`
4. Click **OK**
5. Wait for Gradle sync to complete (may take 2-5 minutes on first run)

## Step 2: Firebase Project Setup

### 2.1 Create Firebase Project

1. Go to [Firebase Console](https://console.firebase.google.com)
2. Click **Add project** (or **Create a project**)
3. Enter project name: `SmartLearn` (or any name)
4. Disable Google Analytics (optional, for simplicity)
5. Click **Create project**
6. Wait for project creation (30 seconds)

### 2.2 Add Android App to Firebase

1. In Firebase Console, click **Add app** → **Android**
2. Enter package name: `com.smartlearn.app` (MUST match exactly)
3. Enter app nickname: `SmartLearn Android` (optional)
4. Enter SHA-1 (optional for now, skip)
5. Click **Register app**
6. Download `google-services.json`
7. **IMPORTANT**: Copy `google-services.json` to `app/` folder in your project
   - Replace the placeholder file that's already there
   - Path: `app/google-services.json`

### 2.3 Enable Firebase Authentication

1. In Firebase Console, go to **Authentication**
2. Click **Get started**
3. Go to **Sign-in method** tab
4. Click **Email/Password**
5. Enable **Email/Password** (toggle ON)
6. Click **Save**

### 2.4 Enable Firestore Database

1. In Firebase Console, go to **Firestore Database**
2. Click **Create database**
3. Select **Start in test mode** (for development)
4. Choose location (closest to you)
5. Click **Enable**

## Step 3: Add Sample Data to Firestore

### 3.1 Create Users Collection

1. In Firestore Console, click **Start collection**
2. Collection ID: `users`
3. Click **Next**
4. Document ID: `test_user_1` (or auto-generate)
5. Add fields:
   ```
   userId (string): test_user_1
   name (string): John Doe
   email (string): john@example.com
   totalLessonsCompleted (number): 0
   totalFocusSessions (number): 0
   totalStudyTime (number): 0
   ```
6. Click **Save**

### 3.2 Create Courses Collection

1. Click **Start collection**
2. Collection ID: `courses`
3. Document ID: `course_java_basics`
4. Add fields:
   ```
   title (string): Java Programming Basics
   description (string): Learn the fundamentals of Java programming
   instructor (string): Dr. Smith
   totalLessons (number): 5
   completedLessons (number): 0
   lessonIds (array): [lesson_1, lesson_2, lesson_3, lesson_4, lesson_5]
   ```
5. Click **Save**

6. Add another course:
   - Document ID: `course_android_dev`
   - Fields:
     ```
     title (string): Android Development
     description (string): Build Android apps with Java
     instructor (string): Prof. Johnson
     totalLessons (number): 8
     completedLessons (number): 0
     lessonIds (array): [lesson_6, lesson_7, lesson_8]
     ```

### 3.3 Create Lessons Collection

1. Click **Start collection**
2. Collection ID: `lessons`
3. Create lesson documents:

**Lesson 1:**
- Document ID: `lesson_1`
- Fields:
  ```
  courseId (string): course_java_basics
  title (string): Introduction to Java
  content (string): Java is a high-level programming language...
  duration (number): 30
  order (number): 1
  ```

**Lesson 2:**
- Document ID: `lesson_2`
- Fields:
  ```
  courseId (string): course_java_basics
  title (string): Variables and Data Types
  content (string): In Java, variables store data...
  duration (number): 45
  order (number): 2
  ```

Add more lessons as needed (at least 3-4 per course).

### 3.4 Create Progress Collection (Optional)

This will be created automatically when users complete lessons, but you can add test data:

1. Collection ID: `progress`
2. Document ID: `test_user_1_lesson_1`
3. Fields:
   ```
   userId (string): test_user_1
   lessonId (string): lesson_1
   completed (boolean): true
   timestamp (number): 1234567890
   ```

## Step 4: Build and Run

### 4.1 Sync Gradle

1. In Android Studio, click **File → Sync Project with Gradle Files**
2. Wait for sync to complete
3. Check for errors in **Build** tab (bottom panel)

### 4.2 Connect Device/Emulator

**Option A: Physical Device**
1. Enable **Developer Options** on your Android phone
2. Enable **USB Debugging**
3. Connect phone via USB
4. Allow USB debugging when prompted

**Option B: Emulator**
1. Click **Tools → Device Manager**
2. Click **Create Device**
3. Select a device (e.g., Pixel 5)
4. Select system image (API 30 or higher)
5. Click **Finish**
6. Click **▶️** to start emulator

### 4.3 Run the App

1. Click **Run** button (▶️) or press `Shift+F10`
2. Select your device/emulator
3. Wait for app to install and launch
4. You should see the Splash Screen!

## Step 5: Test the App

### 5.1 Test Registration

1. On Login screen, click **"Don't have an account? Register"**
2. Enter:
   - Name: `Test User`
   - Email: `test@example.com`
   - Password: `password123` (min 6 characters)
3. Click **Register**
4. You should be redirected to Dashboard

### 5.2 Test Login

1. Logout (menu → Logout)
2. Enter email and password
3. Click **Login**
4. Should redirect to Dashboard

### 5.3 Test Features

- **Dashboard**: View statistics
- **Courses**: Browse courses (should show courses from Firestore)
- **Focus Timer**: Start a Pomodoro session
- **Profile**: View user information

## Common Issues & Solutions

### Issue: "google-services.json not found"
**Solution**: Make sure `google-services.json` is in `app/` folder (not `app/src/main/`)

### Issue: "Build failed - Gradle sync"
**Solution**: 
1. File → Invalidate Caches / Restart
2. Check internet connection
3. Try: Build → Clean Project, then Build → Rebuild Project

### Issue: "Firebase Authentication not working"
**Solution**: 
1. Check Email/Password is enabled in Firebase Console
2. Verify `google-services.json` is correct
3. Check package name matches: `com.smartlearn.app`

### Issue: "No courses showing"
**Solution**: 
1. Check Firestore has `courses` collection
2. Verify collection name is exactly `courses` (lowercase)
3. Check internet connection

### Issue: "Camera permission denied"
**Solution**: 
- This is expected! Camera is opt-in
- Go to Settings → Apps → SmartLearn → Permissions → Enable Camera
- Or grant permission when prompted

## Next Steps

1. ✅ App is running
2. ✅ Firebase connected
3. ✅ Test data added
4. 📝 Customize courses and lessons
5. 📝 Add more features
6. 📝 Prepare for demo/presentation

## Project Structure Reminder

```
app/
├── src/main/
│   ├── java/com/smartlearn/app/
│   │   ├── activities/      # SplashActivity, LoginActivity, MainActivity
│   │   ├── ui/              # Fragments (Dashboard, Courses, Timer, etc.)
│   │   ├── models/          # User, Course, Lesson classes
│   │   ├── firebase/        # Firebase helpers
│   │   └── ...
│   ├── res/
│   │   ├── layout/          # XML layouts
│   │   ├── values/          # strings.xml, colors.xml, themes.xml
│   │   └── menu/            # Navigation menus
│   └── AndroidManifest.xml
└── google-services.json     # ⚠️ IMPORTANT: Download from Firebase
```

## Need Help?

1. Check **README.md** for architecture overview
2. Read code comments (they explain everything!)
3. Check Firebase Console for data
4. Use Android Studio's **Logcat** to see debug messages

---

**Happy Coding! 🚀**
