package com.smartlearn.app.ui.timer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.smartlearn.app.R;
import com.smartlearn.app.database.AppDatabase;
import com.smartlearn.app.database.FocusSessionDao;
import com.smartlearn.app.firebase.FirebaseAuthHelper;
import com.smartlearn.app.models.FocusSession;
import com.smartlearn.app.utils.Constants;

import java.util.Date;

/**
 * TimerFragment
 * 
 * Implements Pomodoro Focus Timer.
 * 
 * Pomodoro Technique:
 * - 25 minutes of focused work
 * - 5 minutes break
 * - Repeat
 * 
 * Features:
 * - Start/Pause/Reset timer
 * - Log sessions to Room database
 * - Display remaining time
 */
public class TimerFragment extends Fragment {
    
    private TextView textViewTimer;
    private Button buttonStart;
    private Button buttonPause;
    private Button buttonReset;
    
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private boolean isRunning = false;
    private boolean isFocusSession = true; // true = focus, false = break
    
    private FirebaseAuthHelper authHelper;
    private FocusSessionDao sessionDao;
    private FocusSession currentSession;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        
        textViewTimer = view.findViewById(R.id.textViewTimer);
        buttonStart = view.findViewById(R.id.buttonStart);
        buttonPause = view.findViewById(R.id.buttonPause);
        buttonReset = view.findViewById(R.id.buttonReset);
        
        // Initialize
        authHelper = new FirebaseAuthHelper();
        AppDatabase database = AppDatabase.getInstance(getContext());
        sessionDao = database.focusSessionDao();
        
        // Set initial time (25 minutes focus)
        timeLeftInMillis = Constants.FOCUS_DURATION_MS;
        updateTimerDisplay();
        
        // Button listeners
        buttonStart.setOnClickListener(v -> startTimer());
        buttonPause.setOnClickListener(v -> pauseTimer());
        buttonReset.setOnClickListener(v -> resetTimer());
        
        return view;
    }
    
    /**
     * Start the timer
     */
    private void startTimer() {
        if (!isRunning) {
            // Create new session if starting fresh
            if (currentSession == null) {
                String userId = authHelper.getCurrentUser() != null 
                    ? authHelper.getCurrentUser().getUid() 
                    : "anonymous";
                
                currentSession = new FocusSession(
                    userId,
                    new Date(),
                    isFocusSession ? Constants.SESSION_TYPE_FOCUS : Constants.SESSION_TYPE_BREAK
                );
            }
            
            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    updateTimerDisplay();
                }
                
                @Override
                public void onFinish() {
                    // Timer finished
                    timeLeftInMillis = 0;
                    updateTimerDisplay();
                    isRunning = false;
                    
                    // Save session to database
                    if (currentSession != null) {
                        currentSession.setEndTime(new Date());
                        currentSession.setDuration(
                            isFocusSession ? Constants.FOCUS_DURATION_MS : Constants.BREAK_DURATION_MS
                        );
                        currentSession.setCompleted(true);
                        
                        // Save to database in background thread
                        new Thread(() -> sessionDao.insertSession(currentSession)).start();
                    }
                    
                    // Switch to break/focus
                    isFocusSession = !isFocusSession;
                    timeLeftInMillis = isFocusSession 
                        ? Constants.FOCUS_DURATION_MS 
                        : Constants.BREAK_DURATION_MS;
                    currentSession = null;
                    
                    updateTimerDisplay();
                }
            }.start();
            
            isRunning = true;
            buttonStart.setEnabled(false);
            buttonPause.setEnabled(true);
        }
    }
    
    /**
     * Pause the timer
     */
    private void pauseTimer() {
        if (isRunning && countDownTimer != null) {
            countDownTimer.cancel();
            isRunning = false;
            buttonStart.setEnabled(true);
            buttonPause.setEnabled(false);
        }
    }
    
    /**
     * Reset the timer
     */
    private void resetTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isRunning = false;
        isFocusSession = true;
        timeLeftInMillis = Constants.FOCUS_DURATION_MS;
        currentSession = null;
        updateTimerDisplay();
        buttonStart.setEnabled(true);
        buttonPause.setEnabled(false);
    }
    
    /**
     * Update timer display
     */
    private void updateTimerDisplay() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        textViewTimer.setText(timeLeftFormatted);
        
        // Update button text based on session type
        String sessionType = isFocusSession ? "Focus" : "Break";
        textViewTimer.append("\n" + sessionType);
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
