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

package com.teammoeg.the_seed.code;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public interface ICondition<O> {
    ICondition<?> TRUE = new True<>();
    ICondition<?> FALSE = new False<>();
    ICondition<?> NULL = new Null<>();
    ICondition<?> NOTNULL = new NotNull<>();

    /**
     * @param aObject the Object to check the Condition on
     * @return if the Condition matches
     */
    boolean isTrue(O aObject);

    // Utility Classes for adding relations between Conditions.

    class Not<O> implements ICondition<O> {
        private final ICondition<O> mCondition;

        public Not(ICondition<O> aCondition) {
            mCondition = aCondition;
        }

        @Override
        public boolean isTrue(O aObject) {
            return !mCondition.isTrue(aObject);
        }
    }

    class Or<O> implements ICondition<O> {
        private final ICondition<O>[] mConditions;

        @SafeVarargs
        public Or(ICondition<O>... aConditions) {
            mConditions = aConditions;
        }

        @Override
        public boolean isTrue(O aObject) {
            for (ICondition<O> tCondition : mConditions) if (tCondition.isTrue(aObject)) return true;
            return false;
        }
    }

    class Nor<O> implements ICondition<O> {
        private final ICondition<O>[] mConditions;

        @SafeVarargs
        public Nor(ICondition<O>... aConditions) {
            mConditions = aConditions;
        }

        @Override
        public boolean isTrue(O aObject) {
            for (ICondition<O> tCondition : mConditions) if (tCondition.isTrue(aObject)) return false;
            return true;
        }
    }

    class And<O> implements ICondition<O> {
        private final ICondition<O>[] mConditions;

        @SafeVarargs
        public And(ICondition<O>... aConditions) {
            mConditions = aConditions;
        }

        @Override
        public boolean isTrue(O aObject) {
            for (ICondition<O> tCondition : mConditions) if (!tCondition.isTrue(aObject)) return false;
            return true;
        }
    }

    class Nand<O> implements ICondition<O> {
        private final ICondition<O>[] mConditions;

        @SafeVarargs
        public Nand(ICondition<O>... aConditions) {
            mConditions = aConditions;
        }

        @Override
        public boolean isTrue(O aObject) {
            for (ICondition<O> tCondition : mConditions) if (!tCondition.isTrue(aObject)) return true;
            return false;
        }
    }

    class Xor<O> implements ICondition<O> {
        private final ICondition<O> mCondition1, mCondition2;

        public Xor(ICondition<O> aCondition1, ICondition<O> aCondition2) {
            mCondition1 = aCondition1;
            mCondition2 = aCondition2;
        }

        @Override
        public boolean isTrue(O aObject) {
            return mCondition1.isTrue(aObject) != mCondition2.isTrue(aObject);
        }
    }

    class Equal<O> implements ICondition<O> {
        private final ICondition<O> mCondition1, mCondition2;

        public Equal(ICondition<O> aCondition1, ICondition<O> aCondition2) {
            mCondition1 = aCondition1;
            mCondition2 = aCondition2;
        }

        @Override
        public boolean isTrue(O aObject) {
            return mCondition1.isTrue(aObject) == mCondition2.isTrue(aObject);
        }
    }

    class True<O> implements ICondition<O> {
        True() {/**/}

        @Override
        public boolean isTrue(O aObject) {
            return true;
        }
    }

    class False<O> implements ICondition<O> {
        False() {/**/}

        @Override
        public boolean isTrue(O aObject) {
            return false;
        }
    }

    class Null<O> implements ICondition<O> {
        Null() {/**/}

        @Override
        public boolean isTrue(O aObject) {
            return aObject == null;
        }
    }

    class NotNull<O> implements ICondition<O> {
        NotNull() {/**/}

        @Override
        public boolean isTrue(O aObject) {
            return aObject != null;
        }
    }
}

