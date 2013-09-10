package com.dreamer8.yosimce.client.material.ui;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.SingleUploaderModal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import com.dreamer8.yosimce.client.ui.ImageButton;
import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.dreamer8.yosimce.client.ui.PlaceHolderTextBox;
import com.dreamer8.yosimce.client.ui.ViewUtils;
import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.dreamer8.yosimce.shared.dto.EmplazamientoDTO;
import com.dreamer8.yosimce.shared.dto.EtapaDTO;
import com.dreamer8.yosimce.shared.dto.HistorialMaterialItemDTO;
import com.dreamer8.yosimce.shared.dto.LoteDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDTO;
import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.ActionCell.Delegate;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuItem;
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

	
	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	@UiField MenuItem cosItem;
	@UiField MenuItem filtrosItem;
	@UiField MenuItem exportarItem;
	
	@UiField(provided=true) DataGrid<HistorialMaterialItemDTO> historialGrid;
	@UiField(provided=true) DataGrid<MaterialDTO> materialGrid;
	@UiField(provided=true) DataGrid<MaterialDTO> ingresoGrid;
	@UiField(provided=true) DataGrid<MaterialDTO> predespachoGrid;
	@UiField(provided=true) DataGrid<MaterialDTO> despachoGrid;
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
	@UiField PlaceHolderTextBox nuevoLoteBox;
	@UiField PlaceHolderTextBox predespachoBox;
	@UiField PlaceHolderTextBox despachoBox;
	@UiField PlaceHolderTextBox rutRetiranteBox;
	@UiField Button ingresoAddButton;
	@UiField Button predespachoButton;
	@UiField Button despachoButton;
	@UiField ImageButton ingresarButton;
	@UiField ImageButton addLoteButton;
	@UiField ImageButton removeLoteButton;
	@UiField ImageButton changeCoButton;
	@UiField ImageButton despacharButton;
	@UiField ListBox lotesBox;
	@UiField ListBox destinoDespachoBox;
	@UiField CheckBox loteBox;
	
	private HandlerRegistration historialHandlerRegistration;
	private HandlerRegistration materialHandlerRegistration;
	private HandlerRegistration ingresoHandlerRegistration;
	private HandlerRegistration predespachoHandlerRegistration;
	private HandlerRegistration despachoHandlerRegistration;
	
	
	private CentroOperacionPresenter presenter;
	
	public CentroOperacionViewD() {
		
		historialGrid = new DataGrid<HistorialMaterialItemDTO>(HistorialMaterialItemDTO.KEY_PROVIDER);
		materialGrid = new DataGrid<MaterialDTO>(MaterialDTO.KEY_PROVIDER);
		ingresoGrid = new DataGrid<MaterialDTO>(MaterialDTO.KEY_PROVIDER);
		predespachoGrid = new DataGrid<MaterialDTO>(MaterialDTO.KEY_PROVIDER);
		despachoGrid = new DataGrid<MaterialDTO>(MaterialDTO.KEY_PROVIDER);
		ingresoUploader = new SingleUploaderModal(FileInputType.ANCHOR);
		ingresoUploader.setAutoSubmit(true);
		despachoUploader = new SingleUploaderModal(FileInputType.ANCHOR);
		despachoUploader.setAutoSubmit(true);
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		materialPager = new SimplePager(TextLocation.CENTER, pagerResources,true, 0, true);
		initWidget(uiBinder.createAndBindUi(this));
		menu.insertSeparator(2);
		menu.setOverItem(menuItem);
		menu.setOverCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.toggleMenu();
			}
		});
		
		buildHistorialTable();
		buildMaterialTable();
		buildIngresoGrid();
		buildPreDespachoTable();
		buildDespachoTable();
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
	
	@UiHandler("predespachoButton")
	void onPredespachoButtonClick(ClickEvent event){
		presenter.onMaterialAddedToPredespachoStack(predespachoBox.getValue());
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
	void onRutRetiranteChange(ChangeEvent event){
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
	public HasData<MaterialDTO> getMaterialDataDisplay() {
		return materialGrid;
	}

	@Override
	public void setMaterialSortHandler(ListHandler<MaterialDTO> handler) {
		if(materialHandlerRegistration!=null){
			materialHandlerRegistration.removeHandler();
		}
		materialHandlerRegistration = materialGrid.addColumnSortHandler(handler);
		configureSortHandler(materialGrid, handler);
		
		handler.setComparator( materialGrid.getColumn(6), new Comparator<MaterialDTO>() {

			@Override
			public int compare(MaterialDTO o1, MaterialDTO o2) {
				return o1.getEtapa().compareTo(o2.getEtapa());
			}
		});
		
	}
	
	@Override
	public HasData<MaterialDTO> getIngresoDataDisplay() {
		return ingresoGrid;
	}
	
	@Override
	public void setIngresoSortHandler(ListHandler<MaterialDTO> handler) {
		if(ingresoHandlerRegistration!=null){
			ingresoHandlerRegistration.removeHandler();
		}
		ingresoHandlerRegistration = ingresoGrid.addColumnSortHandler(handler);
		configureSortHandler(ingresoGrid, handler);
	}

	@Override
	public HasData<MaterialDTO> getPredespachoDataDisplay() {
		return predespachoGrid;
	}

	@Override
	public void setPredespachoSortHandler(ListHandler<MaterialDTO> handler) {
		if(predespachoHandlerRegistration!=null){
			predespachoHandlerRegistration.removeHandler();
		}
		predespachoHandlerRegistration = predespachoGrid.addColumnSortHandler(handler);
		configureSortHandler(predespachoGrid, handler);
	}
	
	@Override
	public HasData<MaterialDTO> getDespachoDataDisplay() {
		return despachoGrid;
	}
	
	@Override
	public void setDespachoSortHandler(ListHandler<MaterialDTO> handler) {
		if(despachoHandlerRegistration!=null){
			despachoHandlerRegistration.removeHandler();
		}
		despachoHandlerRegistration = despachoGrid.addColumnSortHandler(handler);
		configureSortHandler(despachoGrid, handler);
	}

	@Override
	public void clearCodigoIngresoBox() {
		ingresoBox.setValue("");
	}

	@Override
	public void clearCodigoPredespachoBox() {
		predespachoBox.setValue("");
	}

	@Override
	public void clearCodigoDespachoBox() {
		despachoBox.setValue("");
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
	public void setFocusOnIngresoCodigoBox() {
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				ingresoBox.setFocus(true);
			}
		});
	}

	@Override
	public void setFocusOnPredespachoCodigoBox() {
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				predespachoBox.setFocus(true);
			}
		});
	}

	@Override
	public void setFocusOnDespachoCodigoBox() {
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				despachoBox.setFocus(true);
			}
		});
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
		for(LoteDTO lote:lotes){
			lotesBox.addItem(lote.getNombre(),lote.getId()+"");
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
		for(EtapaDTO etapa:etapas){
			destinoDespachoBox.addItem(etapa.getEtapa(),etapa.getId()+"");
		}
		destinoDespachoBox.addItem("Trasferencia a C.O.","-2");
	}

	@Override
	public void setChangeCoButtonVisivility(boolean visible) {
		changeCoButton.setVisible(visible);
	}

	@Override
	public void setSelectedCo(EmplazamientoDTO co) {
		coLabel.setText(co.getNombre()+" - "+co.getTipoEmplazamiento());
	}

	@Override
	public void setRetirante(UserDTO user) {
		retiranteLabel.setText(ViewUtils.limitarString(user.getNombres()+" "+user.getApellidoPaterno()+" "+user.getApellidoMaterno(), 25));
	}
	
	private void buildHistorialTable(){
		
		historialGrid.setAutoHeaderRefreshDisabled(true);		
		historialGrid.setAutoFooterRefreshDisabled(true);
		materialGrid.setPageSize(50);
		materialGrid.setPageStart(0);
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
				return (object.getAutorizante()!=null)?object.getAutorizante().getNombres()+" "+object.getAutorizante().getApellidoPaterno()+" "+object.getAutorizante().getApellidoMaterno():"";
			}
		};
		historialGrid.addColumn(usuarioColumn,"Usuario autorizante");
	}
	
	private void buildMaterialTable(){
		
		materialGrid.setKeyboardPagingPolicy(KeyboardPagingPolicy.CHANGE_PAGE);
	    materialPager.setDisplay(materialGrid);
	    
	    final SingleSelectionModel<MaterialDTO> sm = new SingleSelectionModel<MaterialDTO>();
	    materialGrid.setSelectionModel(sm);
	    
	    sm.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				presenter.onMaterialSelected(sm.getSelectedObject());
			}
		});
	    
	    buildTable(materialGrid);
		
		Column<MaterialDTO,String> etapaColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO o) {
				return o.getEtapa();
			}
		};
		etapaColumn.setSortable(true);
		materialGrid.addColumn(etapaColumn,"Etapa");
	}
	
	private void buildIngresoGrid(){
		ingresoGrid.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		buildTable(ingresoGrid);
		Column<MaterialDTO,MaterialDTO> removeColumn = new Column<MaterialDTO,MaterialDTO>(new ActionCell<MaterialDTO>("Eliminar", new Delegate<MaterialDTO>() {

			@Override
			public void execute(MaterialDTO object) {
				presenter.onRemoveIngresoItem(object);
			}
		})){

			@Override
			public MaterialDTO getValue(MaterialDTO object) {
				return object;
			}
		};
		removeColumn.setSortable(false);
		ingresoGrid.addColumn(removeColumn,"");
	}
	
	private void buildPreDespachoTable(){
		predespachoGrid.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		buildTable(predespachoGrid);
		Column<MaterialDTO,MaterialDTO> removeColumn = new Column<MaterialDTO,MaterialDTO>(new ActionCell<MaterialDTO>("Eliminar", new Delegate<MaterialDTO>() {

			@Override
			public void execute(MaterialDTO object) {
				presenter.onRemovePredespachoItem(object);
			}
		})){

			@Override
			public MaterialDTO getValue(MaterialDTO object) {
				return object;
			}
		};
		removeColumn.setSortable(false);
		predespachoGrid.addColumn(removeColumn,"");
	}
	
	private void buildDespachoTable(){
		despachoGrid.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		buildTable(despachoGrid);
		Column<MaterialDTO,MaterialDTO> removeColumn = new Column<MaterialDTO,MaterialDTO>(new ActionCell<MaterialDTO>("Eliminar", new Delegate<MaterialDTO>() {

			@Override
			public void execute(MaterialDTO object) {
				presenter.onRemoveDespachoItem(object);
			}
		})){

			@Override
			public MaterialDTO getValue(MaterialDTO object) {
				return object;
			}
		};
		removeColumn.setSortable(false);
		despachoGrid.addColumn(removeColumn,"");
	}
	
	private void buildTable(DataGrid<MaterialDTO> d){
		
		d.setAutoFooterRefreshDisabled(true);
		d.setAutoHeaderRefreshDisabled(true);
		d.setPageSize(50);
		d.setPageStart(0);
		
		Column<MaterialDTO,String> idColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO o) {
				return (o.getCodigo()!=null && o.getCodigo().length()>5)?"..."+o.getCodigo().substring(o.getCodigo().length()-4):"...";
			}
		};
		idColumn.setSortable(false);
		d.addColumn(idColumn,"Id");
		
		Column<MaterialDTO,String> tipoColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO o) {
				return o.getTipo();
			}
		};
		tipoColumn.setSortable(true);
		d.addColumn(tipoColumn,"Tipo");
		
		Column<MaterialDTO,String> rbdColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO o) {
				return o.getRbd();
			}
		};
		rbdColumn.setSortable(true);
		d.addColumn(rbdColumn,"RBD");
		
		Column<MaterialDTO,String> establecimientoColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO o) {
				return o.getEstablecimiento();
			}
		};
		establecimientoColumn.setSortable(true);
		d.addColumn(establecimientoColumn,"Establecimiento");
		
		Column<MaterialDTO,String> nivelColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO o) {
				return o.getNivel();
			}
		};
		nivelColumn.setSortable(true);
		d.addColumn(nivelColumn,"Nivel");
		
		Column<MaterialDTO,String> cursoColumn = new Column<MaterialDTO,String>(new TextCell()){

			@Override
			public String getValue(MaterialDTO o) {
				return o.getCurso();
			}
		};
		cursoColumn.setSortable(true);
		d.addColumn(cursoColumn,"Curso");
	}
	
	private void configureSortHandler(DataGrid<MaterialDTO> d, ListHandler<MaterialDTO> h){
		
		h.setComparator( d.getColumn(1), new Comparator<MaterialDTO>() {

			@Override
			public int compare(MaterialDTO o1, MaterialDTO o2) {
				return o1.getTipo().compareTo(o2.getTipo());
			}
		});
		
		h.setComparator( d.getColumn(2), new Comparator<MaterialDTO>() {

			@Override
			public int compare(MaterialDTO o1, MaterialDTO o2) {
				return o1.getRbd().compareTo(o2.getRbd());
			}
		});
		
		h.setComparator( d.getColumn(3), new Comparator<MaterialDTO>() {

			@Override
			public int compare(MaterialDTO o1, MaterialDTO o2) {
				return o1.getEstablecimiento().compareTo(o2.getEstablecimiento());
			}
		});
		h.setComparator( d.getColumn(4), new Comparator<MaterialDTO>() {

			@Override
			public int compare(MaterialDTO o1, MaterialDTO o2) {
				return o1.getNivel().compareTo(o2.getNivel());
			}
		});
		h.setComparator( d.getColumn(5), new Comparator<MaterialDTO>() {

			@Override
			public int compare(MaterialDTO o1, MaterialDTO o2) {
				return o1.getCurso().compareTo(o2.getCurso());
			}
		});
	}
}
