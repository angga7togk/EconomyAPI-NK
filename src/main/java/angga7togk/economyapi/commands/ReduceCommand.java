package angga7togk.economyapi.commands;

import angga7togk.economyapi.EconomyAPI;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

import java.util.Map;

public class ReduceCommand extends Command {
    public EconomyAPI plugin;

    public ReduceCommand(EconomyAPI plugin) {
        super("reducemoney", "reduce money player", "/reducemoney <player> <money>");
        this.setPermission("reducemoney.command");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        String prefix = EconomyAPI.prefix;

        if (testPermission(sender)) {
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

            boolean status = this.plugin.reduceMoney(targetName, money);
            if (!status) {
                sender.sendMessage(prefix + "§cFailed!");
                return false;
            }
            sender.sendMessage(prefix + "§aSuccessfully gave money to {player} in the amount of {money}".replace("{player}", targetName).replace("{money}", String.valueOf(money)));
        }
        return true;
    }
}