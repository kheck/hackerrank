package nonDivisibleSubset;

import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

// https://www.hackerrank.com/challenges/non-divisible-subset/problem

class Result {

    /*
     * Complete the 'nonDivisibleSubset' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER k
     *  2. INTEGER_ARRAY s
     */

    public static int nonDivisibleSubset(int nonFactor, List<Integer> numbers) {
        return permute(numbers).stream()
            .filter(permutation -> allPossiblePairsSatisfy(permutation, (x, y) -> (x + y) % nonFactor != 0 ))
            .mapToInt(List::size)
            .max()
            .getAsInt();
    }

    public static <T> List<List<T>> permute(List<T> items) {
        if (items.size() == 1) {
            return Arrays.asList(items, new ArrayList<>());
        }

        T head = items.get(0);
        List<T> tail = items.subList(1, items.size());

        List<List<T>> permutationWithoutHead = permute(tail);
        List<List<T>> permutationWithHead =  permutationWithoutHead.stream()
            .map(list -> {
                LinkedList<T> withHead = new LinkedList<>(list);
                withHead.addFirst(head);
                return withHead;
            })
            .collect(Collectors.toList());

        List<List<T>> result = new ArrayList<>();
        result.addAll(permutationWithHead);
        result.addAll(permutationWithoutHead);

        return result;
    }

    public static <T> boolean allPossiblePairsSatisfy(List<T> items, BiFunction<T, T, Boolean> biPredicate) {
        for (int i = 0; i < items.size(); i++) {
            for (int j = i + 1; j < items.size(); j++) {
                if (i == j) {
                    continue;
                }

                if (!biPredicate.apply(items.get(i), items.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int k = Integer.parseInt(firstMultipleInput[1]);

        List<Integer> s = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Integer::parseInt)
                .collect(toList());

        int result = Result.nonDivisibleSubset(k, s);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

