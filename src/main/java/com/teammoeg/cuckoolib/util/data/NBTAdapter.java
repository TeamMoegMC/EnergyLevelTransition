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

package com.teammoeg.cuckoolib.util.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

import java.util.function.Supplier;

public class NBTAdapter {
	public static INBT getNBTTag(CompoundNBT nbt, String key, Supplier<INBT> supplier) {
		if (!nbt.contains(key)) {
			nbt.put(key, supplier.get());
		}
		return nbt.get(key);
	}

	public static byte getByte(CompoundNBT nbt, String key, byte value) {
		if (!nbt.contains(key, 1)) {
			nbt.putByte(key, value);
		}
		return nbt.getByte(key);
	}

	public static short getShort(CompoundNBT nbt, String key, short value) {
		if (!nbt.contains(key, 2)) {
			nbt.putShort(key, value);
		}
		return nbt.getShort(key);
	}

	public static int getInteger(CompoundNBT nbt, String key, int value) {
		if (!nbt.contains(key, 3)) {
			nbt.putInt(key, value);
		}
		return nbt.getInt(key);
	}

	public static long getLong(CompoundNBT nbt, String key, long value) {
		if (!nbt.contains(key, 4)) {
			nbt.putLong(key, value);
		}
		return nbt.getLong(key);
	}

	public static float getFloat(CompoundNBT nbt, String key, float value) {
		if (!nbt.contains(key, 5)) {
			nbt.putFloat(key, value);
		}
		return nbt.getFloat(key);
	}

	public static double getDouble(CompoundNBT nbt, String key, double value) {
		if (!nbt.contains(key, 6)) {
			nbt.putDouble(key, value);
		}
		return nbt.getDouble(key);
	}

	public static byte[] getByteArray(CompoundNBT nbt, String key, byte[] value) {
		if (!nbt.contains(key, 7)) {
			nbt.putByteArray(key, value);
		}
		return nbt.getByteArray(key);
	}

	public static String getString(CompoundNBT nbt, String key, String value) {
		if (!nbt.contains(key, 8)) {
			nbt.putString(key, value);
		}
		return nbt.getString(key);
	}

	public static ListNBT getList(CompoundNBT nbt, String key, int type) {
		return getList(nbt, key, () -> new ListNBT(), type);
	}

	public static ListNBT getList(CompoundNBT nbt, String key, Supplier<ListNBT> supplier, int type) {
		if (!nbt.contains(key, 9)) {
			nbt.put(key, supplier.get());
		}
		return nbt.getList(key, type);
	}

	public static CompoundNBT getCompound(CompoundNBT nbt, String key) {
		return getCompound(nbt, key, () -> new CompoundNBT());
	}

	public static CompoundNBT getCompound(CompoundNBT nbt, String key, Supplier<CompoundNBT> supplier) {
		if (!nbt.contains(key, 10)) {
			nbt.put(key, supplier.get());
		}
		return nbt.getCompound(key);
	}

	public static int[] getIntArray(CompoundNBT nbt, String key, int[] value) {
		if (!nbt.contains(key, 11)) {
			nbt.putIntArray(key, value);
		}
		return nbt.getIntArray(key);
	}

	public static long[] getLongArray(CompoundNBT nbt, String key, long[] value) {
		if (!nbt.contains(key, 12)) {
			nbt.putLongArray(key, value);
		}
		return nbt.getLongArray(key);
	}
}