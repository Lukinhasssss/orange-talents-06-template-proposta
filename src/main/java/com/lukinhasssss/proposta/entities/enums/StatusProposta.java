package com.lukinhasssss.proposta.entities.enums;

import java.util.HashMap;
import java.util.Map;

public enum StatusProposta {

    NAO_ELEGIVEL, ELEGIVEL, RECUSADA, APROVADA;

    public static StatusProposta convert(String status){
        Map<String, StatusProposta> mapper = new HashMap<String, StatusProposta>();
        mapper.put("COM_RESTRICAO", StatusProposta.NAO_ELEGIVEL);
        mapper.put("SEM_RESTRICAO", StatusProposta.ELEGIVEL);
        mapper.put("NAO_ELEGIVEL", StatusProposta.RECUSADA);
        mapper.put("ELEGIVEL", StatusProposta.APROVADA);

        return mapper.get(status);
    }

}
