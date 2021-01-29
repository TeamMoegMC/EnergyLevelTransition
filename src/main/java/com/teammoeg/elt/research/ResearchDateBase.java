package com.teammoeg.elt.research;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ResearchDateBase {

    public synchronized ListNBT writeProgressToNBT(ListNBT json, @Nullable List<UUID> users)
    {
/*       for(Research research : this.getEntries()) {
            CompoundNBT jq = new CompoundNBT();
            jq.putString("questID", Research.getId());
            json.appendTag(jq);
        }*/
        return json;
    }
}
