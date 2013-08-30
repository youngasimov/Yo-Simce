package com.dreamer8.yosimce.client.ui;

import com.dreamer8.yosimce.shared.dto.DocumentoDTO;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;

public class HyperTextCell extends AbstractCell<DocumentoDTO> {

	interface Template extends SafeHtmlTemplates {
	    @Template("<a target=\"_blank\" href=\"{0}\">{1}</a>")
	    SafeHtml hyperText(SafeUri link, String text);
	}
	
	private static Template template;
	
	public HyperTextCell(){
		if (template == null) {
	        template = GWT.create(Template.class);
	    }
	}
	
	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context,
			DocumentoDTO value, SafeHtmlBuilder sb) {
		if (value != null) {
	        sb.append(template.hyperText(UriUtils.fromString(value.getUrl()), value.getName()));
	    }
	}

}
