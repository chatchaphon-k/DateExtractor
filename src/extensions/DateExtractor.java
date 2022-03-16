package extensions;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateExtractor
{
    //patt = pattern
    private static String patt_year = "([2-9]\\d\\d\\d)";
    private static String patt_months = "([0-1]\\d)";
    private static String patt_days = "([0-3]\\d)";

    // <editor-fold defaultstate="collapsed" desc="Getter - YYYYMMDD">

    public static String get_YYYYMMDD(String text)
    {
        Pattern date_pattern = Pattern.compile(patt_year + patt_months + patt_days);
        return get_date(text, date_pattern);
    }

    public static String get_YYYYMMDD_delimitByhyphen(String text)
    {
        return get_YYYYMMDD_wDelimiter(text, "-");
    }

    public static String get_YYYYMMDD_wDelimiter(String text, String delimiter)
    {
        String date = get_YYYYMMDD(text);
        return date.substring(0, 4) + delimiter + date.substring(4, 6) + delimiter + date.substring(6, 8);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public static String get_YYYYMMDD_buddhistCalendar_convertFromDDMMYYYY(String DDMMYYYY_noDelimiter)
    {
        Map<String, String> k2v = get_DDMMYYYY_separated_buddhistCalendar(DDMMYYYY_noDelimiter);
        return get_YYYYMMDD(k2v.get("YYYY"), k2v.get("MM"), k2v.get("DD"));
    }

    public static String get_YYYYMMDD_buddhistCalendar(String YYYYMMDD_noDelimiter)
    {
        Map<String, String> k2v = get_YYYYMMDD_separated_buddhistCalendar(YYYYMMDD_noDelimiter);
        return get_YYYYMMDD(k2v.get("YYYY"), k2v.get("MM"), k2v.get("DD"));
    }
    
    public static Map<String, String> get_YYYYMMDD_separated_buddhistCalendar(String YYYYMMDD_noDelimiter)
    {
        Map<String, String> k2v = get_YYYYMMDD_separated(YYYYMMDD_noDelimiter);
        k2v.put("YYYY", get_year_buddhistCalendar(k2v.get("YYYY")));
        return k2v;
    }

    public static Map<String, String> get_YYYYMMDD_separated(String date_noDelimiter)
    {
        Map<String, String> k2v = new HashMap<>();
        k2v.put("YYYY", date_noDelimiter.substring(0, 4));
        k2v.put("MM", date_noDelimiter.substring(4, 6));
        k2v.put("DD", date_noDelimiter.substring(6, 8));
        return k2v;        
    }
    
    public static String get_YYYYMMDD(String dd, String mm, String yyyy)
    {
        return yyyy + mm + dd;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getter - DDMMYYYY">

    public static String get_DDMMYYYY_buddhistCalendar_convertFromYYYYMMDD(String YYYYMMDD_noDelimiter)
    {
        Map<String, String> k2v = get_YYYYMMDD_separated_buddhistCalendar(YYYYMMDD_noDelimiter);
        return get_DDMMYYYY(k2v.get("DD"), k2v.get("MM"), k2v.get("YYYY"));
    }

    public static String get_DDMMYYYY_buddhistCalendar(String DDMMYYYY_noDelimiter)
    {
        Map<String, String> k2v = get_DDMMYYYY_separated_buddhistCalendar(DDMMYYYY_noDelimiter);
        return get_DDMMYYYY(k2v.get("DD"), k2v.get("MM"), k2v.get("YYYY"));
    }
    
    public static Map<String, String> get_DDMMYYYY_separated_buddhistCalendar(String DDMMYYYY_noDelimiter)
    {
        Map<String, String> k2v = get_DDMMYYYY_separated(DDMMYYYY_noDelimiter);
        k2v.put("YYYY", get_year_buddhistCalendar(k2v.get("YYYY")));
        return k2v;
    }

    public static Map<String, String> get_DDMMYYYY_separated(String DDMMYYYY_noDelimiter)
    {
        Map<String, String> k2v = new HashMap<>();
        k2v.put("DD", DDMMYYYY_noDelimiter.substring(0, 2));
        k2v.put("MM", DDMMYYYY_noDelimiter.substring(2, 4));
        k2v.put("YYYY", DDMMYYYY_noDelimiter.substring(4, 8));
        return k2v;
    }

    public static String get_DDMMYYYY(String dd, String mm, String yyyy)
    {
        return dd + mm + yyyy;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getter - pattern methods">
    
    public static String get_date_noDay(String text)
    {
        Pattern pattern = Pattern.compile(patt_year + patt_months);
        return get_date(text, pattern);
    }

    public static String get_date_yearOnly(String text)
    {
        Pattern pattern = Pattern.compile(patt_year);
        return get_date(text, pattern);
    }

    public static String get_date(String text, Pattern pattern)
    {
        System.out.print("\tEXTRACTING date from " + text);
        Matcher matcher = pattern.matcher(text);
        if(matcher.find())
        {
            System.out.println(", found : " + matcher.group());
            return matcher.group();
        }
        System.out.println(", not found");
        return "";
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getter - substring methods">

    public static String get_date_asBeginningOfMonth(String date)
    {
        int end_i = 6;
        if(date.contains("-") || date.contains("/"))
            end_i = 8;
        return date.substring(0, end_i) + "01";
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getter - Convert year format">

    public static String get_year_buddhistCalendar(String year_christianEra)
    {
        return "" + get_year_buddhistCalendar(Integer.parseInt(year_christianEra));
    }

    public static int get_year_buddhistCalendar(int year_christianEra)
    {
        return year_christianEra + 543;
    }
    public static String get_year_christianEra(String year_buddhistCalendar)
    {
        return "" + get_year_christianEra(Integer.parseInt(year_buddhistCalendar));
    }

    public static int get_year_christianEra(int year_buddhistCalendar)
    {
        return year_buddhistCalendar - 543;
    }

    // </editor-fold>
}
