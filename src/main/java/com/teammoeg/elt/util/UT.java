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

package com.teammoeg.elt.util;

import com.teammoeg.the_seed.code.ArrayListNoNulls;
import net.minecraft.entity.Entity;

import java.text.DateFormat;
import java.util.*;

import static com.teammoeg.the_seed.data.legacy.CS.*;

/**
 * @author YueSha (GitHub @yuesha-yc)
 * Common Utils
 */
public class UT {
    public static byte side(int aSide) {
        return aSide > 5 || aSide < 0 ? SIDE_INVALID : (byte)aSide;
    }

    /** If this Index exists inside the passed Array and if it is != null */
    public static <E> boolean exists(int aIndex, E[] aArray) {
        return aIndex >= 0 && aIndex < aArray.length && aArray[aIndex] != null;
    }

    /** @return a Value for a Scale between 0 and aMax with aScale+1 possible Steps. 0 is only returned if the aValue is <= 0, aScale is only returned if the Value is >= aMax. The remaining values between ]0:aScale[ are returned for each Step of the Scale. This Function finds use in Displays such as the Barometer, but also in Redstone. */
    public static long scale(long aValue, long aMax, long aScale, boolean aInvert) {
        long rScale = (aValue <= 0 ? 0 : aValue >= aMax ? aScale : aScale <= 2 ? 1 : 1 + (aValue * (aScale-1)) / aMax);
        return aInvert ? aScale - rScale : rScale;
    }
    /** @return a Value for a Scale between aMin and aMax with aScale+1 possible Steps. 0 is only returned if the aValue is <= aMin, aScale is only returned if the Value is >= aMax. The remaining values between ]0:aScale[ are returned for each Step of the Scale. This Function finds use in Displays such as the Barometer, but also in Redstone. */
    public static long scale(long aValue, long aMin, long aMax, long aScale, boolean aInvert) {
        return scale(aValue-aMin, aMax-aMin, aScale, aInvert);
    }

    public static long bind(long aMin, long aMax, long aBoundValue) {
        return aMin > aMax ? Math.max(aMax, Math.min(aMin, aBoundValue)) : Math.max(aMin, Math.min(aMax, aBoundValue));
    }
    public static long bind_(long aMin, long aMax, long aBoundValue) {
        return Math.max(aMin, Math.min(aMax, aBoundValue));
    }

    public static float  bindF    (float  aBoundValue) {return        Math.max(0, Math.min(         1, aBoundValue));}
    public static double bindD    (double aBoundValue) {return        Math.max(0, Math.min(         1, aBoundValue));}
    public static byte   bind1    (long   aBoundValue) {return (byte) Math.max(0, Math.min(         1, aBoundValue));}
    public static byte   bind2    (long   aBoundValue) {return (byte) Math.max(0, Math.min(         3, aBoundValue));}
    public static byte   bind3    (long   aBoundValue) {return (byte) Math.max(0, Math.min(         7, aBoundValue));}
    public static byte   bind4    (long   aBoundValue) {return (byte) Math.max(0, Math.min(        15, aBoundValue));}
    public static byte   bind5    (long   aBoundValue) {return (byte) Math.max(0, Math.min(        31, aBoundValue));}
    public static byte   bind6    (long   aBoundValue) {return (byte) Math.max(0, Math.min(        63, aBoundValue));}
    public static byte   bind7    (long   aBoundValue) {return (byte) Math.max(0, Math.min(       127, aBoundValue));}
    public static short  bind8    (long   aBoundValue) {return (short)Math.max(0, Math.min(       255, aBoundValue));}
    public static short  bind15   (long   aBoundValue) {return (short)Math.max(0, Math.min(     32767, aBoundValue));}
    public static int    bind16   (long   aBoundValue) {return (int)  Math.max(0, Math.min(     65535, aBoundValue));}
    public static int    bind24   (long   aBoundValue) {return (int)  Math.max(0, Math.min(  16777215, aBoundValue));}
    public static int    bind31   (long   aBoundValue) {return (int)  Math.max(0, Math.min(2147483647, aBoundValue));}
    public static int    bindInt  (long   aBoundValue) {return (int)  Math.max(Integer.MIN_VALUE, Math.min(Integer.MAX_VALUE, aBoundValue));}
    public static short  bindShort(long   aBoundValue) {return (short)Math.max(Short.MIN_VALUE, Math.min(Short.MAX_VALUE, aBoundValue));}
    public static byte   bindByte (long   aBoundValue) {return (byte) Math.max(Byte.MIN_VALUE, Math.min(Byte.MAX_VALUE, aBoundValue));}
    public static byte   bindStack(long   aBoundValue) {return (byte) Math.max(1, Math.min(64, aBoundValue));}

    public static short[] bindRGBa(short[] aColors) {
        if (aColors == null) return new short[] {255,255,255,255};
        for (int i = 0; i < aColors.length; i++) aColors[i] = bind8(aColors[i]);
        return aColors;
    }

