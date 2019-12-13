package com.psybergate.absaassessment.mpatho.streamdemo;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class StreamsDemo {

    private static final Predicate<String> IS_EMPTY = String::isEmpty;

    public static void main(String[] args) {
        List<String> statuses = Arrays.asList("Received", "Pending", "", "Awaiting Maturity", "Completed", "");
        System.out.println("------Parallel Print-------");
        parallelPrint(statuses);
        System.out.println("------Sequential Print-----");
        sequentialPrint(statuses);
    }

    private static void parallelPrint(List<String> statuses) {
        statuses.parallelStream()
                .filter(IS_EMPTY.negate())
                .forEach(System.out::println);
    }

    private static void sequentialPrint(List<String> statuses) {
        statuses.stream()
                .filter(IS_EMPTY.negate())
                .forEach(System.out::println);
    }
}
