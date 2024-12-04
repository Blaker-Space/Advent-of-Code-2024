/*Author: Blake Mills
 * This program was written to solve the second puzzle for the second day of Advent of Code (AoC)
 * To view the problem, visit  https://adventofcode.com/2024/day/2
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SafeReports {
    public static String inputFile = "reports.txt";

    public static void main(String[] args) {
        int numSafeReports = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                List<Integer> report = parseLineToList(line);

                if (isSafeReport(report)) {
                    numSafeReports++;
                }
            }

            System.out.println("Total Safe Reports: " + numSafeReports);
        } catch (IOException e) {
            System.out.println("File read error: " + e);
        }
    }

    private static List<Integer> parseLineToList(String line) {
        List<Integer> list = new ArrayList<>();
        for (String num : line.split(" ")) {
            list.add(Integer.parseInt(num));
        }
        return list;
    }

    public static boolean isSafeReport(List<Integer> report) {
        return checkReport(report, true) || checkReport(report, false);
    }

    private static boolean checkReport(List<Integer> report, boolean forward) {
        for (int i = 0; i <= report.size(); i++) {
            List<Integer> modifiedReport = new ArrayList<>(report);
            if (i < report.size()) {
                modifiedReport.remove(i);
            }
            if (isValid(modifiedReport, forward)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValid(List<Integer> report, boolean forward) {
        if (report.size() < 2) {
            return false;
        }

        boolean decreasing = forward ? report.get(1) < report.get(0) : report.get(report.size() - 2) < report.get(report.size() - 1);
        int prev = forward ? report.get(0) : report.get(report.size() - 1);

        for (int i = forward ? 1 : report.size() - 2; 
             forward ? i < report.size() : i >= 0; 
             i += forward ? 1 : -1) {
            int current = report.get(i);

            // Check rate of change
            int diff = Math.abs(current - prev);
            if (diff < 1 || diff > 3) {
                return false;
            }

            // Check ordering
            if ((decreasing && current > prev) || (!decreasing && current < prev)) {
                return false;
            }

            prev = current;
        }

        return true;
    }
}
