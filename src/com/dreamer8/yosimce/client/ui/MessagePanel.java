package com.dreamer8.yosimce.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class MessagePanel extends Composite {

	private static MessagePanelUiBinder uiBinder = GWT
			.create(MessagePanelUiBinder.class);

	interface MessagePanelUiBinder extends UiBinder<Widget, MessagePanel> {
	}
	
	@UiField(provided = true) Image image;
	@UiField Image closeButton;
	@UiField HTML message;
	
	private int id;
	private MessageContainer parent;
	private int openTime;
	private Command closeCommand;
	private Timer timer;
	private boolean showing;

	public MessagePanel(int id, ImageResource i, String message, MessageContainer parent) {
		image = new Image(i);
		initWidget(uiBinder.createAndBindUi(this));
		this.id = id;
		this.message.setHTML(message);
		this.parent = parent;
		openTime = 0;
		showing = false;
	}
	
	@UiHandler("closeButton")
	void onCloseClick(ClickEvent event){
		remove();
	}
	
	public void setAutoClose(boolean autoclose){
		closeButton.setVisible(!autoclose);
		if(autoclose){
			timer = new Timer(){

				@Override
				public void run() {
					remove();
				}
				
			};
		}
	}
	
	public void setOpenTime(int openTime){
		this.openTime = openTime;
	}
	
	public void setCloseCommand(Command c){
		this.closeCommand = c;
	}
	
	public void show(){
		if(!showing){
			parent.add(this);
			showing = true;
			if(timer!=null && openTime>0){
				timer.schedule(openTime);
			}
		}
	}
	
	public int getId(){
		return id;
	}
	
	private void remove(){
		if(showing){
			showing = false;
			parent.remove(MessagePanel.this);
			if(closeCommand!=null){
				closeCommand.execute();
			}
		}
	}
	

}
