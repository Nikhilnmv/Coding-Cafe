# ⚡ Quick Start Guide

Get the app running in 10 minutes!

## 🎯 Fast Setup (TL;DR)

1. **Open in Android Studio** → Wait for Gradle sync
2. **Firebase Setup**:
   - Go to [Firebase Console](https://console.firebase.google.com)
   - Create project → Add Android app (`com.smartlearn.app`)
   - Download `google-services.json` → Place in `app/` folder
   - Enable **Authentication** (Email/Password)
   - Enable **Firestore Database** (test mode)
3. **Add Test Data** (in Firestore Console):
   - Collection: `courses` → Add 1-2 courses
   - Collection: `lessons` → Add 2-3 lessons per course
4. **Run** → Click ▶️ → Select device/emulator

## 📝 Minimal Test Data

### Course (in Firestore)
```
Collection: courses
Document ID: course1
Fields:
  title: "Java Basics"
  description: "Learn Java"
  instructor: "Dr. Smith"
  totalLessons: 2
  completedLessons: 0
  lessonIds: ["lesson1", "lesson2"]
```

### Lesson (in Firestore)
```
Collection: lessons
Document ID: lesson1
Fields:
  courseId: "course1"
  title: "Introduction"
  content: "Java is a programming language..."
  duration: 30
  order: 1
```

## ✅ Verification Checklist

- [ ] App opens (Splash screen shows)
- [ ] Can register new user
- [ ] Can login
- [ ] Dashboard shows (may show 0 stats - that's OK)
- [ ] Courses tab shows courses from Firestore
- [ ] Focus Timer works (starts countdown)
- [ ] Profile shows user info

## 🐛 Quick Fixes

**"google-services.json not found"**
→ Download from Firebase, place in `app/` folder

**"No courses showing"**
→ Add courses in Firestore Console

**"Login fails"**
→ Enable Email/Password in Firebase Authentication

**"Build errors"**
→ File → Invalidate Caches / Restart

---

That's it! For detailed setup, see **SETUP_GUIDE.md**