    public static int mixRGBInt(int aRGB1, int aRGB2) {
        return getRGBInt(new short[] {(short)((getR(aRGB1) + getR(aRGB2)) >> 1), (short)((getG(aRGB1) + getG(aRGB2)) >> 1), (short)((getB(aRGB1) + getB(aRGB2)) >> 1)});
    }

    public static int getRGBInt(short[] aColors) {
        return aColors == null ? 16777215 : (bind8(aColors[0]) << 16) | (bind8(aColors[1]) << 8) | bind8(aColors[2]);
    }

    public static int getRGBaInt(short[] aColors) {
        return aColors == null ? 16777215 : (bind8(aColors[0]) << 16) | (bind8(aColors[1]) << 8) | bind8(aColors[2]) | (bind8(aColors[3]) << 24);
    }

    public static int getRGBInt(long aR, long aG, long aB) {
        return (bind8(aR) << 16) | (bind8(aG) << 8) | bind8(aB);
    }

    public static int getRGBaInt(long aR, long aG, long aB, long aA) {
        return (bind8(aR) << 16) | (bind8(aG) << 8) | bind8(aB) | (bind8(aA) << 24);
    }

    public static short[] getRGBaArray(int aColors) {
        return new short[] {(short)((aColors >>> 16) & 255), (short)((aColors >>> 8) & 255), (short)(aColors & 255), (short)((aColors >>> 24) & 255)};
    }

    public static short getR(int aColors) {return (short)((aColors >>> 16) & 255);}
    public static short getG(int aColors) {return (short)((aColors >>>  8) & 255);}
    public static short getB(int aColors) {return (short) (aColors         & 255);}
    public static short getA(int aColors) {return (short)((aColors >>> 24) & 255);}

    public static class Code {
        /** Note: Does not work on huge amounts of Bytes. */
        public static byte averageBytes(byte... aBytes) {
            if (aBytes == null || aBytes.length <= 0) return 0;
            return (byte)(sum(aBytes) / aBytes.length);
        }

        /** Note: Does not work on huge amounts of Bytes. */
        public static byte averageUnsignedBytes(byte... aBytes) {
            if (aBytes == null || aBytes.length <= 0) return 0;
            long rValue = 0;
            for (byte aByte : aBytes) rValue += unsignB(aByte);
            return (byte)(rValue / aBytes.length);
        }

        /** Note: Does not work on huge amounts of Bytes. */
        public static short averageShorts(short... aShorts) {
            if (aShorts == null || aShorts.length <= 0) return 0;
            return (short)(sum(aShorts) / aShorts.length);
        }

        /** Note: Does not work on huge amounts of Shorts. */
        public static short averageUnsignedShorts(short... aShorts) {
            if (aShorts == null || aShorts.length <= 0) return 0;
            long rValue = 0;
            for (short aShort : aShorts) rValue += unsignS(aShort);
            return (short)(rValue / aShorts.length);
        }

        /** Note: Does not work on huge amounts of Integers. */
        public static int averageInts(int... aInts) {
            if (aInts == null || aInts.length <= 0) return 0;
            return bindInt(sum(aInts) / aInts.length);
        }

        /** Note: Does not work on huge amounts of Integers. */
        public static int averageUnsignedInts(int... aInts) {
            if (aInts == null || aInts.length <= 0) return 0;
            long rValue = 0;
            for (int aInt : aInts) rValue += unsignI(aInt);
            return bindInt(rValue / aInts.length);
        }

        /** Note: Does not work on huge amounts of Longs. */
        public static long averageLongs(long... aLongs) {
            if (aLongs == null || aLongs.length <= 0) return 0;
            return sum(aLongs) / aLongs.length;
        }

        public static int roundDown(double aNumber) {
            int rRounded = (int)aNumber;
            return rRounded > aNumber ? rRounded-1 : rRounded;
        }
        public static int roundUp(double aNumber) {
            int rRounded = (int)aNumber;
            return rRounded < aNumber ? rRounded+1 : rRounded;
        }

        /** @return an unsigned representation of this Byte. */
        public static short unsignB(byte aByte) {
            return aByte < 0 ? (short)(aByte + 256) : aByte;
        }

        /** @return an unsigned representation of this Short. */
        public static int unsignS(short aShort) {
            return aShort < 0 ? aShort + 65536 : aShort;
        }

        /** @return an unsigned representation of this Integer. */
        public static long unsignI(int aInteger) {
            return aInteger < 0 ? aInteger + 4294967296L : aInteger;
        }

        public static byte toByteS(short aValue, int aIndex) {return (byte)(aValue >> (aIndex<<3));}
        public static byte toByteI(int   aValue, int aIndex) {return (byte)(aValue >> (aIndex<<3));}
        public static byte toByteL(long  aValue, int aIndex) {return (byte)(aValue >> (aIndex<<3));}

