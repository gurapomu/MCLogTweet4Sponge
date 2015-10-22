package com.github.gurapomu.mcltSponge.eventListener;

import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import com.github.gurapomu.mcltSponge.twitter.Authorization;

public class PlayerJoinEventListener implements EventListener<ClientConnectionEvent.Join> {
	@Override
	public void handle(ClientConnectionEvent.Join event) throws Exception {
		String str = event.getTargetEntity().getName() + " has logged in.";
		Authorization.tweetString(str);
	}
}
