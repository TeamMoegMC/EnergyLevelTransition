# coding=utf-8
# Village Economy

# 最重要的几个数据是需求商品种类，需求商品数量，产出商品种类，产出商品数量，库存商品种类，库存商品数量，以及村落财富
import matplotlib.pyplot as plt
# import numpy as np


class Village:
    def __init__(self, farmer, miner, smith):
        self.Farmer.num = farmer
        self.Miner.num = miner
        self.Smith.num = smith

    storage = {}
    demand = {}
    supply = {}

    storage_limit = 256

    class Farmer:
        num = 4
        demand_wheat = 3
        demand_cabbage = 2
        demand_fertilizer = 1
        demand_hematite = 0
        demand_iron = 0

        supply_wheat = 9
        supply_cabbage = 9
        supply_fertilizer = 0
        supply_hematite = 0
        supply_iron = 0

    class Miner:
        num = 2
        demand_wheat = 4
        demand_cabbage = 2
        demand_fertilizer = 0
        demand_hematite = 0
        demand_iron = 0

        supply_wheat = 0
        supply_cabbage = 0
        supply_fertilizer = 0
        supply_hematite = 8
        supply_iron = 0

    class Smith:
        num = 1
        demand_wheat = 3
        demand_cabbage = 0
        demand_fertilizer = 0
        demand_hematite = 8
        demand_iron = 0

        supply_wheat = 0
        supply_cabbage = 0
        supply_fertilizer = 0
        supply_hematite = 0
        supply_iron = 8

    storage["wheat"] = 100
    storage["cabbage"] = 100
    storage["fertilizer"] = 100
    storage["hematite"] = 100
    storage["iron"] = 100

    demand["wheat"] = Farmer.num * Farmer.demand_wheat + Miner.num * Miner.demand_wheat + Smith.num * Smith.demand_wheat
    demand["cabbage"] = Farmer.num * Farmer.demand_cabbage + Miner.num * Miner.demand_cabbage + Smith.num * Smith.demand_cabbage
    demand["fertilizer"] = Farmer.num * Farmer.demand_fertilizer + Miner.num * Miner.demand_fertilizer + Smith.num * Smith.demand_fertilizer
    demand["hematite"] = Farmer.num * Farmer.demand_hematite + Miner.num * Miner.demand_hematite + Smith.num * Smith.demand_hematite
    demand["iron"] = Farmer.num * Farmer.demand_iron + Miner.num * Miner.demand_iron + Smith.num * Smith.demand_iron

    supply["wheat"] = Farmer.num * Farmer.supply_wheat + Miner.num * Miner.supply_wheat + Smith.num * Smith.supply_wheat
    supply["cabbage"] = Farmer.num * Farmer.supply_cabbage + Miner.num * Miner.supply_cabbage + Smith.num * Smith.supply_cabbage
    supply["fertilizer"] = Farmer.num * Farmer.supply_fertilizer + Miner.num * Miner.supply_fertilizer + Smith.num * Smith.supply_fertilizer
    supply["hematite"] = Farmer.num * Farmer.supply_hematite + Miner.num * Miner.supply_hematite + Smith.num * Smith.supply_hematite
    supply["iron"] = Farmer.num * Farmer.supply_iron + Miner.num * Miner.supply_iron + Smith.num * Smith.supply_iron

period = []

village_1_storage = {}
village_1_storage["wheat"] = []
village_1_storage["cabbage"] = []
village_1_storage["fertilizer"] = []
village_1_storage["hematite"] = []
village_1_storage["iron"] = []

village_2_storage = {}
village_2_storage["wheat"] = []
village_2_storage["cabbage"] = []
village_2_storage["fertilizer"] = []
village_2_storage["hematite"] = []
village_2_storage["iron"] = []

village_a = Village(farmer=4, miner=2, smith=1)

village_b = Village(farmer=2, miner=3, smith=2)

def before_supply(cycle_num, isNotBankrupt):
    if village_a.storage["fertilizer"] <= 0:
        village_a.storage["fertilizer"] = 0
        village_a.Farmer.supply_wheat = 4
        village_a.Farmer.supply_cabbage = 6
    else:
        village_a.Farmer.supply_wheat = 9
        village_a.Farmer.supply_cabbage = 9

    # Check bankrupt
    if village_a.storage["wheat"] <= 0 and village_a.storage["cabbage"] <= 0:
        print("Food has run out in this village! BANKRUPT!")
        print("Survived " + str(cycle_num) + " cycles")
        for key in village_a.storage.keys():
            if village_a.storage[key] > 0:
                print(key + " stored is " + str(village_a.storage[key]))
        isNotBankrupt = False

    # Log
    print("=============== " + "Cycle - " + str(cycle_num) + " ===============")
    for key in village_a.storage.keys():
        if village_a.storage[key] > 0:
            print("- " + key + ": " + str(village_a.storage[key]))
        else:
            print("- " + key + ": EMPTY")


def after_supply():
    for key in village_a.storage.keys():
        if village_a.storage[key] > village_a.storage_limit:
            village_a.storage[key] = village_a.storage_limit


def cycle():
    print("Simulation starts: ")
    print()
    isNotBankrupt = True
    cyclesSurvived = 0

    while isNotBankrupt and cyclesSurvived < 100:
        # time.sleep(0.1)

        # append cycle and storage
        period.append(cyclesSurvived)
        for key in village_a.storage.keys():
            village_1_storage[key].append(village_a.storage[key])

        # demand
        cyclesSurvived += 1
        for key in village_a.storage.keys():
            village_a.storage[key] -= village_a.demand[key]

        # before supply
        before_supply(cyclesSurvived, isNotBankrupt)

        # supply
        for key in village_a.storage.keys():
            village_a.storage[key] += village_a.supply[key]

        # after supply
        after_supply()



cycle()


def plot():
    plt.figure()
    ax1 = plt.subplot2grid((1, 1), (0, 0), colspan = 1, rowspan = 1)
    for key in village_a.storage.keys():
        plt.plot(period, village_1_storage[key], 'b', linewidth=1)
    ax1.set_title("Village Economy Simulation")
    plt.xlabel('Time / Cycle')
    plt.ylabel('Commodity Quantity in Village')
    plt.grid()
    plt.show()

plot()