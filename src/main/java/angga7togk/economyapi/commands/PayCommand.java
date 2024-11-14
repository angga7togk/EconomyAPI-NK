package angga7togk.economyapi.commands;

import angga7togk.economyapi.EconomyAPI;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

import java.util.Map;

public class PayCommand extends Command {

    public EconomyAPI plugin;

    public PayCommand(EconomyAPI plugin) {
        super("paymoney", "pay money to player", "/paymoney <player> <money>", new String[]{"pay"});
        this.setPermission("paymoney.command");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player player) {
            String prefix = EconomyAPI.prefix;
            if (this.testPermission(sender)) {
                if (args.length < 2) {
                    player.sendMessage(prefix + this.getUsage());
                    return false;
                }

                String targetName = args[0];
                if (!this.plugin.playerExists(targetName)) {
                    player.sendMessage(prefix + "§cPlayer not found.");
                    return false;
                }
                int myMoney = this.plugin.myMoney(player);
                int money;
                try {
                    money = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage(prefix + "§cMoney must be a number.");
                    return false;
                }

                if (myMoney < money) {
                    player.sendMessage(prefix + "§cSorry, your money is not enough.");
                    return false;
                }

                boolean addMoney = this.plugin.addMoney(targetName, money);

                if (!addMoney) {
                    boolean reduceMoney = this.plugin.reduceMoney(player, money);
                    if (!reduceMoney) {
                        player.sendMessage(prefix + "§cFailed!");
                        return false;
                    }
                }

                player.sendMessage(prefix + "§aSuccessfully gave money to {player} in the amount of {money}".replace("{player}", targetName).replace("{money}", String.valueOf(money)));

                if (Server.getInstance().getPlayer(targetName) != null) {
                    Player target = Server.getInstance().getPlayer(targetName);
                    target.sendMessage(prefix + "§a{player} gave you money in the amount of {money}".replace("{player}", player.getName()).replace("{money}", String.valueOf(money)));
                }
            }
        }
        return true;
    }
}