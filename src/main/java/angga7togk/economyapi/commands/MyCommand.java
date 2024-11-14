package angga7togk.economyapi.commands;

import angga7togk.economyapi.EconomyAPI;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class MyCommand extends Command {
    public EconomyAPI plugin;
    public MyCommand(EconomyAPI plugin) {
        super("mymoney",  "see my money");
        this.setPermission("mymoney.command");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(sender instanceof Player player){
            if(testPermission(sender)){
                if(this.plugin.playerExists(player)){
                    player.sendMessage(EconomyAPI.prefix + "§6Money, §r" + this.plugin.formatMoney(this.plugin.myMoney(player)));
                }
            }
        }
        return true;
    }
}