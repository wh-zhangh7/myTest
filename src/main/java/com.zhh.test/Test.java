package com.zhh;
import java.util.*;

public class Test {

    public static void main(String[] args) {
        String t = "44201530300052503434";
        Long s = Long.parseLong(t);
        System.out.print(s);

    }

    public static boolean containsUserDefinedUnicode(String string) {
        if (string == null) {
            throw new NullPointerException("Stirng must be non-null");
        }
        int[] code = toCodePointArray(string);
        //  U+E000..U+F8FF
        for (int c : code) {
            if (c >= '\ue000' && c <= '\uf8ff') {
                return true;
            }
        }
        return false;
    }

    static int[] toCodePointArray(String str) {
        int len = str.length();
        int[] acp = new int[str.codePointCount(0, len)];

        for (int i = 0, j = 0; i < len; i = str.offsetByCodePoints(i, 1)) {
            acp[j++] = str.codePointAt(i);
        }
        return acp;
    }

    static String toHex(int[] chars) {
        String r = "[";
        for (int i=0; i<chars.length; i++) {
            if (r.length() > 1) {
                r += ",";
            }
            r += Integer.toHexString(chars[i]);
        }
        r += "]";
        return r;
    }

    public static void main(String[] argu) {
//        String rr = ("哲\uE07E");//\
//        System.out.println("Unicode = " + toHex(toCodePointArray(rr)));
//
//        boolean r = (containsUserDefinedUnicode(rr));
//        System.out.println("Test result = " + r + " should be true");

//        Calendar cal = Calendar.getInstance();
//
//        Date date = DateUtils.truncate(cal.getTime(), Calendar.MONTH);
//
//        System.out.println(DateUtil.date2Str(date));

        System.out.println(dateChangeNew("20181130","1100",1L,1L));
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(DateUtil.str2Date("20181129", DateUtil.YYYYMMDD));
//        System.out.println(DateUtil.date2Str(cal.getTime(), "YYYYMM"));

    }

    private static String dateChangeNew(String pcszStartDate, String pcszUnit,Long nTimeLen, Long dynbill_day) {
        String sDateRlt = "";
        String startdate = "01";
        long start_day = 0;
        long last_day = 0;
        String day = "";
        String strdynbill_day = "";

        if (dynbill_day == 0) {
            dynbill_day = 1L;
        }

        if (8 > pcszStartDate.length()) {
            // 输入日期长度不满足要求
            return sDateRlt;
        } else if (nTimeLen < 1) {
            // 计算时长不满足要求
            return sDateRlt;
        } else if (!PeriodUnit.M.equals(pcszUnit)) {
            // 计算时长非M
            return sDateRlt;
        }

        startdate = pcszStartDate.substring(6, 8);
        start_day = Long.parseLong(startdate);
        if (start_day < dynbill_day) {
            nTimeLen = nTimeLen - 1;
        }
        if (dynbill_day < 10) {
            strdynbill_day = "0" + String.valueOf(dynbill_day);
        } else {
            strdynbill_day = String.valueOf(dynbill_day);
        }

        if(pcszStartDate.length() > 8)
            pcszStartDate = pcszStartDate.substring(0, 8);
        Date date = CalendarUtils.dateAdd(DateUtil.str2Date(pcszStartDate, DateUtil.YYYYMMDD),
                PeriodUnit.M, nTimeLen.intValue());
        if (date == null) {
            return "";
        }

        sDateRlt = DateUtil.date2Str(date,"yyyyMM")+strdynbill_day+"000000";

        day = sDateRlt.substring(0, 6) + "01";
        Date lastDay = CalendarUtils.getLastDayInCurrentMonth(DateUtil.str2Date(day, DateUtil.YYYYMMDD));
        last_day =  Long.valueOf(DateUtil.date2Str(lastDay,"DD"));
        if (last_day <= 0) {
            return "";
        }

        if (dynbill_day > last_day) {
            sDateRlt = sDateRlt.substring(0, 6) + String.valueOf(last_day);
        }
        return sDateRlt;
    }

}
