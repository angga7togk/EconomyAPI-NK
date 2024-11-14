package angga7togk.economyapi.commands;

import angga7togk.economyapi.EconomyAPI;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

import java.util.Map;

public class SetCommand extends Command {
    public EconomyAPI plugin;

    public SetCommand(EconomyAPI plugin) {
        super("setmoney", "set money player", "/setmoney <player> <money>");
        this.setPermission("setmoney.command");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        String prefix = EconomyAPI.prefix;
        if (this.testPermission(sender)) {
            if (args.length < 2) {
                sender.sendMessage(prefix + this.getUsage());
                return false;
            }
            String targetName = args[0];
            if (!this.plugin.playerExists(targetName)) {
                sender.sendMessage(prefix + "§cPlayer not found.");
                return false;
            }
            int money;
            try {
                money = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage(prefix + "§cMoney must be a number.");
                return false;
            }

            boolean status = this.plugin.setMoney(targetName, money);
            if (!status) {
                sender.sendMessage(prefix + "§cFailed!");
                return false;
            }
            sender.sendMessage(prefix + "§aSuccessfully set {player}'s money to {money}".replace("{player}", targetName).replace("{money}", String.valueOf(money)));
        }
            return true;
        }
}