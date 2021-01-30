package com.teammoeg.elt.capability;

import com.teammoeg.the_seed.api.IResearchProgress;

public class ResearchProgress implements IResearchProgress {
    public int ResearchExperience;
    public ResearchProgress(int xp){
     this.ResearchExperience=xp;
    }
    public int getResearchExperience() {
        return ResearchExperience;
    }
}
