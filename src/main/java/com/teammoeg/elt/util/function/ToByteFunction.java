/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package com.teammoeg.elt.util.function;

@FunctionalInterface
public interface ToByteFunction<T>
{
    byte get(T t);
}