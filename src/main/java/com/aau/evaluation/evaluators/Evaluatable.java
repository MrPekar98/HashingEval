package com.aau.evaluation.evaluators;

public interface Evaluatable<R>
{
    String title();
    R eval();
    String unit();
    double progress();
}
