package com.vornitologist.ui;

import org.vaadin.addon.leaflet.LMap;
import org.vaadin.addon.leaflet.shared.BaseLayer;

public class VornitologistMap extends LMap {

    public VornitologistMap() {
        // Configure layer used as baselayer
        BaseLayer baselayer = new BaseLayer();
        baselayer.setName("CloudMade");
        baselayer.setAttributionString("&copy; OpenStreetMap contributors");

        // Note, this url should only be used for testing purposes. If you wish
        // to use cloudmade base maps, get your own API key.
        baselayer
                .setUrl("http://{s}.tile.cloudmade.com/a751804431c2443ab399100902c651e8/997/256/{z}/{x}/{y}.png");
        setBaseLayers(baselayer);
    }
}
