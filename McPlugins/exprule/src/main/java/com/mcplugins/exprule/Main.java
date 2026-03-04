package com.mcplugins.exprule;

import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // 注册监听器
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("经验掉落修正插件已加载");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        
        // 如果是开启了死亡不掉落
        Boolean keepInv = player.getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY);
        if (keepInv != null && keepInv) {
            // 计算原版掉落经验（等级 * 7，最大上限 100）
            int droppedExp = Math.min(player.getLevel() * 7, 100);
            
            event.setDroppedExp(droppedExp); // 掉出经验球
            event.setKeepLevel(false);       // 不保留等级
            event.setNewLevel(0);            // 玩家复活后等级为0
            event.setNewExp(0);              // 玩家复活后经验条为空
        }
    }
}