package repositories;

import models.BrandName;
import models.Handle;
import models.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubham on 9/9/14.
 */
public class HandleRepository {
    public static List<Handle> getHandles(int entityId)
    {
        Query query= Query.find.byId(entityId);
        List<Handle> result = Handle.find.where().eq("query",query).findList();
        for (Handle handle:result) handle.query=query;
        return result;
    }

    public static List<String> getHandleNames(int entityId)
    {
        List<Handle> handles=getHandles(entityId);
        List<String> result=new ArrayList<String>();
        for(Handle handle:handles) result.add(handle.name);
        return result;
    }

    public static List<BrandName> getBrandNames(int entityId){
        Query query= Query.find.byId(entityId);
        List<BrandName> result = BrandName.find.where().eq("query",query).findList();
        for (BrandName brandName:result) brandName.query=query;
        return result;
    }

    public static List<String> getBrandNamesText(int entityId)
    {
        List<BrandName> brandNames=getBrandNames(entityId);
        List<String> result=new ArrayList<String>();
        for(BrandName brandName:brandNames) result.add(brandName.name);
        return result;
    }

    public static void addHandle(int entityId,String name) {
        Query query=Query.find.byId(entityId);
        Handle handle=new Handle(name,query);
        handle.save();
    }

    public static void removeAllHandles(int entityId) {
        List<Handle> handleList=getHandles(entityId);
        for(Handle handle:handleList) handle.delete();
    }

    public static List<Handle> getIds(String screenName){
        screenName=screenName.toLowerCase();
        return Handle.find.where().eq("name",screenName).findList();

    }
}
