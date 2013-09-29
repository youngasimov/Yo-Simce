package com.dreamer8.yosimce.client.administracion.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import com.dreamer8.yosimce.client.ui.OverMenuBar;
import com.dreamer8.yosimce.client.ui.resources.SimceResources;
import com.dreamer8.yosimce.shared.dto.PermisoDTO;
import com.dreamer8.yosimce.shared.dto.TipoUsuarioDTO;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SafeHtmlHeader;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasData;

public class PermisosViewD extends Composite implements PermisosView {

	public class PermisoColumn extends Column<PermisoDTO, Boolean>{

		private int idTipoUsuario;
		private PermisosPresenter presenter; 
		
		public PermisoColumn(CheckboxCell cell, int tipoUsuarioId, PermisosPresenter presenter) {
			super(cell);
			idTipoUsuario = tipoUsuarioId;
			this.presenter = presenter;
			setFieldUpdater(new FieldUpdater<PermisoDTO, Boolean>() {

				@Override
				public void update(int index, PermisoDTO object, Boolean value) {
					if(!PermisoColumn.this.presenter.hasUpdatePermisos()){
						return;
					}
					if(value && !object.getIdTiposUsuariosPermitidos().contains(idTipoUsuario)){
						object.getIdTiposUsuariosPermitidos().add(idTipoUsuario);
						PermisoColumn.this.presenter.permisoActualizado(object);
					}else if(!value && object.getIdTiposUsuariosPermitidos().contains(idTipoUsuario)){
						object.getIdTiposUsuariosPermitidos().remove(new Integer(idTipoUsuario));
						PermisoColumn.this.presenter.permisoActualizado(object);
					}
				}
			});
		}

		@Override
		public Boolean getValue(PermisoDTO p) {
			return p.getIdTiposUsuariosPermitidos().contains(idTipoUsuario);
		}
		
	}
	
	private static PermisosViewDUiBinder uiBinder = GWT
			.create(PermisosViewDUiBinder.class);

	interface PermisosViewDUiBinder extends UiBinder<Widget, PermisosViewD> {
	}

	@UiField OverMenuBar menu;
	@UiField MenuItem menuItem;
	@UiField MenuItem updatePermisosItem;
	@UiField MenuItem updateViewItem;
	@UiField MenuItem programarActualizacionItem;
	@UiField MenuItem enviarCorreosSimceItem;
	@UiField MenuItem enviarCorreosSimceTicItem;
	
	@UiField(provided = true) DataGrid<PermisoDTO> dataGrid;
	
	private PermisosPresenter presenter;
	
	private Column<PermisoDTO, String> moduloColumn;
	private Column<PermisoDTO, String> permisoColumn;
	private Column<PermisoDTO, String> moduloColumnFinal;
	private Column<PermisoDTO, String> permisoColumnFinal;
	private ArrayList<PermisoColumn> columns;
	
	private PopupPanel programarUpdatePopup;
	private UpdateProgramarViewD programarUpdatePanel;
	
	private DateTimeFormat format;
	
