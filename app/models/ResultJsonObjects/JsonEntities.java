package models.ResultJsonObjects;

import models.Query;

import java.util.List;

/**
 * Created by shubham on 20/9/14.
 */
public class JsonEntities {
    int id;
    String name;
    List<String> handles;
    List<String> brandNames;

    public JsonEntities(Query query,List<String> handles,List<String> brandNames){
        this.id=query.id;
        this.name=query.name;
        this.handles=handles;
        this.brandNames=brandNames;
        /*for(Handle handle:handles) this.handles.add(handle.name);
        for(BrandName brandName:brandNames) this.brandNames.add(brandName.name);*/
    }

    public String getName() {
        return name;
    }

    public List<String> getHandles() {
        return handles;
    }

    public List<String> getBrandNames() {
        return brandNames;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHandles(List<String> handles) {
        this.handles = handles;
    }

    public void setBrandNames(List<String> brandNames) {
        this.brandNames = brandNames;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
