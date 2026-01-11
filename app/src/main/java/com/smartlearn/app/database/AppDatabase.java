package com.smartlearn.app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.smartlearn.app.models.FocusSession;

/**
 * AppDatabase Class
 * 
 * This is the main database class for Room.
 * Room uses this to create the database and provide DAO instances.
 * 
 * @Database annotation tells Room:
 * - Which entities (tables) are in the database
 * - What version the database is
 * 
 * Why Room?
 * - Official Android database library
 * - Works with Java (no Kotlin required)
 * - Type-safe queries
 * - Automatic SQL generation
 * - Local storage (works offline)
 */
@Database(entities = {FocusSession.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    
    // Singleton pattern - only one database instance
    private static AppDatabase instance;
    
    // Abstract method - Room generates the implementation
    public abstract FocusSessionDao focusSessionDao();
    
    /**
     * Get database instance (Singleton pattern)
     * 
     * Why Singleton?
     * - Only one database connection needed
     * - Prevents multiple database instances
     * - More efficient
     */
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                context.getApplicationContext(),
                AppDatabase.class,
                "smartlearn_database" // Database file name
            ).build();
        }
        return instance;
    }
}
