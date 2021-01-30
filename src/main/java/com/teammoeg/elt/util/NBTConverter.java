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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.nbt.*;

import java.util.Map.Entry;

public class NBTConverter {
    /**
     * Convert NBT tags to a JSON object
     */
    private static JsonElement nbtToJsonBase(INBT tag, boolean format) {
        if (tag == null) {
            return new JsonObject();
        }

        if (tag.getId() >= 1 && tag.getId() <= 6) {
            return new JsonPrimitive(getNumber(tag));
        }
        if (tag instanceof StringNBT) {
            return new JsonPrimitive(tag.getAsString());
        } else if (tag instanceof CompoundNBT) {
            return nbtToJsonCompound((CompoundNBT) tag, new JsonObject(), format);
        } else if (tag instanceof ListNBT) {
            if (format) {
                JsonObject jAry = new JsonObject();

                ListNBT tagList = (ListNBT) tag;

                for (int i = 0; i < tagList.size(); i++) {
                    jAry.add(i + ":" + tagList.get(i).getId(), nbtToJsonBase(tagList.get(i), true));
                }

                return jAry;
            } else {
                JsonArray jAry = new JsonArray();

                ListNBT tagList = (ListNBT) tag;

                for (INBT t : tagList) {
                    jAry.add(nbtToJsonBase(t, false));
                }

                return jAry;
            }
        } else if (tag instanceof ByteArrayNBT) {
            JsonArray jAry = new JsonArray();

            for (byte b : ((ByteArrayNBT) tag).getAsByteArray()) {
                jAry.add(new JsonPrimitive(b));
            }

            return jAry;
        } else if (tag instanceof IntArrayNBT) {
            JsonArray jAry = new JsonArray();

            for (int i : ((IntArrayNBT) tag).getAsIntArray()) {
                jAry.add(new JsonPrimitive(i));
            }

            return jAry;
        } else if (tag instanceof LongArrayNBT) {
            JsonArray jAry = new JsonArray();

            for (long l : ((LongArrayNBT) tag).getAsLongArray()) {
                jAry.add(new JsonPrimitive(l));
            }

            return jAry;
        } else {
            return new JsonObject(); // No valid types found. We'll just return this to prevent a NPE
        }
    }

    public static JsonObject nbtToJsonCompound(CompoundNBT parent, JsonObject jObj, boolean format) {
        if (parent == null) {
            return jObj;
        }

        for (String key : parent.getAllKeys()) {
            INBT tag = parent.get(key);
            if (tag == null) continue;

            if (format) {
                jObj.add(key + ":" + tag.getId(), nbtToJsonBase(tag, true));
            } else {
                jObj.add(key, nbtToJsonBase(tag, false));
            }
        }

        return jObj;
    }

    /**
     * Convert JsonObject to a NBTTagCompound
     */
    public static CompoundNBT jsonToNbtObject(JsonObject jObj, CompoundNBT tags, boolean format) {
        if (jObj == null) {
            return tags;
        }

        for (Entry<String, JsonElement> entry : jObj.entrySet()) {
            String key = entry.getKey();

            if (!format) {
                tags.put(key, jsonToNbtElement(entry.getValue(), (byte) 0, false));
            } else {
                String[] s = key.split(":");
                byte id = 0;

                try {
                    id = Byte.parseByte(s[s.length - 1]);
                    key = key.substring(0, key.lastIndexOf(":" + id));
                } catch (Exception e) {
                    if (tags.contains(key)) {

                        continue;
                    }
                }

                tags.put(key, jsonToNbtElement(entry.getValue(), id, true));
            }
        }

        return tags;
    }

