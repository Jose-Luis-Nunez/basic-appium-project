package appium.utils

import org.junit.runners.Parameterized
import org.junit.runners.model.RunnerScheduler
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class Parallelized(klass: Class<*>?) : Parameterized(klass) {
    private class ThreadPoolScheduler : RunnerScheduler {
        private val executor: ExecutorService
        override fun finished() {
            executor.shutdown()
            try {
                executor.awaitTermination(10, TimeUnit.MINUTES)
            } catch (exc: InterruptedException) {
                throw RuntimeException(exc)
            }
        }

        override fun schedule(childStatement: Runnable) {
            executor.submit(childStatement)
        }

        init {
            val threads = System.getProperty("junit.parallel.threads", "16")
            val numThreads = threads.toInt()
            executor = Executors.newFixedThreadPool(numThreads)
        }
    }

    init {
        setScheduler(ThreadPoolScheduler())
    }
}
