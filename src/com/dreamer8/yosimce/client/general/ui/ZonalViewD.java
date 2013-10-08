package com.dreamer8.yosimce.client.general.ui;

import java.util.ArrayList;

import com.dreamer8.yosimce.shared.dto.CentroOperacionDTO;
import com.dreamer8.yosimce.shared.dto.EtapaDTO;
import com.dreamer8.yosimce.shared.dto.ItemReporteMaterialDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ZonalViewD extends Composite {

	private static ZonalViewDUiBinder uiBinder = GWT
			.create(ZonalViewDUiBinder.class);

	interface ZonalViewDUiBinder extends UiBinder<Widget, ZonalViewD> {
	}

	private ArrayList<ItemReporteMaterialDTO> items;
	
	public ZonalViewD() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setTitle(String title){
		
	}
	
	public void setCentrosOperacion(ArrayList<CentroOperacionDTO> centros){
		
	}
	
	public void setEtapas(ArrayList<EtapaDTO> etapas){
		
	}
	
	public void updateData(ArrayList<ItemReporteMaterialDTO> items){
		this.items = items;
	}

}
