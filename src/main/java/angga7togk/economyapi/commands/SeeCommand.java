package angga7togk.economyapi.commands;

import angga7togk.economyapi.EconomyAPI;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

import java.util.Map;

public class SeeCommand extends Command {
    public EconomyAPI plugin;

    public SeeCommand(EconomyAPI plugin) {
        super("seemoney", "see money player", "/seemoney <player>");
        this.setPermission("seemoney.command");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        String prefix = EconomyAPI.prefix;
        if (this.testPermission(sender)) {
            if (args.length < 1) {
                sender.sendMessage(prefix + this.getUsage());
                return false;
            }
            String targetName = args[0];
            if (!this.plugin.playerExists(targetName)) {
                sender.sendMessage(prefix + "§cPlayer not found.");
                return false;
            }
            sender.sendMessage(prefix + "§6Name, §r" + targetName);
            sender.sendMessage(prefix + "§6Money, §r" + this.plugin.formatMoney(this.plugin.myMoney(targetName)));
        }
        return true;
    }
}