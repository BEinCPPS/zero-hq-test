package it.eng.zerohqt.orion.client.model;
import it.eng.zerohqt.orion.client.model.subscribe.OrionEntity;

import java.util.Optional;


/**
 * Created by ascatox on 10/02/17.
 */
public class OrionContextElementToOrionEntityTransformer {


    public static Optional<OrionEntity> transform(OrionContextElement orionContextElement){

        if(null == orionContextElement) return Optional.empty();
        OrionEntity orionEntity = new OrionEntity();
        orionEntity.setId(orionContextElement.getId());
        orionEntity.setType(orionContextElement.getType());
        orionEntity.setIsPattern(orionContextElement.getIsPattern());
        return Optional.of(orionEntity);
    }


}
