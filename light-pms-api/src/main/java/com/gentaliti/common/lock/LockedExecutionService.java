package com.gentaliti.common.lock;

import com.gentaliti.common.exceptions.LightPmsValidationException;
import lombok.AllArgsConstructor;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockingTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@AllArgsConstructor
public class LockedExecutionService {
    private static final int LOCK_EXPIRATION_SECONDS = 30;

    private LockingTaskExecutor executor;

    public <T> T runLocked(AtomicFunction<T> function, String lockName) {
        try {
            Duration lockAtMostUntil = Duration.ofSeconds(LOCK_EXPIRATION_SECONDS);
            LockConfiguration lockConfiguration = new LockConfiguration(Instant.now(), lockName, lockAtMostUntil, Duration.ZERO);

            return executor.executeWithLock(function::apply, lockConfiguration).getResult();
        } catch (LightPmsValidationException e) {
            throw e; // rethrow known exceptions
        } catch (Throwable e) {
            throw new RuntimeException("Running locked task failed", e);
        }
    }
}