        public static short combine(byte aValue1, byte aValue2)                                                                                     {return (short) ((0xff & aValue1) | aValue2 << 8);}
        public static int   combine(byte aValue1, byte aValue2, byte aValue3, byte aValue4)                                                         {return          (0xff & aValue1) | (0xff & aValue2) << 8 | (0xff & aValue3) << 16 | aValue4 << 24;}
        public static long  combine(byte aValue1, byte aValue2, byte aValue3, byte aValue4, byte aValue5, byte aValue6, byte aValue7, byte aValue8) {return ((long)aValue1 & 0xff) | ((long)aValue2 & 0xff) << 8 | ((long)aValue3 & 0xff) << 16 | ((long)aValue4 & 0xff) << 24 | ((long)aValue5 & 0xff) << 32 | ((long)aValue6 & 0xff) << 40 | ((long)aValue7 & 0xff) << 48 | (long)aValue8 << 56;}

        public static long getBits(boolean... aBits) {
            long rBits = 0;
            for (int i = 0; i < 64 && i < aBits.length; i++) if (aBits[i]) rBits |= (1 << i);
            return rBits;
        }

        public static boolean[] getBitsB(byte aBits) {
            boolean[] rBits = new boolean[8];
            for (int i = 0; i < rBits.length; i++) if ((aBits & (1 << i)) != 0) rBits[i] = T;
            return rBits;
        }

        public static boolean[] getBitsS(short aBits) {
            boolean[] rBits = new boolean[16];
            for (int i = 0; i < rBits.length; i++) if ((aBits & (1 << i)) != 0) rBits[i] = T;
            return rBits;
        }

        public static boolean[] getBitsI(int aBits) {
            boolean[] rBits = new boolean[32];
            for (int i = 0; i < rBits.length; i++) if ((aBits & (1 << i)) != 0) rBits[i] = T;
            return rBits;
        }

        public static boolean[] getBitsL(long aBits) {
            boolean[] rBits = new boolean[64];
            for (int i = 0; i < rBits.length; i++) if ((aBits & (1 << i)) != 0) rBits[i] = T;
            return rBits;
        }

//        public static ItemStack toStack(int aStack) {
//            Item tItem = Item.getItemById(aStack&(~0>>>16));
//            if (tItem != null) return ST.make(tItem, 1, aStack>>>16);
//            return null;
//        }

        public static UUID getUUID(byte[] aData, int aOffset) {
            return aData.length - aOffset < 16 ? null : new UUID(combine(aData[aOffset], aData[aOffset+1], aData[aOffset+2], aData[aOffset+3], aData[aOffset+4], aData[aOffset+5], aData[aOffset+6], aData[aOffset+7]), combine(aData[aOffset+8], aData[aOffset+9], aData[aOffset+10], aData[aOffset+11], aData[aOffset+12], aData[aOffset+13], aData[aOffset+14], aData[aOffset+15]));
        }

        public static byte[] getBytes(UUID aData, int aOffset) {
            if (aData == null) return new byte[aOffset];
            byte[] rData = new byte[aOffset+16];
            for (int i = 0; i < 8; i++) {
                rData[aOffset+  i] = toByteL(aData.getMostSignificantBits(), i);
                rData[aOffset+8+i] = toByteL(aData.getLeastSignificantBits(), i);
            }
            return rData;
        }

        /** Converts a Number to a String */
        public static String makeString(long aNumber) {
            String tString = "";
            boolean temp = T, negative = F;

            if (aNumber<0) {
                aNumber *= -1;
                negative = T;
            }

            for (long i = 1000000000000000000L; i > 0; i /= 10) {
                long tDigit = (aNumber/i)%10;
                if ( temp && tDigit != 0) temp = F;
                if (!temp) {
                    tString += tDigit;
                    if (i != 1) for (long j = i; j > 0; j /= 1000) if (j == 1) tString += ",";
                }
            }

            if (tString.equals("")) tString = "0";

            return negative?"-"+tString:tString;
        }

        @SafeVarargs
        public static <E> boolean contains(E aTarget, E... aArray) {
            if (aArray != null) for (E tValue : aArray) if (tValue == aTarget || (tValue != null && aTarget != null && tValue.equals(aTarget))) return T;
            return F;
        }

        public static boolean containsBoolean(boolean aTarget, boolean... aArray) {
            if (aArray != null) for (boolean tValue : aArray) if (tValue == aTarget) return T;
            return F;
        }

        @SafeVarargs
        public static <E> boolean containsSomething(E... aArray) {
            if (aArray != null) for (Object tObject : aArray) if (tObject != null) return T;
            return F;
        }

        public static <E> E[] fill(E aToFillIn, E[] rArray) {
            for (int i = 0; i < rArray.length; i++) rArray[i] = aToFillIn;
            return rArray;
        }

        @SafeVarargs
        public static <E> E[] makeArray(E[] rArray, E... aArray) {
            for (int i = 0; i < rArray.length && i < aArray.length; i++) rArray[i] = aArray[i];
            return rArray;
        }

        @SafeVarargs
        public static <E> long getNonNulls(E... aArray) {
            long rAmount = 0;
            if (aArray != null) for (Object tObject : aArray) if (tObject != null) rAmount++;
            return rAmount;
        }

        @SafeVarargs
        public static <E> ArrayList<E> getWithoutNulls(E... aArray) {
            if (aArray == null) return new ArrayListNoNulls<>();
            ArrayList<E> rList = new ArrayListNoNulls<>(Arrays.asList(aArray));
            return rList;
        }

