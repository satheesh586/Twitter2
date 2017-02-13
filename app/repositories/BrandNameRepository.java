package repositories;

import models.BrandName;
import models.Query;

import java.util.List;

/**
 * Created by shubham on 17/9/14.
 */
public class BrandNameRepository {
    public static List<BrandName> getBrandNames(int entityId){
        return BrandName.find.where().eq("id",entityId).findList();
    }

    public static void addBrandName(int entityId,String name) {
        Query query=Query.find.byId(entityId);
        BrandName brandName=new BrandName(name,query);
        brandName.save();
    }

    public static List<BrandName> getIds(String hashTag){
        hashTag="#"+hashTag.toLowerCase();
        return BrandName.find.where().like("name", hashTag).findList();

    }

    public static void removeAllBrandNames(int entityId) {
        /*BrandName brandName=BrandName.find.ref(Query.find.byId(entityId));
        while (brandName!=null)
        {
            brandName.delete();
            brandName=BrandName.find.ref(Query.find.byId(entityId));
        }*/
        List<BrandName> brandNameList=BrandName.find.where().eq("id",entityId).findList();
    }
    //edit entity - remove all, then add one by one
}