	public PermisosViewD() {
		dataGrid = new DataGrid<PermisoDTO>(PermisoDTO.KEY_PROVIDER);
		dataGrid.setPageSize(500);
		initWidget(uiBinder.createAndBindUi(this));
		format = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM);
		columns = new ArrayList<PermisosViewD.PermisoColumn>();
		menu.insertSeparator(1);
		menu.setOverItem(menuItem);
		menu.setOverCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.toggleMenu();
			}
		});
		updatePermisosItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.onUpdatePermisosClick();
			}
		});
		
		updateViewItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				presenter.onUpdateTablaClick();
			}
		});
		
		programarActualizacionItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				openUpdateProgramerView();
			}
		});
		
		enviarCorreosSimceItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				
			}
		});
		
		enviarCorreosSimceTicItem.setScheduledCommand(new Scheduler.ScheduledCommand() {
			
			@Override
			public void execute() {
				
			}
		});
		
		programarUpdatePanel = new UpdateProgramarViewD();
		programarUpdatePopup = new PopupPanel(true, false);
		programarUpdatePopup.setAnimationEnabled(true);
		programarUpdatePopup.setGlassEnabled(false);
		programarUpdatePopup.setWidget(programarUpdatePanel);
		
		programarUpdatePanel.cerrarButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				programarUpdatePopup.hide();
			}
		});
		
		programarUpdatePanel.cancelarButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				presenter.onProgramarUpdate("");
				setUpdateTime(new Date());
				programarUpdatePopup.hide();
			}
		});
		
		programarUpdatePanel.programarButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				presenter.onProgramarUpdate(format.format(new Date(programarUpdatePanel.timeBox.getTime())));
				programarUpdatePopup.hide();
			}
		});
		
	}
	
	@UiFactory
	public static SimceResources getResources() {
		return SimceResources.INSTANCE;
	}
	
	@Override
	public void setActualizarPermisosVisivility(boolean visible) {
		updatePermisosItem.setVisible(visible);
	}
	
	@Override
	public void setActualizarTablaVisivility(boolean visible) {
		updateViewItem.setVisible(visible);
	}

	@Override
	public void setTiposUsuarios(ArrayList<TipoUsuarioDTO> tiposUsuario) {
		dataGrid.setRowData(new ArrayList<PermisoDTO>());
		dataGrid.setRowCount(0);
		if(moduloColumn!=null){dataGrid.removeColumn(moduloColumn);}
		if(permisoColumn!=null){dataGrid.removeColumn(permisoColumn);}
		if(moduloColumnFinal!=null){dataGrid.removeColumn(moduloColumnFinal);}
		if(permisoColumnFinal!=null){dataGrid.removeColumn(permisoColumnFinal);}
		for(PermisoColumn c:columns){
			dataGrid.removeColumn(c);
		}
		columns.clear();
		buildStaticColumns();
		Collections.sort(tiposUsuario, new Comparator<TipoUsuarioDTO>(){
			@Override
			public int compare(TipoUsuarioDTO arg0, TipoUsuarioDTO arg1) {
				return arg0.getId().compareTo(arg1.getId());
			}
		});
		PermisoColumn hasPermisoColumn;
		for(int i = 0; i<tiposUsuario.size();i++){
			hasPermisoColumn =new PermisoColumn(new CheckboxCell(), tiposUsuario.get(i).getId(),presenter);
			columns.add(hasPermisoColumn);
			dataGrid.addColumn(hasPermisoColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant(tiposUsuario.get(i).getTipoUsuario())));
	        dataGrid.setColumnWidth(hasPermisoColumn, 150, Unit.PX);
		}
		buildStaticFinalsColumns();
		//dataGrid.redraw();
	}
	
	private void buildStaticColumns(){
		moduloColumn =new Column<PermisoDTO, String>(new TextCell()) {
            @Override
            public String getValue(PermisoDTO object) {
                return object.getClase();
            }
        };
        moduloColumn.setSortable(false);
        dataGrid.addColumn(moduloColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Modulo")));
        dataGrid.setColumnWidth(moduloColumn, 200, Unit.PX);
        
        
        permisoColumn =new Column<PermisoDTO, String>(new TextCell()) {
            @Override
            public String getValue(PermisoDTO object) {
                return object.getMetodo();
            }
        };
        permisoColumn.setSortable(false);
        dataGrid.addColumn(permisoColumn, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Permiso")));
        dataGrid.setColumnWidth(permisoColumn, 220, Unit.PX);
	}
	
	private void buildStaticFinalsColumns(){
		permisoColumnFinal =new Column<PermisoDTO, String>(new TextCell()) {
            @Override
            public String getValue(PermisoDTO object) {
                return object.getMetodo();
            }
        };
        permisoColumnFinal.setSortable(false);
        dataGrid.addColumn(permisoColumnFinal, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Permiso")));
        dataGrid.setColumnWidth(permisoColumnFinal, 220, Unit.PX);
		
		moduloColumnFinal =new Column<PermisoDTO, String>(new TextCell()) {
            @Override
            public String getValue(PermisoDTO object) {
                return object.getClase();
            }
        };
        moduloColumnFinal.setSortable(false);
        dataGrid.addColumn(moduloColumnFinal, new SafeHtmlHeader(SafeHtmlUtils.fromSafeConstant("Modulo")));
        dataGrid.setColumnWidth(moduloColumnFinal, 200, Unit.PX);
	}

	@Override
	public void setPresenter(PermisosPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public HasData<PermisoDTO> getDataDisplay() {
		return dataGrid;
	}

	@Override
	public int getColumnUserId(int column) {
		if(dataGrid.getColumn(column) instanceof PermisoColumn){
			PermisoColumn pc = (PermisoColumn)dataGrid.getColumn(column);
			return pc.idTipoUsuario;
		}
		return 0;
	}
	
	@Override
	public void setUpdateTime(String update) {
		Date d = null;
		if(update == null || update.isEmpty()){
			 d = new Date();
			
		}else{
			d = format.parse(update);
		}
		setUpdateTime(d);
	}
	
	@Override
	public void setUpdateProgramerVisivility(boolean visible) {
		programarActualizacionItem.setVisible(visible);
	}
	
	private void setUpdateTime(Date d){
		programarUpdatePanel.dateBox.setValue(d);
		programarUpdatePanel.timeBox.setValue(d.getTime());
	}
	
	private void openUpdateProgramerView(){
		programarUpdatePopup.center();
	}
}
