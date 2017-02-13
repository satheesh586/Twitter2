package repositories;

import models.Query;

import java.util.List;

/**
 * Created by shubham on 12/9/14.
 */
public class EntityRepository {
    public static List<Query> getEntities()
    {
        List<Query> result = Query.find.findList();
        return result;
    }

    public static int addEntity(String name){
        Query query=new Query(name);
        query.save();
        return query.id;
    }
}
