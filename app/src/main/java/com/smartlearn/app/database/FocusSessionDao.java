package com.smartlearn.app.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.smartlearn.app.models.FocusSession;

import java.util.List;

/**
 * FocusSessionDao (Data Access Object)
 * 
 * This interface defines database operations for FocusSession.
 * Room uses this to generate SQL queries automatically.
 * 
 * @Dao annotation tells Room this is a database access interface
 * 
 * Why DAO?
 * - Separates database logic from business logic
 * - Room generates SQL code for you (no need to write SQL manually)
 * - Type-safe queries (compiler checks for errors)
 */
@Dao
public interface FocusSessionDao {
    
    /**
     * Insert a new focus session
     * @Insert annotation tells Room to generate INSERT SQL
     */
    @Insert
    void insertSession(FocusSession session);
    
    /**
     * Get all sessions for a user
     * @Query annotation lets you write custom SQL
     */
    @Query("SELECT * FROM focus_sessions WHERE userId = :userId ORDER BY startTime DESC")
    List<FocusSession> getSessionsByUser(String userId);
    
    /**
     * Get total focus time for a user (in milliseconds)
     */
    @Query("SELECT SUM(duration) FROM focus_sessions WHERE userId = :userId AND sessionType = 'FOCUS'")
    Long getTotalFocusTime(String userId);
    
    /**
     * Get total number of completed focus sessions
     */
    @Query("SELECT COUNT(*) FROM focus_sessions WHERE userId = :userId AND sessionType = 'FOCUS' AND isCompleted = 1")
    int getTotalSessions(String userId);
}