        @SafeVarargs
        public static <E> ArrayList<E> getWithoutTrailingNulls(E... aArray) {
            if (aArray == null) return new ArrayList<>(1);
            ArrayList<E> rList = new ArrayList<>(Arrays.asList(aArray));
            for (int i = rList.size() - 1; i >= 0 && rList.get(i) == null;) rList.remove(i--);
            return rList;
        }

        @SafeVarargs
        public static <E> E getFirstNonNull(E... aArray) {
            if (aArray != null) for (E tObject : aArray) if (tObject != null) return tObject;
            return null;
        }

        private static final DateFormat sDateFormat = DateFormat.getInstance();
        public static String dateAndTime() {
            return sDateFormat.format(new Date());
        }

        public static byte tier(long aSize) {
            return tierMax(aSize);
        }

        public static byte tierMax(long aSize) {
            byte i = -1;
            aSize = Math.abs(aSize);
            while (++i < V.length) if (aSize <= V[i]) return i;
            return i;
        }

        public static byte tierMin(long aSize) {
            byte i = -1;
            aSize = Math.abs(aSize);
            while (++i < V.length) if (aSize < V[i]) return (byte)Math.max(0, i-1);
            return --i;
        }

        public static long voltMax(long aSize) {
            aSize = Math.abs(aSize);
            for (int i = 0; i < VMAX.length; i++) if (aSize < VMAX[i]) return VMAX[i];
            return VMAX[VMAX.length-1];
        }

        public static long voltMin(long aSize) {
            aSize = Math.abs(aSize);
            for (int i = 0; i < VMAX.length; i++) if (aSize < VMAX[i]) return VMIN[i];
            return VMIN[VMIN.length-1];
        }

        public static boolean haveOneCommonElement(Iterable<?> aCollection1, Collection<?> aCollection2) {
            if (aCollection1 == aCollection2) return T;
            for (Object tObject : aCollection1) if (aCollection2.contains(tObject)) return T;
            return F;
        }

        /** re-maps all Keys of a Map after the Keys were weakened. */
        public static <X, Y> Map<X, Y> reMap(Map<X, Y> aMap) {
            Map<X, Y> tMap = new HashMap<>();
            tMap.putAll(aMap);
            aMap.clear();
            aMap.putAll(tMap);
            return aMap;
        }

        /** Why the fuck do neither Java nor Guava have a Function to do this? */
        @SuppressWarnings("rawtypes")
        public static <X, Y extends Comparable> LinkedHashMap<X,Y> sortByValuesAcending(Map<X,Y> aMap) {
            List<Map.Entry<X,Y>> tEntrySet = new LinkedList<>(aMap.entrySet());
            Collections.sort(tEntrySet, new Comparator<Map.Entry<X,Y>>() {@SuppressWarnings("unchecked") @Override public int compare(Map.Entry<X, Y> aValue1, Map.Entry<X, Y> aValue2) {return aValue1.getValue().compareTo(aValue2.getValue());}});
            LinkedHashMap<X,Y> rMap = new LinkedHashMap<>();
            for (Map.Entry<X,Y> tEntry : tEntrySet) rMap.put(tEntry.getKey(), tEntry.getValue());
            return rMap;
        }

        /** Why the fuck do neither Java nor Guava have a Function to do this? */
        @SuppressWarnings("rawtypes")
        public static <X, Y extends Comparable> LinkedHashMap<X,Y> sortByValuesDescending(Map<X,Y> aMap) {
            List<Map.Entry<X,Y>> tEntrySet = new LinkedList<>(aMap.entrySet());
            Collections.sort(tEntrySet, new Comparator<Map.Entry<X,Y>>() {@SuppressWarnings("unchecked") @Override public int compare(Map.Entry<X, Y> aValue1, Map.Entry<X, Y> aValue2) {return -aValue1.getValue().compareTo(aValue2.getValue());}});
            LinkedHashMap<X,Y> rMap = new LinkedHashMap<>();
            for (Map.Entry<X,Y> tEntry : tEntrySet) rMap.put(tEntry.getKey(), tEntry.getValue());
            return rMap;
        }

        public static <E> E select(long aIndex, E aReplacement, List<E> aList) {
            if (aList == null || aList.isEmpty()) return aReplacement;
            if (aList.size() <= aIndex) return aList.get(aList.size() - 1);
            if (aIndex < 0) return aList.get(0);
            return aList.get((int)aIndex);
        }

        public static <E> E select(E aReplacement, List<E> aList) {
            if (aList == null || aList.isEmpty()) return aReplacement;
            return aList.get(RNGSUS.nextInt(aList.size()));
        }

        @SafeVarargs
        public static <E> E select(long aIndex, E aReplacement, E... aArray) {
            if (aArray == null || aArray.length <= 0) return aReplacement;
            if (aArray.length <= aIndex) return aArray[aArray.length - 1];
            if (aIndex < 0) return aArray[0];
            return aArray[(int)aIndex];
        }

