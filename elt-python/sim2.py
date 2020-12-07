# coding=utf-8

# Village Economy

# 最重要的几个数据是需求商品种类，需求商品数量，产出商品种类，产出商品数量，库存商品种类，库存商品数量，以及村落财富
import matplotlib.pyplot as plt


class Person:

    def __init__(self, wheat, cabbage, fert, hematite, iron):
        self.wheat = wheat
        self.cabbage = cabbage
        self.fertilizer = fert
        self.hematite = hematite
        self.iron = iron


farmer = Person(wheat=(3, 9), cabbage=(2, 9), fert=(1, 0), hematite=(0, 0), iron=(0, 0))


class Village:

    def __init__(self, farmer, miner, smith):
        self.Farmer().num = farmer
        self.Miner().num = miner
        self.Smith().num = smith


        self.demand = {}
        self.supply = {}
        self.storage_list = {"wheat": [], "cabbage": [], "fertilizer": [], "hematite": [], "iron": []}
        self.storage_limit = 256
        self.storage = {"wheat": 100, "cabbage": 100, "fertilizer": 100, "hematite": 100, "iron": 100}

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

all_villages = []

village_a = Village(farmer=4, miner=2, smith=1)
village_b = Village(farmer=2, miner=3, smith=2)

all_villages.append(village_a)
all_villages.append(village_b)


def before_supply(this_village, cycle_num, isNotBankrupt):
    if this_village.storage["fertilizer"] <= 0:
        this_village.storage["fertilizer"] = 0
        this_village.Farmer.supply_wheat = 4
        this_village.Farmer.supply_cabbage = 6
    else:
        this_village.Farmer.supply_wheat = 9
        this_village.Farmer.supply_cabbage = 9

    # Check bankrupt
    if this_village.storage["wheat"] <= 0 and this_village.storage["cabbage"] <= 0:
        print("Food has run out in this village! BANKRUPT!")
        print("Survived " + str(cycle_num) + " cycles")
        for key in this_village.storage.keys():
            if this_village.storage[key] > 0:
                print(key + " stored is " + str(this_village.storage[key]))
        isNotBankrupt = False

    # Log
    print("=============== " + "Cycle - " + str(cycle_num) + " ===============")
    for key in this_village.storage.keys():
        if this_village.storage[key] > 0:
            print("- " + key + ": " + str(this_village.storage[key]))
        else:
            print("- " + key + ": EMPTY")


def after_supply(this_village):
    for key in this_village.storage.keys():
        if this_village.storage[key] > this_village.storage_limit:
            this_village.storage[key] = this_village.storage_limit


def cycle(this_village):
    print("Simulation starts: ")
    print()
    isNotBankrupt = True
    cyclesSurvived = 0

    while isNotBankrupt and cyclesSurvived < 50:
        # time.sleep(0.1)

        # append cycle and storage
        period.append(cyclesSurvived)
        for key in this_village.storage.keys():
            this_village.storage_list[key].append(this_village.storage[key])

        # demand
        cyclesSurvived += 1
        for key in this_village.storage.keys():
            this_village.storage[key] -= this_village.demand[key]

        # before supply
        before_supply(this_village, cyclesSurvived, isNotBankrupt)

        # supply
        for key in this_village.storage.keys():
            this_village.storage[key] += this_village.supply[key]

        # after supply
        after_supply(this_village)


def plot(village):
    plt.figure()
    ax1 = plt.subplot2grid((1, 1), (0, 0), colspan=1, rowspan=1)
    colors = ['orange', 'purple', 'black', 'red', 'blue']
    count = 0
    for key in village.storage.keys():
        plt.plot(period, village.storage_list[key], colors[count], linewidth=1)
        count += 1
    ax1.set_title("Village Economy Simulation")
    plt.xlabel('Time / Cycle')
    plt.ylabel('Commodity Quantity in Village')
    plt.grid()
    plt.show()


cycle(village_a)
plot(village_a)

cycle(village_b)
plot(village_b)

