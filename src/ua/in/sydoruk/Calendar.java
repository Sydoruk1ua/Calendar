package ua.in.sydoruk;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

class Calendar {
    private static final String BLUE = "\u001B[1;34m";
    private static final String RED = "\u001B[31m";
    private static final String BLACK = "\u001B[30m";

    private int month;
    private int year;

    public Calendar() {
        this.month = LocalDate.now().getMonth().getValue();
        this.year = LocalDate.now().getYear();
    }

    public Calendar(String month) {
        this(month, String.valueOf(LocalDate.now().getYear()));
    }

    public Calendar(String month, String year) {
        try {
            this.month = Integer.parseInt(month);
            if (this.month < 1 || this.month > 12) {
                System.out.println("Invalid value for month (valid values 1 - 12)");
                System.exit(1);
            }
            this.year = Integer.parseInt(year);
        } catch (NumberFormatException e) {
            System.out.println("Wrong date format: " + month + " " + year + "\n" +
                    "Example: \"10\" or \"10 2015\"  where 10-month, 2015-year ");
            System.exit(1);
        }
    }

    private String highlightDate(int day) {
        if (day == LocalDate.now().getDayOfMonth())
            if (month == LocalDate.now().getMonthValue() && year == LocalDate.now().getYear()) {
                return BLUE;
            }
        if (LocalDate.of(year, month, day).getDayOfWeek() == DayOfWeek.SATURDAY ||
                LocalDate.of(year, month, day).getDayOfWeek() == DayOfWeek.SUNDAY) {
            return RED;
        }
        return BLACK;
    }

    private String highlightDate(DayOfWeek dayOfWeek) {
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return RED;
        }
        return BLACK;
    }

    public void printToConsole() {

        Locale locale = Locale.getDefault();
        for (DayOfWeek d : DayOfWeek.values()) {
            System.out.print(highlightDate(d));
            System.out.printf("%5s", d.getDisplayName(TextStyle.SHORT, locale));
            if (d == DayOfWeek.SUNDAY) {
                System.out.printf("%n", "");
            }
        }

        if (LocalDate.of(year, month, 1).getDayOfWeek() != DayOfWeek.MONDAY) {
            int offset = (LocalDate.of(year, month, 1).getDayOfWeek().getValue() - 1) * 5;
            System.out.printf("%" + offset + "s", "");
        }

        for (int i = 1; i <= LocalDate.of(year, month, 1).lengthOfMonth(); i++) {
            System.out.print(highlightDate(i));
            System.out.printf("%5s", i);
            if (LocalDate.of(year, month, i).getDayOfWeek() == DayOfWeek.SUNDAY || i == LocalDate.of(year, month, 1).lengthOfMonth()) {
                System.out.printf("%n", "");
            }
        }
    }
}