        @SafeVarargs
        public static <E> E select(E aReplacement, E... aArray) {
            if (aArray == null || aArray.length <= 0) return aReplacement;
            return aArray[RNGSUS.nextInt(aArray.length)];
        }

        public static boolean inArray(Object aObject, Object... aObjects) {
            return inList(aObject, Arrays.asList(aObjects));
        }

        public static boolean inList(Object aObject, Collection<?> aObjects) {
            if (aObjects == null) return F;
            return aObjects.contains(aObject);
        }

        public static final int[][] ASCENDING_ARRAYS = new int[1024][];

        public static int[] getAscendingArray(int aLength) {
            if (aLength <= 0) return ZL_INTEGER;
            if (aLength < ASCENDING_ARRAYS.length) {
                if (ASCENDING_ARRAYS[aLength] == null) {
                    ASCENDING_ARRAYS[aLength] = new int[aLength];
                    for (int i = 0; i < aLength; i++) ASCENDING_ARRAYS[aLength][i] = i;
                }
                return ASCENDING_ARRAYS[aLength];
            }
            int[] rArray = new int[aLength];
            for (int i = 0; i < aLength; i++) rArray[i] = i;
            return rArray;
        }

        public static String stringValidate(Object aString) {
            if (aString == null) return "";
            String rString = aString.toString();
            return rString == null ? "" : rString;
        }

        public static boolean stringValid(Object aString) {
            return aString != null && !aString.toString().isEmpty();
        }

        public static boolean stringInvalid(Object aString) {
            return aString == null || aString.toString().isEmpty();
        }

//        public static byte side(ForgeDirection aDirection) {
//            return (byte)(aDirection==null?SIDE_INVALID:aDirection.ordinal());
//        }

        public static byte side(int aSide) {
            return aSide > 5 || aSide < 0 ? SIDE_INVALID : (byte)aSide;
        }

        /** If this Index exists inside the passed Array and if it is != null */
        public static <E> boolean exists(int aIndex, E[] aArray) {
            return aIndex >= 0 && aIndex < aArray.length && aArray[aIndex] != null;
        }

        /** @return a Value for a Scale between 0 and aMax with aScale+1 possible Steps. 0 is only returned if the aValue is <= 0, aScale is only returned if the Value is >= aMax. The remaining values between ]0:aScale[ are returned for each Step of the Scale. This Function finds use in Displays such as the Barometer, but also in Redstone. */
        public static long scale(long aValue, long aMax, long aScale, boolean aInvert) {
            long rScale = (aValue <= 0 ? 0 : aValue >= aMax ? aScale : aScale <= 2 ? 1 : 1 + (aValue * (aScale-1)) / aMax);
            return aInvert ? aScale - rScale : rScale;
        }

        /** @return a Value for a Scale between aMin and aMax with aScale+1 possible Steps. 0 is only returned if the aValue is <= aMin, aScale is only returned if the Value is >= aMax. The remaining values between ]0:aScale[ are returned for each Step of the Scale. This Function finds use in Displays such as the Barometer, but also in Redstone. */
        public static long scale(long aValue, long aMin, long aMax, long aScale, boolean aInvert) {
            return scale(aValue-aMin, aMax-aMin, aScale, aInvert);
        }

        public static long bind(long aMin, long aMax, long aBoundValue) {
            return aMin > aMax ? Math.max(aMax, Math.min(aMin, aBoundValue)) : Math.max(aMin, Math.min(aMax, aBoundValue));
        }
        public static long bind_(long aMin, long aMax, long aBoundValue) {
            return Math.max(aMin, Math.min(aMax, aBoundValue));
        }

        public static float  bindF    (float  aBoundValue) {return        Math.max(0, Math.min(         1, aBoundValue));}
        public static double bindD    (double aBoundValue) {return        Math.max(0, Math.min(         1, aBoundValue));}
        public static byte   bind1    (long   aBoundValue) {return (byte) Math.max(0, Math.min(         1, aBoundValue));}
        public static byte   bind2    (long   aBoundValue) {return (byte) Math.max(0, Math.min(         3, aBoundValue));}
        public static byte   bind3    (long   aBoundValue) {return (byte) Math.max(0, Math.min(         7, aBoundValue));}
        public static byte   bind4    (long   aBoundValue) {return (byte) Math.max(0, Math.min(        15, aBoundValue));}
        public static byte   bind5    (long   aBoundValue) {return (byte) Math.max(0, Math.min(        31, aBoundValue));}
        public static byte   bind6    (long   aBoundValue) {return (byte) Math.max(0, Math.min(        63, aBoundValue));}
        public static byte   bind7    (long   aBoundValue) {return (byte) Math.max(0, Math.min(       127, aBoundValue));}
        public static short  bind8    (long   aBoundValue) {return (short)Math.max(0, Math.min(       255, aBoundValue));}
        public static short  bind15   (long   aBoundValue) {return (short)Math.max(0, Math.min(     32767, aBoundValue));}
        public static int    bind16   (long   aBoundValue) {return (int)  Math.max(0, Math.min(     65535, aBoundValue));}
        public static int    bind24   (long   aBoundValue) {return (int)  Math.max(0, Math.min(  16777215, aBoundValue));}
        public static int    bind31   (long   aBoundValue) {return (int)  Math.max(0, Math.min(2147483647, aBoundValue));}
        public static int    bindInt  (long   aBoundValue) {return (int)  Math.max(Integer.MIN_VALUE, Math.min(Integer.MAX_VALUE, aBoundValue));}
        public static short  bindShort(long   aBoundValue) {return (short)Math.max(Short.MIN_VALUE, Math.min(Short.MAX_VALUE, aBoundValue));}
        public static byte   bindByte (long   aBoundValue) {return (byte) Math.max(Byte.MIN_VALUE, Math.min(Byte.MAX_VALUE, aBoundValue));}
        public static byte   bindStack(long   aBoundValue) {return (byte) Math.max(1, Math.min(64, aBoundValue));}

