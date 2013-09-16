package com.dreamer8.yosimce.client.material;


import com.dreamer8.yosimce.shared.dto.DetallesMaterialDTO;
import com.dreamer8.yosimce.shared.dto.MaterialDTO;
import com.google.gwt.view.client.ProvidesKey;

public class MaterialWrap {

	public static final ProvidesKey<MaterialWrap> KEY_PROVIDER = new ProvidesKey<MaterialWrap>() {

		@Override
		public Object getKey(MaterialWrap item) {
			return (item == null) ? null : item.getMaterial().getCodigo();
		}
	};
	
	private MaterialDTO material;
	private DetallesMaterialDTO detalles;
	private boolean updating;
	
	public MaterialDTO getMaterial() {
		return material;
	}
	public void setMaterial(MaterialDTO material) {
		this.material = material;
	}
	public DetallesMaterialDTO getDetalles() {
		return detalles;
	}
	public void setDetalles(DetallesMaterialDTO detalles) {
		this.detalles = detalles;
	}
	public boolean isUpdating() {
		return updating;
	}
	public void setUpdating(boolean updating) {
		this.updating = updating;
	}
	
}
