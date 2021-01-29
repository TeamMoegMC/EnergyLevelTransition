package com.teammoeg.elt.capability;

import com.teammoeg.the_seed.api.IResearchProgress;

public class ResearchProgress implements IResearchProgress {
    public int ResearchExperience;
    public ResearchProgress(int exp){
     this.ResearchExperience=exp;
    }
    public int getResearchExperience() {
        return ResearchExperience;
    }
}
