package segabank.menu;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

public class Tools {
    public static String getBoard(String msg) {
        msg = msg.toUpperCase();

        String border = "====================================";
        StringBuilder buildBorder = new StringBuilder(border);
        buildBorder.append(System.lineSeparator());
        Integer n = border.length();
        Integer m = n / 2;
        Integer y = msg.length() / 2;
        m = m - y - 2;
        Integer t = m * 2;
        StringBuilder buildMd = new StringBuilder();
        for (Integer i = 0; i != t; i++) {
            buildMd.append("=");
            if (i == m) {
                buildMd.append(" ");
                buildMd.append(msg);
                buildMd.append(" ");
            }
        }
        String md = buildMd.toString();
        Integer borderLength = border.length();
        while (md.length() != borderLength) {
            md += "=";
        }
        buildBorder.append(md);
        buildBorder.append(System.lineSeparator());
        buildBorder.append(border);
        return buildBorder.toString();
    }

    public static Integer getIntBetween(Integer a, Integer b, Scanner scanner, String msg, String error) {
        Integer n = a - 1;
        boolean typed = false;
        while (n < a || n > b) {
            if (typed) {
                System.out.println(error);
            }
            System.out.print(msg);
            n = scanner.nextInt();
            scanner.nextLine();
            typed = true;
        }
        return n;
    }

    public static Object modifForms(Object actual, Class<?> type) {
        if (type == Date.class) {
            actual = modifDate((Date)actual);
        } else if (type == String.class) {
            actual = modifString((String)actual);
        } else if (type == Integer.class) {
            actual = modifInteger((Integer)actual);
        } else if (type == Double.class) {
            actual = modifDouble((Double)actual);
        }

        return actual;
    }

    private static Double modifDouble(Double nb) {
        Scanner scanner = new Scanner(System.in);
        String tmp = scanner.nextLine();
        if (tmp.equals("")) {
            return nb;
        }
        return Double.parseDouble(tmp);
    }

    private static Integer modifInteger(Integer nb) {
        Scanner scanner = new Scanner(System.in);
        String tmp = scanner.nextLine();
        if (tmp.equals("")) {
            return nb;
        }
        return Integer.parseInt(tmp);
    }

    private static String modifString(String str) {
        Scanner scanner = new Scanner(System.in);
        String value = scanner.nextLine();
        if (value.equals(""))
            return str;
        return value;
    }

    public static Date modifDate(Date date) {
        Scanner scanner = new Scanner(System.in);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);

        Integer jour = cal.get(Calendar.DAY_OF_MONTH);
        jour = getValueBetweenActual(jour, 1, 30, scanner, "jour: ", "erreur de saisie");
        Integer mois = cal.get(Calendar.MONTH);
        mois = getValueBetweenActual(mois, 1, 12, scanner, "mois: ", "erreur de saisie");
        Integer annee = cal.get(Calendar.YEAR);
        annee = getValueBetweenActual(annee, 1970, 2045, scanner, "annee: ", "erreur de saisie");
        Integer heure = cal.get(Calendar.HOUR);
        heure = getValueBetweenActual(heure, 1, 24, scanner, "heure: ", "erreur de saisie");
        Integer minute = cal.get(Calendar.MINUTE);
        minute = getValueBetweenActual(minute, 1, 59, scanner, "minute: ", "erreur de saisie");
        Integer seconde = cal.get(Calendar.SECOND);
        seconde = getValueBetweenActual(seconde, 1, 59, scanner, "seconde:", "erreur de saisie");

        cal.set(Calendar.DAY_OF_MONTH, jour);
        cal.set(Calendar.MONTH, mois);
        cal.set(Calendar.YEAR, annee);
        cal.set(Calendar.HOUR, heure);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, seconde);

        return cal.getTime();
    }

    public static Integer getValueBetweenActual(Integer value, Integer a, Integer b, Scanner scanner, String msg, String error) {
        String n;
        Integer tmp = a - 1;
        boolean typed = false;
        while (tmp < a || tmp > b) {
            if (typed) {
                System.out.println(error);
            }
            System.out.print(msg);
            n = scanner.nextLine();
            if (n.equals(""))
                return value;

            try {
                tmp = Integer.parseInt(n);
            } catch (NumberFormatException e) {}

            typed = true;
        }
        return tmp;
    }

    public static Integer getIntSuperZero(Scanner scanner, String msg, String error) {
        boolean typed = false;
        Integer n = -1;
        while (n < 0) {
            if (typed) {
                System.out.println(error);
            }
            System.out.print(msg);
            n = scanner.nextInt();
            scanner.nextLine();
            typed = true;
        }
        return n;
    }

    public static Double getDoubleSuperZero(Scanner scanner, String msg, String error) {
        boolean typed = false;
        Double n = -1d;
        while (n < 0) {
            if (typed) {
                System.out.println(error);
            }
            System.out.print(msg);
            n = scanner.nextDouble();
            scanner.nextLine();
            typed = true;
        }
        return n;
    }

    public static Double getDoubleSuperZeroOrActual(Double actual, Scanner scanner, String msg, String error) {
        Double v;
        boolean typed = false;
        while (true) {
            if (typed) {
                System.out.println(error);
            }
            System.out.print(msg);
            String tmp = scanner.nextLine();
            if (tmp.equals("")) {
                return actual;
            }
            try {
                v = Double.parseDouble(tmp);
                if (v > 0)
                    return v;
                typed = true;
            } catch (NumberFormatException e) {}
        }

    }

    public static Integer getIntSuperZeroOrBlanck(Integer value, Scanner scanner, String msg, String error) {
        String tmp;
        Integer v = -1;
        boolean typed = false;
        while (v < 0) {
            if (typed) {
                System.out.println(error);
            }
            System.out.print(msg);
            tmp = scanner.nextLine();
            if (tmp.equals("")) {
                return value;
            }
            v = Integer.parseInt(tmp);
            typed = true;
        }
        return v;
    }
}
