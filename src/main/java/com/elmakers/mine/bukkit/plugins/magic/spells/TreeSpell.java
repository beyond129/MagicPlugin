package com.elmakers.mine.bukkit.plugins.magic.spells;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;

import com.elmakers.mine.bukkit.plugins.magic.Spell;
import com.elmakers.mine.bukkit.utilities.borrowed.ConfigurationNode;

public class TreeSpell extends Spell
{
	private TreeType defaultTreeType = null;
	private boolean requireSapling = false;
	
	@Override
	public boolean onCast(ConfigurationNode parameters) 
	{
		Block target = getTargetBlock();

		if (target == null)
		{
			castMessage(player, "No target");
			return false;
		}
		
		if (requireSapling && target.getType() != Material.SAPLING)
		{
			castMessage(player, "Plant a sapling first");
			return false;
		}

		Location treeLoc = new Location(player.getWorld(), target.getX(), target.getY() + 1, target.getZ(), 0, 0);
		TreeType treeType = defaultTreeType;
		String typeString = parameters.getString("type", "");
        treeType = parseTreeString(typeString, defaultTreeType);
		
		if (treeType == null)
		{
		    treeType = TreeType.values()[(int)(Math.random() * TreeType.values().length)];
		}
		boolean result = player.getWorld().generateTree(treeLoc, treeType);
		
		if (result)
		{
			castMessage(player, "You grow a " + getTreeName(treeType) + " tree");
		}
		else
		{
			castMessage(player, "Your tree didn't grow");
		}
		return result;
	}
	
	public String getTreeName(TreeType treeType)
	{
		return treeType.name().toLowerCase();
	}
	
	public static TreeType parseTreeString(String s, TreeType defaultTreeType)
	{
		if (s.equalsIgnoreCase("big")) return TreeType.BIG_TREE;
		if (s.equalsIgnoreCase("tall")) return TreeType.TALL_REDWOOD;
		
		TreeType tree = defaultTreeType;
		for (TreeType t : TreeType.values())
		{
			if (t.name().equalsIgnoreCase(s))
			{
				tree = t;
			}
		}
		return tree;
	}

	@Override
	public void onLoad(ConfigurationNode properties)  
	{
		requireSapling = properties.getBoolean("require_sapling", requireSapling);
	}
}
