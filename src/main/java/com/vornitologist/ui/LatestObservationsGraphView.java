package com.vornitologist.ui;

import java.util.Locale;
import java.util.ResourceBundle;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vornitologist.VornitologistUI;
import com.vornitologist.model.Observation;
import com.vornitologist.model.ObservationDB;
import com.vornitologist.util.Translations;

public class LatestObservationsGraphView extends NavigationView implements
        ClickListener {

    private final Button close = new Button(null, this);
    private final ResourceBundle tr;

    public LatestObservationsGraphView() {
        Locale locale = VornitologistUI.getApp().getLocale();
        tr = Translations.get(locale);

        close.setStyleName("close");
        setRightComponent(close);
        setCaption(tr.getString("ObservationsLast6h"));
        setContent(getChart());
    }

    private Chart getChart() {
        Chart chart = new Chart();
        chart.setHeight("100%");
        chart.setWidth("100%");
        Configuration configuration = new Configuration();
        configuration.getChart().setType(ChartType.COLUMN);
        configuration.setTitle("");
        
        configuration.getyAxis().setTitle(tr.getString("Observations"));
        
        BeanItemContainer<Observation> observationContainer = ObservationDB
                .getObservationContainer(getUI());


        for (Object itemid : observationContainer.getItemIds()) {
            Observation o = observationContainer.getItem(itemid).getBean();
            int hours = (int) ((System.currentTimeMillis() - o
                    .getObservationTime().getTime()) / (1000.0 * 60 * 60));
            if (hours < 3) {
                DataSeries series = new DataSeries();
                series.setName(o.getSpecies().getName());
                DataSeriesItem item = new DataSeriesItem();
                item.setY(o.getCount());
                series.add(item);
                configuration.addSeries(series);
            }
        }
        configuration.getTooltip().setEnabled(false);
        chart.drawChart(configuration);
        chart.setSizeFull();
        return chart;
    }

    public void buttonClick(ClickEvent event) {
        UI.getCurrent().removeWindow((Window) getParent());
    }

}
