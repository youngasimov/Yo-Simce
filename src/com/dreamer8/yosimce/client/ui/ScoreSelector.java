package com.dreamer8.yosimce.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;

public class ScoreSelector extends Composite implements HasValue<Integer> {

	private static ScoreSelectorUiBinder uiBinder = GWT
			.create(ScoreSelectorUiBinder.class);

	interface ScoreSelectorUiBinder extends UiBinder<Widget, ScoreSelector> {
	}

	@UiField RadioButton reallyBadButton;
	@UiField RadioButton badButton;
	@UiField RadioButton okButton;
	@UiField RadioButton goodButton;
	@UiField RadioButton remarkableButton;
	
	private int value;
	
	public ScoreSelector() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("reallyBadButton")
	void onReallyBadButtonValueChange(ValueChangeEvent<Boolean> event){
		if(event.getValue()){
			value = Integer.parseInt(reallyBadButton.getFormValue());
			ValueChangeEvent.fire(this, value);
		}
	}
	
	@UiHandler("reallyBadButton")
	void onBadButtonValueChange(ValueChangeEvent<Boolean> event){
		if(event.getValue()){
			value = Integer.parseInt(badButton.getFormValue());
			ValueChangeEvent.fire(this, value);
		}
	}
	
	@UiHandler("okButton")
	void onOkValueChange(ValueChangeEvent<Boolean> event){
		if(event.getValue()){
			value = Integer.parseInt(okButton.getFormValue());
			ValueChangeEvent.fire(this, value);
		}
	}
	
	@UiHandler("goodButton")
	void onGoodButtonValueChange(ValueChangeEvent<Boolean> event){
		if(event.getValue()){
			value = Integer.parseInt(goodButton.getFormValue());
			ValueChangeEvent.fire(this, value);
		}
	}
	
	@UiHandler("remarkableButton")
	void onRemarkableValueChange(ValueChangeEvent<Boolean> event){
		if(event.getValue()){
			value = Integer.parseInt(remarkableButton.getFormValue());
			ValueChangeEvent.fire(this, value);
		}
	}
	
	

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Integer> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}

	@Override
	public Integer getValue() {
		return value;
	}

	@Override
	public void setValue(Integer value) {
		this.value = value;
		reallyBadButton.setValue(value == 0);
		badButton.setValue(value == 1);
		okButton.setValue(value == 2);
		goodButton.setValue(value == 3);
		remarkableButton.setValue(value == 4);
	}

	@Override
	public void setValue(Integer value, boolean fireEvents) {
		setValue(value);
		if(fireEvents){
			ValueChangeEvent.fire(this, value);
		}
	}

}
