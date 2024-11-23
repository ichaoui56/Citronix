package com.projet.citronix.event;

import com.projet.citronix.model.Harvest;
import com.projet.citronix.model.Tree;

import java.util.List;

public record HarvestCreatedEvent(
        Harvest harvest,
        List<Tree> trees
) {

}