        public static short[] bindRGBa(short[] aColors) {
            if (aColors == null) return new short[] {255,255,255,255};
            for (int i = 0; i < aColors.length; i++) aColors[i] = bind8(aColors[i]);
            return aColors;
        }

        public static int mixRGBInt(int aRGB1, int aRGB2) {
            return getRGBInt(new short[] {(short)((getR(aRGB1) + getR(aRGB2)) >> 1), (short)((getG(aRGB1) + getG(aRGB2)) >> 1), (short)((getB(aRGB1) + getB(aRGB2)) >> 1)});
        }

        public static int getRGBInt(short[] aColors) {
            return aColors == null ? 16777215 : (bind8(aColors[0]) << 16) | (bind8(aColors[1]) << 8) | bind8(aColors[2]);
        }

        public static int getRGBaInt(short[] aColors) {
            return aColors == null ? 16777215 : (bind8(aColors[0]) << 16) | (bind8(aColors[1]) << 8) | bind8(aColors[2]) | (bind8(aColors[3]) << 24);
        }

        public static int getRGBInt(long aR, long aG, long aB) {
            return (bind8(aR) << 16) | (bind8(aG) << 8) | bind8(aB);
        }

        public static int getRGBaInt(long aR, long aG, long aB, long aA) {
            return (bind8(aR) << 16) | (bind8(aG) << 8) | bind8(aB) | (bind8(aA) << 24);
        }

        public static short[] getRGBaArray(int aColors) {
            return new short[] {(short)((aColors >>> 16) & 255), (short)((aColors >>> 8) & 255), (short)(aColors & 255), (short)((aColors >>> 24) & 255)};
        }

        public static short getR(int aColors) {return (short)((aColors >>> 16) & 255);}
        public static short getG(int aColors) {return (short)((aColors >>>  8) & 255);}
        public static short getB(int aColors) {return (short) (aColors         & 255);}
        public static short getA(int aColors) {return (short)((aColors >>> 24) & 255);}

//        @Environment(EnvType.CLIENT)
//        /** estebes helped with the code for this one, and yes that cast down there is fucking necessary... */
//        public static short[] color(ItemStack aStack) {
//            if (ST.invalid(aStack)) return UNCOLOURED;
//            IIcon tIcon = null;
//            try {tIcon = aStack.getIconIndex();} catch(Throwable e) {return UNCOLOURED;} // And ofcourse some Mod needs to crash here...
//            if (tIcon == null) return UNCOLOURED;
//            String tResourceLocation = tIcon.getIconName();
//            if (stringInvalid(tResourceLocation)) return UNCOLOURED;
//            short[] rColor = color(tResourceLocation);
//            if (rColor == null) return UNCOLOURED;
//            short[] rModulation = getRGBaArray(aStack.getItem().getColorFromItemStack(aStack, 0));
//            for (byte i = 0; i < 3; i++) rColor[i] = (short)((rColor[i] * rModulation[i]) / 255);
//            return rColor;
//        }
//
//        @Environment(EnvType.CLIENT)
//        /** estebes helped with the code for this one */
//        public static short[] color(String aResourceLocation) {
//            ResourceLocation aux = null;
//            if (aResourceLocation.contains(":")) {
//                String[] modid_itemid = aResourceLocation.split(":");
//                aux = new ResourceLocation(modid_itemid[0], "textures/items/" + modid_itemid[1] + ".png");
//            } else {
//                aux = new ResourceLocation("minecraft", "textures/items/" + aResourceLocation + ".png");
//            }
//            java.awt.image.BufferedImage tIcon = null;
//            try {tIcon = javax.imageio.ImageIO.read(Minecraft.getMinecraft().getResourceManager().getResource(aux).getInputStream());} catch (IOException e) {/**/}
//            return tIcon == null ? null : color(tIcon);
//        }
//
//        @Environment(EnvType.CLIENT)
//        /** estebes helped with the code for this one */
//        public static short[] color(java.awt.image.BufferedImage icon) {
//            long tR = 0, tG = 0, tB = 0, tPixels = 0;
//            for (int tWidth = 0; tWidth < icon.getWidth(); tWidth++) for (int tHeight = 0; tHeight < icon.getHeight(); tHeight++) {
//                int tPixel = icon.getRGB(tWidth, tHeight);
//                if ((     (tPixel >>> 24) & 255) > 128) {
//                    tR += (tPixel >>> 16) & 255;
//                    tG += (tPixel >>>  8) & 255;
//                    tB +=  tPixel         & 255;
//                    tPixels++;
//                }
//            }
//            return new short[] {(short)(tR / tPixels), (short)(tG / tPixels), (short)(tB / tPixels)};
//        }

