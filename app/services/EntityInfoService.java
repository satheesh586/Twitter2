package services;

import models.Handle;
import models.Query;
import models.ResultJsonObjects.JsonEntities;
import models.UserEntities;
import play.Logger;
import play.api.libs.json.Json;
import repositories.EntityRepository;
import repositories.HandleRepository;
import repositories.UserEntitiesRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubham on 9/9/14.
 */
public class EntityInfoService {
    public static List<JsonEntities> getEntityList(){
        List<Query> entityList = UserEntitiesRepository.getEntities(StartupService.getCurrentUser());
        List<JsonEntities> result=new ArrayList<>();
        for(Query entity: entityList){
            int entityId=entity.id;
            JsonEntities jsonObject=new JsonEntities(entity,getHandleNames(entityId),getBrandNamesText(entityId));
            Logger.info("Discovered entity with info = "+jsonObject.toString());
            result.add(new JsonEntities(entity,getHandleNames(entityId),getBrandNamesText(entityId)));
        }
        return result;
    }
    public static List<String> getHandleNames(int entityId)
    {
        return HandleRepository.getHandleNames(entityId);
    }

    public static List<String> getBrandNamesText(int entityId)
    {
        return HandleRepository.getBrandNamesText(entityId);
    }
}
