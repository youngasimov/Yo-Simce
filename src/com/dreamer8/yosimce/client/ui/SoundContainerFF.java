package com.dreamer8.yosimce.client.ui;

import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.Audio;

public class SoundContainerFF extends SoundContainer {

	
	public SoundContainerFF(){};
	
	@Override
	public Audio getNotificationSound() {
		Audio notificationSound = Audio.createIfSupported();
		if(notificationSound!=null){
	        notificationSound.setAutoplay(false);
	        notificationSound.setControls(false);
	        notificationSound.setLoop(false);
	        notificationSound.setPreload(MediaElement.PRELOAD_AUTO);
	        notificationSound.setSrc("images/notifo.ogg");
	        notificationSound.setVolume(1.0);
	        notificationSound.setMuted(false);
	        notificationSound.load();
	        notificationSound.setVisible(false);
		}
		return notificationSound;
	}

	@Override
	public Audio getErrorSound() {
		Audio errorSound = Audio.createIfSupported();
        if(errorSound != null){
	        errorSound.setAutoplay(false);
	        errorSound.setControls(false);
	        errorSound.setLoop(false);
	        errorSound.setPreload(MediaElement.PRELOAD_AUTO);
	        errorSound.setSrc("images/error.ogg");
	        errorSound.setVolume(1.0);
	        errorSound.setMuted(false);
	        errorSound.load();
	        errorSound.setVisible(false);
        }
        return errorSound;
	}

}