    /**
     * Tries to interpret the tagID from the JsonElement's contents
     */
    private static INBT jsonToNbtElement(JsonElement jObj, byte id, boolean format) {
        if (jObj == null) {
            return StringNBT.valueOf("");
        }

        byte tagID = id <= 0 ? fallbackTagID(jObj) : id;

        try {
            if (tagID == 1 && (id <= 0 || jObj.getAsJsonPrimitive().isBoolean())) // Edge case for BQ2 legacy files
            {
                return ByteNBT.valueOf(jObj.getAsBoolean() ? (byte) 1 : (byte) 0);
            } else if (tagID >= 1 && tagID <= 6) {
                return instanceNumber(jObj.getAsNumber(), tagID);
            } else if (tagID == 8) {
                return StringNBT.valueOf(jObj.getAsString());
            } else if (tagID == 10) {
                return jsonToNbtObject(jObj.getAsJsonObject(), new CompoundNBT(), format);
            } else if (tagID == 7) // Byte array
            {
                JsonArray jAry = jObj.getAsJsonArray();

                byte[] bAry = new byte[jAry.size()];

                for (int i = 0; i < jAry.size(); i++) {
                    bAry[i] = jAry.get(i).getAsByte();
                }

                return new ByteArrayNBT(bAry);
            } else if (tagID == 11) {
                JsonArray jAry = jObj.getAsJsonArray();

                int[] iAry = new int[jAry.size()];

                for (int i = 0; i < jAry.size(); i++) {
                    iAry[i] = jAry.get(i).getAsInt();
                }

                return new IntArrayNBT(iAry);
            } else if (tagID == 12) {
                JsonArray jAry = jObj.getAsJsonArray();

                long[] lAry = new long[jAry.size()];

                for (int i = 0; i < jAry.size(); i++) {
                    lAry[i] = jAry.get(i).getAsLong();
                }

                return new LongArrayNBT(lAry);
            } else if (tagID == 9) {
                ListNBT tList = new ListNBT();

                if (jObj.isJsonArray()) {
                    JsonArray jAry = jObj.getAsJsonArray();

                    for (int i = 0; i < jAry.size(); i++) {
                        JsonElement jElm = jAry.get(i);
                        tList.add(jsonToNbtElement(jElm, (byte) 0, format));
                    }
                } else if (jObj.isJsonObject()) {
                    JsonObject jAry = jObj.getAsJsonObject();

                    for (Entry<String, JsonElement> entry : jAry.entrySet()) {
                        try {
                            String[] s = entry.getKey().split(":");
                            byte id2 = Byte.parseByte(s[s.length - 1]);
                            //String key = entry.getKey().substring(0, entry.getKey().lastIndexOf(":" + id));
                            tList.add(jsonToNbtElement(entry.getValue(), id2, format));
                        } catch (Exception e) {
                            tList.add(jsonToNbtElement(entry.getValue(), (byte) 0, format));
                        }
                    }
                }

                return tList;
            }
        } catch (Exception e) {

        }


        return StringNBT.valueOf("");
    }

    @SuppressWarnings("WeakerAccess")
    public static Number getNumber(INBT tag) {
        if (tag instanceof ByteNBT) {
            return ((ByteNBT) tag).getAsByte();
        } else if (tag instanceof ShortNBT) {
            return ((ShortNBT) tag).getAsShort();
        } else if (tag instanceof IntNBT) {
            return ((IntNBT) tag).getAsInt();
        } else if (tag instanceof FloatNBT) {
            return ((FloatNBT) tag).getAsFloat();
        } else if (tag instanceof DoubleNBT) {
            return ((DoubleNBT) tag).getAsDouble();
        } else if (tag instanceof LongNBT) {
            return ((LongNBT) tag).getAsLong();
        } else {
            return 0;
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static INBT instanceNumber(Number num, byte type) {
        switch (type) {
            case 1:
                return ByteNBT.valueOf(num.byteValue());
            case 2:
                return ShortNBT.valueOf(num.shortValue());
            case 3:
                return IntNBT.valueOf(num.intValue());
            case 4:
                return LongNBT.valueOf(num.longValue());
            case 5:
                return FloatNBT.valueOf(num.floatValue());
            default:
                return DoubleNBT.valueOf(num.doubleValue());
        }
    }

    private static byte fallbackTagID(JsonElement jObj) {
        byte tagID = 0;

        if (jObj.isJsonPrimitive()) {
            JsonPrimitive prim = jObj.getAsJsonPrimitive();

            if (prim.isNumber()) {
                if (prim.getAsString().contains(".")) // Just in case we'll choose the largest possible container supporting this number type (Long or Double)
                {
                    tagID = 6;
                } else {
                    tagID = 4;
                }
            } else if (prim.isBoolean()) {
                tagID = 1;
            } else {
                tagID = 8; // Non-number primitive. Assume string
            }
        } else if (jObj.isJsonArray()) {
            JsonArray array = jObj.getAsJsonArray();

            for (JsonElement entry : array) {
                if (entry.isJsonPrimitive() && tagID == 0) // Note: TagLists can only support Integers, Bytes and Compounds (Strings can be stored but require special handling)
                {
                    try {
                        for (JsonElement element : array) {
                            // Make sure all entries can be bytes
                            if (element.getAsLong() != element.getAsByte()) // In case casting works but overflows
                            {
                                throw new ClassCastException();
                            }
                        }
                        tagID = 7; // Can be used as byte
                    } catch (Exception e1) {
                        try {
                            for (JsonElement element : array) {
                                // Make sure all entries can be integers
                                if (element.getAsLong() != element.getAsInt()) // In case casting works but overflows
                                {
                                    throw new ClassCastException();
                                }
                            }
                            tagID = 11;
                        } catch (Exception e2) {
                            tagID = 9; // Is primitive however requires TagList interpretation
                        }
                    }
                } else if (!entry.isJsonPrimitive()) {
                    break;
                }
            }

            tagID = 9; // No data to judge format. Assuming tag list
        } else {
            tagID = 10;
        }

        return tagID;
    }
}
