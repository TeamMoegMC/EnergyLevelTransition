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

package com.teammoeg.cuckoolib.material.element;

public enum Element {
    H("Hydrogen", 1, 0),
    He("Helium", 2, 2),
    Li("Lithium", 3, 4),
    Be("Beryllium", 4, 5),
    B("Boron", 5, 6),
    N("Nitrogen", 7, 7),
    O("Oxygen", 8, 8),
    F("Fluorine", 9, 10),
    Ne("Neon", 10, 10),
    Na("Sodium", 11, 12),
    Mg("Magnesium", 12, 12),
    Al("Aluminium", 13, 14),
    Si("Silicon", 14, 14),
    P("Phosphor", 15, 16),
    S("Sulfur", 16, 16),
    Cl("Chlorine", 17, 18),
    Ar("Argon", 18, 22),
    K("Potassium", 19, 20),
    Ca("Calcium", 20, 20),
    Sc("Scandium", 21, 24),
    Ti("Titanium", 22, 26),
    V("Vanadium", 23, 28),
    Cr("Chrome", 24, 28),
    Mn("Manganese", 25, 30),
    Fe("Iron", 26, 30),
    Co("Cobalt", 27, 32),
    Ni("Nickel", 28, 31),
    Cu("Copper", 29, 35),
    Zn("Zinc", 30, 35),
    Ga("Gallium", 31, 39),
    Ge("Germanium", 32, 41),
    As("Arsenic", 33, 42),
    Se("Selenium", 34, 45),
    Br("Bromine", 35, 45),
    Kr("Krypton", 36, 48),
    Rb("Rubidium", 37, 48),
    Sr("Strontium", 38, 50),
    Y("Yttrium", 39, 50),
    Zr("Zirconium", 40, 51),
    Nb("Niobium", 41, 52),
    Mo("Molybdenum", 42, 54),
    Tc("Technetium", 43, 55),
    Ru("Ruthenium", 44, 57),
    Rh("Rhodium", 45, 58),
    Pd("Palladium", 46, 60),
    Ag("Silver", 47, 61),
    Cd("Cadmium", 48, 64),
    In("Indium", 49, 66),
    Sn("Tin", 50, 69),
    Sb("Antimony", 51, 71),
    Te("Tellurium", 52, 76),
    I("Iodine", 53, 74),
    Xe("Xenon", 54, 77),
    Cs("Caesium", 55, 78),
    Ba("Barium", 56, 81),
    La("Lantanium", 57, 82),
    Ce("Cerium", 58, 82),
    Pr("Praseodymium", 59, 82),
    Nd("Neodymium", 60, 84),
    Pm("Promethium", 61, 84),
    Sm("Samarium", 62, 88),
    Eu("Europium", 63, 89),
    Gd("Gadolinium", 64, 93),
    Tb("Terbium", 65, 94),
    Dy("Dysprosium", 66, 97),
    Ho("Holmium", 67, 98),
    Er("Erbium", 68, 99),
    Tm("Thulium", 69, 100),
    Yb("Ytterbium", 70, 103),
    Lu("Lutetium", 71, 104),
    Hf("Hafnium", 72, 106),
    Ta("Tantalum", 73, 108),
    W("Tungsten", 74, 110),
    Re("Rhenium", 75, 111),
    Os("Osmium", 76, 114),
    Ir("Iridium", 77, 115),
    Pt("Platinum", 78, 117),
    Au("Gold", 79, 118),
    Hg("Mercury", 80, 121),
    Tl("Thallium", 81, 123),
    Pb("Lead", 82, 125),
    Bi("Bismuth", 83, 126),
    Po("Polonium", 84, 125),
    At("Astatine", 85, 125),
    Rn("Radon", 86, 136),
    Fr("Francium", 87, 136),
    Ra("Radium", 88, 138),
    Ac("Actinium", 89, 138),
    Th("Thorium", 90, 142),
    Pa("Protactinium", 91, 140),
    U("Uranium", 92, 146),
    Np("Neptunium", 93, 144),
    Pu("Plutonium", 94, 150),
    Am("Americium", 95, 148),
    Cm("Curium", 96, 151),
    Bk("Berkelium", 97, 150),
    Cf("Californium", 98, 153),
    Es("Einsteinium", 99, 153),
    Fm("Fermium", 100, 157),
    Md("Mendelevium", 101, 157),
    No("Nobelium", 102, 157),
    Lr("Lawrencium", 103, 159),
    Rf("Rutherfordium", 104, 157),
    Db("Dubnium", 105, 157),
    Sg("Seaborgium", 106, 157),
    Bh("Bohrium", 107, 155),
    Hs("Hassium", 108, 157),
    Mt("Meitnerium", 109, 157),
    Ds("Darmstadtium", 110, 151),
    Rg("Roentgenium", 111, 161),
    Cn("Copernicium", 112, 165),
    Nh("Nihonium", 113, 173),
    Fl("Flerovium", 114, 175),
    Mc("Moscovium", 115, 173),
    Lv("Livermorium", 116, 177),
    Ts("Tennessine", 117, 177),
    Og("Oganesson", 118, 176);

    private final String name;
    private final int proton;
    private final int neutron;

    Element(String name, int proton, int neutron) {
        this.name = name;
        this.proton = proton;
        this.neutron = neutron;
    }

    public String getName() {
        return this.name;
    }

    public int getProton() {
        return this.proton;
    }

    public int getNeutron() {
        return this.neutron;
    }

    public int getMassNumber() {
        return this.proton + this.neutron;
    }
}