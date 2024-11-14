package angga7togk.economyapi.commands;

import angga7togk.economyapi.EconomyAPI;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class GiveCommand extends Command {
    public EconomyAPI plugin;

    public GiveCommand(EconomyAPI plugin) {
        super("givemoney", "give money player", "/givemoney <player> <money>");
        this.setPermission("givemoney.command");
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

            boolean status = this.plugin.addMoney(targetName, money);
            if (!status) {
                sender.sendMessage(prefix + "§cFailed!");
                return false;
            }
            sender.sendMessage(prefix + "§aSuccessfully gave money to {player} in the amount of {money}".replace("{player}", targetName).replace("{money}", String.valueOf(money)));

            if (Server.getInstance().getPlayer(targetName) != null) {
                Player target = Server.getInstance().getPlayer(targetName);
                target.sendMessage(prefix + "§a{player} gave you money in the amount of {money}".replace("{player}", sender.getName()).replace("{money}", String.valueOf(money)));
            }
        }
        return true;
    }
}