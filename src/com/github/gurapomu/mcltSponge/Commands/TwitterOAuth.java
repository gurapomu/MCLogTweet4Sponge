package com.github.gurapomu.mcltSponge.Commands;

import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import com.github.gurapomu.mcltSponge.twitter.Authorization;

public class TwitterOAuth implements CommandExecutor {
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException{
		Authorization.mcltOAuth();
		return CommandResult.success();
	}
}