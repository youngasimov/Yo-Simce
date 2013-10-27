package com.dreamer8.yosimce.client.material.ui;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.SingleUploaderModal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

import com.dreamer8.yosimce.client.material.MaterialWrap;
import com.dreamer8.yosimce.client.ui.ImageButton;
import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.dreamer8.yosimce.client.ui.PlaceHolderTextBox;
import com.dreamer8.yosimce.client.ui.ViewUtils;
import com.dreamer8.yosimce.shared.dto.DetallesMaterialDTO;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.EtapaDTO;
import com.dreamer8.yosimce.shared.dto.HistorialMaterialItemDTO;
import com.dreamer8.yosimce.shared.dto.LoteDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class CentroOperacionViewD extends Composite implements CentroOperacionView {

	private static CentroOperacionViewDUiBinder uiBinder = GWT
			.create(CentroOperacionViewDUiBinder.class);

	interface CentroOperacionViewDUiBinder extends
			UiBinder<Widget, CentroOperacionViewD> {
	}
	
	interface Style extends CssResource{
		String keyColumn();
		String valueColumn();
		String parRow();
		String inparRow();
		
	}
	
	@UiField Style style;
	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	@UiField MenuItem cosItem;
	@UiField MenuItem filtrosItem;
	@UiField MenuItem exportarItem;
	@UiField MenuItem manualOperacionItem;
	
	@UiField TabLayoutPanel tabPanel;
	@UiField SplitLayoutPanel materialPanel;
	@UiField SplitLayoutPanel ingresoPanel;
	@UiField SplitLayoutPanel predespachoPanel;
	@UiField SplitLayoutPanel despachoPanel;
	@UiField(provided=true) DataGrid<HistorialMaterialItemDTO> historialGrid;
	@UiField FlexTable detallesGrid;
	@UiField(provided=true) DataGrid<MaterialWrap> materialGrid;
	@UiField(provided=true) DataGrid<MaterialWrap> ingresoGrid;
	@UiField(provided=true) DataGrid<MaterialWrap> predespachoGrid;
	@UiField(provided=true) DataGrid<MaterialWrap> despachoGrid;
	@UiField(provided=true) SingleUploaderModal ingresoUploader;
	@UiField(provided=true) SingleUploaderModal despachoUploader;
	@UiField(provided=true) SimplePager materialPager;
	@UiField Label totalIngresoLabel;
	@UiField Label docIngresoLabel;
	@UiField Label totalLoteLabel;
	@UiField Label totalDespachoLabel;
	@UiField Label coLabel;
	@UiField Label retiranteLabel;
	@UiField Label salidaDocLabel;
	@UiField PlaceHolderTextBox ingresoBox;
	@UiField PlaceHolderTextBox ingresoFolioBox;
	@UiField PlaceHolderTextBox nuevoLoteBox;
	@UiField PlaceHolderTextBox predespachoBox;
	@UiField PlaceHolderTextBox despachoBox;
	@UiField PlaceHolderTextBox despachoFolioBox;
	@UiField PlaceHolderTextBox rutRetiranteBox;
	@UiField Button ingresoAddButton;
	@UiField Button predespachoButton;
	@UiField Button addLoteButton;
	@UiField Button despachoButton;
	@UiField ImageButton changeCoButton;
	@UiField ImageButton ingresarButton;
	@UiField ImageButton removeLoteButton;
	@UiField ImageButton addOrEditLoteButton;
	@UiField ImageButton retiranteButton;
	@UiField ImageButton despacharButton;
	@UiField ListBox lotesBox;
	@UiField ListBox destinoDespachoBox;
	@UiField CheckBox loteBox;
	
	private HandlerRegistration historialHandlerRegistration;
	private HandlerRegistration materialHandlerRegistration;
	private HandlerRegistration ingresoHandlerRegistration;
	private HandlerRegistration predespachoHandlerRegistration;
	private HandlerRegistration despachoHandlerRegistration;
	
	private int idCentro;
	
	
	private CentroOperacionPresenter presenter;
	
	public CentroOperacionViewD() {
		
		historialGrid = new DataGrid<HistorialMaterialItemDTO>(HistorialMaterialItemDTO.KEY_PROVIDER);
		materialGrid = new DataGrid<MaterialWrap>(MaterialWrap.KEY_PROVIDER);
		ingresoGrid = new DataGrid<MaterialWrap>(MaterialWrap.KEY_PROVIDER);
		predespachoGrid = new DataGrid<MaterialWrap>(MaterialWrap.KEY_PROVIDER);
		despachoGrid = new DataGrid<MaterialWrap>(MaterialWrap.KEY_PROVIDER);
		ingresoUploader = new SingleUploaderModal(FileInputType.ANCHOR);
		ingresoUploader.setAutoSubmit(true);
		despachoUploader = new SingleUploaderModal(FileInputType.ANCHOR);
		despachoUploader.setAutoSubmit(true);
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		materialPager = new SimplePager(TextLocation.CENTER, pagerResources,true, 0, true);
		
		
		
		initWidget(uiBinder.createAndBindUi(this));
		menu.insertSeparator(2);
		menu.insertSeparator(4);
		menu.setOverItem(menuItem);
		menu.setOverCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.toggleMenu();
			}
		});
		exportarItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.onExportarClick();
			}
		});
		
		manualOperacionItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.onManualOperacionClick();
			}
		});
		
		buildHistorialTable();
		buildMaterialTable();
		buildIngresoGrid();
		buildPreDespachoTable();
		buildDespachoTable();
		
		
        
		
	}
	
	@UiHandler("tabPanel")
	void onTabSelected(SelectionEvent<Integer> event){
		int x = tabPanel.getSelectedIndex();
		switch(x){
		case 0:
			presenter.onMaterialTabSelected();
			break;
		case 1:
			presenter.onIngresoTabSelected();
			break;
		case 2:
			presenter.onPredespachoTabSelected();
			break;
		case 3:
			presenter.onDespachoTabSelected();
			break;
		}
	}
	
	@UiHandler("ingresoBox")
	void onIngresoBoxKeyUp(KeyUpEvent event){
		if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
			presenter.onMaterialAddedToIngresoStack(ingresoBox.getValue());
		}
	}
	
	@UiHandler("ingresoAddButton")
	void onIngresoAddButtonClick(ClickEvent event){
		presenter.onMaterialAddedToIngresoStack(ingresoBox.getValue());
	}
	
	@UiHandler("ingresarButton")
	void onIngresarButtonClick(ClickEvent event){
		presenter.onRealizarIngresoStackActualClick();
	}

	@UiHandler("addLoteButton")
	void onAddLoteButtonClick(ClickEvent event){
		presenter.onCrearLote(nuevoLoteBox.getValue());
	}
	
	@UiHandler("lotesBox")
	void onSelectedLoteChange(ChangeEvent event){
		presenter.onLoteSelected(Integer.parseInt(lotesBox.getValue(lotesBox.getSelectedIndex())));
	}
	
	@UiHandler("removeLoteButton")
	void onRemoveLoteButtonClick(ClickEvent event){
		presenter.onDeleteLote(Integer.parseInt(lotesBox.getValue(lotesBox.getSelectedIndex())));
	}
	
	@UiHandler("predespachoBox")
	void onPredespachoBoxKeyUp(KeyUpEvent event){
		if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
			presenter.onMaterialAddedToPredespachoStack(predespachoBox.getValue());
		}
	}
	
	@UiHandler("predespachoButton")
	void onPredespachoButtonClick(ClickEvent event){
		presenter.onMaterialAddedToPredespachoStack(predespachoBox.getValue());
	}
	
	@UiHandler("addOrEditLoteButton")
	void onAddOrEditLoteButtonClick(ClickEvent event){
		presenter.guardarLote();
	}
	
	@UiHandler("despachoBox")
	void onDespachoBoxKeyUp(KeyUpEvent event){
		if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
			presenter.onMaterialAddedToDespachoStack(despachoBox.getValue());
		}
	}
	
	@UiHandler("despachoButton")
	void onDepachoButtonClick(ClickEvent event){
		presenter.onMaterialAddedToDespachoStack(despachoBox.getValue());
	}
	
	@UiHandler("destinoDespachoBox")
	void onDestinoDespachoChange(ChangeEvent event){
		presenter.onEtapaChange(Integer.parseInt(destinoDespachoBox.getValue(destinoDespachoBox.getSelectedIndex())));
	}
	
	@UiHandler("changeCoButton")
	void onChangeCoButtonClick(ClickEvent event){
		presenter.onChangeSelectedStageCo();
	}
	
	@UiHandler("rutRetiranteBox")
	void onRutRetiranteKeyUp(KeyUpEvent event){
		if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
			presenter.onRutRetiranteChange(rutRetiranteBox.getValue());
		}
	}
	
	@UiHandler("retiranteButton")
	void onRetiranteButtonClick(ClickEvent event){
		presenter.onRutRetiranteChange(rutRetiranteBox.getValue());
	}
	
	@UiHandler("despacharButton")
	void onDespacharButtonClick(ClickEvent event){
		presenter.onRealizarDespachoStackActualClick();
	}
	
	@Override
	public void setPresenter(CentroOperacionPresenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public MenuItem getFiltroMenu() {
		return filtrosItem;
	}
	
	@Override
	public void setCO(EmplazamientoDTO emplazamiento) {
		if(emplazamiento!=null){
			idCentro = emplazamiento.getId();
			cosItem.setText(emplazamiento.getNombre());
		}else{
			idCentro  = -1;
			cosItem.setText("-----");
		}
	}
	
	@Override
	public void setMaterialVisivility(boolean visible) {
		tabPanel.getTabWidget(0).setVisible(visible);
		materialPanel.setVisible(visible);
	}
	
	@Override
	public void setIngresoVisivility(boolean visible) {
		tabPanel.getTabWidget(1).setVisible(visible);
		ingresoPanel.setVisible(visible);
	}
	
	@Override
	public void setPredespachoVisivility(boolean visible) {
		tabPanel.getTabWidget(2).setVisible(visible);
		predespachoPanel.setVisible(visible);
	}
	
	@Override
	public void setDespachoVisivility(boolean visible) {
		tabPanel.getTabWidget(3).setVisible(visible);
		despachoPanel.setVisible(visible);
	}
	
	@Override
	public HasData<HistorialMaterialItemDTO> getHistorialDataDisplay() {
		return historialGrid;
	}

	@Override
	public void setHistorialSortHandler(ListHandler<HistorialMaterialItemDTO> handler) {
		if(historialHandlerRegistration!=null){
			historialHandlerRegistration.removeHandler();
		}
		historialHandlerRegistration = historialGrid.addColumnSortHandler(handler);
		handler.setComparator(historialGrid.getColumn(0), new Comparator<HistorialMaterialItemDTO>() {

			@Override
			public int compare(HistorialMaterialItemDTO o1,
					HistorialMaterialItemDTO o2) {
				return o1.getFecha().compareTo(o2.getFecha());
			}
		});
		
	}

	@Override
	public HasData<MaterialWrap> getMaterialDataDisplay() {
		return materialGrid;
	}

	@Override
	public void setMaterialSortHandler(ListHandler<MaterialWrap> handler) {
		if(materialHandlerRegistration!=null){
			materialHandlerRegistration.removeHandler();
		}
		materialHandlerRegistration = materialGrid.addColumnSortHandler(handler);
		configureSortHandler(materialGrid, handler);
		
		handler.setComparator( materialGrid.getColumn(7), new Comparator<MaterialWrap>() {

			@Override
			public int compare(MaterialWrap o1, MaterialWrap o2) {
				if(o1.getMaterial().getEtapa()!=null){
					return o1.getMaterial().getEtapa().compareTo(o2.getMaterial().getEtapa());
				}else{
					return -1;
				}
			}
		});
		
	}
	
	@Override
	public HasData<MaterialWrap> getIngresoDataDisplay() {
		return ingresoGrid;
	}
	
	@Override
	public void setIngresoSortHandler(ListHandler<MaterialWrap> handler) {
		if(ingresoHandlerRegistration!=null){
			ingresoHandlerRegistration.removeHandler();
		}
		ingresoHandlerRegistration = ingresoGrid.addColumnSortHandler(handler);
		configureSortHandler(ingresoGrid, handler);
	}

	@Override
	public HasData<MaterialWrap> getPredespachoDataDisplay() {
		return predespachoGrid;
	}

	@Override
	public void setPredespachoSortHandler(ListHandler<MaterialWrap> handler) {
		if(predespachoHandlerRegistration!=null){
			predespachoHandlerRegistration.removeHandler();
		}
		predespachoHandlerRegistration = predespachoGrid.addColumnSortHandler(handler);
		configureSortHandler(predespachoGrid, handler);
	}
	
	@Override
	public HasData<MaterialWrap> getDespachoDataDisplay() {
		return despachoGrid;
	}
	
	@Override
	public void setDespachoSortHandler(ListHandler<MaterialWrap> handler) {
		if(despachoHandlerRegistration!=null){
			despachoHandlerRegistration.removeHandler();
		}
		despachoHandlerRegistration = despachoGrid.addColumnSortHandler(handler);
		configureSortHandler(despachoGrid, handler);
	}

	@Override
	public void setDetallesMaterial(MaterialDTO m,  DetallesMaterialDTO detalles, String detallesToken) {
		detallesGrid.clear();
		if(m == null){
			return;
		}
		detallesGrid.getColumnFormatter().setStyleName(0, style.keyColumn());
		detallesGrid.getColumnFormatter().setStyleName(1, style.valueColumn());
		detallesGrid.setWidget(0, 0, new Label("Etapa:"));
		detallesGrid.setWidget(0, 1, new Label(m.getEtapa()));
		detallesGrid.getRowFormatter().setStyleName(0, style.parRow());
		
		detallesGrid.setWidget(1, 0, new Label("Lote:"));
		detallesGrid.setWidget(1, 1, new Label((m.getLote()!=null)?m.getLote().getNombre():""));
		detallesGrid.getRowFormatter().setStyleName(1, style.inparRow());
		
		if(detalles == null){
			return;
		}
		detallesGrid.setWidget(2, 0, new Label("C.O. asignado:"));
		detallesGrid.setWidget(2, 1, new Label((detalles.getCentroOperacionAsignado()!=null)?
				detalles.getCentroOperacionAsignado().getNombre():""));
		detallesGrid.getRowFormatter().setStyleName(2, style.parRow());
		detallesGrid.setWidget(3, 0, new Label("C.O. ingresado:"));
		detallesGrid.setWidget(3, 1, new Label((detalles.getCentroOperacionIngresado()!=null)?
				detalles.getCentroOperacionIngresado().getNombre():""));
		detallesGrid.getRowFormatter().setStyleName(3, style.inparRow());
		int i = 4;
		if(detalles.getDocumentos()!=null && !detalles.getDocumentos().isEmpty()){
			
			for(Map.Entry<String,DocumentoDTO> entry:detalles.getDocumentos().entrySet()){
				Anchor a = new Anchor("#Folio:"+entry.getKey());
				if(entry.getValue()!=null){
					a.setHref(entry.getValue().getUrl());
				}
				detallesGrid.setWidget(i, 0, a);
				detallesGrid.getFlexCellFormatter().setColSpan(i, 0, 2);
				detallesGrid.getFlexCellFormatter().setStyleName(i, 0, (i%2==0)?style.parRow():style.inparRow());
				i++;
				
			}
		}
		detallesGrid.setWidget(i, 0, new Hyperlink("más detalles", detallesToken));
		detallesGrid.getFlexCellFormatter().setColSpan(i, 0, 2);
		detallesGrid.getFlexCellFormatter().setStyleName(i, 0, (i%2==0)?style.parRow():style.inparRow());
	}
	
	@Override
	public void clearIngresoFolioBox() {
		ingresoFolioBox.setValue(null);
	}
	
	@Override
	public void clearDespachoFolioBox() {
		despachoFolioBox.setValue(null);
	}
	
	@Override
	public void clearRutRetiranteBox() {
		rutRetiranteBox.setValue("");
		
	}

	@Override
	public void clearNuevoLoteBox() {
		nuevoLoteBox.setValue("");
	}

	@Override
	public void setFocusOnIngresoCodigoBox(boolean cleanFirst) {
		if(cleanFirst){
			ingresoBox.setValue(null);
		}
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				ingresoBox.setFocus(true);
			}
		});
	}

	@Override
	public void setFocusOnPredespachoCodigoBox(boolean cleanFirst) {
		if(cleanFirst){
			predespachoBox.setValue(null);
		}
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				predespachoBox.setFocus(true);
			}
		});
	}

	@Override
	public void setFocusOnDespachoCodigoBox(boolean cleanFirst) {
		if(cleanFirst){
			despachoBox.setValue(null);
		}
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				despachoBox.setFocus(true);
			}
		});
	}
	
	@Override
	public String getIngresoFolio() {
		return ingresoFolioBox.getValue();
	}
	
	@Override
	public String getDespachoFolio() {
		return despachoFolioBox.getValue();
	}

	@Override
	public void setTotalMaterialIngresando(int total) {
		totalIngresoLabel.setText(total+"");
	}

	@Override
	public void setTotalMaterialEnLote(int total) {
		totalLoteLabel.setText(total+"");
	}

	@Override
	public void setTotalMaterialDespachando(int total) {
		totalDespachoLabel.setText(total+"");
	}

	@Override
	public void setIngresoDocumento(DocumentoDTO doc) {
		docIngresoLabel.setText((doc!=null)?doc.getName():"");
	}

	@Override
	public void setDespachoDocumento(DocumentoDTO doc) {
		salidaDocLabel.setText((doc!=null)?doc.getName():"");
	}

	@Override
	public void setLotes(ArrayList<LoteDTO> lotes) {
		lotesBox.clear();
		lotesBox.addItem("----------","-1");
		if(lotes!=null){
			for(LoteDTO lote:lotes){
				lotesBox.addItem(lote.getNombre(),lote.getId()+"");
			}
		}
	}
	
	@Override
	public void selectLote(int loteId) {
		if(loteId == -1){
			lotesBox.setSelectedIndex(0);
		}else{
			for(int i = 0;i<lotesBox.getItemCount(); i++){
				int id = Integer.parseInt(lotesBox.getValue(i));
				if(id == loteId){
					lotesBox.setSelectedIndex(i);
				}
			}
		}
	}

	@Override
	public void setAddByLote(boolean addByLote) {
		loteBox.setValue(addByLote);
	}

	@Override
	public boolean getAddByLote() {
		return loteBox.getValue();
	}

	@Override
	public void setEtapas(ArrayList<EtapaDTO> etapas) {
		destinoDespachoBox.clear();
		destinoDespachoBox.addItem("-------------","-1");
		if(etapas!=null){
			for(EtapaDTO etapa:etapas){
				destinoDespachoBox.addItem(etapa.getEtapa(),etapa.getId()+"");
			}
		}
	}

	@Override
	public void setChangeCoButtonVisivility(boolean visible) {
		changeCoButton.setVisible(visible);
	}

	@Override
	public void setSelectedCo(EmplazamientoDTO co) {
		coLabel.setText((co!=null)?co.getTipoEmplazamiento()+" - "+co.getNombre():"");
	}

	@Override
	public void setRetirante(UserDTO user) {
		if(user!=null){
			retiranteLabel.setText(ViewUtils.limitarString(user.getNombres()+" "+user.getApellidoPaterno()+" "+user.getApellidoMaterno(), 30));
		}else{
			retiranteLabel.setText("");
		}
	}
	
	@Override
	public void setManualOperacionVisible(boolean visible) {
		manualOperacionItem.setVisible(visible);
	}
	
	private void buildHistorialTable(){
		
		historialGrid.setAutoHeaderRefreshDisabled(true);		
		historialGrid.setAutoFooterRefreshDisabled(true);
		historialGrid.setPageSize(50);
		historialGrid.setPageStart(0);
		historialGrid.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		
		DateTimeFormat dtf = DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM);
		Column<HistorialMaterialItemDTO,Date> dateColumn = new Column<HistorialMaterialItemDTO,Date>(new DateCell(dtf)){

			@Override
			public Date getValue(HistorialMaterialItemDTO object) {
				return object.getFecha();
			}
		};
		dateColumn.setSortable(true);
		historialGrid.addColumn(dateColumn,"Fecha");
		
		Column<HistorialMaterialItemDTO,String> desdeColumn = new Column<HistorialMaterialItemDTO,String>(new TextCell()){

			@Override
			public String getValue(HistorialMaterialItemDTO object) {
				return object.getDesde();
			}
		};
		historialGrid.addColumn(desdeColumn,"Desde");
		
		Column<HistorialMaterialItemDTO,String> haciaColumn = new Column<HistorialMaterialItemDTO,String>(new TextCell()){

			@Override
			public String getValue(HistorialMaterialItemDTO object) {
				return object.getHacia();
			}
		};
		historialGrid.addColumn(haciaColumn,"Hacia");
		
		Column<HistorialMaterialItemDTO,String> usuarioColumn = new Column<HistorialMaterialItemDTO,String>(new TextCell()){

			@Override
			public String getValue(HistorialMaterialItemDTO object) {
				return (object.getReceptor()!=null)?object.getReceptor().getNombres()+" "+object.getReceptor().getApellidoPaterno()+" "+object.getReceptor().getApellidoMaterno():"";
			}
		};
		historialGrid.addColumn(usuarioColumn,"Usuario receptor");
	}
	
	private void buildMaterialTable(){
		
		materialGrid.setKeyboardPagingPolicy(KeyboardPagingPolicy.CHANGE_PAGE);
	    materialPager.setDisplay(materialGrid);
	    
	    final SingleSelectionModel<MaterialWrap> sm = new SingleSelectionModel<MaterialWrap>();
	    materialGrid.setSelectionModel(sm);
	    
	    sm.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				presenter.onMaterialSelected(sm.getSelectedObject());
			}
		});
	    
	    buildTable(materialGrid);
		
		Column<MaterialWrap,String> etapaColumn = new Column<MaterialWrap,String>(new TextCell()){

			@Override
			public String getValue(MaterialWrap o) {
				if(o.getMaterial().getTransicion() == null){
					o.getMaterial().setTransicion("En ");
				}
				return o.getMaterial().getTransicion()+o.getMaterial().getEtapa();
			}
		};
		etapaColumn.setSortable(true);
		materialGrid.addColumn(etapaColumn,"Etapa");
		materialGrid.setColumnWidth(etapaColumn, "110px");
		buildUpdateColumn(materialGrid);
	}
	
	private void buildIngresoGrid(){
		ingresoGrid.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		buildTable(ingresoGrid);
		buildUpdateColumn(ingresoGrid);
		Column<MaterialWrap,MaterialWrap> removeColumn = new Column<MaterialWrap,MaterialWrap>(new ActionCell<MaterialWrap>("Eliminar", new Delegate<MaterialWrap>() {

			@Override
			public void execute(MaterialWrap object) {
				presenter.onRemoveIngresoItem(object);
			}
		})){

			@Override
			public MaterialWrap getValue(MaterialWrap object) {
				return object;
			}
		};
		removeColumn.setSortable(false);
		ingresoGrid.addColumn(removeColumn,"");
		ingresoGrid.setColumnWidth(removeColumn, "90px");
	}
	
	private void buildPreDespachoTable(){
		predespachoGrid.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		buildTable(predespachoGrid);
		buildUpdateColumn(predespachoGrid);
		Column<MaterialWrap,MaterialWrap> removeColumn = new Column<MaterialWrap,MaterialWrap>(new ActionCell<MaterialWrap>("Eliminar", new Delegate<MaterialWrap>() {

			@Override
			public void execute(MaterialWrap object) {
				presenter.onRemovePredespachoItem(object);
			}
		})){

			@Override
			public MaterialWrap getValue(MaterialWrap object) {
				return object;
			}
		};
		removeColumn.setSortable(false);
		predespachoGrid.addColumn(removeColumn,"");
		predespachoGrid.setColumnWidth(removeColumn, "90px");
	}
	
	private void buildDespachoTable(){
		despachoGrid.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		buildTable(despachoGrid);
		buildUpdateColumn(despachoGrid);
		Column<MaterialWrap,MaterialWrap> removeColumn = new Column<MaterialWrap,MaterialWrap>(new ActionCell<MaterialWrap>("Eliminar", new Delegate<MaterialWrap>() {

			@Override
			public void execute(MaterialWrap object) {
				presenter.onRemoveDespachoItem(object);
			}
		})){

			@Override
			public MaterialWrap getValue(MaterialWrap object) {
				return object;
			}
		};
		removeColumn.setSortable(false);
		despachoGrid.addColumn(removeColumn,"");
		despachoGrid.setColumnWidth(removeColumn, "90px");
	}
	
	private void buildTable(DataGrid<MaterialWrap> d){
		
		d.setAutoFooterRefreshDisabled(true);
		d.setAutoHeaderRefreshDisabled(true);
		d.setPageSize(50);
		d.setPageStart(0);
		
		Column<MaterialWrap,String> warningColumn = new Column<MaterialWrap,String>(new ImageCell()){

			@Override
			public String getValue(MaterialWrap o) {
				return(o.getMaterial().getIdCentro() == null || o.getMaterial().getIdCentro() != idCentro)?"/images/warning.png":"";
			}
		};
		warningColumn.setSortable(false);
		d.addColumn(warningColumn,"");
		d.setColumnWidth(warningColumn, "45px");
		
		
		Column<MaterialWrap,String> idColumn = new Column<MaterialWrap,String>(new TextCell()){

			@Override
			public String getValue(MaterialWrap o) {
				return (o.getMaterial().getCodigo()!=null && o.getMaterial().getCodigo().length()>5)?"..."+o.getMaterial().getCodigo().substring(o.getMaterial().getCodigo().length()-4):"...";
			}
		};
		idColumn.setSortable(false);
		d.addColumn(idColumn,"Código");
		d.setColumnWidth(idColumn, "90px");
		
		Column<MaterialWrap,String> tipoColumn = new Column<MaterialWrap,String>(new TextCell()){

			@Override
			public String getValue(MaterialWrap o) {
				return o.getMaterial().getTipo();
			}
		};
		tipoColumn.setSortable(true);
		d.addColumn(tipoColumn,"Tipo");
		d.setColumnWidth(tipoColumn, "160px");
		
		Column<MaterialWrap,String> rbdColumn = new Column<MaterialWrap,String>(new TextCell()){

			@Override
			public String getValue(MaterialWrap o) {
				return o.getMaterial().getRbd();
			}
		};
		rbdColumn.setSortable(true);
		d.addColumn(rbdColumn,"RBD");
		d.setColumnWidth(rbdColumn, "70px");
		
		Column<MaterialWrap,String> establecimientoColumn = new Column<MaterialWrap,String>(new TextCell()){

			@Override
			public String getValue(MaterialWrap o) {
				return o.getMaterial().getEstablecimiento();
			}
		};
		establecimientoColumn.setSortable(true);
		d.addColumn(establecimientoColumn,"Establecimiento");
		d.setColumnWidth(establecimientoColumn, "240px");
		
		Column<MaterialWrap,String> nivelColumn = new Column<MaterialWrap,String>(new TextCell()){

			@Override
			public String getValue(MaterialWrap o) {
				return o.getMaterial().getNivel();
			}
		};
		nivelColumn.setSortable(true);
		d.addColumn(nivelColumn,"Nivel");
		d.setColumnWidth(nivelColumn, "100px");
		
		Column<MaterialWrap,String> cursoColumn = new Column<MaterialWrap,String>(new TextCell()){

			@Override
			public String getValue(MaterialWrap o) {
				return o.getMaterial().getCurso();
			}
		};
		cursoColumn.setSortable(true);
		d.addColumn(cursoColumn,"Curso");
		d.setColumnWidth(cursoColumn, "60px");
	}
	
	private void buildUpdateColumn(DataGrid<MaterialWrap> d){
		Column<MaterialWrap,String> updateColumn = new Column<MaterialWrap,String>(new ButtonCell()){

			@Override
			public String getValue(MaterialWrap o) {
				return (o.isUpdating())?"Actualizando...":
						(o.getMaterial().getId() == null)?"Actualizar":"Actualizado";
			}
		};
		updateColumn.setSortable(false);
		updateColumn.setFieldUpdater(new FieldUpdater<MaterialWrap, String>() {
			
			@Override
			public void update(int index, MaterialWrap object, String value) {
				presenter.actualizarMaterial(object);
			}
		});
		d.addColumn(updateColumn,"");
		d.setColumnWidth(updateColumn, "110px");
	}
	
	private void configureSortHandler(DataGrid<MaterialWrap> d, ListHandler<MaterialWrap> h){
		
		h.setComparator( d.getColumn(2), new Comparator<MaterialWrap>() {

			@Override
			public int compare(MaterialWrap o1, MaterialWrap o2) {
				if(o1.getMaterial().getTipo()!=null){
					return o1.getMaterial().getTipo().compareTo(o2.getMaterial().getTipo());
				}else{
					return -1;
				}
			}
		});
		
		h.setComparator( d.getColumn(3), new Comparator<MaterialWrap>() {

			@Override
			public int compare(MaterialWrap o1, MaterialWrap o2) {
				if(o1.getMaterial().getRbd()!=null){
					return o1.getMaterial().getRbd().compareTo(o2.getMaterial().getRbd());
				}else{
					return -1;
				}
			}
		});
		
		h.setComparator( d.getColumn(4), new Comparator<MaterialWrap>() {

			@Override
			public int compare(MaterialWrap o1, MaterialWrap o2) {
				if(o1.getMaterial().getEstablecimiento()!=null){
					return o1.getMaterial().getEstablecimiento().compareTo(o2.getMaterial().getEstablecimiento());
				}else{
					return -1;
				}
			}
		});
		h.setComparator( d.getColumn(5), new Comparator<MaterialWrap>() {

			@Override
			public int compare(MaterialWrap o1, MaterialWrap o2) {
				if(o1.getMaterial().getNivel()!=null){
					return o1.getMaterial().getNivel().compareTo(o2.getMaterial().getNivel());
				}else{
					return -1;
				}
			}
		});
		h.setComparator( d.getColumn(6), new Comparator<MaterialWrap>() {

			@Override
			public int compare(MaterialWrap o1, MaterialWrap o2) {
				if(o1.getMaterial().getCurso()!=null){
					return o1.getMaterial().getCurso().compareTo(o2.getMaterial().getCurso());
				}else{
					return -1;
				}
			}
		});
	}
}