        /** toUpperCases the first Character of the String and returns it */
        public static String capitalise(String aString) {
            return aString == null ? "" : aString.length() <= 1 ? aString.toUpperCase() : aString.substring(0, 1).toUpperCase() + aString.substring(1);
        }

        /** toUpperCases the first Character of each Word in the String and returns it */
        public static String capitaliseWords(String aString) {
            StringBuilder rString = new StringBuilder();
            for (String tString : aString.split(" ")) if (!tString.isEmpty()) rString.append(capitalise(tString)).append(" ");
            return rString.toString().trim();
        }

        /** @return the opposite facing of this Side of a Block, with a boundary check. */
        public static byte opposite(int aSide) {
            return aSide < OPPOSITES.length && aSide >= 0 ? OPPOSITES[aSide] : 6;
        }

        /** Turns the Amount of Stuff into a more readable String. */
        public static String displayUnits(long aAmount) {
            if (aAmount < 0) return "?.???";
            long tDigits = ((aAmount % U) * 1000) / U;
            return (aAmount / U) + "." + (tDigits<1?"000":tDigits<10?"00"+tDigits:tDigits<100?"0"+tDigits:tDigits);
        }

        /** Translates Amount of aUnit1 to Amount of aUnit2. */
        public static long units(long aAmount, long aOriginalUnit, long aTargetUnit, boolean aRoundUp) {
            if (aTargetUnit == 0) return 0;
            if (aOriginalUnit == aTargetUnit || aOriginalUnit == 0) return aAmount;
            if (aOriginalUnit %   aTargetUnit == 0) {aOriginalUnit /=   aTargetUnit;   aTargetUnit = 1;} else
            if (aTargetUnit   % aOriginalUnit == 0) {  aTargetUnit /= aOriginalUnit; aOriginalUnit = 1;}
            return Math.max(0, ((aAmount * aTargetUnit) / aOriginalUnit) + (aRoundUp && (aAmount * aTargetUnit) % aOriginalUnit > 0 ? 1 : 0));
        }

        /** Translates Amount of aUnit1 to Amount of aUnit2. With additional checks to avoid 64 Bit Overflow. */
        public static long units_(long aAmount, long aOriginalUnit, long aTargetUnit, boolean aRoundUp) {
            if (aTargetUnit == 0) return 0;
            if (aOriginalUnit == aTargetUnit || aOriginalUnit == 0) return aAmount;
            if (aOriginalUnit %   aTargetUnit == 0) {aOriginalUnit /=   aTargetUnit;   aTargetUnit = 1;} else
            if (aTargetUnit   % aOriginalUnit == 0) {  aTargetUnit /= aOriginalUnit; aOriginalUnit = 1;} else {
                if (aOriginalUnit %  648 == 0 && aTargetUnit %  648 == 0) {aOriginalUnit /=  648; aTargetUnit /=  648;}
                if (aOriginalUnit % 1000 == 0 && aTargetUnit % 1000 == 0) {aOriginalUnit /= 1000; aTargetUnit /= 1000;}}
            return Math.max(0, ((aAmount * aTargetUnit) / aOriginalUnit) + (aRoundUp && (aAmount * aTargetUnit) % aOriginalUnit > 0 ? 1 : 0));
        }

        /** Divides but rounds up. */
        public static long divup(long aNumber, long aDivider) {
            return aNumber / aDivider + (aNumber % aDivider == 0 ? 0 : 1);
        }

        public static long sum(byte... aArray) {
            long rAmount = 0;
            for (byte tNumber : aArray) rAmount += tNumber;
            return rAmount;
        }

        public static long sum(short... aArray) {
            long rAmount = 0;
            for (short tNumber : aArray) rAmount += tNumber;
            return rAmount;
        }

        public static long sum(int... aArray) {
            long rAmount = 0;
            for (int tNumber : aArray) rAmount += tNumber;
            return rAmount;
        }

        public static long sum(long... aArray) {
            long rAmount = 0;
            for (long tNumber : aArray) rAmount += tNumber;
            return rAmount;
        }

        public static boolean abs_greater(long aAmount1, long aAmount2) {return Math.abs(aAmount1) > Math.abs(aAmount2);}
        public static boolean abs_smaller(long aAmount1, long aAmount2) {return Math.abs(aAmount1) < Math.abs(aAmount2);}
        public static boolean abs_greater_equal(long aAmount1, long aAmount2) {return Math.abs(aAmount1) >= Math.abs(aAmount2);}
        public static boolean abs_smaller_equal(long aAmount1, long aAmount2) {return Math.abs(aAmount1) <= Math.abs(aAmount2);}

