package com.dreamer8.yosimce.client.ui;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

public class OverMenuBar extends MenuBar{

	private MenuItem item;
	private ScheduledCommand command;
	private Timer t;
	private boolean mouseover;
	
	public OverMenuBar(){
		super();
		
	}
	
	public void setOverItem(MenuItem item){
		this.item = item;
	}
	
	public MenuItem getOverItem(){
		return item;
	}
	
	public ScheduledCommand getOverCommand() {
		return command;
	}

	public void setOverCommand(ScheduledCommand command) {
		this.command = command;
	}

	@Override
	public void onBrowserEvent(Event event) {
		MenuItem currentItem = null;
		
		if(item == null && getItems().size()>0){
			item = getItems().get(0);
		}
		
		if(command == null || item == null){
			return;
		}
		if(item.getElement().isOrHasChild(DOM.eventGetTarget(event))){
			currentItem = item;
		}
        switch (DOM.eventGetType(event)) {
        	case Event.ONMOUSEOVER:
        		if (currentItem != null) {
        			mouseover = true;
        			t = new Timer() {
        				
        				@Override
        				public void run() {
        					if(command!=null && mouseover){
        						command.execute();
        					}
        				}
        			};
        			t.schedule(200);
        		}
        		break;
        	case Event.ONMOUSEOUT:
        		if (currentItem != null) {
        			mouseover = false;
        		}
        		break;              
        	}
        super.onBrowserEvent(event);
	}
}
