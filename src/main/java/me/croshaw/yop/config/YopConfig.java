package me.croshaw.yop.config;

import draylar.omegaconfig.api.Comment;
import draylar.omegaconfig.api.Config;
import me.croshaw.yop.Yop;
import org.jetbrains.annotations.Nullable;

public class YopConfig implements Config {
    @Comment(value = "Maximum number of backpack slots")
    public int InvMaxBackpack = 27;
    @Comment(value = "Minimum number of backpack slots")
    public int InvMinBackpack = 6;
    @Comment(value = "Maximum number of belt slots")
    public int InvMaxBelt = 18;
    @Comment(value = "Minimum number of belt slots")
    public int InvMinBelt = 3;
    @Comment(value = "The cost of a belt from a merchant")
    public int CostBelt = 15;
    @Comment(value = "The cost of a backpack from a merchant")
    public int CostBackpack = 30;

    @Override
    public String getName() {
        return Yop.MOD_ID+"-config";
    }
    @Override
    public @Nullable String getModid() {
        return Yop.MOD_ID;
    }

    @Override
    public String getDirectory() {
        return "croshaw-mods";
    }
}
