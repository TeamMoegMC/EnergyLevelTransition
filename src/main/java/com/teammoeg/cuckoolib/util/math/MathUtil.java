/*
 *  Copyright (c) 2020. TeamMoeg
 *
 *  This file is part of Energy Level Transition.
 *
 *  Energy Level Transition is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *  Energy Level Transition is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Energy Level Transition.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.teammoeg.cuckoolib.util.math;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class MathUtil {
    private static final String[] M = {"", "M", "MM", "MMM"};
    private static final String[] C = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
    private static final String[] X = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    private static final String[] I = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

    private static final String[] HIGH = {"k", "M", "G", "T", "P", "E"};
    private static final String[] LOW = {"m", "\u03BC", "n", "p", "f", "a"};

    private static final String WORD = "fZodR9XQDSUm21yCkr6zBqiveYah8bt4xsWpHnJE7jL5VG3guMTKNPAwcF";
    private static final BiMap<Integer, String> WORD_MAP = HashBiMap.create(58);
    private static final BiMap<String, Integer> INVERSED_WORD_MAP = WORD_MAP.inverse();
    private static final int[] SET = {9, 8, 1, 6, 2, 4};
    private static final long XOR = 177451812;
    private static final long ADD = 8728348608l;

    static {
        for (int i = 0; i < WORD.length(); i++) {
            WORD_MAP.put(i, WORD.substring(i, i + 1));
        }
    }

    public static String encodeBv(long av) {
        av = (av ^ XOR) + ADD;
        String bv = "1  4 1 7  ";
        for (int i = 0; i < 6; i++) {
            String update = WORD_MAP.get((int) ((av / Math.pow(58, i)) % 58));
            bv = bv.substring(0, SET[i]) + update + bv.substring(SET[i] + 1);
        }
        return bv;
    }

    public static long decodeBv(String bv) {
        long av = 0;
        for (int i = 0; i < 6; i++) {
            av += ((long) INVERSED_WORD_MAP.get(bv.substring(SET[i], SET[i] + 1))) * Math.pow(58, i);
        }
        av = (av - ADD) ^ XOR;
        return av;
    }

    public static String romanNumber(int n) {
        if (n == 0) {
            return null;
        }
        return n < 4000 ? M[n / 1000] + C[(n % 1000) / 100] + X[(n % 100) / 10] + I[n % 10] : "";
    }

    public static String formatOrder(double num) {
        if (num == 0) {
            return "0";
        }
        String str = "";
        if (num < 0) {
            num = -num;
        }
        int i;
        if (num >= 1000) {
            for (i = -1; num >= 1000; i++) {
                num /= 1000;
            }
            str += HIGH[i];
        } else if (num < 1) {
            for (i = -1; num < 1; i++) {
                num *= 10;
                num *= 10;
                num *= 10;
            }
            str += LOW[i];
        }
        return str;
    }

    public static String formatNumber(double num) {
        if (num == 0) {
            return "0";
        }
        String str = "";
        if (num < 0) {
            num = -num;
            str += "-";
        }
        int i;
        if (1 <= num && num < 1000) {
            if ((int) (num * 10 % 10) == 0) {
                str += String.format("%.0f", num);
            } else {
                str += String.format("%.1f", num);
            }
        } else if (num >= 1000) {
            for (i = -1; num >= 1000 && i < 6; i++) {
                num /= 1000;
            }
            if ((int) (num * 10 % 10) == 0) {
                str += String.format("%.0f", num);
            } else {
                str += String.format("%.1f", num);
            }
        } else {
            for (i = -1; num < 1 && i < 6; i++) {
                num *= 1000;
            }
            if ((int) (num * 10 % 10) == 0) {
                str += String.format("%.0f", num);
            } else {
                str += String.format("%.1f", num);
            }
        }
        return str;
    }

    public static String format(double num) {
        return formatNumber(num) + formatOrder(num);
    }

    public static boolean between(int value, int min, int max) {
        return value >= min && value <= max;
    }

    public static boolean between(long value, long min, long max) {
        return value >= min && value <= max;
    }

    public static boolean between(float value, float min, float max) {
        return value >= min && value <= max;
    }

    public static boolean between(double value, double min, double max) {
        return value >= min && value <= max;
    }

    public static int getTrueBits(int num) {
        int cnt = 0;
        while (num != 0) {
            num &= (num - 1);
            cnt++;
        }
        return cnt;
    }

    public static int getTrueBits(long num) {
        int cnt = 0;
        while (num != 0) {
            num &= (num - 1);
            cnt++;
        }
        return cnt;
    }

    public static long gcd(long a, long b) {
        long t;
        while (b > 0) {
            t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    public static long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }
}