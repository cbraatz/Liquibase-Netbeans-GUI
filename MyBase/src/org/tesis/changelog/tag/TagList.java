package org.tesis.changelog.tag;

import java.util.ArrayList;
import java.util.List;

public class TagList {
    private List<Tag> tags;

    public TagList() {
        tags=new ArrayList<>();
    }

    public List<? extends Tag> getTagList() {
        return tags;
    }
    
    public void addTag(Tag tag){
        this.tags.add(tag);
    }
    public void addTags(List<? extends Tag> tags){
        for(Tag t:tags){
            this.tags.add(t);
        }
    }
    public boolean isEmpty(){
        return this.tags.isEmpty();
    }
}

