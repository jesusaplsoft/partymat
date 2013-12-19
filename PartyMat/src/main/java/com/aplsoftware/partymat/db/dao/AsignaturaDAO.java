package com.aplsoftware.partymat.db.dao;

import com.apl.base.dao.Generic;

import com.aplsoftware.partymat.db.model.Asignatura;
import com.aplsoftware.partymat.db.model.find.AsignaturaItem;

import java.util.List;


public interface AsignaturaDAO
    extends Generic<Asignatura> {

    @Override
    Asignatura findById(Asignatura asi);

    List<AsignaturaItem> findItems();

}