        public static boolean inside(long aMin, long aMax, long aNumber) {return aMin < aMax ? aMin <= aNumber && aNumber <= aMax : aMax <= aNumber && aNumber <= aMin;}
        public static boolean inside_(double aMin, double aMax, double aNumber) {return aMin < aMax ? aMin <= aNumber && aNumber <= aMax : aMax <= aNumber && aNumber <= aMin;}

        /** @return an Array containing the X and the Y Coordinate of the clicked Point, with the top left Corner as Origin, like on the Texture Sheet. return values should always be between [0.0F and 0.99F]. */
        public static float[] getFacingCoordsClicked(byte aSide, float aHitX, float aHitY, float aHitZ) {
            switch (aSide) {
                case  0: return new float[] {Math.min(0.99F, Math.max(0,  aHitX)), Math.min(0.99F, Math.max(0,1-aHitZ))};
                case  1: return new float[] {Math.min(0.99F, Math.max(0,  aHitX)), Math.min(0.99F, Math.max(0,  aHitZ))};
                case  2: return new float[] {Math.min(0.99F, Math.max(0,1-aHitX)), Math.min(0.99F, Math.max(0,1-aHitY))};
                case  3: return new float[] {Math.min(0.99F, Math.max(0,  aHitX)), Math.min(0.99F, Math.max(0,1-aHitY))};
                case  4: return new float[] {Math.min(0.99F, Math.max(0,  aHitZ)), Math.min(0.99F, Math.max(0,1-aHitY))};
                case  5: return new float[] {Math.min(0.99F, Math.max(0,1-aHitZ)), Math.min(0.99F, Math.max(0,1-aHitY))};
                default: return new float[] {0.5F, 0.5F};
            }
        }

        public static byte getSideForPlayerPlacing(Entity aPlayer) {
            if (aPlayer.yRot >=  65) return SIDE_UP;
            if (aPlayer.yRot <= -65) return SIDE_DOWN;
            return COMPASS_DIRECTIONS[Code.roundDown(4*aPlayer.xRot/360+0.5)&3];
        }

        public static byte getSideForPlayerPlacing(Entity aPlayer, byte aDefaultFacing, boolean[] aAllowedFacings) {
            if (aPlayer.yRot >=  65 && aAllowedFacings[SIDE_UP]) return SIDE_UP;
            if (aPlayer.yRot <= -65 && aAllowedFacings[SIDE_DOWN]) return SIDE_DOWN;
            byte rFacing = COMPASS_DIRECTIONS[Code.roundDown(0.5+4*aPlayer.xRot/360)&3];
            if (aAllowedFacings[rFacing]) return rFacing;
            for (byte tSide : ALL_SIDES_VALID) if (aAllowedFacings[tSide]) return tSide;
            return aDefaultFacing;
        }

        public static byte getOppositeSideForPlayerPlacing(Entity aPlayer, byte aDefaultFacing, boolean[] aAllowedFacings) {
            if (aPlayer.yRot >=  65 && aAllowedFacings[SIDE_DOWN]) return SIDE_DOWN;
            if (aPlayer.yRot <= -65 && aAllowedFacings[SIDE_UP]) return SIDE_UP;
            byte rFacing = OPPOSITES[COMPASS_DIRECTIONS[Code.roundDown(0.5+4*aPlayer.xRot/360)&3]];
            if (aAllowedFacings[rFacing]) return rFacing;
            for (byte tSide : ALL_SIDES_VALID) if (aAllowedFacings[tSide]) return tSide;
            return aDefaultFacing;
        }

        /**
         * This Function determines the direction a Block gets when being Wrenched.
         */
        public static byte getSideWrenching(byte aSide, float aHitX, float aHitY, float aHitZ) {
            switch (aSide) {
                case  0: case  1:
                    if (aHitX < 0.25) return aHitZ < 0.25 || aHitZ > 0.75 ? OPPOSITES[aSide] : 4;
                    if (aHitX > 0.75) return aHitZ < 0.25 || aHitZ > 0.75 ? OPPOSITES[aSide] : 5;
                    if (aHitZ < 0.25) return 2;
                    if (aHitZ > 0.75) return 3;
                    return aSide;
                case  2: case  3:
                    if (aHitX < 0.25) return aHitY < 0.25 || aHitY > 0.75 ? OPPOSITES[aSide] : 4;
                    if (aHitX > 0.75) return aHitY < 0.25 || aHitY > 0.75 ? OPPOSITES[aSide] : 5;
                    if (aHitY < 0.25) return 0;
                    if (aHitY > 0.75) return 1;
                    return aSide;
                case  4: case  5:
                    if (aHitZ < 0.25) return aHitY < 0.25 || aHitY > 0.75 ? OPPOSITES[aSide] : 2;
                    if (aHitZ > 0.75) return aHitY < 0.25 || aHitY > 0.75 ? OPPOSITES[aSide] : 3;
                    if (aHitY < 0.25) return 0;
                    if (aHitY > 0.75) return 1;
                    return aSide;
            }
            return SIDE_INVALID;
        }
    }
}
