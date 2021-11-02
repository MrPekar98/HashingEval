package com.aau.evaluation;

import com.aau.evaluation.evaluators.Evaluatable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class EvaluationLogger
{
    private List<Evaluatable> evaluators;
    private final List<Future<String>> futures = new ArrayList<>();
    private Thread log = null;

    public EvaluationLogger(List<Evaluatable> evaluators)
    {
        this.evaluators = evaluators;
        loadFutures();
    }

    private void loadFutures()
    {
        for (Evaluatable eval : this.evaluators)
        {
            this.futures.add(Executors.newSingleThreadExecutor().submit(() ->
                    eval.title() + ": " + eval.eval() + eval.unit()));
        }
    }

    private double progress()
    {
        int count = 0;

        for (Future<String> future : this.futures)
        {
            count += future.isDone() ? 1 : 0;
        }

        return ((double) count / this.futures.size()) * 100;
    }

    private static void logProgress(double prog)
    {
        System.out.println("Progress: " + prog + "%");
    }

    private void logResults()
    {
        for (Future<String> future : this.futures)
        {
            try
            {
                System.out.println(future.get());
            }

            catch (ExecutionException | InterruptedException exc)
            {
                System.err.println("An com.aau.evaluation.evaluation has failed due to multithreading");
            }
        }
    }

    private void startProgressLogging()
    {
        if (this.log != null)
            this.log.interrupt();

        (this.log = new Thread(() -> {
            double progress, prev = -1;

            while ((progress = progress()) != 100)
            {
                if (progress != prev)
                {
                    prev = progress;
                    logProgress(progress);
                }

                attemptSleeping(10000);
            }
        })).start();
    }

    private static void attemptSleeping(long millis)
    {
        try
        {
            Thread.sleep(millis);
        }

        catch (InterruptedException exc) {}
    }

    public void computeAndLog()
    {
        startProgressLogging();
        logResults();
    }
}
